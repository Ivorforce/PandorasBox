package ivorius.pandorasbox.mods;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effectcreators.PBECBuffEntities;
import ivorius.pandorasbox.effectcreators.PBECDrugEntities;
import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.effectcreators.PBEffectCreator;
import ivorius.pandorasbox.effects.PBEffectEntitiesDrug;
import ivorius.pandorasbox.effects.PBEffectRegistry;
import ivorius.pandorasbox.random.DLinear;
import ivorius.pandorasbox.random.ILinear;
import ivorius.pandorasbox.random.IWeighted;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 06.12.14.
 */
public class Psychedelicraft extends ModRepresentation
{
    public static final String MOD_ID = "psychedelicraft";

    public static final List<WeightedDrug> drugs = new ArrayList<>();

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

    public static void addRandomDrugNames(int weight, float minValue, float maxValue, String... drugNames)
    {
        for (String drugName : drugNames)
            drugs.add(new WeightedDrug(weight, drugName, minValue, maxValue));
    }

    public static WeightedDrug getRandomDrug(Random random)
    {
        return ((WeightedDrug) WeightedRandom.getRandomItem(random, drugs));
    }

    public static void initEffectCreators()
    {
        if (isLoaded())
        {
            addRandomDrugNames(100, 0.3f, 1.2f, "Alcohol", "Cannabis", "Cocaine");
            addRandomDrugNames(80, 0.2f, 0.8f, "BrownShrooms", "RedShrooms", "Peyote");
            addRandomDrugNames(50, 0.2f, 1.2f, "Zero");

            PBECRegistry.register(new PBECDrugEntities(new ILinear(60, 20 * 30), new IWeighted(1, 100, 2, 80, 3, 50), new DLinear(8.0, 25.0), 0.3f, Psychedelicraft.drugs), "drugs", false);

            try
            {
                PandorasBoxHelper.addItems(100, modItem("hop_seeds"), modItem("hop_cones"), modItem("wineGrapes"), modItem("cannabisSeeds"), modItem("cannabisLeaf"), modItem("cannabisBuds"), modItem("tobaccoSeeds"), modItem("tobaccoLeaf"), modItem("cocaSeeds"), modItem("cocaLeaf"), modItem("juniperBerries"), modItem("coffeaCherries"), modItem("peyote"));
                PandorasBoxHelper.addItems(80, modItem("hashMuffin"), modItem("brownMagicMushrooms"), modItem("redMagicMushrooms"), modItem("cigarette"), modItem("cigar"), modItem("joint"), modItem("cocaine_powder"), modItem("peyoteJoint"));
                PandorasBoxHelper.addItems(80, modItem("stone_cup"), modItem("woodenMug"), modItem("glassChalice"), modItem("bottle"), modItem("smokingPipe"));
                PandorasBoxHelper.addItems(60, modItem("barrel"), modItem("mash_tub"), modItem("flask"), modItem("distillery"));
                PandorasBoxHelper.addItems(30, modItem("syringe"), modItem("bong"));
            }
            catch (Exception ex)
            {
                PandorasBox.logger.error("Incompatible Psychedelicraft Compatibility", ex);
            }
        }
    }

    public static void initEffects()
    {
        PBEffectRegistry.register(PBEffectEntitiesDrug.class, "drugEntities");
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

    public static class WeightedDrug extends WeightedRandom.Item
    {
        public String drugName;
        public float minAddValue;
        public float maxAddValue;

        public WeightedDrug(int weight, String drugName, float minAddValue, float maxAddValue)
        {
            super(weight);
            this.drugName = drugName;
            this.minAddValue = minAddValue;
            this.maxAddValue = maxAddValue;
        }
    }
}
