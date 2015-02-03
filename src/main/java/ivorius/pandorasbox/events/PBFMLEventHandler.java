package ivorius.pandorasbox.events;

import ivorius.pandorasbox.PBConfig;
import ivorius.pandorasbox.PandorasBox;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by lukas on 29.07.14.
 */
public class PBFMLEventHandler
{
    public void register()
    {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event)
    {
        if (event instanceof ConfigChangedEvent.OnConfigChangedEvent && event.modID.equals(PandorasBox.MODID))
        {
            PBConfig.loadConfig(event.configID);

            if (PandorasBox.config.hasChanged())
                PandorasBox.config.save();
        }
    }
}
