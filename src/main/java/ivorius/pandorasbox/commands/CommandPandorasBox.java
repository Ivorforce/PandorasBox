/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.commands;

import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class CommandPandorasBox extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "pandora";
    }

    @Override
    public String getCommandUsage(ICommandSender var1)
    {
        return "/pandora [player=???] [effect=???] [invisible=true]";
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) throws CommandException
    {
        Hashtable<String, String> arguments = new Hashtable<String, String>();
        for (String arg : var2)
        {
            int eqIndex = arg.indexOf('=');
            if (eqIndex > 0)
            {
                arguments.put(arg.substring(0, eqIndex), arg.substring(eqIndex + 1));
            }
        }

        EntityPlayer player = null;
        if (arguments.containsKey("player"))
        {
            try
            {
                player = getPlayer(var1, arguments.get("player"));
            }
            catch (Exception ex)
            {
                //Player not found exception, damn you Mojang...
            }
        }

        if (player == null)
            player = getCommandSenderAsPlayer(var1);

        if (player != null)
        {
            EntityPandorasBox box;

            String effectName = arguments.get("effect");
            if (effectName != null)
            {
                box = PBECRegistry.spawnPandorasBox(player.worldObj, player.getRNG(), effectName, player);

                if (box != null)
                {
                    box.setCanGenerateMoreEffectsAfterwards(false);
                }
            }
            else
            {
                box = PBECRegistry.spawnPandorasBox(player.worldObj, player.getRNG(), true, player);
            }

            if (box != null)
            {
                if ("true".equals(arguments.get("invisible")))
                {
                    box.setInvisible(true);
                    box.stopFloating();
                }
            }

            byte[] playerNameBytes = new byte[player.getName().length()];
            for (int i = 0; i < playerNameBytes.length; i++)
            {
                playerNameBytes[i] = (byte) player.getName().charAt(i);
            }
        }
        else
        {
            throw new PlayerNotFoundException();
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr, BlockPos pos)
    {
        if (par2ArrayOfStr.length == 0)
            return null;

        List<String> newArgs = new ArrayList<>();

        if (!hasArgument("effect", par2ArrayOfStr))
            newArgs.addAll(Arrays.asList(prefixArgumentsWithKey("effect", PBECRegistry.getIDArray())));

        if (!hasArgument("player", par2ArrayOfStr))
            newArgs.addAll(Arrays.asList(prefixArgumentsWithKey("player", this.func_71542_c())));

        if (!hasArgument("invisible", par2ArrayOfStr))
            newArgs.addAll(Arrays.asList(prefixArgumentsWithKey("invisible", "true", "false")));

        return getListOfStringsMatchingLastWord(par2ArrayOfStr, newArgs.toArray(new String[newArgs.size()]));
    }

    private static boolean hasArgument(String key, String[] arguments)
    {
        for (int i = 0; i < arguments.length - 1; i++)
        {
            if (arguments[i].startsWith(key + "="))
                return true;
        }

        return false;
    }

    private static String[] prefixArgumentsWithKey(String key, String... currentArguments)
    {
        String[] prefixed = new String[currentArguments.length];
        for (int i = 0; i < prefixed.length; i++)
            prefixed[i] = key + "=" + currentArguments[i];
        return prefixed;
    }

    private static String[] getMissingArguments(String[] currentArguments, String... arguments)
    {
        ArrayList<String> arrayList = new ArrayList<String>();

        for (String s : arguments)
        {
            if (!hasArgument(s, currentArguments))
                arrayList.add(s + "=");
        }

        return arrayList.toArray(new String[arrayList.size()]);
    }

    protected String[] func_71542_c()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
