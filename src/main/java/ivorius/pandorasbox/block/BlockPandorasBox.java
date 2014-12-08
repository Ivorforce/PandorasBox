/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 15.04.14.
 */
public class BlockPandorasBox extends BlockContainer
{
    public BlockPandorasBox()
    {
        super(Material.wood);
        setBlockBounds(0.2f, 0.0f, 0.2f, 0.8f, 0.6f, 0.8f);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityPandorasBox();
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return Item.getItemFromBlock(PBBlocks.pandorasBox);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityPandorasBox)
        {
            ((TileEntityPandorasBox) tileEntity).spawnPandorasBox();
        }

        world.setBlock(x, y, z, Blocks.air);

        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {

    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        return Blocks.planks.getIcon(2, 2);
    }
}
