package com.techelevator.view;

import junit.framework.TestCase;
import org.junit.Assert;

public class SnackTest extends TestCase {

    public void testDisplayQuantity() {
        Snack testSnack = new Candy("A3", "SnackTest", 0.0);
        testSnack.setQuantity(0);
        System.out.println(testSnack.displayQuantity());
        Assert.assertEquals("SnackTest Quantity: SOLD OUT", testSnack.displayQuantity());
        Snack testSnack2 = new Chip("A4", "SnackTest2", 0.0);
        System.out.println(testSnack2.displayQuantity());
        Assert.assertEquals("SnackTest2 Quantity: 5", testSnack2.displayQuantity());
    }
}