/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.random.PandorasBoxEntityNamer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectSpawnEntityIDList extends PBEffectSpawnEntities
{
    public String[][] entityIDs;

    public int nameEntities;
    public int equipLevel;
    public int buffLevel;

    public PBEffectSpawnEntityIDList()
    {
    }

    public PBEffectSpawnEntityIDList(int time, String[] entityIDs, int nameEntities, int equipLevel, int buffLevel)
    {
        super(time, entityIDs.length);
        this.entityIDs = get2DStringArray(entityIDs);

        this.nameEntities = nameEntities;
        this.equipLevel = equipLevel;
        this.buffLevel = buffLevel;
    }

    public PBEffectSpawnEntityIDList(int time, String[][] entityIDs, int nameEntities, int equipLevel, int buffLevel)
    {
        super(time, entityIDs.length);
        this.entityIDs = entityIDs;

        this.nameEntities = nameEntities;
        this.equipLevel = equipLevel;
        this.buffLevel = buffLevel;
    }

    private String[][] get2DStringArray(String[] strings)
    {
        String[][] result = new String[strings.length][1];
        for (int i = 0; i < strings.length; i++)
        {
            result[i][0] = strings[i];
        }
        return result;
    }

    @Override
    public Entity createEntity(World world, EntityPandorasBox pbEntity, Random random, int number, double x, double y, double z)
    {
        String[] entityTower = entityIDs[number];
        Entity previousEntity = null;

        for (String entityID : entityTower)
        {
            Entity newEntity = createEntity(world, pbEntity, random, entityID, x, y, z);

            if (newEntity instanceof EntityLiving)
            {
                randomizeEntity(random, pbEntity.getEntityId(), (EntityLiving) newEntity, nameEntities, equipLevel, buffLevel);
            }

            if (previousEntity != null)
            {
                world.spawnEntityInWorld(previousEntity);
                previousEntity.mountEntity(newEntity);
            }

            previousEntity = newEntity;
        }

        return previousEntity;
    }

    public static void randomizeEntity(Random random, long namingSeed, EntityLiving entityLiving, int nameEntities, int equipLevel, int buffLevel)
    {
        if (nameEntities == 1)
        {
            entityLiving.setCustomNameTag(PandorasBoxEntityNamer.getRandomName(random));
            entityLiving.setAlwaysRenderNameTag(true);
        }
        else if (nameEntities == 2)
        {
            entityLiving.setCustomNameTag(PandorasBoxEntityNamer.getRandomCasualName(random));
        }
        else if (nameEntities == 3)
        {
            entityLiving.setCustomNameTag(PandorasBoxEntityNamer.getRandomCasualName(new Random(namingSeed)));
        }

        if (equipLevel > 0)
        {
            float itemChancePerSlot = 1.0f - (0.5f / equipLevel);
            float upgradeChancePerSlot = 1.0f - (1.0f / equipLevel);

            for (int i = 0; i < 5; i++)
            {
                if (random.nextFloat() < itemChancePerSlot)
                {
                    int itemLevel = 0;
                    while (random.nextFloat() < upgradeChancePerSlot && itemLevel < equipLevel)
                    {
                        itemLevel++;
                    }

                    if (i == 0)
                    {
                        ItemStack itemStack = PandorasBoxHelper.getRandomWeaponItemForLevel(random, itemLevel);

                        entityLiving.setCurrentItemOrArmor(i, itemStack);
                    }
                    else
                    {
                        if (i == 4 && random.nextFloat() < 0.2f / equipLevel)
                        {
                            entityLiving.setCurrentItemOrArmor(4, new ItemStack(random.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                        }
                        else
                        {
                            Item item = EntityLiving.getArmorItemForSlot(i, Math.min(itemLevel, 4));

                            if (item != null)
                            {
                                entityLiving.setCurrentItemOrArmor(i, new ItemStack(item));
                            }
                            else
                            {
                                System.err.println("Pandora's Box: Item not found for slot '" + i + "', level '" + itemLevel + "'");
                            }
                        }
                    }
                }
            }
        }

        if (buffLevel > 0)
        {
            IAttributeInstance health = entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            if (health != null)
            {
                double healthMultiplierP = random.nextDouble() * buffLevel * 0.25;
                health.applyModifier(new AttributeModifier("Zeus's magic", healthMultiplierP, 2));
            }

            IAttributeInstance knockbackResistance = entityLiving.getEntityAttribute(SharedMonsterAttributes.knockbackResistance);
            if (knockbackResistance != null)
            {
                double knockbackResistanceP = random.nextDouble() * buffLevel * 0.25;
                knockbackResistance.applyModifier(new AttributeModifier("Zeus's magic", knockbackResistanceP, 2));
            }

            IAttributeInstance movementSpeed = entityLiving.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            if (movementSpeed != null)
            {
                double movementSpeedP = random.nextDouble() * buffLevel * 0.08;
                movementSpeed.applyModifier(new AttributeModifier("Zeus's magic", movementSpeedP, 2));
            }

            IAttributeInstance attackDamage = entityLiving.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            if (attackDamage != null)
            {
                double attackDamageP = random.nextDouble() * buffLevel * 0.25;
                attackDamage.applyModifier(new AttributeModifier("Zeus's magic", attackDamageP, 2));
            }
        }
    }

    public static Entity createEntity(World world, EntityPandorasBox pbEntity, Random random, String entityID, double x, double y, double z)
    {
        try
        {
            if ("pbspecial_XP".equals(entityID))
            {
                return new EntityXPOrb(world, x, y, z, 10);
            }
            else if ("pbspecial_wolfTamed".equals(entityID))
            {
                EntityPlayer nearest = getPlayer(world, pbEntity);
                EntityWolf wolf = new EntityWolf(world);

                wolf.setPosition(x, y, z);
                wolf.rotationYaw = random.nextFloat() * 360.0f;

                if (nearest != null)
                {
                    wolf.setTamed(true);
                    wolf.setPathToEntity(null);
                    wolf.setAttackTarget(null);
                    wolf.func_152115_b(nearest.getUniqueID().toString());
                    wolf.worldObj.setEntityState(wolf, (byte) 7);
                }

                return wolf;
            }
            else if ("pbspecial_ocelotTamed".equals(entityID))
            {
                EntityPlayer nearest = getPlayer(world, pbEntity);

                EntityOcelot ocelot = new EntityOcelot(world);

                ocelot.setPosition(x, y, z);
                ocelot.rotationYaw = random.nextFloat() * 360.0f;

                if (nearest != null)
                {
                    ocelot.setTamed(true);
                    ocelot.setTameSkin(1 + ocelot.worldObj.rand.nextInt(3));
                    ocelot.func_152115_b(nearest.getUniqueID().toString());
                    ocelot.worldObj.setEntityState(ocelot, (byte) 7);
                }

                return ocelot;
            }
            else if (entityID.startsWith("pbspecial_tnt"))
            {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, x, y, z, null);
                entitytntprimed.fuse = Integer.valueOf(entityID.substring(13));

                return entitytntprimed;
            }
            else if ("pbspecial_invisibleTnt".startsWith(entityID))
            {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, x, y, z, null);
                entitytntprimed.fuse = Integer.valueOf(entityID.substring(22));
                entitytntprimed.setInvisible(true); // Doesn't work yet :/

                return entitytntprimed;
            }
            else if ("pbspecial_firework".equals(entityID))
            {
                ItemStack stack = new ItemStack(Items.fireworks);
                stack.setTagInfo("Fireworks", createRandomFirework(random));

                EntityFireworkRocket fireworkRocket = new EntityFireworkRocket(world, x, y, z, stack);
                return fireworkRocket;
            }
            else if ("pbspecial_angryWolf".equals(entityID))
            {
                EntityWolf wolf = new EntityWolf(world);
                wolf.setPosition(x, y, z);
                wolf.rotationYaw = random.nextFloat() * 360.0f;
                wolf.setAttackTarget(world.getClosestPlayer(x, y, z, 40.0));

                return wolf;
            }
            else if ("pbspecial_superchargedCreeper".equals(entityID))
            {
                EntityCreeper creeper = new EntityCreeper(world);
                creeper.setPosition(x, y, z);
                creeper.rotationYaw = random.nextFloat() * 360.0f;
                creeper.getDataWatcher().updateObject(17, (byte) 1); // Supercharge
                return creeper;
            }
            else if ("pbspecial_skeletonWither".equals(entityID))
            {
                EntitySkeleton skeleton = new EntitySkeleton(world);
                skeleton.setPosition(x, y, z);
                skeleton.rotationYaw = random.nextFloat() * 360.0f;

                skeleton.setSkeletonType(1);
                skeleton.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
                skeleton.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);

                return skeleton;
            }

            Entity entity = EntityList.createEntityByName(entityID, world);
            entity.setPosition(x, y, z);
            entity.rotationYaw = random.nextFloat() * 360.0f;

            return entity;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public static NBTTagCompound createRandomFirework(Random random)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Explosions", createRandomFireworkExplosions(random, (random.nextInt(20)) != 0 ? 1 : (1 + random.nextInt(2))));
        compound.setByte("Flight", (byte) ((random.nextInt(15) != 0) ? 1 : (2 + random.nextInt(2))));
        return compound;
    }

    public static NBTTagList createRandomFireworkExplosions(Random random, int number)
    {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < number; i++)
        {
            list.appendTag(createRandomFireworkExplosion(random));
        }

        return list;
    }

    public static NBTTagCompound createRandomFireworkExplosion(Random random)
    {
        NBTTagCompound fireworkCompound = new NBTTagCompound();

        fireworkCompound.setBoolean("Flicker", random.nextInt(20) == 0);
        fireworkCompound.setBoolean("Trail", random.nextInt(30) == 0);
        fireworkCompound.setByte("Type", (byte) ((random.nextInt(10) != 0) ? 0 : (random.nextInt(4) + 1)));

        int[] colors = new int[(random.nextInt(15) != 0) ? 1 : (random.nextInt(2) + 2)];
        for (int i = 0; i < colors.length; i++)
        {
            colors[i] = ItemDye.field_150922_c[random.nextInt(16)];
        }
        fireworkCompound.setIntArray("Colors", colors);

        if (random.nextInt(25) == 0)
        {
            int[] fadeColors = new int[random.nextInt(2) + 1];
            for (int i = 0; i < fadeColors.length; i++)
            {
                fadeColors[i] = ItemDye.field_150922_c[random.nextInt(16)];
            }
            fireworkCompound.setIntArray("FadeColors", fadeColors);
        }

        return fireworkCompound;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        setNBTStrings2D("entityIDs", entityIDs, compound);

        compound.setInteger("nameEntities", nameEntities);
        compound.setInteger("equipLevel", equipLevel);
        compound.setInteger("buffLevel", buffLevel);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        entityIDs = getNBTStrings2D("entityIDs", compound);

        nameEntities = compound.getInteger("nameEntities");
        equipLevel = compound.getInteger("equipLevel");
        buffLevel = compound.getInteger("buffLevel");
    }
}
