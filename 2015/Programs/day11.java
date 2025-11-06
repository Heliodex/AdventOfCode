package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class day11 {
	final String path = "./Inputs/11";

	String increment(final String input) {
		final var sb = new StringBuilder(input);
		for (int i = sb.length() - 1; i >= 0; i--) {
			if (sb.charAt(i) != 'z') {
				sb.setCharAt(i, (char) (sb.charAt(i) + 1));
				break;
			}
			sb.setCharAt(i, 'a');
		}
		return sb.toString();
	}

	boolean req1(final String input) {
		int count = 0;

		for (int i = 1; i < input.length(); i++) {
			if (input.charAt(i) != input.charAt(i - 1) + 1) {
				count = 0;
				continue;
			}

			if (++count >= 2)
				return true;
		}
		return false;
	}

	boolean req2(final String input) {
		final char[] disallowed = { 'i', 'o', 'l' };
		for (final char c : disallowed)
			if (input.indexOf(c) != -1)
				return false;
		return true;
	}

	boolean containsPair(final String input) {
		for (int i = 1; i < input.length(); i++)
			if (input.charAt(i) == input.charAt(i - 1))
				return true;
		return false;
	}

	boolean req3(final String input) {
		for (int split = 2; split < input.length() - 2; split++) {
			final var part1 = input.substring(0, split);
			final var part2 = input.substring(split);

			if (containsPair(part1) && containsPair(part2))
				return true;
		}
		return false;
	}

	String next(final String input) {
		// return increment(input);
		var candidate = increment(input);
		while (true) {
			if (req1(candidate) && req2(candidate) && req3(candidate))
				return candidate;
			candidate = increment(candidate);
		}
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		final var p1 = next(content);
		IO.println(p1);
		IO.println(next(p1));
	}
}
