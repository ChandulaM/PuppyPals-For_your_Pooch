package com.example.puppypals_foryourpooch.IT19069814;

import com.example.puppypals_foryourpooch.DistanceCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DistanceCalculatorTest {

    private DistanceCalculator distanceCalculator;

    @Before
    public void setUp(){
        distanceCalculator = new DistanceCalculator();
    }

    @Test
    public void distance_isCorrect(){
        //distance from coordinates 0.00, 0.00 to SLIIT Kandy Campus
        double result = distanceCalculator.distance(0, 7.275440 ,0, 80.612873);
        Assert.assertEquals(8972.221409523767 , result, 0.001);
    }

}
