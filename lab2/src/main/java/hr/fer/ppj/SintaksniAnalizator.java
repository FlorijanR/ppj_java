package hr.fer.ppj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SintaksniAnalizator {
	static String data = null;
	static String tokens[] = null;
	static int currentIndex = 0;
	static String currentToken = null;
	static StringBuilder sb = new StringBuilder();

	private static void readNext() {
		if (currentIndex >= tokens.length) {
			currentToken = "EOF";
		} else {
			currentToken = tokens[currentIndex];
			currentIndex++;
		}
	}

	private static String type() {
		if (currentToken.equals("EOF"))
			return "EOF";
		else
			return currentToken.split(" ")[0];
	}

	private static void err() {
		if (type().equals("EOF")) {
			System.out.println("err kraj");
		} else {
			System.out.println("err " + currentToken);
		}
		System.exit(0);
	}

	private static void append(String name, int level) {
		for (int i = 0; i < level; i++) {
			sb.append(" ");
		}
		sb.append(name).append("\r\n");
	}

	private static void program(int level) {
		append("<program>", level);

		if (type().equals("IDN") || type().equals("KR_ZA") || type().equals("EOF")) {
			lista_naredbi(level + 1);
		} else {
			err();
		}
	}

	private static void lista_naredbi(int level) {
		append("<lista_naredbi>", level);

		if (type().equals("IDN") || type().equals("KR_ZA")) {
			naredba(level + 1);
			lista_naredbi(level + 1);
		} else if (type().equals("EOF") || type().equals("KR_AZ")) {
			append("$", level + 1);
		} else {
			err();
		}
	}

	private static void naredba(int level) {
		append("<naredba>", level);

		if (type().equals("IDN")) {
			naredba_pridruzivanja(level + 1);
		} else if (type().equals("KR_ZA")) {
			za_petlja(level + 1);
		} else {
			err();
		}
	}

	private static void naredba_pridruzivanja(int level) {
		append("<naredba_pridruzivanja>", level);

		if (type().equals("IDN")) {
			append(currentToken, level + 1);
			readNext();

			if (type().equals("OP_PRIDRUZI")) {
				append(currentToken, level + 1);
				readNext();

				E(level + 1);
			} else {
				err();
			}

		} else {
			err();
		}
	}

	private static void za_petlja(int level) {
		append("<za_petlja>", level);

		if (type().equals("KR_ZA")) {
			append(currentToken, level + 1);
			readNext();

			if (type().equals("IDN")) {
				append(currentToken, level + 1);
				readNext();

				if (type().equals("KR_OD")) {
					append(currentToken, level + 1);
					readNext();

					E(level + 1);

					if (type().equals("KR_DO")) {
						append(currentToken, level + 1);
						readNext();

						E(level + 1);
						lista_naredbi(level + 1);

						if (type().equals("KR_AZ")) {
							append(currentToken, level + 1);
							readNext();
						} else {
							err();
						}
					} else {
						err();
					}
				} else {
					err();
				}
			} else {
				err();
			}

		} else {
			err();
		}
	}

	private static void E(int level) {
		append("<E>", level);

		if (type().equals("IDN") || type().equals("BROJ") || type().equals("OP_PLUS") || type().equals("OP_MINUS")
				|| type().equals("L_ZAGRADA")) {

			T(level + 1);
			E_lista(level + 1);
		} else {
			err();
		}
	}

	private static void E_lista(int level) {
		append("<E_lista>", level);

		if (type().equals("OP_PLUS")) {
			append(currentToken, level + 1);
			readNext();

			E(level + 1);
		} else if (type().equals("OP_MINUS")) {
			append(currentToken, level + 1);
			readNext();

			E(level + 1);
		} else if (type().equals("IDN") || type().equals("KR_ZA") || type().equals("KR_DO") || type().equals("KR_AZ")
				|| type().equals("D_ZAGRADA") || type().equals("EOF")) {
			append("$", level + 1);
		} else {
			err();
		}
	}

	private static void T(int level) {
		append("<T>", level);

		if (type().equals("IDN") || type().equals("BROJ") || type().equals("OP_PLUS") || type().equals("OP_MINUS")
				|| type().equals("L_ZAGRADA")) {

			P(level + 1);
			T_lista(level + 1);
		} else {
			err();
		}
	}

	private static void T_lista(int level) {
		append("<T_lista>", level);

		if (type().equals("OP_PUTA")) {
			append(currentToken, level + 1);
			readNext();

			T(level + 1);
		} else if (type().equals("OP_DIJELI")) {
			append(currentToken, level + 1);
			readNext();

			T(level + 1);
		} else if (type().equals("IDN") || type().equals("KR_ZA") || type().equals("KR_DO") || type().equals("KR_AZ")
				|| type().equals("OP_PLUS") || type().equals("OP_MINUS") || type().equals("D_ZAGRADA")
				|| type().equals("EOF")) {
			append("$", level + 1);
		} else {
			err();
		}
	}

	private static void P(int level) {
		append("<P>", level);

		if (type().equals("OP_PLUS")) {
			append(currentToken, level + 1);
			readNext();

			P(level + 1);
		} else if (type().equals("OP_MINUS")) {
			append(currentToken, level + 1);
			readNext();

			P(level + 1);
		} else if (type().equals("L_ZAGRADA")) {
			append(currentToken, level + 1);
			readNext();

			E(level + 1);

			if (type().equals("D_ZAGRADA")) {
				append(currentToken, level + 1);
				readNext();

			} else {
				err();
			}
		} else if (type().equals("IDN")) {
			append(currentToken, level + 1);
			readNext();
		} else if (type().equals("BROJ")) {
			append(currentToken, level + 1);
			readNext();
		} else {
			err();
		}
	}

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
		data = stringBuilder.toString();
		tokens = data.split("\n");

		readNext();
		program(0);

		System.out.print(sb.toString());
	}

}
