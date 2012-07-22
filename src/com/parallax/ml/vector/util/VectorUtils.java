/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.parallax.ml.vector.util;

import com.parallax.ml.vector.LinearVector;

public class VectorUtils {
	private static double absTolx = 1e-4; // tolerance on absolute value difference
	public static final double SMALL = 1e-6;
	
	private VectorUtils(){}
	
	public static final double LPNorm(LinearVector vect, double p) {
		return vect.LPNorm(p);
	}
	
	public static final double dotProduct(LinearVector x, LinearVector y) {
		double out = 0;
		for(int x_i : x) {
			out += x.getValue(x_i)*y.getValue(x_i);
		}
		return out;
	}
	
	// returns true iff we've converged based on absolute x difference
	public static boolean smallAbsDiff(LinearVector x, LinearVector y) {
		return smallAbsDiff(x, y, absTolx);
	}
	
	public static boolean smallAbsDiff(LinearVector x, LinearVector y, double epsilon) {
		for (int i = 0; i < x.size(); i++) {
			if (Math.abs(x.getValue(i) - y.getValue(i)) > epsilon) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNaN(LinearVector x) {
		for(int xi : x)
			if(Double.isNaN(x.getValue(xi)))
				return true;
		return false;
	}
	
	public static final boolean floatingPointEquals(double a, double b) {
		return (a - b < SMALL) && (b - a < SMALL);
	}
	
	public static boolean floatingPointGreaterThan(double a, double b) {

		return (a - b > SMALL);
	}

	public static boolean floatingPointLessThanOrEquals(double a, double b) {

		return (a - b < SMALL);
	}

	public static boolean floatingPointLessThan(double a, double b) {

		return (b - a > SMALL);
	}
}
