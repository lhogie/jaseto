package jaseto;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import toools.io.JavaResource;
import toools.io.file.Directory;
import toools.reflect.Clazz;
import toools.text.TextUtilities;

public class SubstitutionObject
{
	public final String packagename, classname;

	public static class Field
	{
		Class clazz;
		String name;
		Object value;
	}

	public final Map<String, Object> fields = new HashMap<>();

	public SubstitutionObject(String classname)
	{
		int p = classname.lastIndexOf('.');

		if (p >= 0)
		{
			this.packagename = classname.substring(0, p);
			this.classname = classname.substring(p + 1);
		}
		else
		{
			this.packagename = null;
			this.classname = classname;
		}
	}

	public void set(String name, Object value)
	{
		Field f = new Field();
		f.clazz = value.getClass();

		System.out.println(Clazz.class_primitives);
		if (Clazz.class_primitives.containsKey(f.clazz))
		{
			f.clazz = Clazz.class_primitives.get(f.clazz);
		}

		f.name = name;
	}

	@Override
	public String toString()
	{
		return "SubstitutionObject [classname=" + classname + ", fields=" + fields + "]";
	}

	public String toJava()
	{
			String s = new String(new JavaResource(getClass(), "template.java").getByteArray());
			s = s.replaceFirst("package o2s;", packagename == null ? "" : packagename);
			s = s.replaceFirst("template", classname);
			
			String fs = "";
			
			for (Entry<String, Object> f : fields.entrySet())
			{
				Object v = f.getValue();
				Class fieldclass = v == null ? Object.class : v.getClass();

				if (Clazz.class_primitives.containsKey(fieldclass))
				{
					fieldclass = Clazz.class_primitives.get(fieldclass);
				}

				fs += "\n\tprivate " + TextUtilities.getNiceClassName(fieldclass) + " "
						+ f.getKey() + ";";
			}

			s = s.replaceFirst("// FIELDS", fs);
			return s;

	}

	public Class toClass()
	{
		String cn = packagename == null ? classname : packagename + "." + classname;
		return Clazz.loadClassfile(cn, Clazz.compile(classname, toJava(), Directory.getSystemTempDirectory()));
	}

	public Object toRealObject()
	{
		Object o = Clazz.makeInstance(toClass());

		for (Entry<String, Object> e : fields.entrySet())
		{
			Clazz.setFieldValue(o, e.getKey(), e.getValue());
		}

		return o;
	}
}
