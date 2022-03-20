package jaseto;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public abstract class MapNode extends ObjectNode {
	public final Map<String, Node> children = new HashMap<>();

	@Override
	public void fill(Object o, Registry registry) {
		super.fill(o, registry);
	}

	@Override
	public void toJSON(Writer w) throws IOException {
		w.write("{");
		super.toJSON(w);
		w.write(", ");

		var i = children.entrySet().iterator();
		
		while (i.hasNext())
		{
			var e = i.next();
			w.write("\"" + e.getKey() + "\": ");
			System.err.println(e.getKey());
			System.err.println(e.getValue().getClass());
			e.getValue().toJSON(w);
			
			if (i.hasNext()) {
				w.write(", ");
			}
		}

		w.write("}");
	}
}
