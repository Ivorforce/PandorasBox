/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.RandomizedItemStack;
import ivorius.ivtoolkit.random.WeightedSelector;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlockState(pos).getBlock();

            if (pass == 0)
            {
                if (isBlockAnyOf(block, Blocks.flowing_water, Blocks.water))
                {
                    setBlockSafe(world, pos, Blocks.ice.getDefaultState());
                }
                else if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(world, pos))
                {
                    boolean setSnow = true;

                    BlockPos posBelow = pos.down();
                    if (world.isBlockNormalCube(posBelow, false))
                    {
                        if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockSafe(world, pos, Blocks.chest.getStateFromMeta(world.rand.nextInt(4)));
                            TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(pos);

                            if (tileentitychest != null)
                            {
                                Collection<RandomizedItemStack> itemSelection = PandorasBoxHelper.blocksAndItems;
                                RandomizedItemStack chestContent = WeightedSelector.selectItem(random, itemSelection);
                                ItemStack stack = chestContent.itemStack.copy();
                                stack.stackSize = chestContent.min + random.nextInt(chestContent.max - chestContent.min + 1);

                                tileentitychest.setInventorySlotContents(world.rand.nextInt(tileentitychest.getSizeInventory()), stack);
                            }

                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockSafe(world, pos, Blocks.redstone_lamp.getDefaultState());
                            setBlockSafe(world, posBelow, Blocks.redstone_block.getDefaultState());
                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockSafe(world, pos, Blocks.cake.getDefaultState());
                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(Items.cookie));
                            entityItem.setPickupDelay(20);
                            world.spawnEntityInWorld(entityItem);
                        }
                    }

                    if (setSnow)
                    {
                        setBlockSafe(world, pos, Blocks.snow_layer.getDefaultState());
                    }
                }
                else if (block == Blocks.fire)
                {
                    setBlockToAirSafe(world, pos);
                }
                else if (block == Blocks.flowing_lava)
                {
                    setBlockSafe(world, pos, Blocks.cobblestone.getDefaultState());
                }
                else if (block == Blocks.lava)
                {
                    setBlockSafe(world, pos, Blocks.obsidian.getDefaultState());
                }
            }
            else
            {
                if (canSpawnEntity(world, block, pos))
                {
                    Entity santa = lazilySpawnEntity(world, entity, random, "Zombie", 1.0f / (150 * 150), pos);
                    if (santa != null)
                    {
                        ItemStack helmet = new ItemStack(Items.leather_helmet);
                        Items.leather_helmet.setColor(helmet, 0xff0000);
                        ItemStack chestPlate = new ItemStack(Items.leather_chestplate);
                        Items.leather_chestplate.setColor(chestPlate, 0xff0000);
                        ItemStack leggings = new ItemStack(Items.leather_leggings);
                        Items.leather_leggings.setColor(leggings, 0xff0000);
                        ItemStack boots = new ItemStack(Items.leather_boots);
                        Items.leather_boots.setColor(boots, 0xff0000);

                        santa.setCurrentItemOrArmor(4, helmet);
                        santa.setCurrentItemOrArmor(3, chestPlate);
                        santa.setCurrentItemOrArmor(2, leggings);
                        santa.setCurrentItemOrArmor(1, boots);
                        santa.setCurrentItemOrArmor(0, new ItemStack(Items.stick));

                        ((EntityLiving) santa).setCustomNameTag("Hogfather");
                    }

                    lazilySpawnEntity(world, entity, random, "SnowMan", 1.0f / (20 * 20), pos);
                }
            }
        }
    }
}
