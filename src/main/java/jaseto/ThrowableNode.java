package jaseto;

import java.util.ArrayList;

public class ThrowableNode extends ObjectNode {

	public static class Entry {
		String classname;
		int lineNumber;
	}
	
	public ThrowableNode(Object o, Jaseto sc) {
		super(o, sc);
		var err = (Throwable) o;
		setProperty("message", new Litteral(err.getMessage(), "message", sc));
		var st = new ArrayList<Entry>();

		for (var el : err.getStackTrace()) {
			var e = new Entry();
			e.classname =el.getClassName();
			e.lineNumber = el.getLineNumber();
			st.add(e);
		}

		setProperty("stack_trace", sc.toNode(st));
	}
}
