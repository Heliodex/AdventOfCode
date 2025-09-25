package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class day1 {
	final String path = "./Inputs/1";

	int part1(String input) {
		var floor = 0;
		for (final char c : input.toCharArray())
			floor += switch (c) {
				case '(' -> 1;
				case ')' -> -1;
				default -> 0;
			};
		return floor;
	}

	int part2(String input) {
		var floor = 0;
		var i = 0;
		for (; i < input.length(); i++) {
			floor += switch (input.charAt(i)) {
				case '(' -> 1;
				case ')' -> -1;
				default -> 0;
			};
			if (floor == -1)
				return i + 1;
		}
		return i;
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
