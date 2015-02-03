/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.dsi.parallax.ml.vector;

import java.util.List;
import java.util.Set;

import com.dsi.parallax.ml.vector.util.ValueScaling;
/**
 * TODO: make a clone method
 * @author jattenberg
 *
 */
public interface LinearVector extends Iterable<Integer> {
	public double getValue(int index);

	/**
	 * increments the value at the given index by the given value
	 * 
	 * @param index index to be updated
	 * @param value the value to be set
	 */
	public void updateValue(int index, double value);
	
	/**
	 * resets the value at the given index by the given value
	 * @param index index to be updated
	 * @param value the value to be set
	 *
	 */
	public void resetValue(int index, double value);
	
	/**
	 * delete the value at the given index
	 * @param index vector index to be deleted 
	 */
	public void delete(int index);
	
	/**
	 * get return the set of indices corresponding to valid features
	 * 
	 * @return set of feature indices
	 */
	public Set<Integer> getFeatureIndicies();
	
	/**
	 * get the size of the linear vector
	 * 
	 * @return the size
	 */
	public int size();
	
	/**
	 * initialize the linear vector with the given param
	 * @param param
	 * 				parameter used to initialize the linear vector
	 */
	public void initW(double param);
	
	/**
	 * return the contents of the linear vector in a double array
	 * @return a double array holding the data
	 */
	public double[] getW();
	
	/**
	 * return the contents of the linear vector in a double array with a bias attached at the end 
	 * @return a double array holding the data and bias
	 */
	public double[] getWbias();
	
	/**
	 * return the contents of the linear vector in a double array with biases attached at the end 
	 * @param biasTerms
	 * 					the number of biases to be attached at the end
	 * @return a double array holding the data and bias
	 */
	public double[] getWbias(int biasTerms);
	
	/**
	 * set the contents of the linear vector with W
	 * @param W
	 * 			double array whose contents are used to load the linear vector
	 */
	public void setW(double[] W);
	
	/**
	 * set the contents of linear vector with W
	 * @param W
	 * 			a list of doubles whose contents are used to load the linear vector
	 */
	public void setW(List<Double> W);
	
	/**
	 * returns the p-norm of the linear vector
	 * @param p
	 * 			the degree of the norm sought
	 * @return the p-norm of the linear vector
	 */
	public double LPNorm(double p);
	
	/**
	 * returns the 0-norm of the linear vector
	 * @return the 0-norm
	 */
	public double L0Norm();
	
	/**
	 * returns the 1-norm of the linear vector
	 * @return the 1-norm
	 */
	public double L1Norm();
	
	/**
	 * returns the 2-norm of the linear vector
	 * @return the 2-norm
	 */
	public double L2Norm();
	
	/**
	 * returns the infinity-norm of the linear vector 
	 * @return the infinity norm
	 */
	public double LInfinityNorm();
	
	/**
	 * multiply each element of the vector by value
	 * @param value
	 * 				the value to be multiplied with each element of the vector
	 * @return an instance of the modified linear vector
	 */
	public LinearVector timesEquals(double value);
	
	/**
	 * increments each element of the vector by value
	 * @param value
	 * 				the value to be added to each element of the vector
	 * @return an instance of the modified linear vector
	 */
	public LinearVector plusEquals(double value);
	public LinearVector plusEquals(LinearVector vect);
	
	/**
	 * decrements each element of the vector by value
	 * @param value
	 * 				the value to be subtracted from each element of the vector
	 * @return an instance of the modified linear vector
	 */
	public LinearVector minusEquals(double value);
	public LinearVector minusEquals(LinearVector vect);
	public LinearVector plusEqualsVectorTimes(LinearVector vect, double factor);
	public LinearVector minusEqualsVectorTimes(LinearVector vect, double factor);


	public LinearVector times(double value);
	public LinearVector plus(double value);
	public LinearVector plus(LinearVector vect);
	public LinearVector minus(double value);
	public LinearVector minus(LinearVector vect);
	public LinearVector plusVectorTimes(LinearVector vect, double factor);
	public LinearVector minusVectorTimes(LinearVector vect, double factor);
	
	public double dot(LinearVector vect);
	public double dot(LinearVector vect, ValueScaling scale);

	/**
	 * element-wise multiplication of two vectors
	 * @param vect
	 *          vector to multiply by
	 * @return an instance of the modified linear vector
	 */
	public LinearVector times(LinearVector vect);
	
	/**
	 * normalize by the L1 norm
	 */
	public void absNormalize();
	
}
