package net.unstream.fractalservice.controller;

import java.util.function.Function;

import org.apache.commons.math3.complex.Complex;

/**
 * Compute the Mandelbrot function f(z) =  z^2 + c. 
 *
 */
public class MandelbrotFunctionOld implements Function<Complex, Integer>  {
	final private int maxIterations;
	
	public MandelbrotFunctionOld(final int maxIterations) {
		this.maxIterations = maxIterations;
	}
	
	@Override
	public Integer apply(Complex c) {
		double x = 0;
		double y = 0;
		double x2, y2;
		int i = 0;
		do {
			x2 = x * x;
			y2 = y * y;
			y = 2 * x * y + c.getImaginary();
			x = x2 - y2 + c.getReal();
			i++;
		} while (i < maxIterations && (x2 + y2) < 4);
		return i;
//		double nsmooth;
//		if (i == maxIterations) {
//			nsmooth = i;
//		} else {
//			nsmooth = i;
//			// nsmooth = 1d + i - Math.log(Math.log(Math.sqrt(x * x + y * y))) / Math.log(2);
//		}
//		//return nsmooth;
//		
	}
}
