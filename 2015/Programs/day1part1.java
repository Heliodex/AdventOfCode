package Programs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class day1part1 {
	final String path = "./Inputs/1";

	void main() throws FileNotFoundException {
		var reader = new Scanner(new File(path));

		var floor = 0;
		for (final var c : reader.nextLine().toCharArray()) {
			floor += switch (c) {
				case '(' -> 1;
				case ')' -> -1;
				default -> 0;
			};
		}

		IO.println(floor);
		reader.close();
	}
}
