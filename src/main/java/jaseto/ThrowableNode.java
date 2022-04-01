package jaseto;

import java.util.ArrayList;

public class ThrowableNode extends ObjectNode {

	public static class Entry {
		String classname;
		int lineNumber;
	}
	
	public ThrowableNode(Object o, String name, Jaseto sc) {
		super(o, name, sc);
		var err = (Throwable) o;
		putKey("message", new StringNode(err.getMessage(), "message", sc));
		var st = new ArrayList<Entry>();

		for (var el : err.getStackTrace()) {
			var e = new Entry();
			e.classname =el.getClassName();
			e.lineNumber = el.getLineNumber();
			st.add(e);
		}

		putKey("stack_trace", sc.toNode(st, "stack_trace", sc.lookupNodeClass(st.getClass())));
	}
}
