/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.items;

import ivorius.pandorasbox.block.PBBlocks;
import ivorius.pandorasbox.block.TileEntityPandorasBox;
import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemPandorasBox extends ItemBlock
{
    public ItemPandorasBox(Block block)
    {
        super(block);

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
            else if (par3World.canPlaceEntityOnSide(PBBlocks.pandorasBox, x, y, z, false, side, par2EntityPlayer, par1ItemStack))
            {
                int i1 = this.getMetadata(par1ItemStack.getItemDamage());
                int j1 = PBBlocks.pandorasBox.onBlockPlaced(par3World, x, y, z, side, hitX, hitY, hitZ, i1);

                if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, x, y, z, side, hitX, hitY, hitZ, j1))
                {
                    par3World.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), PBBlocks.pandorasBox.stepSound.func_150496_b(), (PBBlocks.pandorasBox.stepSound.getVolume() + 1.0F) / 2.0F, PBBlocks.pandorasBox.stepSound.getPitch() * 0.8F);
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
            if (!world.setBlock(x, y, z, PBBlocks.pandorasBox))
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
