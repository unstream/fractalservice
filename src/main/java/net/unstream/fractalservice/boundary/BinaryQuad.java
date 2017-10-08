package net.unstream.fractalservice.boundary;

import java.io.Serializable;

public class BinaryQuad implements Serializable {

  private static final long serialVersionUID = 7135360214387909364L;

  final static private int width = 500;
  private String data = null;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getWidth() {
    return width;
  }

}
