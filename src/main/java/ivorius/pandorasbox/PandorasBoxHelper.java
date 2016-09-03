/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ivorius.pandorasbox.block.PBBlocks;
import ivorius.pandorasbox.utils.RandomizedItemStack;
import ivorius.ivtoolkit.random.WeightedSelector;
import ivorius.pandorasbox.weighted.WeightedBlock;
import ivorius.pandorasbox.weighted.WeightedEntity;
import ivorius.pandorasbox.weighted.WeightedPotion;
import ivorius.pandorasbox.weighted.WeightedSet;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import java.util.*;

public class PandorasBoxHelper
{
    public static List<WeightedEntity> mobs = new ArrayList<>();
    public static List<WeightedEntity> creatures = new ArrayList<>();
    public static List<WeightedEntity> waterCreatures = new ArrayList<>();
    public static List<WeightedEntity> waterMobs = new ArrayList<>();
    public static List<WeightedEntity> tameableCreatures = new ArrayList<>();

    public static List<RandomizedItemStack> blocksAndItems = new ArrayList<>();
    public static Multimap<Block, IProperty> randomizableBlockProperties = HashMultimap.create();

    public static List<WeightedBlock> blocks = new ArrayList<>();

    public static List<RandomizedItemStack> items = new ArrayList<>();
    public static List<WeightedSet> equipmentSets = new ArrayList<>();
    public static Hashtable<Item, Hashtable<Integer, ItemStack>> equipmentForLevels = new Hashtable<>();

    public static List<WeightedPotion> buffs = new ArrayList<>();
    public static List<WeightedPotion> debuffs = new ArrayList<>();

    public static List<RandomizedItemStack> enchantableArmorList = new ArrayList<>();
    public static List<RandomizedItemStack> enchantableToolList = new ArrayList<>();

    public static List<WeightedBlock> heavyBlocks = new ArrayList<>();

    public static void addEntities(List<WeightedEntity> list, double weight, int minNumber, int maxNumber, String... entities)
    {
        for (String s : entities)
        {
            list.add(new WeightedEntity(weight, s, minNumber, maxNumber));
        }
    }

    public static void addBlocks(double weight, Block... blocks)
    {
        for (Block block : blocks)
        {
            PandorasBoxHelper.blocks.add(new WeightedBlock(weight, block));

            Item item = Item.getItemFromBlock(block);
            if (item != null)
                addItem(new RandomizedItemStack(item, 0, 1, item.getItemStackLimit(new ItemStack(item)), weight));
        }
    }

    public static void addBlocks(List<WeightedBlock> list, double weight, Block... blocks)
    {
        for (Block block : blocks)
        {
            list.add(new WeightedBlock(weight, block));
        }
    }

    public static void addItem(RandomizedItemStack RandomizedItemStack)
    {
        items.add(RandomizedItemStack);
        blocksAndItems.add(RandomizedItemStack);
    }

