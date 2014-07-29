/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectSpawnItemStacks;
import ivorius.pandorasbox.random.*;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnItems implements PBEffectCreator
{
    public IValue number;
    public IValue ticksPerItem;
    public List<WeightedRandomChestContent> items;
    public ValueThrow valueThrow;
    public ValueSpawn valueSpawn;

    public PBECSpawnItems(IValue number, IValue ticksPerItem, List<WeightedRandomChestContent> items, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        this.number = number;
        this.ticksPerItem = ticksPerItem;
        this.items = items;
        this.valueThrow = valueThrow;
        this.valueSpawn = valueSpawn;
    }

    public PBECSpawnItems(IValue number, IValue ticksPerItem, List<WeightedRandomChestContent> items)
    {
        this(number, ticksPerItem, items, defaultThrow(), null);
    }

    public static ValueThrow defaultThrow()
    {
        return new ValueThrow(new DLinear(0.05, 0.2), new DLinear(0.2, 1.0));
    }

    public static ValueSpawn defaultShowerSpawn()
    {
        return new ValueSpawn(new DLinear(5.0, 30.0), new DConstant(150.0));
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int number = this.number.getValue(random);
        int ticksPerItem = this.ticksPerItem.getValue(random);

        ItemStack[] stacks = getItemStacks(random, items, number, random.nextInt(3) != 0, true, 0, false);
        return constructEffect(random, stacks, number * ticksPerItem + 1, valueThrow, valueSpawn);
    }

    public static PBEffect constructEffect(Random random, ItemStack[] stacks, int time, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        boolean canSpawn = valueSpawn != null;
        boolean canThrow = valueThrow != null;

        if (canThrow && (!canSpawn || random.nextBoolean()))
        {
            PBEffectSpawnItemStacks effect = new PBEffectSpawnItemStacks(time, stacks);
            PBECSpawnEntities.setEffectThrow(effect, random, valueThrow);
            return effect;
        }
        else if (canSpawn)
        {
            PBEffectSpawnItemStacks effect = new PBEffectSpawnItemStacks(time, stacks);
            PBECSpawnEntities.setEffectSpawn(effect, random, valueSpawn);
            return effect;
        }

        throw new RuntimeException("Both spawnRange and throwStrength are null!");
    }

    public static ItemStack[] getItemStacks(Random random, List<WeightedRandomChestContent> items, int number, boolean split, boolean mixUp, int enchantLevel, boolean giveNames)
    {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int i = 0; i < number; i++)
        {
            WeightedRandomChestContent wrcc = mixUp ? ((WeightedRandomChestContent) WeightedRandom.getRandomItem(random, items)) : items.get(i);
            ItemStack stack = wrcc.theItemId.copy();
            stack.stackSize = wrcc.theMinimumChanceToGenerateItem + random.nextInt(wrcc.theMaximumChanceToGenerateItem - wrcc.theMinimumChanceToGenerateItem + 1);

            if (enchantLevel > 0)
            {
                List enchantments = EnchantmentHelper.buildEnchantmentList(random, stack, enchantLevel);

                if (enchantments == null)
                {
                    enchantments = EnchantmentHelper.buildEnchantmentList(random, new ItemStack(Items.iron_axe), enchantLevel);
                }

                if (enchantments != null)
                {
                    for (Object enchantment : enchantments)
                    {
                        EnchantmentData enchantmentdata = (EnchantmentData) enchantment;

                        if (stack.getItem() == Items.enchanted_book)
                        {
                            Items.enchanted_book.addEnchantment(stack, enchantmentdata);
                        }
                        else
                        {
                            stack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                        }
                    }
                }
            }

            if (giveNames)
            {
                stack.setStackDisplayName(PandorasBoxItemNamer.getRandomName(random));
            }

            if (split)
            {
                for (int n = 0; n < stack.stackSize; n++)
                {
                    ItemStack splitStack = stack.splitStack(1);
                    list.add(splitStack);
                }
            }
            else
            {
                list.add(stack);
            }
        }

        return list.toArray(new ItemStack[list.size()]);
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
