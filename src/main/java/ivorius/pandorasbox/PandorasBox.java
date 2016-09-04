/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox;

import ivorius.pandorasbox.block.BlockPandorasBox;
import ivorius.pandorasbox.block.PBBlocks;
import ivorius.pandorasbox.block.TileEntityPandorasBox;
import ivorius.pandorasbox.commands.CommandPandorasBox;
import ivorius.pandorasbox.effects.PBEffects;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.entitites.PBEntityList;
import ivorius.pandorasbox.events.PBFMLEventHandler;
import ivorius.pandorasbox.items.ItemPandorasBox;
import ivorius.pandorasbox.network.PacketEntityData;
import ivorius.pandorasbox.network.PacketEntityDataHandler;
import ivorius.pandorasbox.worldgen.PBWorldGen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Logger;

import static ivorius.pandorasbox.crafting.OreDictionaryConstants.*;

@Mod(modid = PandorasBox.MOD_ID, version = PandorasBox.VERSION, name = PandorasBox.NAME, guiFactory = "ivorius.pandorasbox.gui.PBConfigGuiFactory")
public class PandorasBox
{
    public static final String NAME = "Pandora's Box";
    public static final String MOD_ID = "pandorasbox";
    public static final String VERSION = "2.1.2";

    @Instance(value = MOD_ID)
    public static PandorasBox instance;

    @SidedProxy(clientSide = "ivorius.pandorasbox.client.ClientProxy", serverSide = "ivorius.pandorasbox.server.ServerProxy")
    public static PBProxy proxy;

    public static String filePathTexturesFull = "pandorasbox:textures/mod/";
    public static String filePathTextures = "textures/mod/";
    public static String basePath = "pandorasbox:";

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

        PBBlocks.pandorasBox = (BlockPandorasBox) new BlockPandorasBox().setUnlocalizedName("pandorasBox").setHardness(0.5f).setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerBlock(PBBlocks.pandorasBox, ItemPandorasBox.class, "pandorasBox");
        GameRegistry.registerTileEntity(TileEntityPandorasBox.class, "pandorasBox");

        EntityRegistry.registerModEntity(EntityPandorasBox.class, "pandorasBox", PBEntityList.pandorasBoxEntityID, this, 256, 20, true);

        proxy.preInit();
        PBEffects.registerEffects();
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        PandorasBox.network.registerMessage(PacketEntityDataHandler.class, PacketEntityData.class, 1, Side.CLIENT);

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

        proxy.load();
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