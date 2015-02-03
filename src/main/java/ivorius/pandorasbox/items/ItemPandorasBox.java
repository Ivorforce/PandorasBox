/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.items;

import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
            executeRandomEffect(world, entityplayer);

        itemstack.stackSize--;
        return itemstack;
    }

    public static EntityPandorasBox executeRandomEffect(World world, EntityLivingBase entity)
    {
        return PBECRegistry.spawnPandorasBox(world, entity.getRNG(), true, entity);
    }
}
