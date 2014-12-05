package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 05.12.14.
 */
public class PBEffectExplode extends PBEffectNormal
{
    public float explosionRadius;
    public boolean burning;

    public PBEffectExplode()
    {
    }

    public PBEffectExplode(int maxTicksAlive, float explosionRadius, boolean burning)
    {
        super(maxTicksAlive);
        this.explosionRadius = explosionRadius;
        this.burning = burning;
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, float prevRatio, float newRatio)
    {

    }

    @Override
    public void finalizeEffect(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random)
    {
        super.finalizeEffect(world, entity, effectCenter, random);

        if (!world.isRemote)
            world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, explosionRadius, burning);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        explosionRadius = compound.getFloat("explosionRadius");
        burning = compound.getBoolean("burning");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setFloat("explosionRadius", explosionRadius);
        compound.setBoolean("burning", burning);
    }
}
