package Programs;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class day4 {
	final String path = "./Inputs/4";

	int find(String input, int zeros) throws NoSuchAlgorithmException {
		final var md = MessageDigest.getInstance("MD5");
		for (var num = 0;; num++) {
			md.update((input + num).getBytes());

			if (new BigInteger(1, md.digest()).bitLength() <= (32 - zeros) * 4)
				return num;
		}
	}

	void main() throws IOException, NoSuchAlgorithmException {
		final var content = Files.readString(new File(path).toPath());
		IO.println(find(content, 5));
		IO.println(find(content, 6));
	}
}
