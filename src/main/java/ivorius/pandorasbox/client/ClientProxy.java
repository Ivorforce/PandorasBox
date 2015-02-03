/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.client;

import com.google.common.collect.Maps;
import ivorius.pandorasbox.PBProxy;
import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.block.BlockPandorasBox;
import ivorius.pandorasbox.block.PBBlocks;
import ivorius.pandorasbox.client.rendering.effects.PBEffectRendererExplosion;
import ivorius.pandorasbox.client.rendering.effects.PBEffectRenderingRegistry;
import ivorius.pandorasbox.effects.PBEffectExplode;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.LinkedHashMap;

public class ClientProxy implements PBProxy
{
    @Override
    public void preInit()
    {
        // TODO
//
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPandorasBox.class, new TileEntityRendererPandorasBox());

//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(PBBlocks.pandorasBox), new ItemRendererPandorasBox());

        B3DLoader.instance.addDomain(PandorasBox.MODID.toLowerCase());
        ModelBakery.addVariantName(Item.getItemFromBlock(PBBlocks.pandorasBox), PandorasBox.basePath + "pandoras_box.b3d");

        PBEffectRenderingRegistry.registerRenderer(PBEffectExplode.class, new PBEffectRendererExplosion());

        ModelLoader.setCustomStateMapper(PBBlocks.pandorasBox, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                LinkedHashMap linkedhashmap = Maps.newLinkedHashMap(state.getProperties());
                return new ModelResourceLocation(PandorasBox.basePath + "pandoras_box", getPropertyString(linkedhashmap));
            }
        });
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(PBBlocks.pandorasBox), 0, new ModelResourceLocation(PandorasBox.basePath + "pandoras_box.b3d", "inventory"));
    }

    @Override
    public void load()
    {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingRegistry.registerEntityRenderingHandler(EntityPandorasBox.class, new RenderPandorasBox(renderManager));
    }

    @Override
    public void loadConfig(String categoryID)
    {

    }
}
