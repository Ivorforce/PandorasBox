/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToChristmas extends PBEffectGenerate
{
    public PBEffectGenConvertToChristmas()
    {
    }

    public PBEffectGenConvertToChristmas(int time, double range, int unifiedSeed)
    {
        super(time, range, 2, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, int x, int y, int z, double range)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlock(x, y, z);

            if (pass == 0)
            {
                if (isBlockAnyOf(block, Blocks.flowing_water, Blocks.water))
                {
                    setBlockSafe(world, x, y, z, Blocks.ice);
                }
                else if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(world, x, y, z))
                {
                    boolean setSnow = true;

                    if (world.isBlockNormalCubeDefault(x, y - 1, z, false))
                    {
                        if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockAndMetaSafe(world, x, y, z, Blocks.chest, world.rand.nextInt(4));
                            TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(x, y, z);

                            if (tileentitychest != null)
                            {
                                Collection<WeightedRandomChestContent> itemSelection = PandorasBoxHelper.blocksAndItems;
                                WeightedRandomChestContent chestContent = (WeightedRandomChestContent) WeightedRandom.getRandomItem(random, itemSelection);
                                ItemStack stack = chestContent.theItemId.copy();
                                stack.stackSize = chestContent.theMinimumChanceToGenerateItem + random.nextInt(chestContent.theMaximumChanceToGenerateItem - chestContent.theMinimumChanceToGenerateItem + 1);

                                tileentitychest.setInventorySlotContents(world.rand.nextInt(tileentitychest.getSizeInventory()), stack);
                            }

                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockSafe(world, x, y, z, Blocks.redstone_lamp);
                            setBlockSafe(world, x, y - 1, z, Blocks.redstone_block);
                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockSafe(world, x, y, z, Blocks.cake);
                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5f, z + 0.5f, new ItemStack(Items.cookie));
                            entityItem.delayBeforeCanPickup = 20;
                            world.spawnEntityInWorld(entityItem);
                        }
                    }

                    if (setSnow)
                    {
                        setBlockSafe(world, x, y, z, Blocks.snow_layer);
                    }
                }
                else if (block == Blocks.fire)
                {
                    setBlockSafe(world, x, y, z, Blocks.air);
                }
                else if (block == Blocks.flowing_lava)
                {
                    setBlockSafe(world, x, y, z, Blocks.cobblestone);
                }
                else if (block == Blocks.lava)
                {
                    setBlockSafe(world, x, y, z, Blocks.obsidian);
                }
            }
            else
            {
                if (canSpawnEntity(world, block, x, y, z))
                {
                    Entity santa = lazilySpawnEntity(world, entity, random, "Zombie", 1.0f / (150 * 150), x, y, z);
                    if (santa != null)
                    {
                        ItemStack helmet = new ItemStack(Items.leather_helmet);
                        Items.leather_helmet.func_82813_b(helmet, 0xff0000);
                        ItemStack chestPlate = new ItemStack(Items.leather_chestplate);
                        Items.leather_chestplate.func_82813_b(chestPlate, 0xff0000);
                        ItemStack leggings = new ItemStack(Items.leather_leggings);
                        Items.leather_leggings.func_82813_b(leggings, 0xff0000);
                        ItemStack boots = new ItemStack(Items.leather_boots);
                        Items.leather_boots.func_82813_b(boots, 0xff0000);

                        santa.setCurrentItemOrArmor(4, helmet);
                        santa.setCurrentItemOrArmor(3, chestPlate);
                        santa.setCurrentItemOrArmor(2, leggings);
                        santa.setCurrentItemOrArmor(1, boots);
                        santa.setCurrentItemOrArmor(0, new ItemStack(Items.stick));

                        ((EntityLiving) santa).setCustomNameTag("Hogfather");
                    }

                    lazilySpawnEntity(world, entity, random, "SnowMan", 1.0f / (20 * 20), x, y, z);
                }
            }
        }
    }
}
