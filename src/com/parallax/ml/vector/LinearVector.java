/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.parallax.ml.vector;

import java.util.List;
import java.util.Set;
/**
 * TODO: make a clone method
 * @author jattenberg
 *
 */
public interface LinearVector extends Iterable<Integer>
{
	public double getValue(int index);
	/**
	 * takes whatever value is at index and increments by value
	 */
	public void updateValue(int index, double value);
	public void resetValue(int index, double value);
	public void delete(int index);
	public Set<Integer> getFeatureIndicies();
	public int size();
	public void initW(double param);
	public double[] getW();
	public double[] getWbias();
	public double[] getWbias(int biasTerms);
	public void setW(double[] W);
	public void setW(List<Double> W);
	
	public double LPNorm(double p);
	public double L0Norm();
	public double L1Norm();
	public double L2Norm();
	public double LInfinityNorm();
	
	public LinearVector timesEquals(double value);
	public LinearVector plusEquals(double value);
	public LinearVector plusEquals(LinearVector vect);
	public LinearVector minusEquals(double value);
	public LinearVector minusEquals(LinearVector vect);
	public LinearVector plusEqualsVectorTimes(LinearVector vect, double factor);
	public LinearVector minusEqualsVectorTimes(LinearVector vect, double factor);
	/**
	 * normalize by the L1 norm
	 */
	public void absNormalize();
	
}
