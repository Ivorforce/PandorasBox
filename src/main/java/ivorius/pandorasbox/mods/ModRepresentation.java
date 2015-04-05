package ivorius.pandorasbox.mods;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by lukas on 06.12.14.
 */
public class ModRepresentation
{
    public static Block block(String modID, String id)
    {
        return GameRegistry.findBlock(modID, id);
    }

    public static String id(Block block)
    {
        return Block.blockRegistry.getNameForObject(block).toString();
    }

    public static Item item(String modID, String id)
    {
        return GameRegistry.findItem(modID, id);
    }

    public static String id(Item item)
    {
        return Item.itemRegistry.getNameForObject(item).toString();
    }
}