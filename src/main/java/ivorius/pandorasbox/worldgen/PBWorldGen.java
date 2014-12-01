package ivorius.pandorasbox.worldgen;

import ivorius.pandorasbox.block.PBBlocks;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

/**
 * Created by lukas on 01.12.14.
 */
public class PBWorldGen
{
    public static void initializeWorldGen()
    {
        Item itemPandorasBox = Item.getItemFromBlock(PBBlocks.pandorasBox);
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(itemPandorasBox, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(itemPandorasBox, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(itemPandorasBox, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(itemPandorasBox, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(itemPandorasBox, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(itemPandorasBox, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(itemPandorasBox, 0, 1, 1, 5));
    }
}
