package ivorius.pandorasbox.mods;

import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effectcreators.PBECDrugEntities;
import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.effects.PBEffectEntitiesDrug;
import ivorius.pandorasbox.effects.PBEffectRegistry;
import ivorius.pandorasbox.random.DLinear;
import ivorius.pandorasbox.random.ILinear;
import ivorius.pandorasbox.random.IWeighted;
import ivorius.ivtoolkit.random.WeightedSelector;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ivorius.pandorasbox.mods.Psychedelicraft.*;

/**
 * Created by lukas on 06.12.14.
 */
public class PsychedelicraftModule extends ModRepresentation
{
    public static final List<WeightedDrug> drugs = new ArrayList<>();

    public static void addRandomDrugNames(double weight, float minValue, float maxValue, String... drugNames)
    {
        for (String drugName : drugNames)
            drugs.add(new WeightedDrug(weight, drugName, minValue, maxValue));
    }

    public static WeightedDrug getRandomDrug(Random random)
    {
        return WeightedSelector.selectItem(random, drugs);
    }

    public static void initEffectCreators()
    {
        if (isLoaded())
        {
            addRandomDrugNames(10.0, 0.3f, 1.2f, "Alcohol", "Cannabis", "Cocaine");
            addRandomDrugNames(8.0, 0.2f, 0.8f, "BrownShrooms", "RedShrooms", "Peyote");
            addRandomDrugNames(5.0, 0.2f, 1.2f, "Zero");

            PBECRegistry.register(new PBECDrugEntities(new ILinear(60, 20 * 30), new IWeighted(1, 100, 2, 80, 3, 50), new DLinear(8.0, 25.0), 0.3f, drugs), "drugs", false);

            try
            {
                PandorasBoxHelper.addItems(10.0, modItem("hop_seeds"), modItem("hop_cones"), modItem("wineGrapes"), modItem("cannabisSeeds"), modItem("cannabisLeaf"), modItem("cannabisBuds"), modItem("tobaccoSeeds"), modItem("tobaccoLeaf"), modItem("cocaSeeds"), modItem("cocaLeaf"), modItem("juniperBerries"), modItem("coffeaCherries"), modItem("peyote"));
                PandorasBoxHelper.addItems(8.0, modItem("hashMuffin"), modItem("brownMagicMushrooms"), modItem("redMagicMushrooms"), modItem("cigarette"), modItem("cigar"), modItem("joint"), modItem("cocaine_powder"), modItem("peyoteJoint"));
                PandorasBoxHelper.addItems(8.0, modItem("stone_cup"), modItem("woodenMug"), modItem("glassChalice"), modItem("bottle"), modItem("smokingPipe"));
                PandorasBoxHelper.addItems(6.0, modItem("barrel"), modItem("mash_tub"), modItem("flask"), modItem("distillery"));
                PandorasBoxHelper.addItems(3.0, modItem("syringe"), modItem("bong"));
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

    public static class WeightedDrug implements WeightedSelector.Item
    {
        public double weight;

        public String drugName;
        public float minAddValue;
        public float maxAddValue;

        public WeightedDrug(double weight, String drugName, float minAddValue, float maxAddValue)
        {
            this.weight = weight;
            this.drugName = drugName;
            this.minAddValue = minAddValue;
            this.maxAddValue = maxAddValue;
        }

        @Override
        public double getWeight()
        {
            return weight;
        }
    }
}
