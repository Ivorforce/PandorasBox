/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import ivorius.pandorasbox.block.BlockPandorasBox;
import ivorius.pandorasbox.block.PBBlocks;
import ivorius.pandorasbox.block.TileEntityPandorasBox;
import ivorius.pandorasbox.commands.CommandPandorasBox;
import ivorius.pandorasbox.crafting.OreDictionaryConstants;
import ivorius.pandorasbox.effects.*;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.entitites.PBEntityList;
import ivorius.pandorasbox.events.PBFMLEventHandler;
import ivorius.pandorasbox.items.ItemPandorasBox;
import ivorius.pandorasbox.network.PacketEntityData;
import ivorius.pandorasbox.network.PacketEntityDataHandler;
import ivorius.pandorasbox.worldgen.PBWorldGen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Logger;

import static ivorius.pandorasbox.crafting.OreDictionaryConstants.DC_GOLD_INGOT;
import static ivorius.pandorasbox.crafting.OreDictionaryConstants.DC_PLANK_WOOD;
import static ivorius.pandorasbox.crafting.OreDictionaryConstants.DC_REDSTONE_DUST;

@Mod(modid = PandorasBox.MODID, version = PandorasBox.VERSION, name = PandorasBox.NAME, guiFactory = "ivorius.pandorasbox.gui.PBConfigGuiFactory")
public class PandorasBox
{
    public static final String NAME = "Pandora's Box";
    public static final String MODID = "pandorasbox";
    public static final String VERSION = "2.0.1";

    @Instance(value = MODID)
    public static PandorasBox instance;

    @SidedProxy(clientSide = "ivorius.pandorasbox.client.ClientProxy", serverSide = "ivorius.pandorasbox.server.ServerProxy")
    public static PBProxy proxy;

    public static String filePathTexturesFull = "pandorasbox:textures/mod/";
    public static String filePathTextures = "textures/mod/";
    public static String textureBase = "pandorasbox:";

    public static Logger logger;
    public static Configuration config;

    public static SimpleNetworkWrapper network;

    public static PBFMLEventHandler fmlEventHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        PBConfig.loadConfig(null);
        config.save();

        fmlEventHandler = new PBFMLEventHandler();
        fmlEventHandler.register();

        PBBlocks.pandorasBox = new BlockPandorasBox().setBlockName("pandorasBox").setHardness(0.5f).setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerBlock(PBBlocks.pandorasBox, ItemPandorasBox.class, "pandorasBox");
        GameRegistry.registerTileEntity(TileEntityPandorasBox.class, "pandorasBox");

        EntityRegistry.registerModEntity(EntityPandorasBox.class, "pandorasBox", PBEntityList.pandorasBoxEntityID, this, 256, 20, true);

        PBEffects.registerEffects();
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        PandorasBox.network.registerMessage(PacketEntityDataHandler.class, PacketEntityData.class, 1, Side.CLIENT);

        proxy.registerRenderers();
        PBEffects.registerEffectCreators();

        if (PBConfig.allowCrafting)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PBBlocks.pandorasBox),
                    "GRG",
                    "ROR",
                    "#R#",
                    'G', DC_GOLD_INGOT,
                    '#', DC_PLANK_WOOD,
                    'R', DC_REDSTONE_DUST,
                    'O', Items.ender_pearl));
        }

        if (PBConfig.allowGeneration)
            PBWorldGen.initializeWorldGen();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent evt)
    {
        evt.registerServerCommand(new CommandPandorasBox());
    }
}