package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class day2 {
	final String path = "./Inputs/2";

	int part1(String input) {
		var lines = input.split("\n");
		var total = 0;
		for (var line : lines) {
			var sides = line.split("x");
			var l = Integer.parseInt(sides[0]);
			var w = Integer.parseInt(sides[1]);
			var h = Integer.parseInt(sides[2]);
			var sidenums = List.of(l * w, w * h, h * l);

			total += sidenums.stream().min(Integer::compare).orElse(0);
			total += sidenums.stream().mapToInt(i -> i).sum() * 2;
		}
		return total;
	}

	int part2(String input) {
		var lines = input.split("\n");
		var total = 0;
		for (var line : lines) {
			var sides = line.split("x");
			var l = Integer.parseInt(sides[0]);
			var w = Integer.parseInt(sides[1]);
			var h = Integer.parseInt(sides[2]);
			var perimiters = List.of(l + w, w + h, h + l);

			total += l * w * h;
			total += perimiters.stream().min(Integer::compare).orElse(0) * 2;
		}
		return total;
	}

	void main() throws IOException {
		var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
