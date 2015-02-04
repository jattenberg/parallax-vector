package com.dsi.parallax.ml.vector;

import static com.google.common.base.Preconditions.checkElementIndex;

import java.util.Iterator;
import java.util.Set;

import javolution.util.FastMap;

import com.dsi.parallax.ml.vector.util.VectorUtils;

public class FastMapLinearVector extends AbstractLinearVector {

	private static final long serialVersionUID = -6806962101328165058L;
	private final FastMap<Integer, Double> w;

	FastMapLinearVector(int bins) {
		super(bins);
		w = new FastMap<Integer, Double>();
	}

	@Override
	public double getValue(int index) {
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
		return w.keySet();
	}

	@Override
	public void initW(double param) {
		if (!VectorUtils.floatingPointEquals(param, 0))
			for (int i = 0; i < numRows; i++)
				w.put(i, param);

	}

	@Override
	public Iterator<Integer> iterator() {
		return w.keySet().iterator();
	}

        @Override
        LinearVector typedCopy() {
	    return new FastMapLinearVector(numRows);
	}
}
