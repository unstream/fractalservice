package net.unstream.fractalservice.controller;

@FunctionalInterface
public interface MandelbrotInterface<Z, C, Iterations, Result> {
	Result apply(Z z, C c, Iterations iterations);
}
