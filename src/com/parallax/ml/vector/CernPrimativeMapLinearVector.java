/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.parallax.ml.vector;

import static com.google.common.base.Preconditions.checkElementIndex;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cern.colt.list.IntArrayList;
import cern.colt.map.OpenIntDoubleHashMap;

/**
 * CernPrimativeMapLinearVector
 * 
 * @author Josh Attenberg
 */
class CernPrimativeMapLinearVector extends AbstractLinearVector {

	private static final long serialVersionUID = -9209682475679371367L;
	private OpenIntDoubleHashMap feats;

	/**
	 * Class constructor specifying number of bins to create
	 * 
	 * @param bins
	 *            number of bins
	 */
	CernPrimativeMapLinearVector(int bins) {
		super(bins);
		feats = new OpenIntDoubleHashMap();
	}

	/**
	 * The method gets value
	 * 
	 * @param index
	 *            index
	 * @return
	 */
	@Override
	public double getValue(int index) {
		checkElementIndex(index, numRows);
		return feats.containsKey(index) ? feats.get(index) : 0.;
	}

	/**
	 * The method updates value
	 * 
	 * @param index
	 * @param value
	 */
	@Override
	public void updateValue(int index, double value) {
		checkElementIndex(index, numRows);
		feats.put(index, feats.containsKey(index) ? (feats.get(index) + value)
				: value);
	}

	/**
	 * The method get iterator
	 * 
	 * @return
	 */
	@Override
	public Iterator<Integer> iterator() {
		Iterator<Integer> out = new PrimativeMapIterator(feats);
		return out;
	}

	@Override
	public void resetValue(int index, double value) {
		checkElementIndex(index, numRows);
		feats.put(index, value);

	}

	/**
	 * The method deletes value
	 * 
	 * @param index
	 */
	@Override
	public void delete(int index) {
		checkElementIndex(index, numRows);
		feats.removeKey(index);
	}

	/**
	 * The method
	 * 
	 * @return Set
	 */
	@Override
	public Set<Integer> getFeatureIndicies() {
		Set<Integer> out = new HashSet<Integer>();
		for (Integer i : this) {
			out.add(i);
		}
		return out;
	}

	private class PrimativeMapIterator implements Iterator<Integer> {

		IntArrayList keys;
		int index;
		OpenIntDoubleHashMap map;

		PrimativeMapIterator(OpenIntDoubleHashMap map) {
			this.map = map;
			keys = map.keys();
			index = 0;
		}

		@Override
		public boolean hasNext() {
			if (keys.size() > index)
				return true;
			else
				return false;
		}

		@Override
		public Integer next() {
			index++;
			return keys.get(index - 1);
		}

		@Override
		public void remove() {
			map.removeKey(index);
			index++;

		}

	}

	@Override
	public void initW(double param) {
		feats = new OpenIntDoubleHashMap();
		if (!VectorUtils.floatingPointEquals(param, 0))
			for (int i = 0; i < numRows; i++)
				updateValue(i, param);
	}

}
