/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.dsi.parallax.ml.vector.util;

import static com.google.common.base.Preconditions.checkElementIndex;

import org.ejml.data.D1Matrix64F;
import org.ejml.data.Matrix64F;
import org.ejml.data.MatrixIterator;

import com.dsi.parallax.ml.vector.LinearVector;
import com.dsi.parallax.ml.vector.LinearVectorFactory;

public class EJMLMatrixDecorator extends D1Matrix64F {

	private static final long serialVersionUID = -685524108978737728L;
	private final LinearVector vect;

	public EJMLMatrixDecorator(LinearVector vect) {
		this.vect = vect;
		numCols = 1;
		numRows = vect.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Matrix64F> T copy() {
		return (T) LinearVectorFactory.getVector(vect);
	}

	@Override
	public double get(int row, int col) {
		checkElementIndex(col, 1);
		return vect.getValue(row);
	}

	@Override
	public int getNumCols() {
		return 1;
	}

	@Override
	public int getNumElements() {
		return vect.size();
	}

	@Override
	public int getNumRows() {
		return vect.size();
	}

	@Override
	public MatrixIterator iterator(boolean rowMajor, int minRow, int minCol,
			int maxRow, int maxCol) {
		return new MatrixIterator(this, rowMajor, minRow, minCol, maxRow,
				maxCol);
	}

	@Override
	public void print() {
		System.out.println(toString());
	}

	@Override
	public void reshape(int rows, int cols, boolean saveValues) {
		throw new UnsupportedOperationException(
				"reshape is not supported for linear vectors");
	}

	@Override
	public void set(int row, int col, double val) {
		checkElementIndex(col, 1);
		vect.resetValue(row, val);
	}

	@Override
	public void set(Matrix64F matrix) {
		checkElementIndex(matrix.numCols, 1);
		checkElementIndex(matrix.numRows, vect.size());
		super.set(matrix);
	}

	@Override
	public double unsafe_get(int row, int col) {
		return vect.getValue(row);
	}

	@Override
	public void unsafe_set(int row, int col, double val) {
		vect.resetValue(row, val);
	}

	@Override
	public int getIndex(int row, int col) {
		checkElementIndex(col, 1);
		return row;
	}

}
