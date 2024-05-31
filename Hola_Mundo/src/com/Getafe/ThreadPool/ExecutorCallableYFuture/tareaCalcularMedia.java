package com.Getafe.ThreadPool.ExecutorCallableYFuture;

import java.util.concurrent.Callable;

public class tareaCalcularMedia implements Callable<Double> {

	private int rangoInicio;
	private int rango;
	private int[][] matriz;
	private double resultado;
	
	public tareaCalcularMedia(int inicio, int fin, int[][] matriz) {
		this.rangoInicio = inicio;
		this.rango = fin;
		this.matriz = matriz;
		this.resultado = 0;
	}

	@Override
	public Double call() throws Exception {
		for (int row = this.rangoInicio; row < this.rango; row++) {
            for (int col = 0; col < this.matriz.length; col++) {
            	this.resultado += this.matriz[row][col];
            }
        }
        return this.resultado;
	}

}