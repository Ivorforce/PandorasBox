package ivorius.pandorasbox.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
