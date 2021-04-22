package com.example.lab_0_tests;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect()
    {
        MainActivity m = new MainActivity();
        assertEquals(4, m.max(3, 4));
        assertEquals(5, m.max(5, 4));
        assertEquals(3, m.min(3, 4));
        assertEquals(4, m.min(5, 4));
        assertEquals(4, 2 + 2);
    }
}