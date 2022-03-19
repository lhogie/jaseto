package jaseto;

import java.util.ArrayList;
import java.util.Arrays;

public class DemoType
{
	boolean b = true;
	char[] chararray = "alors?".toCharArray();
	int[] intarray = new int[] {0, 1, 2, 3, 4, 5};
	Object[] array = new Object[] { "Luc", true };
	boolean[] arrayBoolean = new boolean[] { true, false };
	String[] arrayString = new String[] { "Luc", "Elisa" };

	byte bb = 0x7;
	char c = 'c';
	float f = 0.1f;
	double d = Math.PI;

	short s = 4;
	int number = - 1;
	long l = Long.MAX_VALUE;
	Object testNull = null;
	Object[] emptyArray = new Object[0];
	Object cycle = this;
	String name = "coucou";
	ArrayList testType = new ArrayList(Arrays.asList("ben", "coucou", "alors?"));

	
	@Override
	public String toString()
	{
		return "DemoType [b=" + b + ", chararray=" + Arrays.toString(chararray)
				+ ", intarray=" + Arrays.toString(intarray) + ", array="
				+ Arrays.toString(array) + ", arrayBoolean="
				+ Arrays.toString(arrayBoolean) + ", arrayString="
				+ Arrays.toString(arrayString) + ", bb=" + bb + ", c=" + c + ", f=" + f
				+ ", d=" + d + ", s=" + s + ", number=" + number + ", l=" + l
				+ ", testNull=" + testNull + ", emptyArray=" + Arrays.toString(emptyArray)
				+ ", name=" + name + ", testType=" + testType + "]";
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return hashCode() == obj.hashCode();
	}
}