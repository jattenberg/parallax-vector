/*******************************************************************************
 * Copyright 2012 Josh Attenberg. Not for re-use or redistribution.
 ******************************************************************************/
package com.parallax.ml.vector;

/**
 * VectorType is enum and defines: primaTiveDoubleMap, byteArr,
 * doubleArr,troveDoubleMap
 * 
 * @author Josh Attenberg
 */
public enum VectorType {
	CERNMAP {
		@Override
		public LinearVector getVector(int bins) {
			return new CernPrimativeMapLinearVector(bins);
		}

		@Override
		public LinearVector getVector(int bins, double init) {
			CernPrimativeMapLinearVector out = new CernPrimativeMapLinearVector(
					bins);
			out.initW(init);
			return out;
		}
	},
	BYTEARR {
		@Override
		public LinearVector getVector(int bins) {
			return new ByteArrayLinearVector(bins);
		}

		@Override
		public LinearVector getVector(int bins, double init) {
			ByteArrayLinearVector out = new ByteArrayLinearVector(bins);
			out.initW(init);
			return out;
		}
	},
	DOUBLEARR {
		@Override
		public LinearVector getVector(int bins) {
			return new DoubleArrayLinearVector(bins);
		}

		@Override
		public LinearVector getVector(int bins, double init) {
			DoubleArrayLinearVector out = new DoubleArrayLinearVector(bins);
			out.initW(init);
			return out;
		}
	},
	TROVEDOUBLEMAP {
		@Override
		public LinearVector getVector(int bins) {
			return new TrovePrimativeMapLinearVector(bins);
		}

		@Override
		public LinearVector getVector(int bins, double init) {
			TrovePrimativeMapLinearVector out = new TrovePrimativeMapLinearVector(
					bins);
			out.initW(init);
			return out;
		}
	},
	HASHMAP {

		@Override
		public LinearVector getVector(int bins) {
			return new HashMapLinearVector(bins);
		}

		@Override
		public LinearVector getVector(int bins, double init) {
			LinearVector out = new HashMapLinearVector(bins);
			out.initW(init);
			return out;

		}

	},
	FASTMAP {

		@Override
		public LinearVector getVector(int bins) {
			return new FastMapLinearVector(bins);
		}

		@Override
		public LinearVector getVector(int bins, double init) {
			LinearVector out = new FastMapLinearVector(bins);
			out.initW(init);
			return out;
		}

	},
	HPPCMAP {

		@Override
		public LinearVector getVector(int bins) {
			return new HPPCPrimativeMapLinearVector(bins);
		}

		@Override
		public LinearVector getVector(int bins, double init) {
			LinearVector out = new HPPCPrimativeMapLinearVector(bins);
			out.initW(init);
			return out;
		}

	};

	public abstract LinearVector getVector(int bins);

	public abstract LinearVector getVector(int bins, double init);
}
