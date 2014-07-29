/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.ivorius.pandorasbox.PandorasBox;
import net.ivorius.pandorasbox.ServerProxy;
import net.ivorius.pandorasbox.block.TileEntityPandorasBox;
import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends ServerProxy
{
    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityPandorasBox.class, new RenderPandorasBox());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPandorasBox.class, new TileEntityRendererPandorasBox());

        MinecraftForgeClient.registerItemRenderer(PandorasBox.itemPandorasBox, new ItemRendererPandorasBox());
    }
}
