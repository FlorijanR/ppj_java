package hr.fer.ppj;

import java.util.HashMap;
import java.util.Map;

public class Test {
	static Map<String, String> vars = new HashMap<>();

	public static void main(String[] args) {
		vars.put("main", "main");
		
		za_petlja();
		
		System.out.println(vars);

	}

	private static void za_petlja() {
		Map<String, String> vars = new HashMap<>(SemantickiAnalizator.vars);
		
		vars.put("main", "potp");
		
		System.out.println(vars);

	}

}
