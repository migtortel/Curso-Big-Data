package com.getafe.basico.lambdas;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Lambdas {
	public static void main(String[] args) {
		/*
		BiFunction<Integer, Integer, Integer> sumar = (a, b) -> {return a + b;};
        BiFunction<Integer, Integer, Integer> restar = (a, b) -> a - b;
		
        List<BiFunction> test = Arrays.asList(sumar,restar);
        
        System.out.println(test.get(1).apply(7,4));
        */
		Comparator<String> c = (o1, o2) -> o1.compareTo(o2);
		String[] nombres = {"Miguel", "Ana", "Sergio", "Lucia", "Marina"};
		Arrays.sort(nombres, (a, b) -> a.compareTo(b));
		
		for (String i : nombres) {
			System.out.println(i);
		}
	}
}
