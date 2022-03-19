package jaseto;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class Registry
{
	private final Int2ObjectMap m = new Int2ObjectOpenHashMap();

	public boolean contains(Object o)
	{
		return m.containsKey(id(o));
	}

	public void add(Object o)
	{
		m.put(id(o), o);
	}

	public Object get(int id)
	{
		return m.get(id);
	}

	private Int2IntOpenHashMap translation = new Int2IntOpenHashMap();

	public int id(Object o)
	{
		int ihc = System.identityHashCode(o);

		if (translation.containsKey(ihc))
		{
			return translation.get(ihc);
		}
		else
		{
			int id = translation.size();
			translation.put(ihc, id);
			return id;
		}
	}
}
