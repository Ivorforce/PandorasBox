package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effectcreators.*;
import ivorius.pandorasbox.mods.Psychedelicraft;
import ivorius.pandorasbox.random.*;
import ivorius.pandorasbox.weighted.WeightedBlock;
import ivorius.pandorasbox.weighted.WeightedEntity;
import ivorius.pandorasbox.weighted.WeightedPotion;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandomChestContent;

import java.util.Arrays;

/**
 * Created by lukas on 01.12.14.
 */
public class PBEffects
{
    public static void registerEffectCreators()
    {
        PandorasBoxHelper.initialize();

        PBECRegistry.register(new PBECDuplicateBox(new IConstant(PBEffectDuplicateBox.MODE_BOX_IN_BOX), new DConstant(0.5)), "matryoshka", 0.02f);

        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(4, 20), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), PandorasBoxHelper.mobs), "mobs", false);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(2, 6), new ILinear(2, 5), new IConstant(0), new IConstant(0), new IConstant(0), PandorasBoxHelper.mobs), "mobTowers", false);
        PBECRegistry.register(new PBECSpawnBlocks(new ILinear(10, 30), new ILinear(0, 10), PandorasBoxHelper.heavyBlocks, null, PBECSpawnBlocks.defaultShowerSpawn()), "megaton", false);
        PBECRegistry.register(new PBECSpawnBlocks(new ILinear(30, 100), new ILinear(0, 10), PandorasBoxHelper.blocks, null, PBECSpawnBlocks.defaultShowerSpawn()), "blockGrave", false);
        PBECRegistry.register(new PBECSpawnBlocks(new ILinear(30, 100), new ILinear(0, 10), PandorasBoxHelper.blocks), "blockShower", false);
        PBECRegistry.register(new PBECTransform(new DLinear(10.0, 80.0), PandorasBoxHelper.blocks), "transform", false);
        PBECRegistry.register(new PBECReplace(new DLinear(10.0, 80.0), null, PandorasBoxHelper.blocks, new ZConstant(true)), "replace", false);
        PBECRegistry.register(new PBECReplace(new DLinear(40.0, 120.0), new Block[]{Blocks.water, Blocks.lava, Blocks.flowing_lava, Blocks.flowing_water}, Arrays.asList(new WeightedBlock(100, Blocks.air)), new ZConstant(false)), "dryness", false);
        PBECRegistry.register(new PBECSpawnTNT(new ILinear(20, 100), new ILinear(5, 50), new ILinear(20, 1000), new ValueThrow(new DLinear(0.2, 2.0), new DLinear(0.5, 5.0)), new ValueSpawn(new DLinear(5.0, 50.0), new DConstant(0.0))), "tntSplosion", false);
        PBECRegistry.register(new PBECMulti(new PBECSpawnManySameItems(new ILinear(10, 30), PandorasBoxHelper.blocksAndItems), 0, new PBECSpawnTNT(new ILinear(20, 60), new ILinear(1, 10), new ILinear(20, 60), new ValueThrow(new DLinear(0.2, 0.5), new DLinear(0.3, 0.5)), new ValueSpawn(new DLinear(3.0, 10.0), new DConstant(0.0))), 60), "dirtyTrick", false);
        PBECRegistry.register(new PBECPool(new DLinear(10.0, 30.0), Blocks.water, PandorasBoxHelper.blocks), "waterPool", false);
        PBECRegistry.register(new PBECPool(new DLinear(10.0, 30.0), Blocks.lava, PandorasBoxHelper.blocks), "lavaPool", false);
        PBECRegistry.register(new PBECHeightNoise(new DLinear(8.0, 30.0), new ILinear(-16, 16), new ILinear(1, 32), new ILinear(1, 8)), "heightNoise", false);
        PBECRegistry.register(new PBECRandomShapes(new DLinear(40.0, 150.0), new DLinear(2.0, 5.0), new ILinear(3, 10), PandorasBoxHelper.blocks, new ZConstant(true)), "madGeometry", false);
        PBECRegistry.register(new PBECRandomShapes(new DLinear(40.0, 150.0), new DLinear(1.0, 3.0), new ILinear(10, 80), PandorasBoxHelper.blocks, new ZConstant(false)), "madderGeometry", false);
        PBECRegistry.register(new PBECLavaCage(new DLinear(15.0, 40.0), Blocks.lava, null, Arrays.asList(new WeightedBlock(100, Blocks.iron_bars))), "lavaCage", false);
        PBECRegistry.register(new PBECLavaCage(new DLinear(15.0, 40.0), null, Blocks.water, Arrays.asList(new WeightedBlock(100, Blocks.glass))), "waterCage", false);
        PBECRegistry.register(new PBECCreativeTowers(new DLinear(5.0, 20.0), new ILinear(3, 10), PandorasBoxHelper.blocks), "classic", false);
        PBECRegistry.register(new PBECSpawnLightning(new ILinear(40, 200), new ILinear(6, 40), new DLinear(10.0, 40.0)), "lightning", false);
        PBECRegistry.register(new PBECConvertToDesert(new DLinear(10.0, 80.0)), "sandForDessert", false);
        PBECRegistry.register(new PBECConvertToEnd(new DLinear(10.0, 80.0)), "inTheEnd", false);
        PBECRegistry.register(new PBECConvertToNether(new DLinear(10.0, 80.0)), "hellOnEarth", false);
        PBECRegistry.register(new PBECConvertToLifeless(new DLinear(20.0, 80.0)), "lifeless", false);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(20, 200), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), Arrays.asList(new WeightedEntity(100, "Arrow", 1, 1)), new ValueThrow(new DLinear(0.05, 0.5), new DLinear(0.3, 0.8)), null), "trappedTribe", false);
        PBECRegistry.register(new PBECBuffEntities(new ILinear(60, 20 * 30), new IWeighted(1, 100, 2, 80, 3, 50), new DLinear(8.0, 25.0), 0.2f, PandorasBoxHelper.debuffs), "buffedDown", false);
        PBECRegistry.register(new PBECBuffEntities(new ILinear(20 * 3, 20 * 6), new IConstant(1), new DLinear(8.0, 25.0), 0.95f, Arrays.asList(new WeightedPotion(100, Potion.moveSlowdown, 4, 8, 20 * 6, 20 * 20))), "frozenInPlace", false);
        PBECRegistry.register(new PBECGenTrees(new DLinear(10.0, 80.0), new DLinear(1.0f / (32.0f * 32.0f * 32.0f), 1.0f / (6.0f * 6.0f * 6.0f)), new ZConstant(false), new IFlags(1, PBEffectGenTrees.treeSmall, 1.0, PBEffectGenTrees.treeNormal, 0.5, PBEffectGenTrees.treeBig, 0.5, PBEffectGenTrees.treeComplexNormal, 0.5, PBEffectGenTrees.treeTaiga, 0.5, PBEffectGenTrees.treeBirch, 0.5)), "flyingForest", false);
        PBECRegistry.register(new PBECCrushEntities(new ILinear(100, 300), new DLinear(20.0, 50.0)), "crush", false);
        PBECRegistry.register(new PBECBombentities(new ILinear(100, 400), new ILinear(5, 15), new DLinear(20.0, 50.0)), "bomberman", false);
        PBECRegistry.register(new PBECMulti(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(4, 20), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), PandorasBoxHelper.mobs), 0, new PBECBuffEntities(new ILinear(60, 200), new IConstant(1), new DLinear(10.0, 40.0), 0.15f, Arrays.asList(new WeightedPotion(100, Potion.blindness, 1, 1, 20 * 40, 20 * 200))), 0), "pitchBlack", false);
        PBECRegistry.register(new PBECCreateVoid(new ILinear(30, 60), new DLinear(20.0, 50.0)), "void", false);
        PBECRegistry.register(new PBECThrowItems(new ILinear(20, 200), new DLinear(20.0, 50.0), new DLinear(0.3, 0.7), new DLinear(0.0, 0.2), new ILinear(0, 5), PandorasBoxHelper.blocksAndItems), "areTheseMine", false);
        PBECRegistry.register(new PBECMulti(new PBECSetTime(new ILinear(60, 200), new ILinear(12000, 24000 * 5), new ZConstant(true)), 0, new PBECSetWeather(new IWeighted(0, 50, 1, 35, 2, 15), new ILinear(100, 12000), new ILinear(10, 120))), "timeLord", false);
        PBECRegistry.register(new PBECSpawnExploMobs(new ILinear(20, 60), new ILinear(8, 16), new ILinear(60, 200), new IWeighted(0, 100, 2, 100, 3, 100), PandorasBoxHelper.creatures), "exploCreatures", false);
        PBECRegistry.register(new PBECSpawnExploMobs(new ILinear(20, 60), new ILinear(8, 16), new ILinear(60, 200), new IConstant(0), PandorasBoxHelper.mobs), "exploMobs", false);
        PBECRegistry.register(new PBECMulti(new PBECDome(new ILinear(60, 200), new DLinear(10.0, 30.0), Arrays.asList(new WeightedBlock(100, Blocks.glass)), Blocks.water), 0, new PBECSpawnEntities(new ILinear(60, 100), new ILinear(4, 20), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), PandorasBoxHelper.waterCreatures), 100), "aquarium", false);
        PBECRegistry.register(new PBECWorldSnake(new ILinear(160, 600), new DConstant(0.0), new DLinear(0.1, 0.5), new DLinear(0.5, 3.0), PandorasBoxHelper.blocks), "worldSnake", false);
        PBECWorldSnake doubleSnakeGen = new PBECWorldSnake(new ILinear(160, 600), new DLinear(5.0, 20.0), new DLinear(0.1, 0.5), new DLinear(0.5, 3.0), PandorasBoxHelper.blocks);
        PBECRegistry.register(new PBECMulti(doubleSnakeGen, 0, doubleSnakeGen, 0), "doubleSnake", false);
        PBECWorldSnake doubleCaveSnake = new PBECWorldSnake(new ILinear(160, 600), new DLinear(5.0, 20.0), new DLinear(0.1, 0.5), new DLinear(1.0, 3.0), Arrays.asList(new WeightedBlock(100, Blocks.air)));
        PBECRegistry.register(new PBECMulti(doubleCaveSnake, 0, doubleCaveSnake, 0), "tunnelBore", false);
        PBECRegistry.register(new PBECSpawnExplosions(new ILinear(80, 300), new ILinear(6, 40), new DLinear(10.0, 40.0), new DLinear(1.0, 4.0), new ZChance(0.3), new ZConstant(true)), "creeperSoul", false);
        PBECRegistry.register(new PBECBombpack(new DLinear(10.0, 60.0), new ILinear(60, 200)), "bombPack", false);
        PBECRegistry.register(new PBECCover(new DLinear(10.0, 15.0), new ZConstant(false), Arrays.asList(new WeightedBlock(100, Blocks.air))), "makeThin", false);
        PBECRegistry.register(new PBECCover(new DLinear(10.0, 15.0), new ZChance(0.5), PandorasBoxHelper.blocks), "cover", false);
        PBECRegistry.register(new PBECTargets(new ILinear(40, 100), new DLinear(10.0, 50.0), new DLinear(6.0, 16.0), new DLinear(0.2, 0.5), PandorasBoxHelper.mobs), "target", false);
        PBECRegistry.register(new PBECSpawnArmy(new ILinear(1, 3), new ILinear(0, 5), Arrays.asList(new WeightedEntity(100, "Zombie", 5, 10), new WeightedEntity(80, "Skeleton", 4, 7), new WeightedEntity(40, "pbspecial_skeletonWither", 5, 10))), "armoredArmy", false);
        PBECRegistry.register(new PBECSpawnArmy(new ILinear(1, 3), new IConstant(0), PandorasBoxHelper.mobs), "army", false);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 40), new IWeighted(1, 100, 2, 20, 3, 5), new IConstant(1), new ILinear(2, 6), new ILinear(2, 10), new IConstant(1), PandorasBoxHelper.mobs), "boss", false);
        PBECRegistry.register(new PBECConvertToIce(new DLinear(10.0, 80.0)), "iceAge", false);
        PBECRegistry.register(new PBECTeleportEntities(0.1f, new ILinear(5, 100), new DLinear(10.0, 80.0), new DLinear(10.0, 100.0), new IConstant(1)), "teleRandom", false);
        PBECRegistry.register(new PBECTeleportEntities(0.5f, new ILinear(5, 200), new DLinear(10.0, 80.0), new DLinear(0.5, 5.0), new ILinear(5, 20)), "crazyPort", false);
        PBECRegistry.register(new PBECExplosion(new ILinear(50, 120), new DGaussian(1.0, 8.0), new ZChance(0.3)), "thingGoBoom", false);
        PBECRegistry.register(new PBECSpawnBlocks(new ILinear(50, 120), new ILinear(0, 2), Arrays.asList(new WeightedBlock(1, Blocks.tnt), new WeightedBlock(1, Blocks.redstone_block)), null, new ValueSpawn(new DLinear(5.0, 10.0), new DConstant(150.0))).setShuffleBlocks(false), "dangerCall", false);

        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(4, 20), new IConstant(1), new IWeighted(0, 100, 2, 50, 3, 30), new IConstant(0), new IConstant(0), PandorasBoxHelper.creatures), "animals", true);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(2, 6), new ILinear(2, 5), new IWeighted(0, 100, 2, 35, 3, 20), new IConstant(0), new IConstant(0), PandorasBoxHelper.creatures), "animalTowers", true);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(1, 8), new IConstant(1), new IWeighted(0, 50, 2, 100, 3, 20), new IConstant(0), new IConstant(0), PandorasBoxHelper.tameableCreatures), "tamer", true);
        PBECRegistry.register(new PBECSpawnItems(new ILinear(1, 5), new ILinear(0, 30), PandorasBoxHelper.blocksAndItems), "items", true);
        PBECRegistry.register(new PBECSpawnEnchantedItems(new ILinear(1, 2), new ILinear(0, 30), new IExp(1, 30, 10.0), PandorasBoxHelper.enchantableArmorList, new ZChance(0.7)), "epicArmor", true);
        PBECRegistry.register(new PBECSpawnEnchantedItems(new ILinear(1, 2), new ILinear(0, 30), new IExp(1, 30, 10.0), PandorasBoxHelper.enchantableToolList, new ZChance(0.7)), "epicTool", true);
        PBECRegistry.register(new PBECSpawnEnchantedItems(new ILinear(1, 2), new ILinear(0, 30), new IExp(1, 30, 10.0), PandorasBoxHelper.items, new ZChance(0.7)), "epicThing", true);
        PBECRegistry.register(new PBECSpawnEnchantedItems(new ILinear(1, 4), new ILinear(0, 30), new IExp(1, 30, 10.0), Arrays.asList(new WeightedRandomChestContent(Items.enchanted_book, 0, 1, 1, 100)), new ZChance(0.1)), "enchantedBook", true);
        PBECRegistry.register(new PBECSpawnManySameItems(new ILinear(0, 30), PandorasBoxHelper.blocksAndItems), "resources", true);
        PBECRegistry.register(new PBECSpawnItemSet(new ILinear(0, 30), PandorasBoxHelper.equipmentSets), "equipmentSet", true);
        PBECRegistry.register(new PBECSpawnManySameItems(new ILinear(0, 30), PandorasBoxHelper.blocksAndItems, null, PBECSpawnItems.defaultShowerSpawn()), "itemRain", true);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(10, 30), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), PandorasBoxHelper.creatures, null, new ValueSpawn(new DLinear(5.0, 20.0), new DConstant(150.0))), "deadCreatures", true);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(10, 30), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), PandorasBoxHelper.mobs, null, new ValueSpawn(new DLinear(5.0, 20.0), new DConstant(150.0))), "deadMobs", true);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(20, 100), new ILinear(10, 110), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), Arrays.asList(new WeightedEntity(100, "pbspecial_XP", 1, 1))), "experience", true);
        PBECRegistry.register(new PBECGenTrees(new DLinear(10.0, 80.0), new DLinear(1.0f / (8.0f * 8.0f), 1.0f / (2.0f * 2.0f)), new ZConstant(true), new IFlags(1, PBEffectGenTrees.treeSmall, 0.5, PBEffectGenTrees.treeNormal, 0.5, PBEffectGenTrees.treeBig, 0.5, PBEffectGenTrees.treeComplexNormal, 0.5, PBEffectGenTrees.treeTaiga, 0.5, PBEffectGenTrees.treeBirch, 0.5)), "suddenForest", true);
        PBECRegistry.register(new PBECGenTrees(new DLinear(10.0, 80.0), new DLinear(1.0f / (8.0f * 8.0f), 1.0f / (3.0f * 3.0f)), new ZConstant(true), new IFlags(1, PBEffectGenTrees.treeJungle, 0.7, PBEffectGenTrees.treeHuge, 0.7, PBEffectGenTrees.treeComplexNormal, 0.7)), "suddenJungle", true);
        PBECRegistry.register(new PBECGenTreesOdd(new DLinear(10.0, 80.0), new DLinear(1.0f / (8.0f * 8.0f), 1.0f / (3.0f * 3.0f)), new ZConstant(true), new IFlags(1, PBEffectGenTreesOdd.treeJungle, 0.7), PandorasBoxHelper.blocks, PandorasBoxHelper.blocks), "oddJungle", true);
        PBECRegistry.register(new PBECBuffEntities(new ILinear(60, 20 * 30), new IWeighted(1, 100, 2, 80, 3, 50), new DLinear(8.0, 25.0), 0.2f, PandorasBoxHelper.buffs), "buffedUp", true);
        PBECRegistry.register(new PBECConvertToSnow(new DLinear(10.0, 80.0)), "snowAge", true);
        PBECRegistry.register(new PBECConvertToOverworld(new DLinear(10.0, 80.0)), "normalLand", true);
        PBECRegistry.register(new PBECConvertToHFT(new DLinear(10.0, 80.0)), "happyFunTimes", true);
        PBECRegistry.register(new PBECConvertToMushroom(new DLinear(10.0, 80.0)), "shroomify", true);
        PBECRegistry.register(new PBECConvertToHomo(new DLinear(10.0, 80.0)), "rainbows", true);
        PBECRegistry.register(new PBECConvertToFarm(new DLinear(5.0, 15.0), new DLinear(0.01, 0.5)), "farm", true);
        PBECRegistry.register(new PBECMulti(new PBECConvertToHeavenly(new DLinear(20.0, 80.0)), 0, new PBECRandomShapes(new DLinear(20.0, 80.0), new DLinear(2, 5), new ILinear(8, 10), Arrays.asList(new WeightedBlock(1, Blocks.web)), new ZConstant(true)), 0), "heavenly", true);
        PBECRegistry.register(new PBECMulti(new PBECConvertToHalloween(new DLinear(10.0, 80.0)), 0, new PBECSetTime(new ILinear(60, 120), new ILinear(15000, 20000), new ZConstant(false)), 0), "halloween", true);
        PBECRegistry.register(new PBECMulti(new PBECConvertToChristmas(new DLinear(10.0, 80.0)), 0, new PBECSetTime(new ILinear(60, 120), new ILinear(15000, 20000), new ZConstant(false)), 0), "christmas", true);
        PBECRegistry.register(new PBECSpawnEntities(new ILinear(100, 200), new ILinear(10, 30), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), Arrays.asList(new WeightedEntity(100, "pbspecial_firework", 1, 1)), new ValueThrow(new DLinear(0.02, 0.06), new DConstant(0.01)), new ValueSpawn(new DLinear(2.0, 20.0), new DConstant(0.0))), "newYear", true);
        PBECRegistry.register(new PBECSpawnBlocks(new ILinear(6, 20), new ILinear(5, 20), PandorasBoxHelper.blocks, null, new ValueSpawn(new DConstant(0.0), new DConstant(25.0))), "blockTower", true);
        PBECRegistry.register(new PBECMulti(new PBECDome(new ILinear(60, 200), new DLinear(10.0, 30.0), Arrays.asList(new WeightedBlock(100, Blocks.glass)), null), 0, new PBECSpawnEntities(new ILinear(60, 100), new ILinear(4, 20), new IConstant(1), new IConstant(0), new IConstant(0), new IConstant(0), PandorasBoxHelper.creatures), 0), "terrarium", true);
        PBECRegistry.register(new PBECSpawnArmy(new ILinear(1, 3), new IConstant(0), PandorasBoxHelper.creatures), "animalFarm", true);

        Psychedelicraft.initEffectCreators();
    }

    public static void registerEffects()
    {
        PBEffectRegistry.register(PBEffectEntitiesBomberman.class, "entitiesBomberman");
        PBEffectRegistry.register(PBEffectEntitiesBuff.class, "entitiesBuff");
        PBEffectRegistry.register(PBEffectEntitiesCrush.class, "entitiesCrush");
        PBEffectRegistry.register(PBEffectEntitiesThrowItems.class, "entitiesThrowItems");
        PBEffectRegistry.register(PBEffectGenConvertToDesert.class, "genConvertToDesert");
        PBEffectRegistry.register(PBEffectGenConvertToEnd.class, "genConvertToEnd");
        PBEffectRegistry.register(PBEffectGenConvertToHalloween.class, "genConvertToHalloween");
        PBEffectRegistry.register(PBEffectGenConvertToHFT.class, "genConvertToHFT");
        PBEffectRegistry.register(PBEffectGenConvertToSnow.class, "genConvertToSnow");
        PBEffectRegistry.register(PBEffectGenConvertToIce.class, "genConvertToIce");
        PBEffectRegistry.register(PBEffectGenConvertToMushroom.class, "genConvertToMushroom");
        PBEffectRegistry.register(PBEffectGenConvertToNether.class, "genConvertToNether");
        PBEffectRegistry.register(PBEffectGenConvertToOverworld.class, "genConvertToOverworld");
        PBEffectRegistry.register(PBEffectGenConvertToChristmas.class, "genConvertToChristmas");
        PBEffectRegistry.register(PBEffectGenConvertToFarm.class, "genConvertToFarm");
        PBEffectRegistry.register(PBEffectGenConvertToHomo.class, "genConvertToHomo");
        PBEffectRegistry.register(PBEffectGenConvertToLifeless.class, "genConvertToLifeless");
        PBEffectRegistry.register(PBEffectGenConvertToHeavenly.class, "genConvertToHeavenly");
        PBEffectRegistry.register(PBEffectGenCreativeTowers.class, "genCreativeTowers");
        PBEffectRegistry.register(PBEffectGenHeightNoise.class, "genHeightNoise");
        PBEffectRegistry.register(PBEffectGenLavaCages.class, "genLavaCages");
        PBEffectRegistry.register(PBEffectGenPool.class, "genPool");
        PBEffectRegistry.register(PBEffectGenReplace.class, "genReplace");
        PBEffectRegistry.register(PBEffectGenShapes.class, "genShapes");
        PBEffectRegistry.register(PBEffectGenTransform.class, "genTransform");
        PBEffectRegistry.register(PBEffectGenTrees.class, "genTrees");
        PBEffectRegistry.register(PBEffectGenTreesOdd.class, "genTreesOdd");
        PBEffectRegistry.register(PBEffectMulti.class, "multi");
        PBEffectRegistry.register(PBEffectSetTime.class, "setTime");
        PBEffectRegistry.register(PBEffectSpawnBlocks.class, "spawnBlocks");
        PBEffectRegistry.register(PBEffectSpawnEntityIDList.class, "spawnEntityList");
        PBEffectRegistry.register(PBEffectSpawnItemStacks.class, "spawnItemStacks");
        PBEffectRegistry.register(PBEffectRandomLightnings.class, "spawnLightning");
        PBEffectRegistry.register(PBEffectGenDome.class, "genDome");
        PBEffectRegistry.register(PBEffectGenWorldSnake.class, "genWorldSnake");
        PBEffectRegistry.register(PBEffectSetWeather.class, "setWeather");
        PBEffectRegistry.register(PBEffectRandomExplosions.class, "randomExplosions");
        PBEffectRegistry.register(PBEffectEntitiesBombpack.class, "entitiesBombPack");
        PBEffectRegistry.register(PBEffectGenCover.class, "genCover");
        PBEffectRegistry.register(PBEffectGenTargets.class, "genTargets");
        PBEffectRegistry.register(PBEffectEntitiesCreateVoid.class, "entitiesGenVoid");
        PBEffectRegistry.register(PBEffectEntitiesTeleport.class, "entitiesTeleport");
        PBEffectRegistry.register(PBEffectDuplicateBox.class, "duplicateBox");
        PBEffectRegistry.register(PBEffectExplode.class, "explode");

        Psychedelicraft.initEffects();
    }
}
