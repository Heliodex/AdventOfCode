package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class day10 {
	final String path = "./Inputs/10";

	String iterate(final String input) {
		// split string whenever the character changes from the previous one
		final var split = input.split("(?<=(.))(?!\\1)");
		var result = new StringBuilder();
		for (var s : split)
			result.append(s.length()).append(s.charAt(0));
		return result.toString();
	}

	int part1(final String input) {
		var n = input;
		for (var i = 0; i < 40; i++)
			n = iterate(n);
		return n.length();
	}

	int part2(final String input) {
		var n = input;
		for (var i = 0; i < 50; i++)
			n = iterate(n);
		return n.length();
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
