/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.pandorasbox.gui;

import ivorius.pandorasbox.PBConfig;
import ivorius.pandorasbox.PandorasBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;

/**
 * Created by lukas on 29.06.14.
 */
public class PBConfigGuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance)
    {

    }

    @Override
    public boolean hasConfigGui()
    {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return new ConfigGui(parentScreen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

    public static class ConfigGui extends GuiConfig
    {
        public ConfigGui(GuiScreen parentScreen)
        {
            super(parentScreen, getConfigElements(), PandorasBox.MOD_ID, false, false, I18n.format("pandorasbox.configgui.title"));
        }

        private static List<IConfigElement> getConfigElements()
        {
            List<IConfigElement> list = new ArrayList<>();
//            list.add(new DummyCategoryElement("pandorasbox.configgui.general", "pandorasbox.configgui.ctgy.general", GeneralEntry.class));
            list.add(new DummyCategoryElement("pandorasbox.configgui.balancing", "pandorasbox.configgui.ctgy.balancing", BalancingEntry.class).setRequiresMcRestart(true));
            return list;
        }

//        public static class GeneralEntry extends GuiConfigEntries.CategoryEntry
//        {
//            public GeneralEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
//            {
//                super(owningScreen, owningEntryList, prop);
//            }
//
//            @Override
//            protected GuiScreen buildChildScreen()
//            {
//                return new GuiConfig(this.owningScreen,
//                        (new ConfigElement(PandorasBox.config.getCategory(Configuration.CATEGORY_GENERAL))).getChildElements(),
//                        this.owningScreen.modID, Configuration.CATEGORY_GENERAL, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
//                        this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
//                        GuiConfig.getAbridgedConfigPath(PandorasBox.config.toString()));
//            }
//        }

        public static class BalancingEntry extends GuiConfigEntries.CategoryEntry
        {
            public BalancingEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
            {
                super(owningScreen, owningEntryList, prop);
            }

            @Override
            protected GuiScreen buildChildScreen()
            {
                return new GuiConfig(this.owningScreen,
                        (new ConfigElement(PandorasBox.config.getCategory(PBConfig.CATERGORY_BALANCING))).getChildElements(),
                        this.owningScreen.modID, PBConfig.CATERGORY_BALANCING, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
                        this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
                        GuiConfig.getAbridgedConfigPath(PandorasBox.config.toString()));
            }
        }
    }
}
