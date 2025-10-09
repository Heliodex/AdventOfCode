package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

class GateMap extends HashMap<String, Gate> {}

interface Gate {
	int get(GateMap wires);
}

class GateCache implements Gate {
	final Gate g;
	Integer out;

	GateCache(Gate g) {
		this.g = g;
	}

	public int get(GateMap wires) {
		if (out == null)
			out = g.get(wires);
		return out;
	}
}

class ConstGate implements Gate {
	final int v;

	ConstGate(int v) {
		this.v = v;
	}

	public int get(GateMap wires) {
		return v;
	}
}

class WireGate implements Gate {
	final String a;

	WireGate(String a) {
		this.a = a;
	}

	public int get(GateMap wires) {
		return wires.get(a).get(wires);
	}
}

class AndGateSS implements Gate {
	final String a, b;

	AndGateSS(String a, String b) {
		this.a = a;
		this.b = b;
	}

	public int get(GateMap wires) {
		return wires.get(a).get(wires) & wires.get(b).get(wires);
	}
}

class AndGateIS implements Gate {
	final int a;
	final String b;

	AndGateIS(int a, String b) {
		this.a = a;
		this.b = b;
	}

	public int get(GateMap wires) {
		return a & wires.get(b).get(wires);
	}
}

class OrGate implements Gate {
	final String a, b;

	OrGate(String a, String b) {
		this.a = a;
		this.b = b;
	}

	public int get(GateMap wires) {
		return wires.get(a).get(wires) | wires.get(b).get(wires);
	}
}

class NotGate implements Gate {
	final String a;

	NotGate(String a) {
		this.a = a;
	}

	public int get(GateMap wires) {
		return ~wires.get(a).get(wires) & 0xFFFF;
	}
}

class LShiftGate implements Gate {
	final String a;
	final int v;

	LShiftGate(String a, int v) {
		this.a = a;
		this.v = v;
	}

	public int get(GateMap wires) {
		return (wires.get(a).get(wires) << v) & 0xFFFF;
	}
}

class RShiftGate implements Gate {
	final String a;
	final int v;

	RShiftGate(String a, int v) {
		this.a = a;
		this.v = v;
	}

	public int get(GateMap wires) {
		return wires.get(a).get(wires) >> v;
	}
}

public class day7 {
	final String path = "./Inputs/7";

	Gate parseGate(String expr) {
		final var parts = expr.split(" ");
		final var a = parts[0];
		switch (parts.length) {
			case 1:
				// constant or wire
				if (a.matches("\\d+"))
					return new ConstGate(Integer.parseInt(a));

				// wire
				return new WireGate(a);
			case 2:
				// NOT
				return new NotGate(parts[1]);
			case 3:
				// AND, OR, LSHIFT, RSHIFT
				final var b = parts[2];
				switch (parts[1]) {
					case "AND":
						return a.matches("\\d+")
								? new AndGateIS(Integer.parseInt(a), b)
								: new AndGateSS(a, b);
					case "OR":
						return new OrGate(a, b);
					case "LSHIFT":
						return new LShiftGate(a, Integer.parseInt(b));
					case "RSHIFT":
						return new RShiftGate(a, Integer.parseInt(b));
				}
		}
		throw new RuntimeException("Failed to parse gate: " + expr);
	}

	GateMap newWires(String input) {
		final var wires = new GateMap();
		final var lines = input.split("\n");
		for (final var line : lines) {
			final var parts = line.split(" -> ");
			if (parts[0] == "lx") {
				IO.println(parts[1]);
			}
			final var g = parseGate(parts[0]);
			if (g == null)
				throw new RuntimeException("Failed to parse gate2: " + parts[0]);

			wires.put(parts[1], new GateCache(g));
		}
		return wires;
	}

	int part1(String input) {
		final var wires = newWires(input);
		return wires.get("a").get(wires);
	}

	int part2(String input) {
		final var wires = newWires(input);
		wires.put("b", new GateCache(new ConstGate(part1(input))));
		return wires.get("a").get(wires);
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
