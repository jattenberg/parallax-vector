/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.dsi.parallax.ml.vector;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;

import java.io.Serializable;
import java.util.List;

import com.dsi.parallax.ml.vector.util.ValueScaling;
import com.google.common.collect.Lists;

/**
 * AbstractLinearVector is abstract class
 * 
 * @author Josh Attenberg
 */
public abstract class AbstractLinearVector implements LinearVector,
		Serializable {

	private static final long serialVersionUID = 5566330222757971576L;
	protected final int numRows;
	protected double L0norm = -1, L1norm = -1, L2norm = -1, LINFnorm = -1; // lazily;
																			// //instantiated

	protected AbstractLinearVector(int bins) {
		checkArgument(bins >= 0);
		this.numRows = bins;
	}

	@Override
	public void setW(double[] W) {
		checkElementIndex(W.length - 1, numRows);
		for (int i = 0; i < W.length; i++)
			updateValue(i, W[i]);
	}

	@Override
	public void setW(List<Double> W) {
		checkElementIndex(W.size() - 1, numRows);
		for (int i = 0; i < W.size(); i++)
			updateValue(i, W.get(i));
	}

	@Override
	public int size() {
		return numRows;
	}

	@Override
	public double[] getW() {
		double[] out = new double[numRows];
		for (int i = 0; i < numRows; i++)
			out[i] = getValue(i);
		return out;
	}

	@Override
	public double[] getWbias() {
		return getWbias(1);
	}

	public double[] getWbias(int biasTerms) {
		double[] out = new double[numRows + biasTerms];
		for (int i = 0; i < numRows; i++)
			out[i] = getValue(i);
		for (int i = numRows; i < numRows + biasTerms; i++)
			out[i] = 1;
		return out;
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		boolean first = true;
		for (int x_i : this) {
			buff.append((first ? "" : ", ") + x_i + ":" + getValue(x_i));
			first = false;
		}

		return buff.toString();
	}

	@Override
	public double LPNorm(double p) {
		if (p <= 0)
			throw new IllegalArgumentException(
					"p must be greater than 0. given: " + p);
		double norm = 0.;
		for (int x : this)
			norm += Math.pow(Math.abs(getValue(x)), p);
		return Math.pow(norm, 1. / p);
	}

	@Override
	public double L0Norm() {
		double norm = 0;
		for (@SuppressWarnings("unused")
		int x : this) {
			norm++;
		}
		return norm;
	}

	@Override
	public double L1Norm() {
		double norm = 0.;
		for (int x : this)
			norm += Math.abs(getValue(x));
		return norm;
	}

	@Override
	public double L2Norm() {
		double norm = 0.;
		for (int x : this) {
			double val = getValue(x);
			norm += Math.pow(Math.abs(val), 2.);
		}
		return Math.pow(norm, 0.5);
	}

	@Override
	public double LInfinityNorm() {
		double max = 0;
		for (int x : this) {
			double next = Math.abs(getValue(x));
			if (next > max)
				max = next;
		}
		return max;
	}

	@Override
	public LinearVector timesEquals(double val) {
		List<Integer> Xs = Lists.newArrayList(this.iterator());
		for (int x : Xs) {
			double changedVal = getValue(x) * val;
			resetValue(x, changedVal);
		}
		return this;
	}

	@Override
	public LinearVector times(double val) {
		LinearVector out = LinearVectorFactory.getVector(numRows);
		for (int x : this) {
			double changedVal = getValue(x) * val;
			out.resetValue(x, changedVal);
		}
		return out;
	}

	@Override
	public LinearVector plusEquals(double val) {
		for (int x = 0; x < size(); x++) {
			double changedVal = getValue(x) + val;
			resetValue(x, changedVal);
		}
		return this;
	}

	@Override
	public LinearVector plus(double val) {
		LinearVector out = LinearVectorFactory.getVector(numRows);
		for (int x = 0; x < size(); x++) {
			double changedVal = getValue(x) + val;
			out.resetValue(x, changedVal);
		}
		return out;
	}

	@Override
	public LinearVector minusEquals(double val) {
		return plusEquals(-val);
	}

	@Override
	public LinearVector minus(double val) {
		return plus(-val);
	}

	@Override
	public LinearVector plusEquals(LinearVector vect) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise addition, given: %s, this is %s",
				vect.size(), size());
		for (int x : vect) {
			updateValue(x, vect.getValue(x));
		}
		return this;
	}

	@Override
	public LinearVector plus(LinearVector vect) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise addition, given: %s, this is %s",
				vect.size(), size());
		LinearVector out = LinearVectorFactory.getVector(numRows);

		for (int x : vect) {
			out.resetValue(x, getValue(x) + vect.getValue(x));
		}
		return out;
	}

	@Override
	public LinearVector minusEquals(LinearVector vect) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise subtraction, given: %s, this is %s",
				vect.size(), size());

		for (int x : vect) {
			updateValue(x, -vect.getValue(x));
		}
		return this;
	}

	@Override
	public LinearVector minus(LinearVector vect) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise addition, given: %s, this is %s",
				vect.size(), size());
		LinearVector out = LinearVectorFactory.getVector(numRows);

		for (int x : vect) {
			out.resetValue(x, getValue(x) - vect.getValue(x));
		}
		return out;
	}

	@Override
	public LinearVector plusEqualsVectorTimes(LinearVector vect, double factor) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise subtraction, given: %s, this is %s",
				vect.size(), size());
		for (int x : vect) {
			updateValue(x, vect.getValue(x) * factor);
		}
		return this;
	}

	@Override
	public LinearVector plusVectorTimes(LinearVector vect, double factor) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise addition, given: %s, this is %s",
				vect.size(), size());
		LinearVector out = LinearVectorFactory.getDenseVector(numRows);

		for (int x : vect) {
			out.resetValue(x, getValue(x) + vect.getValue(x) * factor);
		}
		return out;
	}

	@Override
	public LinearVector minusEqualsVectorTimes(LinearVector vect, double factor) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise subtraction, given: %s, this is %s",
				vect.size(), size());

		for (int x : vect)
			updateValue(x, -vect.getValue(x) * factor);
		return this;
	}

	@Override
	public LinearVector minusVectorTimes(LinearVector vect, double factor) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise subtraction, given: %s, this is %s",
				vect.size(), size());
		LinearVector out = LinearVectorFactory.getDenseVector(numRows);

		for (int x : vect) {
			out.resetValue(x, getValue(x) - vect.getValue(x) * factor);
		}
		return out;
	}

	@Override
	public void absNormalize() {
		double norm = L1Norm();
		if (norm > 0)
			for (int x : this)
				resetValue(x, getValue(x) / norm);
	}

	public double getValue(ValueScaling scaling, int dimension) {
		return scaling.scaleValue(getValue(dimension));
	}

	public double computeLInfNorm(ValueScaling scaling) {
		if (LINFnorm < 0) {
			LINFnorm = 0;
			for (int x_i : this) {
				double val = Math.abs(getValue(scaling, x_i));
				if (val > LINFnorm)
					LINFnorm = val;
			}
		}
		return LINFnorm;
	}

	public double computeLInfNorm() {
		return computeLInfNorm(ValueScaling.UNSCALED);
	}

	public double computeLPnorm(double p, ValueScaling scaling) {
		double tot = 0.;
		for (int x_i : this) {
			tot += Math.pow(Math.abs(getValue(scaling, x_i)), p);
		}
		return Math.pow(tot, 1.0 / p);
	}

	public double computeLPNorm(double p) {
		return computeLPnorm(p, ValueScaling.UNSCALED);
	}

	public double computeL0Norm(ValueScaling scaling) {
		if (L0norm < 0) {
			L0norm = 0;
			for (int x_i : this) {
				if (Math.abs(getValue(scaling, x_i)) > 0) {
					L0norm++;
				}
			}
		}
		return L0norm;
	}

	public double computeL0Norm() {
		return computeL0Norm(ValueScaling.UNSCALED);
	}

	public double computeL1Norm(ValueScaling scaling) {
		if (L1norm < 0) {
			L1norm = 1;
			for (int x_i : this)
				L1norm += Math.abs(getValue(scaling, x_i));

		}
		return L1norm;
	}

	public double computeL1Norm() {
		return computeL1Norm(ValueScaling.UNSCALED);
	}

	public double computeL2Norm(ValueScaling scaling) {
		if (L2norm < 0) {
			double tot = 0;
			for (int x_i : this) {
				double val = getValue(scaling, x_i);
				tot += val * val;
			}
			L2norm = Math.sqrt(tot);
		}
		return L2norm;
	}

	public double computeL2Norm() {
		return computeL2Norm(ValueScaling.UNSCALED);
	}
	
	
	//TODO: override with implementation-specific versions if we feel they'll be more efficient.
	@Override
	public double dot(LinearVector x) {
		double out = 0;
		for(int x_i : this) {
			out += getValue(x_i)*x.getValue(x_i);
		}
		return out;
	}

	@Override
	public double dot(LinearVector x, ValueScaling scale) {
		if(scale == ValueScaling.UNSCALED) {
			return dot(x);
		}
		double out = 0;
		for(int x_i : this) {
			out += getValue(scale, x_i)*scale.scaleValue(x.getValue(x_i));
		}
		return out;
	}

        @Override
	public LinearVector times(LinearVector vect) {
		checkArgument(
				this.size() == vect.size(),
				"vector sizes must match when doing element-wise multiplication, given: %s, this is %s",
				vect.size(), size());

	    for (int x_i : vect) {
		this.resetValue(x_i, vect.getValue(x_i)*getValue(x_i));
	    }

	    return this;
	}

        @Override
        public LinearVector copy() {
	    LinearVector vect = typedCopy();
	    for (int x_i : this) {
		vect.resetValue(x_i, getValue(x_i));
	    }

	    return vect;
	}

        abstract LinearVector typedCopy();
}
