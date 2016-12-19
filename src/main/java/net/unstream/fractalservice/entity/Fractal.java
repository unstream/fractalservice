package net.unstream.fractalservice.entity;

import org.apache.commons.math3.complex.Complex;

public class Fractal {

  private Complex c0, c1;
  private int iterations;

  public int getIterations() {
    return iterations;
  }

  public void setIterations(int iterations) {
    this.iterations = iterations;
  }

  public Complex getC0() {
    return c0;
  }

  public void setC0(Complex c0) {
    this.c0 = c0;
  }

  public Complex getC1() {
    return c1;
  }

  public void setC1(Complex c1) {
    this.c1 = c1;
  }

}
