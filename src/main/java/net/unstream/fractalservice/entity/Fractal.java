package net.unstream.fractalservice.entity;

import lombok.Builder;
import lombok.Data;

import org.apache.commons.math3.complex.Complex;

@Data
@Builder
public class Fractal {

  private Complex c0, c1;
  private int iterations;
  private int minIterations;
  @Builder.Default 
  private double threshold = 0.01;

}
