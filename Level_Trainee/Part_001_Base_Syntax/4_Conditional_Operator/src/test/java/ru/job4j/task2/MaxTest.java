package ru.job4j.task2;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *  MaxTest.
 *
 * @author Alex Proshak (olexandr_proshak@ukr.net)
 */
public class MaxTest {

    /**
     * Test for max task with two arguments.
     */
    @Test
    public void whenOneCompareTwoThenReturnTwo() {
        Max max = new Max();
        int result = max.max(1, 2);
        int expected = 2;
        assertThat(result, is(expected));
    }
    /**
     * Test for max task with three arguments.
     */
    @Test
    public void whenOneCompareTwoCompareThreeThenReturnThree() {
        Max max = new Max();
        int result = max.max(1, 2, 3);
        int expected = 3;
        assertThat(result, is(expected));
    }
}