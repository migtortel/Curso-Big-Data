package com.Getafe.ThreadPool.ExecutorCallableYFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainThreadPool_Matriz {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
        // Crear una matriz cuadrada de ejemplo
        int n = 1000; // tamaño de la matriz
        int[][] matriz = new int[n][n];
        
        // Rellenar la matriz con valores aleatorios entre 1 y 100
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = (int)(Math.random() * 100) + 1;
            }
        }
        
        // Crear un thread pool con 16 hilos
        int numProcesadores = 16;
        ExecutorService executor = Executors.newFixedThreadPool(numProcesadores);
        	
        // Lista para guardar los futuros resultados
        List<Future<int[]>> resultados = new ArrayList<>();

        // Dividir la matriz en partes iguales para cada hilo
        int rango = n / numProcesadores;
        for (int i = 0; i < numProcesadores; i++) {
            final int inicio = i * rango;
            final int fin = (i == numProcesadores - 1) ? n : inicio + rango;
            
            //declara el callable como una funcion lambda, cada tarea es una lambda que no recibe parametros y que devuelve 
            //un array unidimensional en el que almacena en la posicion 0 los pares y en la 1 los impares
            Callable<int[]> tarea = () -> {
                int[] resultado = new int[2]; // resultado[0] para pares, resultado[1] para impares
                for (int row = inicio; row < fin; row++) {
                    for (int col = 0; col < n; col++) {
                        if (matriz[row][col] % 2 == 0) {
                            resultado[0]++;
                        } else {
                            resultado[1]++;
                        }
                    }
                }
                return resultado;
            };
            //en el future se almacenan los resultados que devuelve la tarea, cada future contiene un array de dos posiciones
            //por eso se almacenan en una lista, porque son tantos arrays como hilos lanzados, y por eso hay que iterar sobre
            //ellos 
            Future<int[]> futuro = executor.submit(tarea);
            resultados.add(futuro);
        }

        // Sumar los resultados de todos los hilos
        int totalPares = 0;
        int totalImpares = 0;
        
        for (Future<int[]> futuro : resultados) {
        	/*
        	 * La llamada al método get() es bloqueante, lo que significa que esperará hasta que el resultado asociado 
        	 * al Future esté disponible. Por lo tanto, cuando este bucle termina, significa que todas las tareas han 
        	 * finalizado y los resultados están listos para ser utilizados.
        	 */
            int[] resultado = futuro.get(); 
            totalPares += resultado[0];
            totalImpares += resultado[1];
        }

        // Imprimir los resultados
        System.out.println("Total de números pares: " + totalPares);
        System.out.println("Total de números impares: " + totalImpares);

        // Apagar el executor
        executor.shutdown();
    }
}
