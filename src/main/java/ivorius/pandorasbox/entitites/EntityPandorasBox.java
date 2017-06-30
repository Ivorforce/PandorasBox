/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.entitites;

import io.netty.buffer.ByteBuf;
import ivorius.pandorasbox.PBConfig;
import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectRegistry;
import ivorius.pandorasbox.mods.Psychedelicraft;
import ivorius.pandorasbox.network.PacketEntityData;
import ivorius.pandorasbox.network.PartialUpdateHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class EntityPandorasBox extends Entity implements IEntityAdditionalSpawnData, PartialUpdateHandler
{
    public static final float BOX_UPSCALE_SPEED = 0.02f;

    private static final DataParameter<Integer> BOX_DEATH_TICKS = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.VARINT);

    protected int timeBoxWaiting;
    protected int effectTicksExisted;
    protected boolean canGenerateMoreEffectsAfterwards = true;

    protected PBEffect boxEffect;

    protected boolean floatUp = false;
    protected float floatAwayProgress = -1.0f;

    protected float scaleInProgress = 1.0f;

    protected Vec3d effectCenter = new Vec3d(0, 0, 0);

    public EntityPandorasBox(World world)
    {
        super(world);

        setSize(0.6f, 0.4f);
    }

    public EntityPandorasBox(World world, PBEffect effect)
    {
        this(world);

        setBoxEffect(effect);
        timeBoxWaiting = 40;
    }

    public int getTimeBoxWaiting()
    {
        return timeBoxWaiting;
    }

    public void setTimeBoxWaiting(int timeBoxWaiting)
    {
        this.timeBoxWaiting = timeBoxWaiting;
    }

    public int getEffectTicksExisted()
    {
        return effectTicksExisted;
    }

    public void setEffectTicksExisted(int effectTicksExisted)
    {
        this.effectTicksExisted = effectTicksExisted;
    }

    public boolean isCanGenerateMoreEffectsAfterwards()
    {
        return canGenerateMoreEffectsAfterwards;
    }

    public void setCanGenerateMoreEffectsAfterwards(boolean canGenerateMoreEffectsAfterwards)
    {
        this.canGenerateMoreEffectsAfterwards = canGenerateMoreEffectsAfterwards;
    }

    public boolean isFloatUp()
    {
        return floatUp;
    }

    public void setFloatUp(boolean floatUp)
    {
        this.floatUp = floatUp;
    }

    public float getFloatAwayProgress()
    {
        return floatAwayProgress;
    }

    public void setFloatAwayProgress(float floatAwayProgress)
    {
        this.floatAwayProgress = floatAwayProgress;
    }

    public Vec3d getEffectCenter()
    {
        return effectCenter;
    }

    public void setEffectCenter(double x, double y, double z)
    {
        this.effectCenter = new Vec3d(x, y, z);
    }

    public float getCurrentScale()
    {
        return scaleInProgress;
    }

    @Override
    protected void entityInit()
    {
        this.getDataManager().register(BOX_DEATH_TICKS, -1);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getDeathTicks() >= 0)
        {
            if (!world.isRemote)
            {
                if (getDeathTicks() >= 30)
                    setDead();
            }
            else
            {
                for (int e = 0; e < Math.min(getDeathTicks(), 60); e++)
                {
                    double xP = (rand.nextDouble() - rand.nextDouble()) * 0.5;
                    double yP = (rand.nextDouble() - rand.nextDouble()) * 0.5;
                    double zP = (rand.nextDouble() - rand.nextDouble()) * 0.5;

                    if (rand.nextBoolean())
                        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + xP, posY + yP, posZ + zP, 0.0D, 0.0D, 0.0D);
                    else
                        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + xP, posY + yP, posZ + zP, 0.0D, 0.0D, 0.0D);
                }
            }

            setDeathTicks(getDeathTicks() + 1);
        }

        if (timeBoxWaiting == 0 && getDeathTicks() < 0)
        {
            PBEffect effect = getBoxEffect();

            if (effect == null)
            {
                if (!world.isRemote)
                {
                    setDead();
                }
            }
            else
            {
                if (effect.isDone(this, effectTicksExisted))
                {
                    if (!world.isRemote)
                    {
                        boolean isCompletelyDone = true;

                        if (canGenerateMoreEffectsAfterwards && effect.canGenerateMoreEffectsAfterwards(this))
                        {
                            if (rand.nextFloat() < PBConfig.boxLongevity)
                            {
                                startNewEffect();

                                isCompletelyDone = false;
                            }
                        }

                        if (isCompletelyDone)
                            startFadingOut();
                    }
                }
                else
                {
                    if (effectTicksExisted == 0)
                        setEffectCenter(posX, posY, posZ);

                    effect.doTick(this, effectCenter, effectTicksExisted);
                }
            }
        }

        if (timeBoxWaiting == 0)
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
                moveRelative(0.0f, 0.01f, speed * 0.02f, 0.02f);
                motionY += speed * 0.005f;
            }

            floatAwayProgress += 0.025f;

            if (floatAwayProgress > 1.0f)
                stopFloating();
        }

        if (scaleInProgress < 1.0f)
            scaleInProgress += BOX_UPSCALE_SPEED;
        if (scaleInProgress > 1.0f)
            scaleInProgress = 1.0f;

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

        if (timeBoxWaiting == 0)
        {
            if (getDeathTicks() < 0)
            {
                if (!isInvisible())
                {
                    if (world.isRemote)
                    {
                        double yCenter = posY + height * 0.5;

                        for (int e = 0; e < 2; e++)
                        {
                            double xP = (rand.nextDouble() - rand.nextDouble()) * 0.2;
                            double yDir = rand.nextDouble() * 0.1;
                            double zP = (rand.nextDouble() - rand.nextDouble()) * 0.2;

                            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + xP, yCenter, posZ + zP, 0.0D, yDir, 0.0D);
                        }
                        for (int e = 0; e < 3; e++)
                        {
                            double xDir = (rand.nextDouble() - rand.nextDouble()) * 3.0;
                            double yDir = rand.nextDouble() * 4.0 + 2.0;
                            double zDir = (rand.nextDouble() - rand.nextDouble()) * 3.0;

                            double xP = (rand.nextDouble() - 0.5) * width;
                            double zP = (rand.nextDouble() - 0.5) * width;

                            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, posX + xP + xDir, yCenter + yDir, posZ + zP + zDir, -xDir, -yDir, -zDir);
                        }
                        for (int e = 0; e < 4; e++)
                        {
                            double xP = (rand.nextDouble() * 16) - 8D;
                            double yP = (rand.nextDouble() * 5) - 2D;
                            double zP = (rand.nextDouble() * 16D) - 8D;

                            double xDir = (rand.nextDouble() * 2D) - 1D;
                            double yDir = (rand.nextDouble() * 2D) - 1D;
                            double zDir = (rand.nextDouble() * 2D) - 1D;

                            world.spawnParticle(EnumParticleTypes.PORTAL, posX + xP, yCenter + yP, posZ + zP, xDir, yDir, zDir);
                        }
                    }

                    if (Psychedelicraft.isLoaded() && PBConfig.boxPowerVisuals)
                    {
                        float powerRange = 5.0f;
                        float powerStrength = 0.07f;
                        List<EntityLivingBase> nearbyEntities = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(powerRange, powerRange, powerRange));
                        for (EntityLivingBase entity : nearbyEntities)
                        {
                            float entityDist = (float) entity.getDistanceSqToEntity(this);
                            if (entityDist < powerRange)
                                Psychedelicraft.addDrugValue(entity, "Power", powerStrength * (powerRange - entityDist));
                        }
                    }
                }

                effectTicksExisted++;
            }
        }
        else
            timeBoxWaiting--;
    }

    public void startNewEffect()
    {
        effectTicksExisted = 0;
        timeBoxWaiting = rand.nextInt(40);

        boxEffect = PBECRegistry.createRandomEffect(world, rand, effectCenter.x, effectCenter.y, effectCenter.z, true);

        PandorasBox.network.sendToDimension(PacketEntityData.packetEntityData(this, "boxEffect"), world.provider.getDimension());
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
        PandorasBox.network.sendToDimension(PacketEntityData.packetEntityData(this, "boxEffect"), world.provider.getDimension());
    }

    public void beginScalingIn()
    {
        scaleInProgress = 0.0f;
    }

    public PBEffect getBoxEffect()
    {
        return boxEffect;
    }

    public void setBoxEffect(PBEffect effect)
    {
        boxEffect = effect;
    }

    public Random getRandom()
    {
        return rand;
    }

    public int getDeathTicks()
    {
        return getDataManager().get(BOX_DEATH_TICKS);
    }

    public void setDeathTicks(int deathTicks)
    {
        getDataManager().set(BOX_DEATH_TICKS, deathTicks);
    }

    public float getRatioBoxOpen(float partialTicks)
    {
        if (floatAwayProgress >= 0.0f)
            return MathHelper.clamp(((floatAwayProgress + partialTicks * 0.025f - 0.5f) * 2.0f), 0.0f, 1.0f);
        else
            return 1.0f;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.getEntityBoundingBox();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.getEntityBoundingBox();
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
        timeBoxWaiting = compound.getInteger("timeBoxWaiting");
        canGenerateMoreEffectsAfterwards = compound.getBoolean("canGenerateMoreEffectsAfterwards");
        floatAwayProgress = compound.getFloat("floatAwayProgress");
        floatUp = compound.getBoolean("floatUp");
        scaleInProgress = compound.getFloat("scaleInProgress");

        if (compound.hasKey("effectCenterX", Constants.NBT.TAG_DOUBLE) && compound.hasKey("effectCenterY", Constants.NBT.TAG_DOUBLE) && compound.hasKey("effectCenterZ", Constants.NBT.TAG_DOUBLE))
            setEffectCenter(compound.getDouble("effectCenterX"), compound.getDouble("effectCenterY"), compound.getDouble("effectCenterZ"));
        else
            setEffectCenter(posX, posY, posZ);
    }

    public void writeBoxData(NBTTagCompound compound)
    {
        NBTTagCompound effectCompound = new NBTTagCompound();
        PBEffectRegistry.writeEffect(getBoxEffect(), effectCompound);
        compound.setTag("boxEffect", effectCompound);

        compound.setInteger("effectTicksExisted", effectTicksExisted);
        compound.setInteger("timeBoxWaiting", timeBoxWaiting);
        compound.setBoolean("canGenerateMoreEffectsAfterwards", canGenerateMoreEffectsAfterwards);
        compound.setFloat("floatAwayProgress", floatAwayProgress);
        compound.setBoolean("floatUp", floatUp);
        compound.setFloat("scaleInProgress", scaleInProgress);

        compound.setDouble("effectCenterX", effectCenter.x);
        compound.setDouble("effectCenterY", effectCenter.y);
        compound.setDouble("effectCenterZ", effectCenter.z);
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
            readBoxData(compound);
    }

    @Override
    public void writeUpdateData(ByteBuf buffer, String context)
    {
        if (context.equals("boxEffect"))
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
