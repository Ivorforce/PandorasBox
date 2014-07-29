package ivorius.pandorasbox.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by lukas on 29.07.14.
 */
public class PacketEntityDataHandler implements IMessageHandler<PacketEntityData, IMessage>
{
    // Copied from ivtoolkit

    @Override
    public IMessage onMessage(PacketEntityData message, MessageContext ctx)
    {
        World world = IvSideClient.getClientWorld();
        Entity entity = world.getEntityByID(message.getEntityID());

        if (entity != null)
            ((PartialUpdateHandler) entity).readUpdateData(message.getPayload(), message.getContext());

        return null;
    }
}
