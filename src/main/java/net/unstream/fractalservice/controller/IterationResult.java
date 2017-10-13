package net.unstream.fractalservice.controller;

import lombok.Builder;
import lombok.Data;

import org.apache.commons.math3.complex.Complex;

@Builder
@Data
public class IterationResult {
	int iteration;
	Complex fi;
}
