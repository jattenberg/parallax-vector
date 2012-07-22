/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.parallax.ml.vector.util;


public enum ValueScaling {
	/**
	 * perform no scaling on the input
	 */
	UNSCALED {
		@Override
		public double scaleValue(double rawValue) {
			return rawValue;
		}
	},
	/**
	 * take the log of the input value
	 * return rawValue > 0 ? Math.log(rawValue) : 0;
	 */
	LOGSCALED {
		@Override
		public double scaleValue(double rawValue) {
			return rawValue > 0 ? Math.log(rawValue) : 0;
		}
	},
	/**
	 * scale according to log(1+inputValue)
	 * return rawValue > 0 ? Math.log(1 + rawValue) : rawValue;
	 */
	TDISTSCALE {
		@Override
		public double scaleValue(double rawValue) {
			return rawValue > 0 ? Math.log(1 + rawValue) : rawValue;
		}
	},
	/**
	 * scale according to log(1+inputValue)/inputValue
	 * return rawValue > 0 ? Math.log(1 + rawValue) / rawValue : rawValue;
	 */
	PRESERVING {
		@Override
		public double scaleValue(double rawValue) {
			return rawValue > 0 ? Math.log(1 + rawValue) / rawValue : rawValue;
		}
	},
	/**
	 * 1 if input is non-zero, else 0
	 * return floatingPointEquals(0., rawValue) ? 0. : 1.;
	 */
	BINARY {
		@Override
		public double scaleValue(double rawValue) {
			return VectorUtils.floatingPointEquals(0., rawValue) ? 0. : 1.;
		}
	},
	/**
	 * take the absolue value of the input value
	 * return Math.abs(rawValue);
	 */
	ABS {
		@Override
		public double scaleValue(double rawValue) {
			return Math.abs(rawValue);
		}
	};

	/**
	 * apply a numeric scaling on the input value
	 * 
	 * @param rawValue
	 *            the value to be scaled
	 * @return the value after the specified scaling has been applied.
	 */
	public abstract double scaleValue(double rawValue);
}
