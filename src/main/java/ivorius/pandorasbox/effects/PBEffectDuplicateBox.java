package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.12.14.
 */
public class PBEffectDuplicateBox extends PBEffectNormal
{
    public static final int MODE_BOX_IN_BOX = 0;

    public int spawnMode;

    public static int timeNeededForSpawnMode(int mode)
    {
        switch (mode)
        {
            case MODE_BOX_IN_BOX:
                return 60;
        }

        return 0;
    }

    public PBEffectDuplicateBox()
    {
    }

    public PBEffectDuplicateBox(int spawnMode)
    {
        super(timeNeededForSpawnMode(spawnMode));
        this.spawnMode = spawnMode;
    }

    @Override
    public void setUpEffect(World world, EntityPandorasBox box, Vec3 effectCenter, Random random)
    {
        if (!world.isRemote)
        {
            PBEffect effect = PBECRegistry.createRandomEffect(world, random, box.posX, box.posY, box.posZ, true);
            EntityPandorasBox newBox = new EntityPandorasBox(world, effect);

            newBox.setPosition(box.posX, box.posY, box.posZ);
            newBox.rotationYaw = box.rotationYaw;

            switch (spawnMode)
            {
                case MODE_BOX_IN_BOX:
                    newBox.beginFloatingUp();
                    newBox.beginScalingIn();
                    world.spawnEntityInWorld(newBox);
                    break;
            }
        }
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, float prevRatio, float newRatio)
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("spawnMode", spawnMode);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        spawnMode = compound.getInteger("spawnMode");
    }
}
