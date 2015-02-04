/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.dsi.parallax.ml.vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * ByteArrayLinearVector
 *
 * @author Josh Attenberg
 */
class ByteArrayLinearVector extends AbstractLinearVector
{
	private static final long serialVersionUID = -3741733876091652645L;
	private byte[] feats;

    /**
     * Class constructor specifying number of bins to create
     * @param bins number of bins
     */
	ByteArrayLinearVector(int bins)
	{
	    super(bins);
	    feats = new byte[bins];
	}

	@Override
	public void delete(int index)
	{
		feats[index] = 0;
	}

	@Override
	public Set<Integer> getFeatureIndicies()
	{
		Set<Integer> out = new HashSet<Integer>(numRows);
		for(int i = 0; i < numRows; i++)
			if(feats[i] != 0)
				out.add(i);
		return out;
	}

	@Override
	public double getValue(int index)
	{
		return Math.signum(feats[index])*Math.exp(Math.abs(feats[index]));
	}

	@Override
	public void resetValue(int index, double value)
	{
		feats[index] = check(value);
	}
	private byte check(double value)
	{
		value = Math.signum(value)*Math.log(Math.abs(value));
		return ((byte)value)>Byte.MAX_VALUE?Byte.MAX_VALUE:((byte)value)<Byte.MIN_VALUE?Byte.MIN_VALUE:(byte)value;
	}
	@Override
	public void updateValue(int index, double value)
	{
		feats[index] = check(feats[index]+value);
		
	}



	@Override
	public Iterator<Integer> iterator()
	{
		return new ByteArrayIterator(getFeatureIndicies());
	}
	private class ByteArrayIterator implements Iterator<Integer>
	{
		Iterator<Integer> it;
 		public ByteArrayIterator(Set<Integer> indexes)
		{
			it = indexes.iterator();
		}
		@Override
		public boolean hasNext()
		{
			return it.hasNext();
		}

		@Override
		public Integer next()
		{
			return it.next();
		}

		@Override
		public void remove()
		{
			int i = it.next();
			feats[i] = 0;
			
		}
		
	}
	@Override
	public void initW(double param)
	{
		feats = new byte[numRows];
		for(int i = 0; i < numRows; i++)
			updateValue(i, param);
	}

        @Override
	LinearVector typedCopy() {
	    return new ByteArrayLinearVector(numRows);
	}
}
