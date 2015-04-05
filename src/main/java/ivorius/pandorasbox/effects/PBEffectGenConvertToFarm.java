/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToFarm extends PBEffectGenerate
{
    private double cropChance;

    public PBEffectGenConvertToFarm()
    {
    }

    public PBEffectGenConvertToFarm(int time, double range, int unifiedSeed, double cropChance)
    {
        super(time, range, 2, unifiedSeed);
        this.cropChance = cropChance;
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlockState(pos).getBlock();

            if (pass == 0)
            {
                BlockPos posBelow = pos.down();
                Block blockBelow = world.getBlockState(posBelow).getBlock();

                if (blockBelow.isNormalCube(world, posBelow) && Blocks.snow_layer.canPlaceBlockAt(world, pos) && block != Blocks.water)
                {
                    if (random.nextDouble() < cropChance)
                    {
                        int b = world.rand.nextInt(7);

                        if (b == 0)
                        {
                            setBlockSafe(world, posBelow, Blocks.farmland.getDefaultState());
                            setBlockSafe(world, pos, Blocks.pumpkin_stem.getStateFromMeta(world.rand.nextInt(4) + 4));
                        }
                        else if (b == 1)
                        {
                            setBlockSafe(world, posBelow, Blocks.farmland.getDefaultState());
                            setBlockSafe(world, pos, Blocks.melon_stem.getStateFromMeta(world.rand.nextInt(4) + 4));
                        }
                        else if (b == 2)
                        {
                            setBlockSafe(world, posBelow, Blocks.farmland.getDefaultState());
                            setBlockSafe(world, pos, Blocks.wheat.getStateFromMeta(world.rand.nextInt(8)));
                        }
                        else if (b == 3)
                        {
                            setBlockSafe(world, posBelow, Blocks.farmland.getDefaultState());
                            setBlockSafe(world, pos, Blocks.carrots.getStateFromMeta(world.rand.nextInt(8)));
                        }
                        else if (b == 4)
                        {
                            setBlockSafe(world, posBelow, Blocks.farmland.getDefaultState());
                            setBlockSafe(world, pos, Blocks.potatoes.getStateFromMeta(world.rand.nextInt(8)));
                        }
                        else if (b == 5)
                        {
                            setBlockSafe(world, pos, Blocks.hay_block.getDefaultState());
                        }
                        else if (b == 6)
                        {
                            setBlockSafe(world, posBelow, Blocks.water.getDefaultState());
                        }
                    }
                }
            }
            else
            {
                if (canSpawnEntity(world, block, pos))
                {
                    lazilySpawnEntity(world, entity, random, "EntityHorse", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Villager", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Pig", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Cow", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Chicken", 1.0f / (50 * 50), pos);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setDouble("cropChance", cropChance);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        cropChance = compound.getDouble("cropChance");
    }
}
