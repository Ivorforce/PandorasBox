package ivorius.pandorasbox.events;

import ivorius.pandorasbox.PBConfig;
import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.worldgen.PBLoot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by lukas on 29.07.14.
 */
public class PBEventHandler
{
    public void register()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event)
    {
        if (event instanceof ConfigChangedEvent.OnConfigChangedEvent && event.getModID().equals(PandorasBox.MOD_ID))
        {
            PBConfig.loadConfig(event.getConfigID());

            if (PandorasBox.config.hasChanged())
                PandorasBox.config.save();
        }
    }

    @SubscribeEvent
    public void onLoadLootTable(LootTableLoadEvent event)
    {
        if (PBConfig.allowLootTableInjection)
            PBLoot.injectLoot(event.getTable(), event.getName());
    }
}
