package net.unstream.fractalservice.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Quad {

  final static private int width = 500;

  public int getWidth() {
    return width;
  }

  final private Double[][] data = new Double[width][width];

  public Double[][] getData() {
    return data;
  }

  public void setXY(int x, int y, Double d) {
    data[x][y] = d;
  }


}
