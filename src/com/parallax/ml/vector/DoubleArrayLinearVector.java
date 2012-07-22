/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.parallax.ml.vector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * DoubleArrayLinearVector
 *
 * @author Josh Attenberg
 */
class DoubleArrayLinearVector extends AbstractLinearVector
{
	private double[] vec;
	private static final long serialVersionUID = -1680771999652411695L;

    /**
     * Class constructor specifying number of bins to create
     * @param bins number of bins
     */
	public DoubleArrayLinearVector( int bins )
	{
	    super(bins);
	    vec = new double[bins];
	}
	
	@Override
	public void delete(int index)
	{
		vec[index] = 0;
	}

	@Override
	public Set<Integer> getFeatureIndicies()
	{
		Set<Integer> out = new HashSet<Integer>();
		
		for(int i = 0; i < vec.length; i++)
			if(vec[i] != 0)
				out.add(i);
		return out;
	}

	@Override
	public double getValue(int index)
	{
		return vec[index];
	}

	@Override
	public void resetValue(int index, double value)
	{
		vec[index] = value;
	}

	@Override
	public void updateValue(int index, double value)
	{
		vec[index] += value;
	}

	@Override
	public Iterator<Integer> iterator()
	{
		return getFeatureIndicies().iterator();
	}

	@Override
	public void initW(double param)
	{
		vec = new double[numRows];
		if(!VectorUtils.floatingPointEquals(param, 0))
			Arrays.fill(vec, param);
	}

}
