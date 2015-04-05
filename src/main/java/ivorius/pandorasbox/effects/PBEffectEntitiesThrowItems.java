/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.PBNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesThrowItems extends PBEffectEntityBased
{
    public double chancePerItem;
    public double itemDeletionChance;

    public ItemStack[] smuggledInItems;

    public PBEffectEntitiesThrowItems()
    {
    }

    public PBEffectEntitiesThrowItems(int maxTicksAlive, double range, double chancePerItem, double itemDeletionChance, ItemStack[] smuggledInItems)
    {
        super(maxTicksAlive, range);
        this.chancePerItem = chancePerItem;
        this.itemDeletionChance = itemDeletionChance;
        this.smuggledInItems = smuggledInItems;
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        if (!world.isRemote && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;

            Random itemRandom = new Random(entity.getEntityId());
            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
            {
                double expectedThrow = itemRandom.nextDouble();
                if (newRatio >= expectedThrow && prevRatio < expectedThrow)
                {
                    ItemStack stack = player.inventory.getStackInSlot(i);
                    if (stack != null)
                    {
                        if (random.nextDouble() < chancePerItem)
                        {
                            if (random.nextDouble() >= itemDeletionChance)
                            {
                                throwItem(entity, world, stack);
                            }

                            player.inventory.setInventorySlotContents(i, null);
                        }
                    }
                }
            }

            for (int i = 0; i < smuggledInItems.length; i++)
            {
                double expectedThrow = itemRandom.nextDouble();
                if (newRatio >= expectedThrow && prevRatio < expectedThrow)
                {
                    throwItem(entity, world, smuggledInItems[i]);
                }
            }
        }
    }

    private void throwItem(Entity entity, World world, ItemStack itemStack)
    {
        EntityItem entityItem = new EntityItem(world, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, itemStack);
        entityItem.setPickupDelay(20);
        entityItem.motionX = (world.rand.nextDouble() - world.rand.nextDouble()) * 1.0;
        entityItem.motionZ = (world.rand.nextDouble() - world.rand.nextDouble()) * 1.0;
        entityItem.motionY = world.rand.nextDouble();
        world.spawnEntityInWorld(entityItem);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setDouble("chancePerItem", chancePerItem);
        compound.setDouble("itemDeletionChance", itemDeletionChance);
        PBNBTHelper.writeNBTStacks("smuggledInItems", smuggledInItems, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        chancePerItem = compound.getDouble("chancePerItem");
        itemDeletionChance = compound.getDouble("itemDeletionChance");
        smuggledInItems = PBNBTHelper.readNBTStacks("smuggledInItems", compound);
    }
}
