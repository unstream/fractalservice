package net.unstream.fractalservice.controller;

import org.apache.commons.math3.complex.Complex;

import javax.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

import net.unstream.fractalservice.entity.Fractal;
import net.unstream.fractalservice.entity.Quad;

@ApplicationScoped
public class MandelbrotService {

	private static final Logger LOG = Logger.getLogger(MandelbrotService.class
			.getName());
	private static double THRESHOLD = 0.01;

	public MandelbrotService() {
	}

	public Quad create(Fractal fractal) {

		MandelbrotFunctionOld f = new MandelbrotFunctionOld(fractal.getIterations());

		Quad data = Quad.builder().build();
		double width = fractal.getC1().getReal() - fractal.getC0().getReal();
		double height = fractal.getC1().getImaginary()
				- fractal.getC0().getImaginary();
		double xstep = width / data.getWidth();
		double ystep = height / data.getWidth();
		for (int x = 0; x < data.getWidth(); x++) {
			for (int y = 0; y < data.getWidth(); y++) {
				Complex c0 = fractal.getC0().add(
						new Complex(xstep * x, ystep * y));
				data.setXY(x, y, f.apply(c0));
			}
		}
		return data;
	}

	/**
	 * Automatically stop when the fractal now longer changes much.
	 */
	public Quad autostop(Fractal fractal) {
		MandelbrotFunction f = new MandelbrotFunction();

		Quad quad = createQuad();

		double width = fractal.getC1().getReal() - fractal.getC0().getReal();
		double height = fractal.getC1().getImaginary()
				- fractal.getC0().getImaginary();
		double xstep = width / quad.getWidth();
		double ystep = height / quad.getWidth();
		
		int i = 0;
		int max = 10;
		int pixels = quad.getWidth() * quad.getWidth();
		int lastIncomplete;
		int incomplete = pixels;
		do {
			lastIncomplete = incomplete;
			incomplete = 0;
			for (int x = 0; x < quad.getWidth(); x++) {
				for (int y = 0; y < quad.getWidth(); y++) {
					if (quad.getData()[x][y] == 0) {
						Complex c = fractal.getC0().add(
								new Complex(xstep * x, ystep * y));
						IterationResult r = f.apply(quad.getZdata()[x][y], c, max);
						if (r.getIteration() == 0) {
							quad.setXYZ(x, y, 0, r.getFi());
							incomplete++;
						} else {
							quad.setXY(x, y, i + r.getIteration());
						}
					}
				}
			}
			i = i + max;
			max = max * 2;
			LOG.info("" + (lastIncomplete - incomplete));
		} while ((lastIncomplete - incomplete)  > THRESHOLD * pixels);
		return quad;
	}

	private Quad createQuad() {
		Quad quad = Quad.builder().build();
		for (int x = 0; x < quad.getWidth(); x++) {
			for (int y = 0; y < quad.getWidth(); y++) {
				quad.setXYZ(x, y, 0, Complex.ZERO);
			}
		}
		return quad;
	}

}
