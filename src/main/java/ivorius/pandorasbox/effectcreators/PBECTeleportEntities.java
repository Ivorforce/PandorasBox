/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectEntitiesTeleport;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECTeleportEntities implements PBEffectCreator
{
    public float chanceForMoreEffects;
    public IValue time;
    public DValue range;
    public DValue teleportRange;
    public IValue teleports;

    public PBECTeleportEntities(float chanceForMoreEffects, IValue time, DValue range, DValue teleportRange, IValue teleports)
    {
        this.chanceForMoreEffects = chanceForMoreEffects;
        this.time = time;
        this.range = range;
        this.teleportRange = teleportRange;
        this.teleports = teleports;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        double range = this.range.getValue(random);
        double teleportRange = this.teleportRange.getValue(random);
        int teleports = this.teleports.getValue(random);

        PBEffectEntitiesTeleport effect = new PBEffectEntitiesTeleport(time, range, teleportRange, teleports);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return chanceForMoreEffects;
    }
}
