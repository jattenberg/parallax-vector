/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.parallax.ml.vector;

/**
 * The factory class used to create and return an instance of type LinearVector
 * 
 * @author Josh Attenberg
 */
public class LinearVectorFactory {

	/**
	 * Class constructor
	 */
	private LinearVectorFactory() {
	}

	/**
	 * The method gets Linear Vector by number of bins
	 * 
	 * @param bins	
	 * 				number of bins
	 * @return Linear Vector
	 */
	public static LinearVector getVector(int bins) {
		VectorType type = bins < 12 ? VectorType.DOUBLEARR
				: VectorType.TROVEMAP;
		return type.getVector(bins);
	}

	/**
	 * The method gets Linear Vector by number of array
	 * 
	 * @param W
	 *            number of array
	 * @return Linear Vector
	 */
	public static LinearVector getVector(double[] W) {
		LinearVector vect = getVector(W.length);
		vect.setW(W);
		return vect;
	}

	/**
	 * The method gets a new Linear Vector by original Linear Vector
	 * 
	 * @param v
	 *            original Linear Vector
	 * @return Linear Vector
	 */
	public static LinearVector getVector(LinearVector v) {
		return copyVtoW(v, getVector(v.size()));
	}
	
	/**
	 * The method gets a new Linear Vector by original Linear Vector, with a 
	 * multiplicative scaling by the given factor
	 * 
	 * @param v
	 *            original Linear Vector
	 * @param scale
	 * 			  factor to scale the input vector
	 * @return Linear Vector
	 */
	public static LinearVector getScaledVector(LinearVector v, double scale) {
		return copyVtoWwithScale(v, getVector(v.size()), scale);
	}

	/**
	 * The method gets Linear Vector by number of bins
	 * 
	 * @param bins
	 *            number of bins
	 * @return Linear Vector
	 */
	public static LinearVector getDenseVector(int bins) {
		return VectorType.DOUBLEARR.getVector(bins);
	}

	/**
	 * The method gets Dense Vector by W
	 * 
	 * @param W
	 *            w
	 * @return LinearVector
	 */
	public static LinearVector getDenseVector(double[] W) {
		LinearVector vect = getDenseVector(W.length);
		vect.setW(W);
		return vect;
	}

	/**
	 * The method gets a new Dense Vector by Linear Vector
	 * 
	 * @param v
	 *            Linear Vector
	 * @return Linear Vector
	 */
	public static LinearVector getDenseVector(LinearVector v) {
		return copyVtoW(v, getDenseVector(v.size()));
	}

	// copy contents of the vector V to vector W
	private static LinearVector copyVtoW(LinearVector v, LinearVector W) {

		for (int x_i : v)
			W.updateValue(x_i, v.getValue(x_i));
		return W;
	}
	
	// scale the contents of the vector V and then copy them to vector W
	private static LinearVector copyVtoWwithScale(LinearVector v, LinearVector W, double scale) {

		for (int x_i : v)
			W.updateValue(x_i, v.getValue(x_i)*scale);
		return W;
	}

	/**
	 * outputs a new linear vector of the specified length. copies the
	 * min{length(input), length} dimensions from input
	 * 
	 * @param input
	 *            vector to be copied
	 * @param length
	 *            length of output
	 * @return linear vector
	 */
	public static LinearVector copyToLength(LinearVector input, int length) {
		LinearVector out = LinearVectorFactory.getVector(length);
		for (int x_i : input)
			if (x_i < length)
				out.resetValue(x_i, input.getValue(x_i));
		return out;
	}
}
