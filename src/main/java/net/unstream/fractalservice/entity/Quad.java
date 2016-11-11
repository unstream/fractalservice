package net.unstream.fractalservice.entity;

public class Quad {

  final static private int width = 500;

  public int getWidth() {
    return width;
  }

  private Double[][] data = new Double[width][width];

  public Double[][] getData() {
    return data;
  }

  public void setXY(int x, int y, Double d) {
    data[x][y] = d;
  }


}
