package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class day5 {

	final String path = "./Inputs/5";

	boolean isNice1(final String line) {
		final String[] forbidden = { "ab", "cd", "pq", "xy" };
		final var vowels = "aeiou";

		if (line.chars().filter(c -> vowels.indexOf(c) >= 0).count() < 3)
			return false;

		var hasDouble = false;
		for (var i = 1; i < line.length(); i++)
			if (line.charAt(i) == line.charAt(i - 1)) {
				hasDouble = true;
				break;
			}
		if (!hasDouble)
			return false;

		for (var f : forbidden)
			if (line.contains(f))
				return false;

		return true;
	}

	long part1(final String input) {
		final var lines = input.split("\n");
		return Arrays.stream(lines).filter(this::isNice1).count();
	}

	boolean isNice2(final String line) {
		final var windows = new ArrayList<String>();
		final var ll = line.length() - 1;

		var hasPair = false;
		for (var i = 0; i < ll; i++) {
			// finallyy
			final var w = windows.subList(0, Math.max(0, windows.size() - 1));

			final var l = line.substring(i, i + 2);
			if (w.contains(l)) {
				hasPair = true;
				break;
			}
			windows.add(l);
		}

		if (!hasPair)
			return false;

		for (var i = 0; i < ll - 1; i++)
			if (line.charAt(i) == line.charAt(i + 2))
				return true;

		return false;
	}

	long part2(String input) {
		final var lines = input.split("\n");
		return Arrays.stream(lines).filter(this::isNice2).count();
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
