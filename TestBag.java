package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import collections.Bag;

import org.junit.Assert;

class TestBag {


	  private int[] values1 = {3, -2 , 45, 2 , 3, 5, -7, 10, 3, -17};
	  private int[] values2 = {3, -2 , 56, 2 , 3, 5, 22, 10, 3, -17}; // somewhat different
	  
	  private Bag bag;
	
	// Method cardinality is tested indirectly in all test methods.
	
	  @BeforeEach
	  void setUp() throws Exception {
	    bag = new Bag();
	  }

	  @Test
	  void testEmptyBag() {
	    assertEquals(0, bag.length(), "Length empty bag is 0");
	    int[] elems = bag.getElems();
	    assertEquals(0, elems.length, "Empty bag has no elements");
	  }
	  
	
	@Test
	void testAddToEmptyBag() {
		boolean res = bag.add(1);
		assertEquals(1, bag.cardinality(1));
    	assertEquals(1, bag.length(), "The new length is the old length plus 1");
    	assertEquals(true, res, "returns true");
	}
	
	@Test
	void testAddToNonEmptyBag() {
		bag.add(1); bag.add(2); bag.add(2); bag.add(3);
		
		boolean res = bag.add(2);
		assertEquals(3, bag.cardinality(2));
    	assertEquals(5, bag.length(), "The new length is the old length plus 1");
    	assertEquals(true, res, "returns true");
	}
	

	
	 @Test
 	 void testRemoveHappyPath() {
		 bag.add(1); bag.add(2); bag.add(2); bag.add(3);
		 boolean res = bag.remove(1);
		 assertEquals(0, bag.cardinality(1));
	     assertEquals(3, bag.length(), "The new length is the old length minus 1");
	     assertEquals(true, res, "returns true");
		 // TODO: add invariant test
	     
	     res = bag.remove(2);
	     assertEquals(1, bag.cardinality(2), "The cadinality of 2 should be 1" );
	     assertEquals(2, bag.length(), "The new length is the old length minus 1");
	     assertEquals(true, res, "returns true");
		 // TODO: add invariant test
	 }
	 
	 @Test
	 void testRemoveNonHappyPath() {
	    // Length = 0
	    boolean res = bag.remove(1);
	    assertEquals(0, bag.length(), "The new length is the old (= 0)");
	    assertEquals(false, res, "returns false");
	    // TODO: add invariant test
	    
	    // Bag does not contain el
	    bag.add(1); bag.add(2); bag.add(2); bag.add(3);
		res = bag.remove(4);
        assertEquals(4, bag.length(), "The new length is the old length (= 4)");
		assertEquals(false, res, "returns false");
		// TODO: add invariant test
	 }


	 
	 
	 @Test
	  void testGetElemsEmpyt() {
	    int[] expected = {};
	    assertArrayEquals(expected, bag.getElems(), "getElems on empty bag");
	  }
	  
	  @Test
	  void testGetElemsFilled() {
	    fillBag(bag, values1);
	    // omdat niet gespecificeerd is wat de volgorde van de elementen is, moet gesorteerd vergeleken worden. 
	    assertArrayEquals(sortedCopy(values1), sortedCopy(bag.getElems()), "getElems on non-empty bag");
	  }
	 
	  @Test
	  void testCardinalityEmptyBag() {
	    assertEquals(0, bag.cardinality(4), "Cardinality of element in an empty bag");
	  }
	  
	  @Test
	  void testCardinalityFilledBagZero() {
	    fillBag(bag, values1); 
	    assertEquals(0, bag.cardinality(100), "Cardinality of element not occuring in a bag");
	  }
	  
	  @Test
	  void testCardinalityFilledBagOne() {
	    fillBag(bag, values1); 
	    assertEquals(1, bag.cardinality(-17), "Cardinality of element occuring once in a bag");
	  }
	  
	  @Test
	  void testCardinalityFilledBagMOre() {
	    fillBag(bag, values1);
	    assertEquals(3, bag.cardinality(3), "Cardinality of element in a bag");
	  }
	  
	  @Test
	  void testEqualsNull() {
	    assertFalse(bag.equals(null), "Equals with null (empty bag)");
	    fillBag(bag, values1);
	    assertFalse(bag.equals(null), "Equals with null (filled bag)");
	  }
	 
	  @Test
	  void testEqualsObject() {
	    Object obj = new Object();
	    assertFalse(bag.equals(obj), "Equals with non-bag object (empty bag)");
	    fillBag(bag, values1);
	    assertFalse(bag.equals(obj), "Equals with non-bag object (filled bag)");
	  }

	  @Test
	  void testEqualsEmpty() {
	    Bag otherBag = new Bag();
	    assertTrue(bag.equals(otherBag), "Equals with both empty bags");
	    fillBag(otherBag, values1);
	    assertFalse(bag.equals(otherBag), "Equals with empty bag and non empty otherbag");
	    assertFalse(otherBag.equals(bag), "Equals with filled bag and empty otherbag");
	  }

	  @Test
	  void testEqualsDifferentLength() {
	    Bag otherBag = new Bag();
	    fillBag(bag, values1);
	    fillBag(otherBag, values1);
	    fillBag(otherBag, values1);
	    assertFalse(bag.equals(otherBag), "Equals unequal length");
	    assertFalse(otherBag.equals(bag), "Equals unequal length");
	  }
	  
	  @Test
	  void testEqualsEqualLength() {
	    Bag otherBag = new Bag();
	    fillBag(bag, values1);
	    assertTrue(bag.equals(bag), "Equals same objects");
	    
	    fillBag(otherBag, values1);
	    assertTrue(bag.equals(otherBag), "Equals same filling");
	    
	    otherBag = new Bag();
	    for (int i = values1.length - 1; i >= 0; i--) {
	      otherBag.add(values1[i]);
	    }
	    assertTrue(bag.equals(otherBag), "Equals different add-order");
	    
	    otherBag = new Bag();
	    fillBag(otherBag, values2);
	    assertFalse(bag.equals(otherBag), "Equals same lengt, other contents");
	    assertFalse(otherBag.equals(bag), "Equals same lengt, other contents");
	  }

	 private static void fillBag(Bag bag, int[] with) {
		    for (int i = 0; i < with.length; i++) {
		      bag.add(with[i]);
		    }
		  }
		    
	private static int[] sortedCopy(int[] a) {
	  int[] c = a.clone();
		    Arrays.sort(c);
		    return c;
		  }



}
