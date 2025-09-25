package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class day5 {

	final String path = "./Inputs/5";

	int part1(String input) {
		final var lines = input.split("\n");
		var total = 0;

		final String[] forbidden = { "ab", "cd", "pq", "xy" };
		final var vowels = "aeiou";

		outer: for (final var line : lines) {
			if (line.chars().filter(c -> vowels.indexOf(c) >= 0).count() < 3)
				continue;

			var hasDouble = false;
			for (int i = 1; i < line.length(); i++)
				if (line.charAt(i) == line.charAt(i - 1)) {
					hasDouble = true;
					break;
				}
			if (!hasDouble)
				continue;

			for (var f : forbidden)
				if (line.contains(f))
					continue outer;

			total++;
		}
		return total;
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
