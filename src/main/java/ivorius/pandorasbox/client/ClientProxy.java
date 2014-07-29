/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import ivorius.pandorasbox.PBProxy;
import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.block.TileEntityPandorasBox;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy implements PBProxy
{
    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityPandorasBox.class, new RenderPandorasBox());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPandorasBox.class, new TileEntityRendererPandorasBox());

        MinecraftForgeClient.registerItemRenderer(PandorasBox.itemPandorasBox, new ItemRendererPandorasBox());
    }
}
