package util;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import modele.Classe;

public class Main {
	public static void main(String[] args) {		
		// 1
		Predicate<String> pred4 = string -> string.length() > 4;
		System.out.println(pred4.test("aaa"));
		System.out.println(pred4.test("aaaaa"));
		System.out.println();
		
		// 2
		Predicate<String> predNullString = string -> string == null || string.isEmpty();
		System.out.println(predNullString.test(""));
		System.out.println(predNullString.test(null));
		System.out.println(predNullString.test("a"));
		System.out.println();
		
		// 3
		Predicate<String> UnbPredNullString = Classe::isStringEmpty;
		System.out.println(UnbPredNullString.test(""));
		System.out.println(UnbPredNullString.test(null));
		System.out.println(UnbPredNullString.test("a"));
		System.out.println();
		
		// 4
		Function<String, String> fctParenthesis = string -> "(" + string + ")";
		System.out.println(fctParenthesis.apply("test"));
		System.out.println();
		
		// 5
		Function<String, String> unbFctUpper = Classe::maj;
		System.out.println(unbFctUpper.apply("test"));
		System.out.println();
		
		// 6
		Function<Object, String> fctNullToString = (Object obj) -> {
            if(obj == null) {
                String string = "";
                char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
                Random rand = new Random();
                int limit = rand.nextInt(1, 10);
                for(int i = 0; i < limit; i++) {
                	string += letters[rand.nextInt(letters.length - 1)];
                }
                System.out.println(string);
                return string;
            }
            return "";
        };
        
        Function<String, Integer> fctCountChar = (String string) -> string.length();

        Function<?, Integer> fctFinal = (Object object) -> {
            return fctCountChar.apply(fctNullToString.apply(object));
        };
        
        System.out.println("Les deux fonctions à la suite:");
        String string = fctNullToString.apply(null);
        System.out.println(fctCountChar.apply(string));        
        System.out.println("Combinaison entre les deux fonctions:");
        System.out.println(fctFinal.apply(null));
		System.out.println();
		
		// 7
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		System.out.println("Avant : " + list);
		Consumer<ArrayList<Integer>> unbEmptyList = Classe::emptyList;
		unbEmptyList.accept(list);
		System.out.println("Apres : " + list);
		System.out.println();
		
		// 8
		BiFunction<String, String, String> biFctConcat = (string1, string2) -> string1 + string2;
		System.out.println(biFctConcat.apply("test1", "test2"));
		System.out.println();
		
		// 9
		Function<String, String> fctFirstUpper = s -> s.substring(0, 1).toUpperCase() + s.substring(1);
		System.out.println(fctFirstUpper.apply("test"));		
	}
}