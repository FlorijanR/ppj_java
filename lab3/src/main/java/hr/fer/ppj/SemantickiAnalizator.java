package hr.fer.ppj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SemantickiAnalizator {
	static String lines[] = null;
	static Map<String, String> vars = new HashMap<>();
	static int currentIndex = 0;

	public static void main(String[] args) {
		String currentLine;
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
			while ((currentLine = br.readLine()) != null) {
				stringBuilder.append(currentLine).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data = stringBuilder.toString();
		lines = data.split("\n");

		program();
	}

	private static void program() {
		while (currentIndex < lines.length) {
			if (lines[currentIndex].contains("<naredba_pridruzivanja>")) {
				naredba_pridruzivanja(vars);
				continue;
			}

			if (lines[currentIndex].contains("<za_petlja>")) {
				za_petlja(vars);
				continue;
			}

			currentIndex++;
		}
	}

	private static void za_petlja(Map<String, String> vars) {
		Map<String, String> varsCopy = new HashMap<>(vars);
		int len = lines[currentIndex].indexOf('<') + 1;

		currentIndex += 2;
		String loopVarName = lines[currentIndex].trim().split(" ")[2];
		String loopVarLine = lines[currentIndex].trim().split(" ")[1];
		varsCopy.put(loopVarName, loopVarLine);
		currentIndex++;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(" ");
		}
		String empty = sb.toString();

		while (currentIndex < lines.length && !lines[currentIndex].contains("<lista_naredbi>")) {
			if (lines[currentIndex].trim().split(" ")[0].equals("IDN")) {
				if (vars.containsKey(lines[currentIndex].trim().split(" ")[2])
						&& !lines[currentIndex].trim().split(" ")[2].equals(loopVarName)) {
					System.out.println(lines[currentIndex].trim().split(" ")[1] + " "
							+ vars.get(lines[currentIndex].trim().split(" ")[2]) + " "
							+ lines[currentIndex].trim().split(" ")[2]);
				} else {
					System.out.println("err " + lines[currentIndex].trim().split(" ")[1] + " "
							+ lines[currentIndex].trim().split(" ")[2]);
					System.exit(0);
				}
			}

			currentIndex++;
		}

		while (currentIndex < lines.length && lines[currentIndex].startsWith(empty)) {
			if (lines[currentIndex].contains("<naredba_pridruzivanja>")) {
				naredba_pridruzivanja(varsCopy);
				continue;
			}

			if (lines[currentIndex].contains("<za_petlja>")) {
				za_petlja(varsCopy);
				continue;
			}

			currentIndex++;
		}

	}

	private static void naredba_pridruzivanja(Map<String, String> vars) {
		int len = lines[currentIndex].indexOf('<') + 1;
		String line = null;
		String name = null;
		currentIndex++;
		int first = currentIndex;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(" ");
		}
		String empty = sb.toString();

		while (currentIndex < lines.length && lines[currentIndex].startsWith(empty)) {
			if (first == currentIndex) {
				line = lines[currentIndex].trim().split(" ")[1];
				name = lines[currentIndex].trim().split(" ")[2];
			} else {
				if (lines[currentIndex].trim().split(" ")[0].equals("IDN")) {
					if (vars.containsKey(lines[currentIndex].trim().split(" ")[2])) {
						System.out.println(lines[currentIndex].trim().split(" ")[1] + " "
								+ vars.get(lines[currentIndex].trim().split(" ")[2]) + " "
								+ lines[currentIndex].trim().split(" ")[2]);
					} else {
						System.out.println("err " + lines[currentIndex].trim().split(" ")[1] + " "
								+ lines[currentIndex].trim().split(" ")[2]);
						System.exit(0);
					}
				}
			}

			currentIndex++;
		}

		if (name != null && line != null && !vars.containsKey(name)) {
			vars.put(name, line);
		}

	}

}
