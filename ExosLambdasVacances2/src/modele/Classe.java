package modele;

import java.util.ArrayList;
import java.util.function.IntFunction;

public class Classe {
	public char m(int x, IntFunction<String> ifunc) {
		return ifunc.apply(x).charAt(x);
	}
	
	public static boolean isStringEmpty(String string) {
		return string == null || string.isEmpty();	
	}
	
	public static String maj(String string) {
		return string.toUpperCase();
	}
	
	public static void emptyList(ArrayList<Integer> liste) {
		liste.clear();		
	}
}
