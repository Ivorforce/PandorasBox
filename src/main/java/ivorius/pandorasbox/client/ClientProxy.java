/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.client;

import ivorius.pandorasbox.PBProxy;
import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.block.PBBlocks;
import ivorius.pandorasbox.client.rendering.RenderPandorasBox;
import ivorius.pandorasbox.client.rendering.effects.PBEffectRendererExplosion;
import ivorius.pandorasbox.client.rendering.effects.PBEffectRenderingRegistry;
import ivorius.pandorasbox.effects.PBEffectExplode;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements PBProxy
{
    @Override
    public void preInit()
    {
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPandorasBox.class, new TileEntityRendererPandorasBox());

        B3DLoader.instance.addDomain(PandorasBox.MOD_ID.toLowerCase());

        PBEffectRenderingRegistry.registerRenderer(PBEffectExplode.class, new PBEffectRendererExplosion());

        ModelLoader.setCustomStateMapper(PBBlocks.pandorasBox, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(PandorasBox.basePath + "pandoras_box", this.getPropertyString(state.getProperties()));
            }
        });

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(PBBlocks.pandorasBox), 0, new ModelResourceLocation(PandorasBox.basePath + "pandoras_box", "inventory"));
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
