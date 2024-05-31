package com.Getafe.ThreadPool.ExecutorCallableYFuture;

import java.util.concurrent.Callable;

public class tareaCalcularMedia implements Callable<Double> {

	private int rangoInicio;
	private int rango;
	private int[][] matriz;
	private double resultado = 0;
	
	public tareaCalcularMedia(int inicio, int fin, int[][] matriz) {
		this.rangoInicio = inicio;
		this.rango = fin;
		this.matriz = matriz;
	}

	@Override
	public Double call() throws Exception {
		int suma = 0;
		int contador = 0;
		for (int row = this.rangoInicio; row < this.rango; row++) {
            for (int col = 0; col < this.matriz.length; col++) {
            	suma += this.matriz[row][col];
            	contador++;
            }
        }
		this.resultado = (suma/contador);
        return this.resultado;
	}

}
