package jaseto;

import java.util.Arrays;

public class ThrowableNode extends ObjectNode {

	public static final String STACK_TRACE = "stack_trace";

	public ThrowableNode(Object o, Jaseto sc) {
		super(o, sc);
		var err = (Throwable) o;

		if (err.getMessage() != null) {
			setProperty("message", sc.toNode(err.getMessage()));
		}

		setProperty(STACK_TRACE, sc.toNode(
				Arrays.stream(err.getStackTrace()).map(e -> e.getClassName() + ":" + e.getLineNumber()).toArray()));
	}
}
