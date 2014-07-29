package ivorius.pandorasbox;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by lukas on 29.07.14.
 */
public class PBConfig
{
    public static boolean allowCrafting;
    public static boolean allowGeneration;

    public static void loadConfig(String configID)
    {
        if (configID == null || configID.equals(Configuration.CATEGORY_GENERAL))
        {
            allowCrafting = PandorasBox.config.get("balancing", "allowCrafting", true, "Whether Pandora's Box can be crafted").getBoolean();
            allowGeneration = PandorasBox.config.get("balancing", "allowGeneration", true, "Whether Pandora's will generate in chests").getBoolean();
        }

        PandorasBox.proxy.loadConfig(configID);
    }
}
