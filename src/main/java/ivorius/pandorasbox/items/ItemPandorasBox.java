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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemPandorasBox extends ItemBlock
{
    public ItemPandorasBox(Block block)
    {
        super(block);

        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (!worldIn.isRemote)
            executeRandomEffect(worldIn, playerIn);

        itemStackIn.stackSize--;
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    public static EntityPandorasBox executeRandomEffect(World world, EntityLivingBase entity)
    {
        return PBECRegistry.spawnPandorasBox(world, entity.getRNG(), true, entity);
    }
}
