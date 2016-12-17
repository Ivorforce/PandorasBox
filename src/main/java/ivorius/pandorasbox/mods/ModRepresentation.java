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
        return Block.REGISTRY.getObject(new ResourceLocation(modID, id));
    }

    public static String id(Block block)
    {
        return Block.REGISTRY.getNameForObject(block).toString();
    }

    public static Item item(String modID, String id)
    {
        return Item.REGISTRY.getObject(new ResourceLocation(modID, id));
    }

    public static String id(Item item)
    {
        return Item.REGISTRY.getNameForObject(item).toString();
    }
}