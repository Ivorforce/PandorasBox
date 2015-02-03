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
import net.minecraft.util.EnumFacing;

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
            PBEffect effect = PBECRegistry.createRandomEffect(worldObj, worldObj.rand, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, true);

            if (effect != null)
            {
                EntityPandorasBox entityPandorasBox = new EntityPandorasBox(worldObj, effect);

                EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getValue(BlockPandorasBox.FACING_PROP);
                entityPandorasBox.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, rotationFromFacing(facing), 0.0f);

                entityPandorasBox.beginFloatingUp();

                worldObj.spawnEntityInWorld(entityPandorasBox);

                return entityPandorasBox;
            }
        }

        return null;
    }

    public static float rotationFromFacing(EnumFacing facing)
    {
        switch (facing)
        {
            case SOUTH:
                return 0.0f;
            case WEST:
                return 90.0f;
            case NORTH:
                return 180.0f;
            case EAST:
                return 270.0f;
        }

        throw new IllegalArgumentException();
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
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new S35PacketUpdateTileEntity(pos, 1, compound);
    }
}
