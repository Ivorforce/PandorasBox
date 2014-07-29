/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.worldgen.WorldGenMegaJungleCustom;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenTreesOdd extends PBEffectGenerateByGenerator
{
    public static final int treeJungle = 0;

    public Block trunkBlock;
    public Block leafBlock;

    private WorldGenerator[] treeGens;

    public PBEffectGenTreesOdd()
    {

    }

    public PBEffectGenTreesOdd(int time, double range, int unifiedSeed, boolean requiresSolidGround, double chancePerBlock, int generatorFlags, Block trunkBlock, Block leafBlock)
    {
        super(time, range, unifiedSeed, requiresSolidGround, chancePerBlock, generatorFlags);
        this.trunkBlock = trunkBlock;
        this.leafBlock = leafBlock;
        initializeGens();
    }

    private void initializeGens()
    {
        treeGens = new WorldGenerator[1];
        treeGens[treeJungle] = new WorldGenMegaJungleCustom(true, 10, 20, 3, 3, trunkBlock, leafBlock);
    }

    @Override
    public WorldGenerator[] getGenerators()
    {
        return treeGens;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("trunkBlock", Block.blockRegistry.getNameForObject(trunkBlock));
        compound.setString("leafBlock", Block.blockRegistry.getNameForObject(leafBlock));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        trunkBlock = (Block) Block.blockRegistry.getObject(compound.getString("trunkBlock"));
        leafBlock = (Block) Block.blockRegistry.getObject(compound.getString("leafBlock"));

        initializeGens();
    }
}
