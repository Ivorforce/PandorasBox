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

    public static double boxLongevity;
    public static double boxIntensity;
    public static int maxEffectsPerBox;
    public static double goodEffectChance;

    public static void loadConfig(String configID)
    {
        if (configID == null || configID.equals(CATERGORY_BALANCING))
        {
            allowCrafting = PandorasBox.config.get(CATERGORY_BALANCING, "allowCrafting", true, "Whether Pandora's Box can be crafted").getBoolean();
            allowGeneration = PandorasBox.config.get(CATERGORY_BALANCING, "allowGeneration", true, "Whether Pandora's Box will generate in chests").getBoolean();
            boxPowerVisuals = PandorasBox.config.get(CATERGORY_BALANCING, "boxPowerVisuals", true, "Indicates whether standing near the box will evoke a 'power' effect (requires Psychedelicraft to be installed)").getBoolean();

            boxLongevity = PandorasBox.config.get(CATERGORY_BALANCING, "boxLongevity", 0.2, "How long a box will last (with continuous effects). Represented by 'chance to continue'.").getDouble();
            boxIntensity = PandorasBox.config.get(CATERGORY_BALANCING, "boxIntensity", 1.0, "How many effects a box will have at once. Represented by 'chance for another effect'. Ex.: 0 for 'Always exactly one effect', 3 for '3 times the default chance'.").getDouble();
            maxEffectsPerBox = PandorasBox.config.get(CATERGORY_BALANCING, "maxEffectsPerBox", 3, "The value up to which the intensity can increase. Keep in mind high values can cause strong lag.").getInt();
            goodEffectChance = PandorasBox.config.get(CATERGORY_BALANCING, "goodEffectChance", 0.49, "The chance for each effect to be 'positive'.").getDouble();
        }

        PandorasBox.proxy.loadConfig(configID);
    }
}
