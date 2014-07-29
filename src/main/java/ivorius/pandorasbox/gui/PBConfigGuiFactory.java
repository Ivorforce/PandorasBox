/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.pandorasbox.gui;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.IConfigElement;
import ivorius.pandorasbox.PandorasBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;

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
    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return ConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
    {
        return null;
    }

    public static class ConfigGui extends GuiConfig
    {
        public ConfigGui(GuiScreen parentScreen)
        {
            super(parentScreen, getConfigElements(), PandorasBox.MODID, false, false, I18n.format("pandorasbox.configgui.title"));
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
                        (new ConfigElement(PandorasBox.config.getCategory("balancing"))).getChildElements(),
                        this.owningScreen.modID, "balancing", this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
                        this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
                        GuiConfig.getAbridgedConfigPath(PandorasBox.config.toString()));
            }
        }
    }
}
