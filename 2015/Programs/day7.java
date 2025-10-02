package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

interface Gate {
	int get();
}

class ConstGate implements Gate {
	int v;

	ConstGate(int v) {
		this.v = v;
	}

	public int get() {
		return v;
	}
}

class AndGate implements Gate {
	Gate a, b;

	AndGate(Gate a, Gate b) {
		this.a = a;
		this.b = b;
	}

	public int get() {
		return a.get() & b.get();
	}
}

class OrGate implements Gate {
	Gate a, b;

	OrGate(Gate a, Gate b) {
		this.a = a;
		this.b = b;
	}

	public int get() {
		return a.get() | b.get();
	}
}

class NotGate implements Gate {
	Gate a;

	NotGate(Gate a) {
		this.a = a;
	}

	public int get() {
		return ~a.get() & 0xFFFF;
	}
}

class LShiftGate implements Gate {
	Gate a;
	int v;

	LShiftGate(Gate a, int v) {
		this.a = a;
		this.v = v;
	}

	public int get() {
		return (a.get() << v) & 0xFFFF;
	}
}

class RShiftGate implements Gate {
	Gate a;
	int v;

	RShiftGate(Gate a, int v) {
		this.a = a;
		this.v = v;
	}

	public int get() {
		return (a.get() >> v) & 0xFFFF;
	}
}

public class day7 {
	final String path = "./Inputs/7";

	Gate parseGate(String expr, HashMap<String, Gate> wires) {
		final var parts = expr.split(" ");
		switch (parts.length) {
			case 1:
				// constant or wire
				if (parts[0].matches("\\d+"))
					return new ConstGate(Integer.parseInt(parts[0]));

				// wire
				return wires.get(parts[0]);
			case 2:
				// NOT
				return new NotGate(wires.get(parts[1]));
			case 3:
				// AND, OR, LSHIFT, RSHIFT
				switch (parts[1]) {
					case "AND":
						return new AndGate(wires.get(parts[0]), wires.get(parts[2]));
					case "OR":
						return new OrGate(wires.get(parts[0]), wires.get(parts[2]));
					case "LSHIFT":
						return new LShiftGate(wires.get(parts[0]), Integer.parseInt(parts[2]));
					case "RSHIFT":
						return new RShiftGate(wires.get(parts[0]), Integer.parseInt(parts[2]));
				}
		}

		return null;
	}

	int part1(String input) {
		final var wires = new HashMap<String, Gate>();
		// return 0;

		final var lines = input.split("\n");
		for (final var line : lines) {
			final var parts = line.split(" -> ");
			wires.put(parts[1], parseGate(parts[0], wires));
		}

		return wires.get("a").get();
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
