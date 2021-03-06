package net.unstream.fractalservice.controller;

import java.util.Arrays;

public class Colors {

  private String[] colors = {"#000000", "#0080ff", "#30b0ff", "#ff00ff"};

	private int [] iterations = {0, 50, 100, 200};

	private boolean useCyclicColors;

	public void addColor(final String color, final int iteration) {
		final int length = colors.length;
		colors = Arrays.copyOf(colors, length + 1);
		iterations = Arrays.copyOf(iterations, length + 1);
		colors[length] = color;
		iterations[length] = iteration;
	}
	
	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public int[] getIterations() {
		return iterations;
	}

	public void setIterations(int[] iterations) {
		this.iterations = iterations;
	}

	public boolean isUseCyclicColors() {
		return useCyclicColors;
	}

	public void setUseCyclicColors(boolean useCyclicColors) {
		this.useCyclicColors = useCyclicColors;
	}
	
}
