package com.parallax.ml.vector.util;

import java.util.Map;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import com.google.common.collect.Maps;
import com.parallax.ml.vector.LinearVector;
import com.parallax.ml.vector.VectorType;

/**
 * compares time per operation and size at different dimensions and sparsity
 * factors
 * 
 * @author jattenberg
 * 
 */
public class Benchmarks {

	static int dims = 19;
	static int dens = 20;
	
	public static long memoryFootprint(VectorType type, int dimension,
			int stepSize) {

		long before = usedMem();
		LinearVector vector = type.getVector(dimension);
		// loadFactor is the % of dimensions that are non-0
		for (int start = 0; start < dimension; start += stepSize) {
			vector.resetValue(start, 1.);
		}
		return usedMem() - before;
	}

	public static long insertTime(VectorType type, int dimension, int stepSize) {

		LinearVector vector = type.getVector(dimension);
		long before = System.currentTimeMillis();
		// loadFactor is the % of dimensions that are non-0

		for (int start = 0; start < dimension; start += stepSize) {
			vector.resetValue(start, 1.);
		}
		return System.currentTimeMillis() - before;
	}

	private static long usedMem() {
		System.gc();
		return Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
	}

	public static long[][] memoryFootprints(VectorType type) {
		int maxsize = (int) Math.pow(2, dims);
		double mindensity = 1. / Math.pow(2, dens);
		long[][] out = new long[1+dims][];
		int index = 0;
		for (int size = 1; size <= maxsize; size *= 2) {
			long[] mems = new long[1+dens];
			int index2 = 0;
			for (double density = 1; density >= mindensity; density /= 2) {
				int step = (int) Math.max(1, Math.floor(density * size));
				mems[index2] = memoryFootprint(type, size, step);
				System.out.println(type + " d: " + size + " D: " + density
						+ " = " + mems[index2]);
				index2++;
			}
			out[index++] = mems;
		}
		return out;
	}

	public static double[][] inesertTimes(VectorType type) {
		int maxsize = (int) Math.pow(2, dims);
		double mindensity = 1. / Math.pow(2, dens);
		double[][] out = new double[1+dims][];
		int index = 0;
		for (int size = 1; size <= maxsize; size *= 2) {
			double[] mems = new double[1+dens];
			int index2 = 0;
			int last = -1;
			for (double density = 1; density >= mindensity; density /= 2) {
				DescriptiveStatistics stats = new DescriptiveStatistics();

				int steps = (int) Math.max(1, Math.floor(density * size));
				int step = (int) Math.max(1, size / steps);
				if (step == last)
					break;
				for (int i = 0; i < 30; i++) {
					stats.addValue(insertTime(type, size, step)
							/ (double) steps);
				}

				mems[index2++] = stats.getMean();
				last = step;
				System.out.println(type + " d: " + size + " D: " + density
						+ " step: " + step + " = " + stats.getMean());
			}
			out[index++] = mems;
		}
		return out;
	}

	public static void main(String[] args) {
		Map<VectorType, double[]> timeArr = Maps.newHashMap();
		for (VectorType type : VectorType.values()) {
			double[][] times = inesertTimes(type);
			double[] means = new double[dims];
			DescriptiveStatistics[] stats = new DescriptiveStatistics[dims];
			for(int i = 0; i < dims; i++) {
				stats[i] = new DescriptiveStatistics();
				for(int j = 0; j < dens; j++) {
					stats[i].addValue(times[i][j]);
				}
				means[i] = stats[i].getMean();
			}
			timeArr.put(type, means);
		}
		StringBuilder buff = new StringBuilder();
		for (VectorType type : VectorType.values()) {
			buff.append(type + "\t");
		}
		buff.append("\n");
		for(int i = 0; i < dims; i++) {
			for (VectorType type : VectorType.values()) {
				buff.append(timeArr.get(type)[i] + "\t");
			}
			buff.append("\n");
		}
		System.out.println(buff.toString());
	}
}
