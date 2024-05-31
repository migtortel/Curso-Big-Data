package com.Getafe.ThreadPool.ExecutorCallableYFuture;

import java.util.concurrent.Callable;

public class tareaCalcularParesImpares implements Callable<int[]> {

	private int rangoInicio;
	private int rango;
	private int[][] matriz;
	private int[] resultado;
	
	public tareaCalcularParesImpares (int inicio, int fin, int[][] matriz) {
		this.rangoInicio = inicio;
		this.rango = fin;
		this.matriz = matriz;
		this.resultado = new int[2];
	}
	
	@Override
	public int[] call() throws Exception {
		for (int row = this.rangoInicio; row < this.rango; row++) {
            for (int col = 0; col < this.matriz.length; col++) {
                if (this.matriz[row][col] % 2 == 0) {
                    this.resultado[0]++;
                } else {
                    this.resultado[1]++;
                }
            }
        }
        return this.resultado;
	}

}
