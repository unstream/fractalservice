package net.unstream.fractalservice.entity;

import org.apache.commons.math3.complex.Complex;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Quad {

	final static private int width = 500;
	private int maxIterations;

	public int getWidth() {
		return width;
	}

	final private Integer[][] data = new Integer[width][width];
	final private Complex[][] zdata = new Complex[width][width];

	public void setXY(int x, int y, Integer d) {
		data[x][y] = d;
	}

	public void setXYZ(int x, int y, Integer d, Complex z) {
		data[x][y] = d;
		zdata[x][y] = z;
	}

}
