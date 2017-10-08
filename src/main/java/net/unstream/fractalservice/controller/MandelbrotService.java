package net.unstream.fractalservice.controller;

import org.apache.commons.math3.complex.Complex;

import javax.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

import net.unstream.fractalservice.entity.Fractal;
import net.unstream.fractalservice.entity.Quad;

@ApplicationScoped
public class MandelbrotService {

  private static final Logger LOG = Logger.getLogger(MandelbrotService.class.getName());

  // @Inject
  // @Named("forkjoin")
  // private final FractalAlg alg = null;



  public MandelbrotService() {
  }

  public Quad create(Fractal fractal) {

    MandelbrotFunction f = new MandelbrotFunction(fractal.getIterations());

    Quad data = Quad.builder().build();
    double width = fractal.getC1().getReal() - fractal.getC0().getReal();
    double height = fractal.getC1().getImaginary() - fractal.getC0().getImaginary();
    double xstep = width / data.getWidth();
    double ystep = height / data.getWidth();
    for (int x = 0; x < data.getWidth(); x++) {
      for (int y = 0; y < data.getWidth(); y++) {
        Complex z = fractal.getC0().add(new Complex(xstep * x, ystep * y));
        double r = f.apply(z);
        data.setXY(x, y, r);
      }
    }
    return data;
  }

}
