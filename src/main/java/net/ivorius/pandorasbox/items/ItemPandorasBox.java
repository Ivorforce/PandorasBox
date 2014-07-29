/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.items;

import net.ivorius.pandorasbox.PandorasBox;
import net.ivorius.pandorasbox.block.TileEntityPandorasBox;
import net.ivorius.pandorasbox.effectcreators.PBECRegistry;
import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemPandorasBox extends Item
{
    public static String[] goodEffects = {"animals", "resources", "equipmentSet", "holyRage", "fishRain", "deadCreatures", "experience", "doubleBox", "tripleBox", "epicArmor", "epicTool", "iceAge", "suddenForest", "flyingForest", "normalLand", "buff", "shrine", "shroomify", "tamer", "christmas", "terrarium", "lake", "heroicItem", "oddJungle", "happyFunTimes"};
    public static String[] badEffects = {"mobs", "explode", "deathCage", "launch", "teleport", "sandGrave", "void", "tnt", "transformation", "creatureMix", "waterSphere", "flyingIsland", "hellOnEarth", "endOnEarth", "itemsplosion", "desert", "pyramid", "replace", "worldCannon", "makeThin", "cover", "copyArea", "pasteArea", "halloween", "tntSplosion", "dryness", "target", "fill", "heightNoise", "canyon", "lavaPrison", "blockSplosion", "arrowBarrage", "madGeometry", "exploBats", "exploMobs", "mobTowers", "aquarium", "classic"};

    public ItemPandorasBox()
    {
        super();

        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if (!world.isRemote)
        {
            executeRandomEffect(world, entityplayer);
        }

        itemstack.stackSize--;
        return itemstack;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (ForgeDirection.getOrientation(side) == ForgeDirection.UP)
        {
            Block block = par3World.getBlock(x, y, z);

            if (block == Blocks.snow_layer && (par3World.getBlockMetadata(x, y, z) & 7) < 1)
            {
                side = 1;
            }
            else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(par3World, x, y, z))
            {
                if (side == 0)
                {
                    --y;
                }

                if (side == 1)
                {
                    ++y;
                }

                if (side == 2)
                {
                    --z;
                }

                if (side == 3)
                {
                    ++z;
                }

                if (side == 4)
                {
                    --x;
                }

                if (side == 5)
                {
                    ++x;
                }
            }

            if (par1ItemStack.stackSize == 0)
            {
                return false;
            }
            else if (!par2EntityPlayer.canPlayerEdit(x, y, z, side, par1ItemStack))
            {
                return false;
            }
            else if (y == 255)
            {
                return false;
            }
            else if (par3World.canPlaceEntityOnSide(PandorasBox.blockPandorasBox, x, y, z, false, side, par2EntityPlayer, par1ItemStack))
            {
                int i1 = this.getMetadata(par1ItemStack.getItemDamage());
                int j1 = PandorasBox.blockPandorasBox.onBlockPlaced(par3World, x, y, z, side, hitX, hitY, hitZ, i1);

                if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, x, y, z, side, hitX, hitY, hitZ, j1))
                {
                    par3World.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), PandorasBox.blockPandorasBox.stepSound.func_150496_b(), (PandorasBox.blockPandorasBox.stepSound.getVolume() + 1.0F) / 2.0F, PandorasBox.blockPandorasBox.stepSound.getPitch() * 0.8F);
                    --par1ItemStack.stackSize;
                }

                return true;
            }
            else
            {
                return false;
            }
        }

        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, x, y, z, side, hitX, hitY, hitZ);
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (ForgeDirection.getOrientation(side) == ForgeDirection.UP)
        {
            if (!world.setBlock(x, y, z, PandorasBox.blockPandorasBox))
            {
                return false;
            }

            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityPandorasBox)
            {
                ((TileEntityPandorasBox) tileEntity).boxRotationYaw = player.rotationYaw + 180.0f;
            }

            return true;
        }

        return false;
    }

    public static EntityPandorasBox executeRandomEffect(World world, EntityLivingBase entity)
    {
        return PBECRegistry.spawnPandorasBox(world, entity.getRNG(), true, entity);
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {

    }
}
