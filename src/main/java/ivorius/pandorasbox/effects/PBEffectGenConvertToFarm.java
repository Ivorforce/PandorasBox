/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, int x, int y, int z, double range)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlock(x, y, z);

            if (pass == 0)
            {
                Block blockBelow = world.getBlock(x, y - 1, z);

                if (blockBelow.isNormalCube(world, x, y - 1, z) && Blocks.snow_layer.canPlaceBlockAt(world, x, y, z) && block != Blocks.water)
                {
                    if (random.nextDouble() < cropChance)
                    {
                        int b = world.rand.nextInt(7);

                        if (b == 0)
                        {
                            setBlockSafe(world, x, y - 1, z, Blocks.farmland);
                            setBlockAndMetaSafe(world, x, y, z, Blocks.pumpkin_stem, world.rand.nextInt(4) + 4);
                        }
                        else if (b == 1)
                        {
                            setBlockSafe(world, x, y - 1, z, Blocks.farmland);
                            setBlockAndMetaSafe(world, x, y, z, Blocks.melon_stem, world.rand.nextInt(4) + 4);
                        }
                        else if (b == 2)
                        {
                            setBlockSafe(world, x, y - 1, z, Blocks.farmland);
                            setBlockAndMetaSafe(world, x, y, z, Blocks.wheat, world.rand.nextInt(8));
                        }
                        else if (b == 3)
                        {
                            setBlockSafe(world, x, y - 1, z, Blocks.farmland);
                            setBlockAndMetaSafe(world, x, y, z, Blocks.carrots, world.rand.nextInt(8));
                        }
                        else if (b == 4)
                        {
                            setBlockSafe(world, x, y - 1, z, Blocks.farmland);
                            setBlockAndMetaSafe(world, x, y, z, Blocks.potatoes, world.rand.nextInt(8));
                        }
                        else if (b == 5)
                        {
                            setBlockAndMetaSafe(world, x, y, z, Blocks.hay_block, 0);
                        }
                        else if (b == 6)
                        {
                            setBlockAndMetaSafe(world, x, y - 1, z, Blocks.water, 0);
                        }
                    }
                }
            }
            else
            {
                if (canSpawnEntity(world, block, x, y, z))
                {
                    lazilySpawnEntity(world, entity, random, "EntityHorse", 1.0f / (50 * 50), x, y, z);
                    lazilySpawnEntity(world, entity, random, "Villager", 1.0f / (50 * 50), x, y, z);
                    lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (50 * 50), x, y, z);
                    lazilySpawnEntity(world, entity, random, "Pig", 1.0f / (50 * 50), x, y, z);
                    lazilySpawnEntity(world, entity, random, "Cow", 1.0f / (50 * 50), x, y, z);
                    lazilySpawnEntity(world, entity, random, "Chicken", 1.0f / (50 * 50), x, y, z);
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
