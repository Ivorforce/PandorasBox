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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        if (!world.isRemote)
        {
            IBlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();

            if (pass == 0)
            {
                if (isBlockAnyOf(block, Blocks.FLOWING_WATER, Blocks.WATER))
                {
                    setBlockSafe(world, pos, Blocks.ICE.getDefaultState());
                }
                else if (blockState.getMaterial() == Material.AIR && Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos))
                {
                    boolean setSnow = true;

                    BlockPos posBelow = pos.down();
                    if (world.isBlockNormalCube(posBelow, false))
                    {
                        if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockSafe(world, pos, Blocks.CHEST.getStateFromMeta(world.rand.nextInt(4)));
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
                            setBlockSafe(world, pos, Blocks.REDSTONE_LAMP.getDefaultState());
                            setBlockSafe(world, posBelow, Blocks.REDSTONE_BLOCK.getDefaultState());
                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            setBlockSafe(world, pos, Blocks.CAKE.getDefaultState());
                            setSnow = false;
                        }
                        else if (random.nextInt(10 * 10) == 0)
                        {
                            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(Items.COOKIE));
                            entityItem.setPickupDelay(20);
                            world.spawnEntityInWorld(entityItem);
                        }
                    }

                    if (setSnow)
                    {
                        setBlockSafe(world, pos, Blocks.SNOW_LAYER.getDefaultState());
                    }
                }
                else if (block == Blocks.FIRE)
                {
                    setBlockToAirSafe(world, pos);
                }
                else if (block == Blocks.FLOWING_LAVA)
                {
                    setBlockSafe(world, pos, Blocks.COBBLESTONE.getDefaultState());
                }
                else if (block == Blocks.LAVA)
                {
                    setBlockSafe(world, pos, Blocks.OBSIDIAN.getDefaultState());
                }
            }
            else
            {
                if (canSpawnEntity(world, blockState, pos))
                {
                    Entity santa = lazilySpawnEntity(world, entity, random, "Zombie", 1.0f / (150 * 150), pos);
                    if (santa != null)
                    {
                        ItemStack helmet = new ItemStack(Items.LEATHER_HELMET);
                        Items.LEATHER_HELMET.setColor(helmet, 0xff0000);
                        ItemStack chestPlate = new ItemStack(Items.LEATHER_CHESTPLATE);
                        Items.LEATHER_CHESTPLATE.setColor(chestPlate, 0xff0000);
                        ItemStack leggings = new ItemStack(Items.LEATHER_LEGGINGS);
                        Items.LEATHER_LEGGINGS.setColor(leggings, 0xff0000);
                        ItemStack boots = new ItemStack(Items.LEATHER_BOOTS);
                        Items.LEATHER_BOOTS.setColor(boots, 0xff0000);

                        santa.setItemStackToSlot(EntityEquipmentSlot.HEAD, helmet);
                        santa.setItemStackToSlot(EntityEquipmentSlot.CHEST, chestPlate);
                        santa.setItemStackToSlot(EntityEquipmentSlot.LEGS, leggings);
                        santa.setItemStackToSlot(EntityEquipmentSlot.FEET, boots);
                        santa.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STICK));

                        ((EntityLiving) santa).setCustomNameTag("Hogfather");
                    }

                    lazilySpawnEntity(world, entity, random, "SnowMan", 1.0f / (20 * 20), pos);
                }
            }
        }
    }
}
