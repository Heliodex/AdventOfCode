package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;

class pos {
	int x = 0;
	int y = 0;

	pos(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	pos move(final char c) {
		return switch (c) {
			case '^' -> new pos(x, ++y);
			case 'v' -> new pos(x, --y);
			case '>' -> new pos(++x, y);
			case '<' -> new pos(--x, y);
			default -> new pos(0, 0);
		};
	}

	pos inverted() {
		return new pos(-x, -y);
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}

public class day3 {
	final String path = "./Inputs/3";

	int part1(final String input) {
		final var chars = input.toCharArray();
		final var pos1 = new pos(0, 0);
		final var set = new HashSet<String>();

		for (final var c : chars) {
			pos1.move(c);
			set.add(pos1.toString());
		}
		return set.size();
	}

	int part2(final String input) {
		final var chars = input.toCharArray();
		final var poss = new pos[] { new pos(0, 0), new pos(0, 0) };
		final var set = new HashSet<String>();

		for (int i = 0; i < chars.length; i++) {
			var c = chars[i];
			set.add(poss[i % 2].move(c).toString());
		}

		return set.size();
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
