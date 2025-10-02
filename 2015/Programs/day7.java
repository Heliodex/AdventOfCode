package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

interface Gate {
	int get(HashMap<String,Gate> wires);
}

class ConstGate implements Gate {
	int v;

	ConstGate(int v) {
		this.v = v;
	}

	public int get(HashMap<String, Gate> wires) {
		return v;
	}
}

class WireGate implements Gate {
	String a;

	WireGate(String a) {
		this.a = a;
	}

	public int get(HashMap<String,Gate> wires) {
		return wires.get(a).get(wires);
	}
}

class AndGate implements Gate {
	String a, b;

	AndGate(String a, String b) {
		this.a = a;
		this.b = b;
	}

	public int get(HashMap<String,Gate> wires) {
		return wires.get(a).get(wires) & wires.get(b).get(wires);
	}
}

class OrGate implements Gate {
	String a, b;

	OrGate(String a, String b) {
		this.a = a;
		this.b = b;
	}

	public int get(HashMap<String,Gate> wires) {
		return wires.get(a).get(wires) | wires.get(b).get(wires);
	}
}

class NotGate implements Gate {
	String a;

	NotGate(String a) {
		this.a = a;
	}

	public int get(HashMap<String,Gate> wires) {
		return ~wires.get(a).get(wires) & 0xFFFF;
	}
}

class LShiftGate implements Gate {
	String a;
	int v;

	LShiftGate(String a, int v) {
		this.a = a;
		this.v = v;
	}

	public int get(HashMap<String,Gate> wires) {
		return (wires.get(a).get(wires) << v) & 0xFFFF;
	}
}

class RShiftGate implements Gate {
	String a;
	int v;

	RShiftGate(String a, int v) {
		this.a = a;
		this.v = v;
	}

	public int get(HashMap<String,Gate> wires) {
		return (wires.get(a).get(wires) >> v) & 0xFFFF;
	}
}

public class day7 {
	final String path = "./Inputs/7";

	Gate parseGate(String expr) {
		final var parts = expr.split(" ");
		switch (parts.length) {
			case 1:
				// constant or wire
				if (parts[0].matches("\\d+"))
					return new ConstGate(Integer.parseInt(parts[0]));

				// wire
				return new WireGate(parts[0]);
			case 2:
				// NOT
				return new NotGate(parts[1]);
			case 3:
				// AND, OR, LSHIFT, RSHIFT
				switch (parts[1]) {
					case "AND":
						return new AndGate(parts[0], parts[2]);
					case "OR":
						return new OrGate(parts[0], parts[2]);
					case "LSHIFT":
						return new LShiftGate(parts[0], Integer.parseInt(parts[2]));
					case "RSHIFT":
						return new RShiftGate(parts[0], Integer.parseInt(parts[2]));
				}
		}

		throw new RuntimeException("Failed to parse gate: " + expr);
	}

	int part1(String input) {
		final var wires = new HashMap<String, Gate>();
		// return 0;

		final var lines = input.split("\n");
		for (final var line : lines) {
			final var parts = line.split(" -> ");
			if (parts[0] == "lx") {
				IO.println(parts[1]);
			}
			final var g = parseGate(parts[0]);
			if (g == null)
				throw new RuntimeException("Failed to parse gate2: " + parts[0]);
			wires.put(parts[1], g);
		}

		return wires.get("a").get(wires);
	}

	int part2(String input) {
		return 0;
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
