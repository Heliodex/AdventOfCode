package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class day9 {
	final String path = "./Inputs/9";

	HashSet<ArrayList<String>> getPermutations(final HashSet<String> list) {
		final var result = new HashSet<ArrayList<String>>();
		if (list.size() == 0) {
			result.add(new ArrayList<>());
			return result;
		}

		for (final var s : list) {
			final var listExcl = new HashSet<>(list);
			listExcl.remove(s);

			final var perms = getPermutations(listExcl);
			for (final var p : perms)
				p.add(s);

			result.addAll(perms);
		}
		return result;
	}

	ArrayList<Integer> totalDistances(final String input) {
		final var cities = new HashSet<String>();
		final var distances = new HashMap<Set<String>, Integer>();
		for (final var line : input.split("\n")) {
			final var parts = line.split(" ");
			final var set = Set.of(parts[0], parts[2]);
			final var dist = Integer.parseInt(parts[4]);
			cities.addAll(set);
			distances.put(set, dist);
		}

		// var minDist = Integer.MAX_VALUE;
		final var totalDistances = new ArrayList<Integer>();
		for (final var p : getPermutations(cities)) {
			var totalDist = 0;
			for (var i = 0; i < p.size() - 1; i++) {
				final var cs = p.subList(i, i + 2);
				totalDist += distances.get(Set.copyOf(cs));
			}
			// minDist = Math.min(minDist, totalDist);
			totalDistances.add(totalDist);
		}
		return totalDistances;
	}

	int part1(final String input) {
		final var totals = totalDistances(input);
		return totals.stream().min(Integer::compare).orElse(0);
	}

	int part2(final String input) {
		final var totals = totalDistances(input);
		return totals.stream().max(Integer::compare).orElse(0);
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(part1(content));
		IO.println(part2(content));
	}
}
