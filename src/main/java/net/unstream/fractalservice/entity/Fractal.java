package net.unstream.fractalservice.entity;

import org.apache.commons.math3.complex.Complex;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Fractal {

  private Complex c0, c1;
  private int iterations;

}
