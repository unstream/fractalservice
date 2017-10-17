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
	public MandelbrotService() {
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
		} while ((fractal.getMinIterations() > i) 
				|| (incomplete == pixels) 
				|| ((lastIncomplete - incomplete) > fractal.getThreshold() * lastIncomplete));
		//Set the unfinished points form 0 to maxiteration
		for (int x = 0; x < quad.getWidth(); x++) {
			for (int y = 0; y < quad.getWidth(); y++) {
				if (quad.getData()[x][y] == 0) {
					quad.setXY(x, y, i);
				}
			}
		}
		quad.setMaxIterations(i);
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
