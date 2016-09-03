package ivorius.pandorasbox.mods;

import ivorius.ivtoolkit.random.WeightedSelector;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Created by lukas on 06.12.14.
 */
public class Psychedelicraft extends ModRepresentation
{
    public static final String MOD_ID = "psychedelicraft";

    public static boolean isLoaded()
    {
        return Loader.isModLoaded(MOD_ID);
    }

    public static Item modItem(String id)
    {
        return item(MOD_ID, id);
    }

    public static Block modBlock(String id)
    {
        return block(MOD_ID, id);
    }

    public static void addDrugValue(Entity entity, String drug, float value)
    {
        NBTTagCompound message = new NBTTagCompound();

        message.setInteger("worldID", entity.dimension);
        message.setInteger("entityID", entity.getEntityId());
        message.setString("drugName", drug);
        message.setFloat("drugValue", value);

        FMLInterModComms.sendMessage(MOD_ID, "drugAddValue", message);
    }
}
