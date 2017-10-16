package net.unstream.fractalservice.controller;

import org.apache.commons.math3.complex.Complex;

/**
 * Iterate the Mandelbrot function f(z) =  z^2 + c over the given number of iterations by returning f(f(...)).  
 * Using the result you can start iterating for further iterations.	
 */
public class MandelbrotFunction implements MandelbrotInterface<Complex, Complex, Integer, IterationResult>  {
	@Override
	public IterationResult apply(Complex z, Complex c, Integer iterations) {
		int completed = 0;
		double x = z.getReal();
		double y = z.getImaginary();
		double x2, y2;
		//z^2+c = (x+yi)^2=x^2 + y^2 i^2 + 2 x y i
		for (int i = 0; i < iterations; i++) {
			x2 = x * x;
			y2 = y * y;
			y = 2 * x * y + c.getImaginary();
			x = x2 - y2 + c.getReal();
			if ((x2 + y2) >= 4) {
				completed = i + 1;
				break;
			}
		}
		return IterationResult.builder()
				.iteration(completed)
				.fi(new Complex(x, y))
				.build();
	}
}
