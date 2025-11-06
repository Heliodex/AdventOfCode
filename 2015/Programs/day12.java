package Programs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Pair<K, V> {
	K key;
	V value;

	Pair(final K key, final V value) {
		this.key = key;
		this.value = value;
	}
}

class JSONValue {
	StringBuffer buf;
	int pos = 0;

	boolean end() {
		return pos >= buf.length();
	}

	char peek() {
		return buf.charAt(pos);
	}

	char read() {
		return buf.charAt(pos++);
	}

	boolean isWhitespace(final char c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t';
	}

	boolean canStartNumber(final char c) {
		return (c >= '0' && c <= '9') || c == '-';
	}

	void parseWhitespace() {
		while (!end() && isWhitespace(peek()))
			read();
	}

	String parseString() {
		if (end() || peek() != '"')
			return null;
		read(); // consume opening quote

		final var sb = new StringBuilder();

		while (!end()) {
			final var c = read();
			if (c == '"')
				break;

			if (c != '\\') {
				sb.append(c);
				continue;
			}

			final var next = read();
			sb.append(switch (next) {
				case '"' -> '"';
				case '\\' -> '\\';
				case '/' -> '/';
				case 'b' -> '\b';
				case 'f' -> '\f';
				case 'n' -> '\n';
				case 'r' -> '\r';
				case 't' -> '\t';
				case 'u' -> {
					var hex = "";
					for (int i = 0; i < 4; i++)
						hex += read();

					try {
						yield (char) Integer.parseInt(hex, 16);
					} catch (final NumberFormatException e) {
						throw new UnsupportedOperationException("Invalid unicode escape sequence: \\u" + hex);
					}
				}
				default -> throw new UnsupportedOperationException("Unknown escape sequence: \\" + next);
			});
		}

		if (end())
			throw new UnsupportedOperationException("Unterminated string literal");

		return sb.toString();
	}

	Double parseNumber() {
		if (end() || !canStartNumber(peek()))
			return null;

		var negative = false;
		if (peek() == '-') {
			negative = true;
			read();
		}

		var numStr = "";
		switch (peek()) {
			case '0' -> {
				numStr += read();
			}
			case '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
				while (!end() && (peek() >= '0' && peek() <= '9'))
					numStr += read();
			}
			default -> throw new UnsupportedOperationException("Invalid number format");
		}

		var fractionStr = "";
		if (!end() && peek() == '.') {
			read(); // consume '.'

			while (!end() && (peek() >= '0' && peek() <= '9'))
				fractionStr += read();

			if (fractionStr.isEmpty())
				throw new UnsupportedOperationException("Invalid number format");
		}

		var exponentNegative = false;
		var exponentStr = "";
		if (!end() && (peek() == 'e' || peek() == 'E')) {
			read(); // consume 'e'/'E'

			if (!end() && (peek() == '+' || peek() == '-')) {
				if (peek() == '-')
					exponentNegative = true;
				read();
			}

			while (!end() && (peek() >= '0' && peek() <= '9'))
				exponentStr += read();
		}

		final var numberStr = numStr
				+ (fractionStr.isEmpty() ? "" : "." + fractionStr)
				+ (exponentStr.isEmpty() ? ""
						: "e" + (exponentNegative ? "-" : "")
								+ exponentStr);

		return Double.parseDouble(numberStr) * (negative ? -1 : 1);
	}

	Pair<String, Object> parseKeyValuePair() {
		parseWhitespace();

		final var key = parseString();
		if (key == null) {
			IO.println("pos: " + pos + ", so far: " + buf.substring(0, pos));
			throw new UnsupportedOperationException("Expected string key");}

		IO.println(key);

		parseWhitespace();
		if (end() || read() != ':') {
			IO.println("pos: " + pos + ", so far: " + buf.substring(0, pos));
			throw new UnsupportedOperationException("Expected ':' after key");
		}

		parseWhitespace();
		final var value = getValue();

		IO.println(value);

		return new Pair<>(key, value);
	}

	Map<String, Object> parseObject() {
		if (end() || peek() != '{')
			return null;
		read(); // consume '{'

		final var map = new HashMap<String, Object>();

		parseWhitespace();

		while (true) {
			final var pair = parseKeyValuePair();
			map.put(pair.key, pair.value);

			parseWhitespace();

			if (peek() != ',')
				break;
			read(); // consume ','
		}

		if (end() || read() != '}')
			throw new UnsupportedOperationException("Expected '}' at end of object");

		return map;
	}

	List<Object> parseArray() {
		if (end() || peek() != '[')
			return null;
		read(); // consume '['

		final var list = new ArrayList<Object>();

		parseWhitespace();

		while (true) {
			final var value = getValue();
			list.add(value);

			parseWhitespace();

			if (peek() != ',')
				break;
			read(); // consume ','
		}

		if (end() || read() != ']')
			throw new UnsupportedOperationException("Expected ']' at end of array");

		return list;
	}

	Boolean parseBoolean() {
		if (end())
			return null;

		if (buf.substring(pos).startsWith("true")) {
			pos += 4;
			return true;
		} else if (buf.substring(pos).startsWith("false")) {
			pos += 5;
			return false;
		}

		return null;
	}

	boolean parseNull() {
		if (!end() && buf.substring(pos).startsWith("null")) {
			pos += 4;
			return true;
		}

		return false;
	}

	Object getValue() {
		parseWhitespace();
		var value = (Object) null;

		while (!end()) {
			if ((value = parseString()) != null
					|| (value = parseNumber()) != null
					|| (value = parseObject()) != null
					|| (value = parseArray()) != null
					|| (value = parseBoolean()) != null
					|| parseNull()) // don't need to assign null
				break;

			throw new UnsupportedOperationException("Unknown character: " + peek());
		}

		parseWhitespace();
		return value;
	}

	JSONValue(final String content) throws IOException {
		// parse json from input string
		this.buf = new StringBuffer(content);
	}
}

class day12 {
	final String path = "./Inputs/12";

	int part1(final JSONValue input) {
		return 0;
	}

	int part2(final JSONValue input) {
		return 0;
	}

	void main() throws IOException {
		final var content = Files.readString(new File(path).toPath());

		// parse json object
		final var json = new JSONValue(content);
		IO.println(part1(json));
		IO.println(part2(json));
	}
}
