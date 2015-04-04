/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.utils.PBNBTHelper;
import ivorius.pandorasbox.worldgen.WorldGenMegaJungleCustom;
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

        compound.setString("trunkBlock", PBNBTHelper.storeBlockString(trunkBlock));
        compound.setString("leafBlock", PBNBTHelper.storeBlockString(leafBlock));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        trunkBlock = PBNBTHelper.getBlock(compound.getString("trunkBlock"));
        leafBlock = PBNBTHelper.getBlock(compound.getString("leafBlock"));

        initializeGens();
    }
}
