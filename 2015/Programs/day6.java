package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

class day6 {
	final String path = "./Inputs/6";

	int part1(final String input) {
		final var lights = new boolean[1000][1000];
		final var lines = input.split("\n");

		for (final var line : lines) {
			final var parts = line.split(" ");
			final var coords1 = Arrays.asList(parts[parts.length - 3].split(",")).stream().mapToInt(Integer::parseInt)
					.toArray();
			final var coords2 = Arrays.asList(parts[parts.length - 1].split(",")).stream().mapToInt(Integer::parseInt)
					.toArray();

			for (var i = coords1[0]; i <= coords2[0]; i++)
				for (var j = coords1[1]; j <= coords2[1]; j++)
					lights[i][j] = parts.length == 4 ? !lights[i][j] : parts[1].equals("on");
		}

		var count = 0;
		for (var i = 0; i < lights.length; i++)
			for (var j = 0; j < lights[i].length; j++)
				if (lights[i][j])
					count++;
		return count;
	}

	int part2(final String input) {
		final var lights = new int[1000][1000];
		final var lines = input.split("\n");

		for (final var line : lines) {
			final var parts = line.split(" ");
			final var coords1 = Arrays.asList(parts[parts.length - 3].split(",")).stream().mapToInt(Integer::parseInt)
					.toArray();
			final var coords2 = Arrays.asList(parts[parts.length - 1].split(",")).stream().mapToInt(Integer::parseInt)
					.toArray();

			for (var i = coords1[0]; i <= coords2[0]; i++)
				for (var j = coords1[1]; j <= coords2[1]; j++)
					if (parts.length == 4)
						lights[i][j] += 2;
					else if (parts[1].equals("on"))
						lights[i][j] += 1;
					else
						lights[i][j] = Math.max(0, lights[i][j] - 1);
		}

		var count = 0;
		for (var i = 0; i < lights.length; i++)
			for (var j = 0; j < lights[i].length; j++)
				count += lights[i][j];
		return count;
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
