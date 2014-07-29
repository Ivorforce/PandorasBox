/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.entitites;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.ivorius.pandorasbox.effectcreators.PBECRegistry;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectRegistry;
import net.ivorius.pandorasbox.network.ChannelHandlerEntityData;
import net.ivorius.pandorasbox.network.IEntityUpdateData;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class EntityPandorasBox extends Entity implements IEntityAdditionalSpawnData, IEntityUpdateData
{
    public int effectTicksExisted;
    public boolean canGenerateMoreEffectsAfterwards = true;

    private PBEffect pbEffect;

    public boolean floatUp = false;
    public float floatAwayProgress = -1.0f;

    public EntityPandorasBox(World par1World)
    {
        super(par1World);

        setSize(0.8f, 0.8f);
    }

    public EntityPandorasBox(World par1World, PBEffect effect)
    {
        this(par1World);

        setBoxEffect(effect);
    }

    @Override
    protected void entityInit()
    {
        this.dataWatcher.addObject(11, -1);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getDeathTicks() >= 0)
        {
            if (!worldObj.isRemote)
            {
                if (getDeathTicks() >= 30)
                {
                    setDead();
                }
            }
            else
            {
                for (int e = 0; e < Math.min(getDeathTicks(), 60); e++)
                {
                    double xP = (rand.nextDouble() - rand.nextDouble()) * 0.75;
                    double yP = (rand.nextDouble() - rand.nextDouble()) * 0.75;
                    double zP = (rand.nextDouble() - rand.nextDouble()) * 0.75;

                    if (rand.nextBoolean())
                    {
                        worldObj.spawnParticle("smoke", posX + xP, posY + yP, posZ + zP, 0.0D, 0.0D, 0.0D);
                    }
                    else
                    {
                        worldObj.spawnParticle("largesmoke", posX + xP, posY + yP, posZ + zP, 0.0D, 0.0D, 0.0D);
                    }
                }
            }

            setDeathTicks(getDeathTicks() + 1);
        }
        int boxTicks = effectTicksExisted - 40;

        if (boxTicks >= 0 && getDeathTicks() < 0)
        {
            PBEffect effect = getBoxEffect();

            if (effect == null)
            {
                if (!worldObj.isRemote)
                {
                    setDead();
                }
            }
            else
            {
                if (effect.isDone(this, boxTicks))
                {
                    if (!worldObj.isRemote)
                    {
                        boolean isCompletelyDone = true;

                        if (canGenerateMoreEffectsAfterwards && effect.canGenerateMoreEffectsAfterwards(this))
                        {
                            if (rand.nextFloat() < 0.2f)
                            {
                                startNewEffect();

                                isCompletelyDone = false;
                            }
                        }

                        if (isCompletelyDone)
                        {
                            startFadingOut();
                        }
                    }
                }
                else
                {
                    effect.doTick(this, boxTicks);
                }
            }
        }

        if (floatAwayProgress >= 0.0f && floatAwayProgress < 1.0f)
        {
            if (floatUp)
            {
                float speed = (floatAwayProgress - 0.7f) * (floatAwayProgress - 0.7f);
                motionY += speed * 0.005f;
            }
            else
            {
                float speed = (floatAwayProgress - 0.7f) * (floatAwayProgress - 0.7f);
                moveFlying(0.0f, -1.0f, speed * 0.02f);
                motionY += speed * 0.005f;
            }

            floatAwayProgress += 0.025f;

            if (floatAwayProgress > 1.0f)
                stopFloating();
        }

        if (boxTicks >= 0)
        {
            motionX *= 0.5;
            motionY *= 0.5;
            motionZ *= 0.5;
        }
        else
        {
            motionX *= 0.95;
            motionY *= 0.95;
            motionZ *= 0.95;
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (worldObj.isRemote)
        {
            if (boxTicks >= 0 && getDeathTicks() < 0 && !isInvisible())
            {
                for (int e = 0; e < 2; e++)
                {
                    double xP = (rand.nextDouble() - rand.nextDouble()) * 0.3;
                    double yDir = rand.nextDouble() * 0.1 + 0.0;
                    double zP = (rand.nextDouble() - rand.nextDouble()) * 0.3;

                    worldObj.spawnParticle("smoke", posX + xP, posY, posZ + zP, 0.0D, yDir, 0.0D);
                }
                for (int e = 0; e < 3; e++)
                {
                    double xDir = (rand.nextDouble() - rand.nextDouble()) * 3.0;
                    double yDir = rand.nextDouble() * 4.0 + 2.0;
                    double zDir = (rand.nextDouble() - rand.nextDouble()) * 3.0;

                    worldObj.spawnParticle("enchantmenttable", posX + (rand.nextDouble() - 0.5) * width + xDir, posY + height * 0.5 + yDir - 0.3f, posZ + (rand.nextDouble() - 0.5) * width + zDir, -xDir, -yDir, -zDir);
                }
                for (int e = 0; e < 4; e++)
                {
                    worldObj.spawnParticle("portal", posX + (rand.nextDouble() * 16) - 8D, posY + height * 0.5f + (rand.nextDouble() * 5) - 2D, posZ + (rand.nextDouble() * 16D) - 8D, (rand.nextDouble() * 2D) - 1D, (rand.nextDouble() * 2D) - 1D, (rand.nextDouble() * 2D) - 1D);
                }
            }
        }

        effectTicksExisted++;
    }

    public void startNewEffect()
    {
        effectTicksExisted = rand.nextInt(40);
        pbEffect = PBECRegistry.createRandomEffect(worldObj, rand, posX, posY, posZ, true);

        ChannelHandlerEntityData.sendUpdatePacketSafe(this, "boxEffect");
    }

    public void startFadingOut()
    {
        setDeathTicks(0);
    }

    public void beginFloatingAway()
    {
        floatAwayProgress = 0.0f;
        floatUp = false;
    }

    public void beginFloatingUp()
    {
        floatAwayProgress = 0.0f;
        floatUp = true;
    }

    public void stopFloating()
    {
        floatAwayProgress = -1.0f;
        effectTicksExisted = 0;
        ChannelHandlerEntityData.sendUpdatePacketSafe(this, "boxEffect");
    }

    public PBEffect getBoxEffect()
    {
        return pbEffect;
    }

    public void setBoxEffect(PBEffect effect)
    {
        pbEffect = effect;
    }

    public Random getRandom()
    {
        return rand;
    }

    public int getDeathTicks()
    {
        return dataWatcher.getWatchableObjectInt(11);
    }

    public void setDeathTicks(int deathTicks)
    {
        dataWatcher.updateObject(11, deathTicks);
    }

    public float getRatioBoxOpen(float partialTicks)
    {
        if (floatAwayProgress >= 0.0f)
        {
            return MathHelper.clamp_float(((floatAwayProgress + partialTicks * 0.025f - 0.5f) * 2.0f), 0.0f, 1.0f);
        }
        else
        {
            return 1.0f;
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1)
    {
        readBoxData(var1);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1)
    {
        writeBoxData(var1);
    }

    public void readBoxData(NBTTagCompound compound)
    {
        setBoxEffect(PBEffectRegistry.loadEffect(compound.getCompoundTag("boxEffect")));

        effectTicksExisted = compound.getInteger("effectTicksExisted");
        canGenerateMoreEffectsAfterwards = compound.getBoolean("canGenerateMoreEffectsAfterwards");
        floatAwayProgress = compound.getFloat("floatAwayProgress");
        floatUp = compound.getBoolean("floatUp");
    }

    public void writeBoxData(NBTTagCompound compound)
    {
        NBTTagCompound effectCompound = new NBTTagCompound();
        PBEffectRegistry.writeEffect(getBoxEffect(), effectCompound);
        compound.setTag("boxEffect", effectCompound);

        compound.setInteger("effectTicksExisted", effectTicksExisted);
        compound.setBoolean("canGenerateMoreEffectsAfterwards", canGenerateMoreEffectsAfterwards);
        compound.setFloat("floatAwayProgress", floatAwayProgress);
        compound.setBoolean("floatUp", floatUp);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        NBTTagCompound compound = new NBTTagCompound();
        writeBoxData(compound);
        ByteBufUtils.writeTag(buffer, compound);
    }

    @Override
    public void readSpawnData(ByteBuf buffer)
    {
        NBTTagCompound compound = ByteBufUtils.readTag(buffer);

        if (compound != null)
        {
            readBoxData(compound);
        }
    }

    @Override
    public void writeUpdateData(ByteBuf buffer, String context)
    {
        if (context.equals("boxEffect"))
        {
            ;
        }
        {
            NBTTagCompound compound = new NBTTagCompound();
            writeBoxData(compound);
            ByteBufUtils.writeTag(buffer, compound);
        }
    }

    @Override
    public void readUpdateData(ByteBuf buffer, String context)
    {
        if (context.equals("boxEffect"))
        {
            NBTTagCompound compound = ByteBufUtils.readTag(buffer);

            if (compound != null)
            {
                readBoxData(compound);
            }
        }
    }
}
