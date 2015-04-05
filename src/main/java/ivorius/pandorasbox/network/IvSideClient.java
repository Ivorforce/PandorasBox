package ivorius.pandorasbox.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by lukas on 29.07.14.
 */
public class IvSideClient
{
    // Copied from ivtoolkit

    @SideOnly(Side.CLIENT)
    public static EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    @SideOnly(Side.CLIENT)
    public static World getClientWorld()
    {
        return Minecraft.getMinecraft().theWorld;
    }
}
