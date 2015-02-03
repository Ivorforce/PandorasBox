package ivorius.pandorasbox.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

/**
 * Created by lukas on 03.02.15.
 */
public class IvNBTHelper
{
    public static NBTTagString storeBlock(Block block)
    {
        return new NBTTagString(storeBlockString(block));
    }

    public static String storeBlockString(Block block)
    {
        ResourceLocation location = (ResourceLocation) Block.blockRegistry.getNameForObject(block);
        return location == null ? "minecraft:air" : location.toString();
    }

    public static Block getBlock(String string)
    {
        return Block.getBlockFromName(string);
    }

    public static NBTTagString storeItem(Item item)
    {
        return new NBTTagString(storeItemString(item));
    }

    public static String storeItemString(Item item)
    {
        ResourceLocation location = (ResourceLocation) Item.itemRegistry.getNameForObject(item);
        return location == null ? "minecraft:air" : location.toString();
    }

    public static Item getItem(String string)
    {
        return Item.getByNameOrId(string);
    }
}
