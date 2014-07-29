/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class IWeighted implements IValue
{
    public int[] values;
    public int[] weights;

    public IWeighted(int[] values, int[] weights)
    {
        this.values = values;
        this.weights = weights;
    }

    public IWeighted(int... valuesWithWeights)
    {
        this.values = new int[valuesWithWeights.length / 2];
        this.weights = new int[values.length];

        for (int i = 0; i < values.length; i++)
        {
            values[i] = valuesWithWeights[i * 2];
            weights[i] = valuesWithWeights[i * 2 + 1];
        }
    }

    @Override
    public int getValue(Random random)
    {
        int total = getTotalWeight(weights);
        int selected = random.nextInt(total);

        for (int i = 0; i < weights.length; i++)
        {
            selected -= weights[i];
            if (selected < 0)
            {
                return values[i];
            }
        }

        throw new RuntimeException("Weights have invalid values!");
    }

    public static int getTotalWeight(int[] weights)
    {
        int weight = 0;

        for (int i : weights)
        {
            weight += i;
        }

        return weight;
    }
}
