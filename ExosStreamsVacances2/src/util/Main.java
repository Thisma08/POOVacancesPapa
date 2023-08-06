package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import modele.Etudiant;

import java.util.function.Predicate;

public class Main {
	public static void main(String[] args) {
		List<Etudiant> etudiants = new ArrayList<>();
		
		Etudiant e1 = new Etudiant("A", 1, "IG", Arrays.asList("POO", "FBD", "TI", "MATH"));
		Etudiant e2 = new Etudiant("C", 6, "IG", Arrays.asList("LPP", "MPP", "MATH"));
		Etudiant e3 = new Etudiant("B", 5, "IG", Arrays.asList("POO", "FBD", "TI", "MATH", "LPP"));
		Etudiant e4 = new Etudiant("F", 3, "IG", Arrays.asList("LPP", "MPP", "MATH"));		
		Etudiant e5 = new Etudiant("d", 4, "IG", Arrays.asList("POO", "FBD", "TI", "MATH"));
		Etudiant e6 = new Etudiant("D", 2, "IG", Arrays.asList("LPP", "MPP", "MATH"));
		Etudiant e7 = new Etudiant("Y", 7, "IG", Arrays.asList("POO", "FBD", "TI", "MATH", "LPP"));
		Etudiant e8 = new Etudiant("P", 21, "IG", Arrays.asList("LPP", "MPP", "MATH"));

		etudiants.add(e1);
		etudiants.add(e2);
		etudiants.add(e3);
		etudiants.add(e4);
		etudiants.add(e5);
		etudiants.add(e6);
		etudiants.add(e7);
		etudiants.add(e8);

		// 1
		long ex1 = etudiants.stream().filter(e -> e.getNom().charAt(0) == 'B' || e.getNom().charAt(0) == 'b')
											.count();
		System.out.println(ex1);
		System.out.println("");
		
		// 2
		etudiants.stream().filter(e -> e.getNom().charAt(0) == 'B' || e.getNom().charAt(0) == 'b')
							.forEach(e -> System.out.println(e));
		System.out.println("");
		
		// 3
		etudiants.stream().sorted(Comparator.comparing(Etudiant::getNom, String.CASE_INSENSITIVE_ORDER))
							.forEach(e -> System.out.println(e));
		System.out.println("");
		
		// 4
		double ex4 = etudiants.stream().mapToInt(Etudiant::getAge)
											.average()
						                    .getAsDouble();
		System.out.println(ex4);
		System.out.println("");		
		
		// 5
		int ageMax = etudiants.stream().mapToInt(Etudiant::getAge)
										.max()
								     	.getAsInt();
		
		List<Etudiant> ex5 = etudiants.stream().filter(e -> e.getAge() == ageMax)
													.collect(Collectors.toList());
		
		for(Etudiant e : ex5) {
			System.out.println(e.getNom());
		}
		System.out.println("");
		
		// 6
		long stream6 = etudiants.stream().filter(e -> e.getAge() > 20)
											.count();
		System.out.println(stream6);
		System.out.println("");
		
		// 7
		List<Etudiant> stream7 = etudiants.stream().filter(e -> e.getAge() > 20)
													.collect(Collectors.toList());
		System.out.println(stream7);
		System.out.println("");
		
		// 8
		etudiants.stream().filter(e -> e.getAge() > 20)
							.forEach(e -> System.out.println(e.getNom()));
		System.out.println("");
		
		// 9
		int coursMax = etudiants.stream().mapToInt(Etudiant::compterCours)
											.max()
		                                    .getAsInt();
		
		List<Etudiant> stream9 = etudiants.stream().filter(e -> e.compterCours() == coursMax)
													.collect(Collectors.toList()); 
		System.out.println(stream9);
		System.out.println("");
		
		// 10
		List<String> stream10 = etudiants.stream()
											.flatMap(e -> e.getCours().stream())
                	        				.distinct()
                					        .collect(Collectors.toList());
        System.out.println(stream10);
	}
}
