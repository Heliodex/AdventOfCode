package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class day8 {
	final String path = "./Inputs/8";

	int part1(final String input) {
		final var lines = input.split("\n");
		var count = 0;
		for (final var line : lines) {
			final var ogLength = line.length();
			final var uq = line.substring(1, ogLength - 1);
			final var chars = uq.toCharArray();

			var newLength = 0;
			for (var i = 0; i < chars.length; i++) {
				final var bs = chars[i] == '\\';
				if (bs && chars[i + 1] == 'x')
					i += 3;
				else if (bs && (chars[i + 1] == '\\' || chars[i + 1] == '\"'))
					i++;
				newLength++;
			}

			count += ogLength - newLength;
		}
		return count;
	}

	int part2(final String input) {
		final var lines = input.split("\n");
		var count = 0;
		for (final var line : lines) {
			final var ogLength = line.length();
			final var chars = line.toCharArray();

			var newLength = 2; // account for the new quotes
			for (var i = 0; i < chars.length; i++)
				newLength += chars[i] == '\"' || chars[i] == '\\' ? 2 : 1;

			count += newLength - ogLength;
		}
		return count;
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
