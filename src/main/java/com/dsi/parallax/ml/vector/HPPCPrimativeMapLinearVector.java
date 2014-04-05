package com.dsi.parallax.ml.vector;

import static com.google.common.base.Preconditions.checkElementIndex;

import java.util.Iterator;
import java.util.Set;

import com.carrotsearch.hppc.IntDoubleOpenHashMap;
import com.carrotsearch.hppc.cursors.IntDoubleCursor;
import com.dsi.parallax.ml.vector.util.VectorUtils;
import com.google.common.collect.Sets;

public class HPPCPrimativeMapLinearVector extends AbstractLinearVector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6410097220567986641L;
	private final IntDoubleOpenHashMap w;

	HPPCPrimativeMapLinearVector(int bins) {
		super(bins);
		w = new IntDoubleOpenHashMap();
	}

	@Override
	public double getValue(int index) {
		checkElementIndex(index, numRows);
		return w.containsKey(index) ? w.get(index) : 0;
	}

	@Override
	public void updateValue(int index, double value) {
		checkElementIndex(index, numRows);
		double current = getValue(index);
		if (VectorUtils.floatingPointEquals(current + value, 0))
			w.remove(index);
		else
			w.put(index, current + value);
	}

	@Override
	public void resetValue(int index, double value) {
		checkElementIndex(index, numRows);
		if (VectorUtils.floatingPointEquals(value, 0))
			w.remove(index);
		else
			w.put(index, value);
	}

	@Override
	public void delete(int index) {
		checkElementIndex(index, numRows);
		if (w.containsKey(index))
			w.remove(index);
	}

	@Override
	public Set<Integer> getFeatureIndicies() {
		return Sets.newHashSet(iterator());
	}

	@Override
	public void initW(double param) {
		if (!VectorUtils.floatingPointEquals(param, 0))
			for (int i = 0; i < numRows; i++)
				w.put(i, param);
	}

	@Override
	public Iterator<Integer> iterator() {
		return new HPPCIterator();
	}

	private class HPPCIterator implements Iterator<Integer> {

		Iterator<IntDoubleCursor> cursor;

		HPPCIterator() {
			cursor = w.iterator();
		}

		@Override
		public boolean hasNext() {
			return cursor.hasNext();
		}

		@Override
		public Integer next() {
			return cursor.next().key;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(
					"remove not supported for Linear Vector Iterator");
		}

	}

}
