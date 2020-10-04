package com.example.puppypals_foryourpooch.IT19241906;

import com.example.puppypals_foryourpooch.pup_add_page;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnitTestClass {

    private pup_add_page adsCount;

    @Before
    public void setUp(){

        adsCount = new pup_add_page();

    }

    @Test
    public void adsCount_isCorrect(){

        long result = adsCount.getAdsCount();

        Assert.assertEquals(0 , result , 0.001);

    }


}
