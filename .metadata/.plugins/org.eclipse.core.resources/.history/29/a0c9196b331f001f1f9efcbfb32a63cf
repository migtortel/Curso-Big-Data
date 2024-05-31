package com.Getafe.ThreadPool.ExecutorCallableYFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainThreadPool_Media {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// Crear una matriz cuadrada de ejemplo
		int tamanioMatriz = 10; // tamaño de la matriz
		int[][] matriz = new int[tamanioMatriz][tamanioMatriz];

		rellenarMatrizConAleatorios(tamanioMatriz, matriz);

		// Crear un thread pool con tantos hilos como procesadores
		int numProcesadores = 16;
		ExecutorService executor = Executors.newFixedThreadPool(numProcesadores);

		// Lista para guardar los futuros resultados
		List<Future<Double>> resultadosMedia = new ArrayList<>();
		List<Future<int[]>> resultadosParesImpares = new ArrayList<>();

		// Dividir la matriz en partes iguales para cada hilo
		int rango = tamanioMatriz / (numProcesadores / 2);
		// El rango es numProcesadores / 2 para dividir las tareas
		for (int i = 0; i < numProcesadores / 2; i++) {
			final int inicio = i * rango;
			final int fin = (i == (numProcesadores / 2) - 1) ? tamanioMatriz : inicio + rango;

			Callable<Double> tareaMedia = new tareaCalcularMedia(inicio, fin, matriz);
			Future<Double> futuro = executor.submit(tareaMedia);
			resultadosMedia.add(futuro);
			
		}
		// Tenemos dos bucles porque de lo contrario cada hilo haria dos tareas, de esta forma hacemos que los 
		// primeros hilos calculen la media y los ultimos calculen los pares e impares
		for (int i = numProcesadores / 2; i < numProcesadores; i++) {
            final int inicio = (i - numProcesadores / 2) * rango;
            final int fin = (i == numProcesadores - 1) ? tamanioMatriz : inicio + rango;
			
			Callable<int[]> tareaParesImpares = new tareaCalcularParesImpares(inicio, fin, matriz);
			Future<int[]> futuroPares = executor.submit(tareaParesImpares);
			resultadosParesImpares.add(futuroPares);
		}

		// Mostrar los resultados de todos los hilos
		printResultadosTareaMedia(resultadosMedia, tamanioMatriz, numProcesadores);
		printResultadosTareaParesImpares(resultadosParesImpares);

		// Apagar el executor
		executor.shutdown();
	}

	private static void rellenarMatrizConAleatorios(int tamanioMatriz, int[][] matriz) {
		Random rand = new Random();
		for (int i = 0; i < tamanioMatriz; i++) {
			for (int j = 0; j < tamanioMatriz; j++) {
				matriz[i][j] = rand.nextInt(100) + 1;
				System.out.print(matriz[i][j] +" ");
			}
			System.out.println();
		}

	}

	private static void printResultadosTareaParesImpares(List<Future<int[]>> resultadosParesImpares)
			throws InterruptedException, ExecutionException {
		// Sumar los resultados de todos los hilos
		int totalPares = 0;
		int totalImpares = 0;

		for (Future<int[]> futuro : resultadosParesImpares) {
			int[] resultado = futuro.get();
			totalPares += resultado[0];
			totalImpares += resultado[1];
		}
		
		// Imprimir los resultados
		System.out.println("Total de números pares: " + totalPares);
		System.out.println("Total de números impares: " + totalImpares);

	}

	private static void printResultadosTareaMedia(List<Future<Double>> resultadosMedia, int tamanio,
			int numProcesadores) throws InterruptedException, ExecutionException {
		double resultado = 0;
		for (Future<Double> futuro : resultadosMedia) {
			resultado = +futuro.get();
		}
		System.out.println("La media de los valores de la matriz es: " + (resultado / (tamanio * tamanio)));
	}

}
