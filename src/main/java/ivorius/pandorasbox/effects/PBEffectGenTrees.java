/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import net.minecraft.world.gen.feature.*;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenTrees extends PBEffectGenerateByGenerator
{
    public static final int treeSmall = 0;
    public static final int treeNormal = 1;
    public static final int treeBig = 2;
    public static final int treeHuge = 3;
    public static final int treeJungle = 4;
    public static final int treeComplexNormal = 5;
    public static final int treeTaiga = 6;
    public static final int treeBirch = 7;

    private WorldGenerator[] treeGens;

    public PBEffectGenTrees()
    {
        initializeGens();
    }

    public PBEffectGenTrees(int time, double range, int unifiedSeed, boolean requiresSolidGround, double chancePerBlock, int generatorFlags)
    {
        super(time, range, unifiedSeed, requiresSolidGround, chancePerBlock, generatorFlags);
        initializeGens();
    }

    private void initializeGens()
    {
        treeGens = new WorldGenerator[8];

        treeGens[treeSmall] = new WorldGenTrees(true, 4, 3, 3, false);
        treeGens[treeNormal] = new WorldGenTrees(true, 7, 3, 3, false);
        treeGens[treeBig] = new WorldGenTrees(true, 10, 3, 3, false);
        treeGens[treeHuge] = new WorldGenTrees(true, 15, 3, 3, false);
        treeGens[treeJungle] = new WorldGenMegaJungle(true, 10, 20, 3, 3);
        treeGens[treeComplexNormal] = new WorldGenBigTree(true);
        treeGens[treeTaiga] = new WorldGenTaiga2(true);
        treeGens[treeBirch] = new WorldGenForest(true, false);
    }

    @Override
    public WorldGenerator[] getGenerators()
    {
        return treeGens;
    }
}