    public static void addItems(double weight, Object... items)
    {
        for (Object object : items)
        {
            if (object instanceof Item)
            {
                Item item = (Item) object;
                addItem(new RandomizedItemStack(item, 0, 1, item.getItemStackLimit(new ItemStack(item)), weight));
            }
            else if (object instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) object;
                addItem(new RandomizedItemStack(itemStack, 1, itemStack.getItem().getItemStackLimit(itemStack), weight));
            }
        }
    }

    public static void addItemsMinMax(double weight, int min, int max, Object... items)
    {
        for (Object object : items)
        {
            if (object instanceof Item)
            {
                Item item = (Item) object;
                addItem(new RandomizedItemStack(item, 0, min, max, weight));
            }
            else if (object instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) object;
                addItem(new RandomizedItemStack(itemStack, min, max, weight));
            }
        }
    }

    public static void addEquipmentSet(double weight, Object... items)
    {
        ItemStack[] set = new ItemStack[items.length];

        for (int i = 0; i < set.length; i++)
        {
            if (items[i] instanceof Item)
            {
                Item item = (Item) items[i];
                set[i] = new ItemStack(item);
            }
            else if (items[i] instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) items[i];
                set[i] = itemStack;
            }
        }

        equipmentSets.add(new WeightedSet(weight, set));
    }

    public static void addPotions(List<WeightedPotion> list, double weight, int minStrength, int maxStrength, int minDuration, int maxDuration, Potion... potions)
    {
        for (Potion potion : potions)
        {
            list.add(new WeightedPotion(weight, potion, minStrength, maxStrength, minDuration, maxDuration));
        }
    }

    public static void addEnchantableArmor(double weight, Object... items)
    {
        for (Object object : items)
        {
            if (object instanceof Item)
            {
                Item item = (Item) object;
                enchantableArmorList.add(new RandomizedItemStack(item, 0, 1, 1, weight));
            }
            else if (object instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) object;
                enchantableArmorList.add(new RandomizedItemStack(itemStack, 1, 1, weight));
            }
        }
    }

    public static void addEnchantableTools(double weight, Object... items)
    {
        for (Object object : items)
        {
            if (object instanceof Item)
            {
                Item item = (Item) object;
                enchantableToolList.add(new RandomizedItemStack(item, 0, 1, 1, weight));
            }
            else if (object instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) object;
                enchantableToolList.add(new RandomizedItemStack(itemStack, 1, 1, weight));
            }
        }
    }

    public static void addEquipmentForLevel(Item base, int level, ItemStack stack)
    {
        if (!equipmentForLevels.containsKey(base))
            equipmentForLevels.put(base, new Hashtable<Integer, ItemStack>());

        equipmentForLevels.get(base).put(level, stack);
    }

    public static void addEquipmentLevelsInOrder(Item base, Object... items)
    {
        for (int i = 0; i < items.length; i++)
        {
            Object object = items[i];

            if (object instanceof Item)
                addEquipmentForLevel(base, i, new ItemStack((Item) items[i]));
            else if (object instanceof ItemStack)
                addEquipmentForLevel(base, i, (ItemStack) items[i]);
        }
    }

    public static void addAllRandomizableBlockProperties(Block... blocks)
    {
        for (Block block : blocks)
            randomizableBlockProperties.putAll(block, block.getDefaultState().getProperties().keySet());
    }

    public static void addRandomizableBlockProperty(Block[] blocks, IProperty... properties)
    {
        for (Block block : blocks)
            for (IProperty property : properties)
                randomizableBlockProperties.put(block, property);
    }

    public static void addRandomizableBlockProperty(Block block, IProperty... properties)
    {
        for (IProperty property : properties)
            randomizableBlockProperties.put(block, property);
    }

    public static void initialize()
    {
        addEntities(mobs, 10.0, 3, 10, "Zombie");
        addEntities(mobs, 10.0, 2, 8, "Spider");
        addEntities(mobs, 10.0, 2, 5, "Skeleton");
        addEntities(mobs, 5.0, 2, 5, "pbspecial_skeletonWither");
        addEntities(mobs, 10.0, 2, 8, "Creeper");
        addEntities(mobs, 6.0, 2, 8, "Slime");
        addEntities(mobs, 4.0, 1, 4, "Ghast");
        addEntities(mobs, 6.0, 2, 8, "PigZombie");
        addEntities(mobs, 6.0, 2, 6, "Enderman");
        addEntities(mobs, 5.0, 2, 4, "CaveSpider");
        addEntities(mobs, 5.0, 10, 20, "Silverfish");
        addEntities(mobs, 4.0, 2, 5, "Blaze");
        addEntities(mobs, 5.0, 2, 6, "LavaSlime");
        addEntities(mobs, 1.0, 1, 1, "WitherBoss");
        addEntities(mobs, 4.0, 2, 4, "Witch");
        addEntities(mobs, 6.0, 10, 20, "Endermite");
        addEntities(mobs, 5.0, 2, 6, "pbspecial_angryWolf");
        addEntities(mobs, 4.0, 2, 5, "pbspecial_superchargedCreeper");

        addEntities(creatures, 10.0, 3, 10, "Pig", "Sheep", "Cow", "Chicken");
        addEntities(creatures, 6.0, 2, 6, "Wolf");
        addEntities(creatures, 5.0, 4, 10, "Bat");
        addEntities(creatures, 7.0, 6, 20, "Rabbit");
        addEntities(creatures, 4.0, 3, 7, "MushroomCow");
        addEntities(creatures, 4.0, 3, 7, "SnowMan");
        addEntities(creatures, 4.0, 2, 5, "EntityHorse");
        addEntities(creatures, 4.0, 2, 6, "Ozelot");
        addEntities(creatures, 3.0, 3, 6, "Villager");
        addEntities(creatures, 3.0, 2, 4, "VillagerGolem");

        addEntities(waterCreatures, 6.0, 3, 10, "Squid");

        addEntities(waterMobs, 6.0, 3, 10, "Guardian");
        addEntities(waterMobs, 5.0, 1, 1, "pbspecial_elderGuardian");

        addEntities(tameableCreatures, 4.0, 1, 4, "pbspecial_wolfTamed");
        addEntities(tameableCreatures, 4.0, 1, 4, "pbspecial_ocelotTamed");

        addBlocks(40.0, Blocks.stone, Blocks.sandstone, Blocks.planks, Blocks.sand, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2, Blocks.wool, Blocks.double_stone_slab, Blocks.double_stone_slab2, Blocks.double_wooden_slab, Blocks.stonebrick, Blocks.stained_hardened_clay);
        addBlocks(15.0, Blocks.prismarine, Blocks.quartz_block);
        addBlocks(10.0, Blocks.cobblestone, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.nether_brick, Blocks.brick_block, Blocks.end_stone, Blocks.hardened_clay);
        addBlocks(10.0, Blocks.dirt, Blocks.grass, Blocks.gravel, Blocks.pumpkin, Blocks.clay, Blocks.mycelium);
        addBlocks(8.0, Blocks.coal_ore, Blocks.lapis_ore, Blocks.redstone_ore, Blocks.quartz_ore, Blocks.glass, Blocks.stained_glass, Blocks.soul_sand);
        addBlocks(0.2, Blocks.diamond_block, Blocks.emerald_block, Blocks.gold_block);
        addBlocks(0.3, Blocks.iron_block);
        addBlocks(0.5, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.gold_ore);
        addBlocks(1.0, Blocks.iron_ore);
        addBlocks(2.0, Blocks.tnt, Blocks.glowstone, Blocks.coal_block, Blocks.lapis_block, Blocks.redstone_block, Blocks.slime_block, Blocks.sponge);
        addBlocks(5.0, Blocks.monster_egg, Blocks.redstone_lamp, Blocks.sea_lantern, Blocks.snow, Blocks.bookshelf, Blocks.lit_pumpkin, Blocks.hay_block, Blocks.obsidian, Blocks.melon_block);

        addItems(10.0, Items.coal, Items.gunpowder, Items.wheat, Items.saddle, Items.redstone, Items.bone, Items.melon, Items.clay_ball, Items.book, Items.gold_nugget, Items.potato, Items.bucket, Items.stick, Items.string, Items.melon_seeds, Items.pumpkin_seeds, Items.wheat_seeds, Items.snowball, Items.sugar, Items.fishing_rod, Items.nether_star, Items.nether_wart, Items.flint, Items.egg, Items.brick, Items.paper, new ItemStack(Blocks.torch));
        addItems(10.0, Item.getItemFromBlock(PBBlocks.pandorasBox));
        addItems(10.0, Items.chicken, Items.cooked_chicken, Items.beef, Items.pumpkin_pie, Items.cooked_beef, Items.mushroom_stew, Items.rotten_flesh, Items.carrot, Items.porkchop, Items.cooked_porkchop, Items.apple, Items.cake, Items.bread, Items.cookie, Items.fish, Items.cooked_fish, Items.mutton, Items.cooked_mutton, Items.rabbit, Items.rabbit_foot, Items.rabbit_hide, Items.rabbit_stew, Items.cooked_rabbit);
        addItems(8.0, Items.lava_bucket, Items.milk_bucket, Items.water_bucket, Items.flint_and_steel, Items.painting, Items.flower_pot, Items.bed, Items.boat, Items.minecart, Items.cauldron);
        addItems(8.0, Items.name_tag);
        addItems(6.0, Items.iron_ingot, Items.glowstone_dust, Items.blaze_powder, Items.blaze_rod, Items.clock, Items.ghast_tear, Items.ender_eye, Items.speckled_melon, Items.spider_eye, Items.fermented_spider_eye, Items.magma_cream, Items.golden_carrot);
        addItems(4.0, Items.leather_helmet, Items.leather_chestplate, Items.leather_leggings, Items.leather_boots, Items.wooden_sword, Items.wooden_pickaxe, Items.wooden_shovel, Items.wooden_axe, Items.wooden_hoe);
        addItems(4.0, Items.golden_helmet, Items.golden_chestplate, Items.golden_leggings, Items.golden_boots, Items.golden_sword, Items.golden_pickaxe, Items.golden_shovel, Items.golden_axe, Items.golden_hoe);
        addItems(4.0, Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots, Items.iron_sword, Items.iron_pickaxe, Items.iron_shovel, Items.iron_axe, Items.iron_hoe);
        addItems(3.0, Items.iron_horse_armor, Items.golden_horse_armor);
        addItems(2.0, Items.diamond_horse_armor);
        addItemsMinMax(2.0, 1, 1, new ItemStack(Blocks.beacon), new ItemStack(Blocks.anvil), new ItemStack(Blocks.brewing_stand), new ItemStack(Blocks.dispenser), new ItemStack(Blocks.ender_chest), new ItemStack(Blocks.jukebox), new ItemStack(Blocks.enchanting_table));
        addItemsMinMax(5.0, 1, 1, new ItemStack(Blocks.chest));
        addItems(2.0, Items.diamond, Items.emerald, Items.gold_ingot, Items.golden_apple, Items.ender_pearl, Items.prismarine_crystals, Items.prismarine_shard);
        addItems(2.0, Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots, Items.diamond_sword, Items.diamond_pickaxe, Items.diamond_shovel, Items.diamond_axe, Items.diamond_hoe);
        addItems(2.0, Items.record_11, Items.record_13, Items.record_blocks, Items.record_cat, Items.record_chirp, Items.record_far, Items.record_mall, Items.record_mellohi, Items.record_stal, Items.record_strad, Items.record_wait, Items.record_ward);
        for (int i = 0; i < 16; i++)
            addItems(10.0, new ItemStack(Items.dye, 1, i));

        addEquipmentSet(10.0, Items.leather_helmet, Items.leather_chestplate, Items.leather_leggings, Items.leather_boots, Items.wooden_sword, Items.wooden_pickaxe, Items.wooden_shovel, Items.wooden_axe, Items.wooden_hoe);
        addEquipmentSet(6.0, Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots, Items.iron_sword, Items.iron_pickaxe, Items.iron_shovel, Items.iron_axe, Items.iron_hoe);
        addEquipmentSet(4.0, Items.golden_helmet, Items.golden_chestplate, Items.golden_leggings, Items.golden_boots, Items.golden_sword, Items.golden_pickaxe, Items.golden_shovel, Items.golden_axe, Items.golden_hoe);
        addEquipmentSet(2.0, Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots, Items.diamond_sword, Items.diamond_pickaxe, Items.diamond_shovel, Items.diamond_axe, Items.diamond_hoe);
        addEquipmentSet(6.0, Items.bow, new ItemStack(Items.arrow, 64), Items.iron_helmet, Items.leather_chestplate, Items.leather_leggings, Items.leather_boots, Items.iron_axe, new ItemStack(Items.apple, 8));
        addEquipmentSet(6.0, Items.iron_helmet, Items.leather_chestplate, Items.leather_leggings, Items.leather_boots, Items.diamond_pickaxe, Items.iron_shovel, Items.iron_axe, Items.stone_sword, new ItemStack(Items.bread, 8), new ItemStack(Blocks.torch, 32));
        addEquipmentSet(8.0, Items.leather_helmet, Items.iron_hoe, new ItemStack(Items.wheat_seeds, 32), new ItemStack(Items.pumpkin_seeds, 4), new ItemStack(Items.melon_seeds, 4), new ItemStack(Items.dye, 8, 15), new ItemStack(Blocks.dirt, 32), Items.water_bucket, Items.water_bucket);
        addEquipmentSet(6.0, Items.iron_helmet, Items.diamond_axe, new ItemStack(Items.beef, 16));
        addEquipmentSet(6.0, new ItemStack(Items.redstone, 64), new ItemStack(Blocks.wool, 16, 0), new ItemStack(Blocks.wool, 16, 15), new ItemStack(Blocks.wool, 16, 1), new ItemStack(Blocks.redstone_block, 8), new ItemStack(Blocks.redstone_torch, 8));

        addEquipmentLevelsInOrder(Items.wooden_sword, Items.wooden_sword, Items.golden_sword, Items.stone_sword, Items.iron_sword, Items.diamond_sword);
        addEquipmentLevelsInOrder(Items.wooden_axe, Items.wooden_axe, Items.golden_axe, Items.stone_axe, Items.iron_axe, Items.diamond_axe);
        addEquipmentLevelsInOrder(Items.wooden_pickaxe, Items.wooden_pickaxe, Items.golden_pickaxe, Items.stone_pickaxe, Items.iron_pickaxe, Items.diamond_pickaxe);
        addEquipmentLevelsInOrder(Items.wooden_shovel, Items.wooden_shovel, Items.golden_shovel, Items.stone_shovel, Items.iron_shovel, Items.diamond_shovel);
        addEquipmentLevelsInOrder(Items.wooden_hoe, Items.wooden_hoe, Items.golden_hoe, Items.stone_hoe, Items.iron_hoe, Items.diamond_hoe);

        addPotions(buffs, 10.0, 0, 3, 20 * 60, 20 * 60 * 10, Potion.regeneration, Potion.moveSpeed, Potion.damageBoost, Potion.jump, Potion.resistance, Potion.waterBreathing, Potion.fireResistance, Potion.nightVision, Potion.invisibility, Potion.absorption);
        addPotions(debuffs, 10.0, 0, 3, 20 * 60, 20 * 60 * 10, Potion.blindness, Potion.confusion, Potion.digSlowdown, Potion.weakness, Potion.hunger);
        addPotions(debuffs, 10.0, 0, 2, 20 * 30, 20 * 60, Potion.wither);

        addEnchantableArmor(10.0, Items.iron_helmet, Items.golden_helmet, Items.diamond_helmet, Items.iron_chestplate, Items.golden_chestplate, Items.diamond_chestplate, Items.iron_leggings, Items.golden_leggings, Items.diamond_leggings, Items.iron_boots, Items.golden_boots, Items.diamond_boots);

        addEnchantableTools(10.0, Items.iron_sword, Items.golden_sword, Items.diamond_sword, Items.iron_shovel, Items.golden_shovel, Items.diamond_shovel, Items.iron_pickaxe, Items.golden_pickaxe, Items.diamond_pickaxe, Items.iron_axe, Items.golden_axe, Items.diamond_axe, Items.bow);

        addBlocks(heavyBlocks, 10.0, Blocks.anvil);

        addAllRandomizableBlockProperties(
                Blocks.stone, Blocks.dirt, Blocks.sand,
                Blocks.wool, Blocks.stained_hardened_clay,
                Blocks.stained_glass, Blocks.stained_glass_pane,
                Blocks.wooden_slab, Blocks.double_wooden_slab,
                Blocks.stone_slab, Blocks.double_stone_slab,
                Blocks.stone_slab2, Blocks.double_stone_slab2,
                Blocks.planks,
                Blocks.leaves, Blocks.leaves2,
                Blocks.log, Blocks.log2,
                Blocks.sapling,
                Blocks.stonebrick, Blocks.stone_brick_stairs,
                Blocks.quartz_block, Blocks.quartz_stairs,
                Blocks.sandstone, Blocks.sandstone_stairs,
                Blocks.red_sandstone, Blocks.red_sandstone_stairs,
                Blocks.rail,
                Blocks.furnace, Blocks.pumpkin, Blocks.lit_pumpkin,
                Blocks.snow, Blocks.snow_layer,
                Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest,
                Blocks.flower_pot,
                Blocks.sponge
        );
    }

    public static int getRandomUnifiedSeed(Random random)
    {
        return Math.abs(random.nextInt());
    }

    private static <T> T randomElement(Collection<T> collection, Random random)
    {
        int num = random.nextInt(collection.size());
        int i = 0;
        for (T t : collection)
            if ((i++) == num)
                return t;
        throw new InternalError();
    }

    public static IBlockState getRandomBlockState(Random rand, Block block, int unified)
    {
        IBlockState state = block.getDefaultState();

        Collection<IProperty> randomizableProperties = randomizableBlockProperties.get(block);
        if (randomizableProperties != null)
        {
            if (unified >= 0)
                rand = new Random(unified ^ Block.blockRegistry.getNameForObject(block).hashCode());

            for (IProperty property : randomizableProperties)
                state = state.withProperty(property, PandorasBoxHelper.<Comparable>randomElement(property.getAllowedValues(), rand));
        }

        return state;
    }

    public static Block[] getRandomBlockList(Random rand, Collection<WeightedBlock> selection)
    {
        int number = 1;
        while (number < 10 && rand.nextFloat() < 0.7f)
            number++;

        int[] weights = new int[number];
        for (int i = 0; i < number; i++)
        {
            weights[i] = 1;

            while (weights[i] < 10 && rand.nextFloat() < 0.7f)
                weights[i]++;
        }

        int total = 0;
        for (int i : weights)
            total += i;

        Block[] blocks = new Block[total];
        int blockIndex = 0;

        for (int i = 0; i < number; i++)
        {
            Block block = WeightedSelector.selectItem(rand, selection).block;

            for (int j = 0; j < weights[i]; j++)
            {
                blocks[blockIndex] = block;
                blockIndex++;
            }
        }

        return blocks;
    }

    public static Block getRandomBlock(Random rand, Collection<WeightedBlock> randomBlockList)
    {
        if (randomBlockList != null && randomBlockList.size() > 0)
            return WeightedSelector.selectItem(rand, randomBlockList).block;

        return WeightedSelector.selectItem(rand, blocks).block;
    }

    public static WeightedEntity[] getRandomEntityList(Random rand, Collection<WeightedEntity> selection)
    {
        WeightedEntity[] entities = new WeightedEntity[rand.nextInt(5) + 1];

        for (int i = 0; i < entities.length; i++)
            entities[i] = getRandomEntityFromList(rand, selection);

        return entities;
    }

    public static WeightedEntity getRandomEntityFromList(Random rand, Collection<WeightedEntity> entityList)
    {
        return WeightedSelector.selectItem(rand, entityList);
    }

    public static ItemStack getRandomWeaponItemForLevel(Random random, int level)
    {
        Set<Item> itemSet = equipmentForLevels.keySet();
        Item[] itemArray = itemSet.toArray(new Item[itemSet.size()]);

        return getWeaponItemForLevel(itemArray[random.nextInt(itemArray.length)], level);
    }

    public static ItemStack getWeaponItemForLevel(Item baseItem, int level)
    {
        Hashtable<Integer, ItemStack> levels = equipmentForLevels.get(baseItem);

        if (levels != null)
        {
            while (level > 0)
            {
                if (levels.containsKey(level))
                    return levels.get(level);

                level--;
            }
        }

        return null;
    }
}
