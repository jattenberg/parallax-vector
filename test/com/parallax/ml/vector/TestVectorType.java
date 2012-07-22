package com.parallax.ml.vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestVectorType {

	int bins = 5;
	double init = 1.2345;
	@Test
	public void testBuilds() {
		LinearVector vector = VectorType.BYTEARR.getVector(bins);
		assertTrue(vector instanceof ByteArrayLinearVector);
		assertEquals(bins, vector.size());
		
		vector = VectorType.CERNMAP.getVector(bins);
		assertTrue(vector instanceof CernPrimativeMapLinearVector);
		assertEquals(bins, vector.size());
		
		vector = VectorType.DOUBLEARR.getVector(bins);
		assertTrue(vector instanceof DoubleArrayLinearVector);
		assertEquals(bins, vector.size());
		
		vector = VectorType.FASTMAP.getVector(bins);
		assertTrue(vector instanceof FastMapLinearVector);
		assertEquals(bins, vector.size());
		
		vector = VectorType.HPPCMAP.getVector(bins);
		assertTrue(vector instanceof HPPCPrimativeMapLinearVector);
		assertEquals(bins, vector.size());
		
		vector = VectorType.TROVEMAP.getVector(bins);
		assertTrue(vector instanceof TrovePrimativeMapLinearVector);
		assertEquals(bins, vector.size());
	}

	@Test
	
	public void testInits() {
		for(VectorType type : VectorType.values()) {
			LinearVector vector = type.getVector(bins, init);
			assertEquals(bins, vector.size());
			if(type.equals(VectorType.BYTEARR))
				continue;
			for(int i = 0; i < bins; i++)
				assertEquals(init, vector.getValue(i), 0);
		}
	}
	
}
