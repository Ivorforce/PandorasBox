package ivorius.pandorasbox;

/**
 * Created by lukas on 29.07.14.
 */
public class PBConfig
{
    public static final String CATERGORY_BALANCING = "balancing";

    public static boolean allowCrafting;
    public static boolean allowGeneration;
    public static boolean boxPowerVisuals;

    public static void loadConfig(String configID)
    {
        if (configID == null || configID.equals(CATERGORY_BALANCING))
        {
            allowCrafting = PandorasBox.config.get(CATERGORY_BALANCING, "allowCrafting", true, "Whether Pandora's Box can be crafted").getBoolean();
            allowGeneration = PandorasBox.config.get(CATERGORY_BALANCING, "allowGeneration", true, "Whether Pandora's Box will generate in chests").getBoolean();
            boxPowerVisuals = PandorasBox.config.get(CATERGORY_BALANCING, "boxPowerVisuals", true, "Indicates whether standing near the box will evoke a 'power' effect (requires Psychedelicraft to be installed)").getBoolean();
        }

        PandorasBox.proxy.loadConfig(configID);
    }
}
