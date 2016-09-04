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
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

/**
 * Created by lukas on 15.04.14.
 */
public class TileEntityPandorasBox extends TileEntity
{
    private float partialRotationYaw;

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

    public void setPartialRotationYaw(float partialRotationYaw)
    {
        this.partialRotationYaw = partialRotationYaw;
    }

    public float getPartialRotationYaw()
    {
        return partialRotationYaw;
    }

    public float getBaseRotationYaw()
    {
        return rotationFromFacing((EnumFacing) this.worldObj.getBlockState(this.pos).getValue(BlockPandorasBox.FACING_PROP));
    }

    public float getRotationYaw()
    {
        return getBaseRotationYaw()/* + partialRotationYaw*/; // TODO Block model doesn't support gradual rotation yet
    }

    public EntityPandorasBox spawnPandorasBox()
    {
        if (!worldObj.isRemote)
        {
            PBEffect effect = PBECRegistry.createRandomEffect(worldObj, worldObj.rand, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, true);

            if (effect != null)
            {
                EntityPandorasBox entityPandorasBox = new EntityPandorasBox(worldObj, effect);

                entityPandorasBox.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, getRotationYaw(), 0.0f);

                entityPandorasBox.beginFloatingUp();

                worldObj.spawnEntityInWorld(entityPandorasBox);

                return entityPandorasBox;
            }
        }

        return null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setFloat("boxRotationYaw", partialRotationYaw);

        return nbtTagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        partialRotationYaw = nbtTagCompound.getFloat("boxRotationYaw");
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new SPacketUpdateTileEntity(pos, 1, compound);
    }
}
