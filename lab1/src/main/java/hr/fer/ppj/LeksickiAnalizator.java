package hr.fer.ppj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeksickiAnalizator {
	static int line = 1;
	static char[] data = null;
	static int currentIndex = 0;
	static String token;

	public static void main(String[] args) {
		/*
		 * Scanner scanner = new Scanner(System.in); String currentLine = null;
		 * 
		 * while (true) { if (scanner.hasNextLine()) { currentLine = scanner.nextLine();
		 * System.out.format(currentLine + "ZZ"); }
		 * 
		 * if (currentLine.equals("")) { if (!scanner.hasNextLine()) { break; } } }
		 * scanner.close();
		 */

		/*
		 * char temp;
		 * 
		 * try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in,
		 * "UTF-8"));) { temp = (char) br.read(); while ((int) temp != -1) {
		 * sb.append(temp); temp = (char) br.read(); } } catch (IOException e) {
		 * e.printStackTrace(); }
		 * 
		 * System.out.format(sb.toString() + "ZZ");
		 */

		String currentLine;
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
			while ((currentLine = br.readLine()) != null) {
				stringBuilder.append(currentLine).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		data = stringBuilder.toString().toCharArray();

		while (true) {
			ignoreBlanks();
			if (currentIndex >= data.length) {
				break;
			}

			StringBuilder sb = new StringBuilder();

			if (data[currentIndex] == '/') {
				if (currentIndex + 1 < data.length) {
					if (data[currentIndex + 1] == '/') {
						while (data[currentIndex] != '\n') {
							currentIndex++;
						}
						continue;
					}
				}
			}

			if (Character.isLetter(data[currentIndex])) {
				while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])) {
					sb.append(data[currentIndex]);
					currentIndex++;
					if (currentIndex == data.length)
						break;
				}
			}
			if (sb.length() > 0) {
				token = sb.toString();
				if (token.equals("za")) {
					System.out.println("KR_ZA " + line + " " + token);
					continue;
				}
				if (token.equals("az")) {
					System.out.println("KR_AZ " + line + " " + token);
					continue;
				}
				if (token.equals("od")) {
					System.out.println("KR_OD " + line + " " + token);
					continue;
				}
				if (token.equals("do")) {
					System.out.println("KR_DO " + line + " " + token);
					continue;
				} else {
					System.out.println("IDN " + line + " " + token);
					continue;
				}
			}

			while (Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex == data.length)
					break;
			}
			if (sb.length() > 0) {
				token = sb.toString();
				System.out.println("BROJ " + line + " " + token);
				continue;
			}
			
			if (data[currentIndex] == '=') {
				token = "=";
				currentIndex++;
				System.out.println("OP_PRIDRUZI " + line + " " + token);
				continue;
			}
			if (data[currentIndex] == '+') {
				token = "+";
				currentIndex++;
				System.out.println("OP_PLUS " + line + " " + token);
				continue;
			}
			if (data[currentIndex] == '-') {
				token = "-";
				currentIndex++;
				System.out.println("OP_MINUS " + line + " " + token);
				continue;
			}
			if (data[currentIndex] == '*') {
				token = "*";
				currentIndex++;
				System.out.println("OP_PUTA " + line + " " + token);
				continue;
			}
			if (data[currentIndex] == '/') {
				token = "/";
				currentIndex++;
				System.out.println("OP_DIJELI " + line + " " + token);
				continue;
			}
			if (data[currentIndex] == '(') {
				token = "(";
				currentIndex++;
				System.out.println("L_ZAGRADA " + line + " " + token);
				continue;
			}
			if (data[currentIndex] == ')') {
				token = ")";
				currentIndex++;
				System.out.println("D_ZAGRADA " + line + " " + token);
				continue;
			}
			
		}
		
	}

	private static void ignoreBlanks() {
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			if (current == ' ' || current == '\n' || current == '\t') {
				if (current == '\n') {
					line++;
				}

				currentIndex++;
				continue;
			}
			break;
		}
	}

}
