/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.block;

import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by lukas on 15.04.14.
 */
public class TileEntityPandorasBox extends TileEntity
{
    public float boxRotationYaw;

    public EntityPandorasBox spawnPandorasBox()
    {
        if (!worldObj.isRemote)
        {
            PBEffect effect = PBECRegistry.createRandomEffect(getWorldObj(), getWorldObj().rand, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, true);

            if (effect != null)
            {
                EntityPandorasBox entityPandorasBox = new EntityPandorasBox(getWorldObj(), effect);

                entityPandorasBox.setLocationAndAngles(xCoord + 0.5, yCoord, zCoord + 0.5, boxRotationYaw, 0.0f);

                entityPandorasBox.beginFloatingUp();

                getWorldObj().spawnEntityInWorld(entityPandorasBox);

                return entityPandorasBox;
            }
        }

        return null;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setFloat("boxRotationYaw", boxRotationYaw);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        boxRotationYaw = nbtTagCompound.getFloat("boxRotationYaw");
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
    }
}
