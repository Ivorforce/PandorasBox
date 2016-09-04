package ivorius.pandorasbox.worldgen;

import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.block.PBBlocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

/**
 * Created by lukas on 01.12.14.
 */
public class PBLoot
{
    public static void injectLoot(LootTable table, ResourceLocation location)
    {
        if (location.getResourceDomain().equals("minecraft"))
        {
            String path = location.getResourcePath();

            maybeInject(table, path, "chests/jungle_temple", "pool1", 5);
            maybeInject(table, path, "chests/abandoned_mineshaft", "main", 5);
            maybeInject(table, path, "chests/simple_dungeon", "pool1", 5);
            maybeInject(table, path, "chests/desert_pyramid", "pool1", 5);
            maybeInject(table, path, "chests/stronghold_corridor", "main", 5);
            maybeInject(table, path, "chests/stronghold_crossing", "main", 5);
            maybeInject(table, path, "chests/stronghold_library", "main", 5);
        }
    }

    private static void maybeInject(LootTable table, String path, String name, String poolName, int weight)
    {
        if (path.equals(name))
            injectIntoLootTable(table, name, poolName, weight);
    }

    public static void injectIntoLootTable(LootTable table, String tableName, String poolName, int weight)
    {
        LootPool pool = table.getPool(poolName);

        if (pool != null)
            pool.addEntry(new LootEntryItem(Item.getItemFromBlock(PBBlocks.pandorasBox), weight, 0, new LootFunction[0], new LootCondition[0], "pandoras_box"));
        else
            PandorasBox.logger.error("Didn't find pool: " + poolName + " in loot table: " + tableName);
    }
}
