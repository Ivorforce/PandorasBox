/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class ValueHelper
{
    public static int[] getValueRange(IValue value, Random random)
    {
        int[] result = new int[2];

        int val1 = value.getValue(random);
        int val2 = value.getValue(random);

        boolean val1F = val1 < val2;
        result[0] = val1F ? val1 : val2;
        result[1] = val1F ? val2 : val1;

        return result;
    }

    public static double[] getValueRange(DValue value, Random random)
    {
        double[] result = new double[2];

        double val1 = value.getValue(random);
        double val2 = value.getValue(random);

        boolean val1F = val1 < val2;
        result[0] = val1F ? val1 : val2;
        result[1] = val1F ? val2 : val1;

        return result;
    }
}
