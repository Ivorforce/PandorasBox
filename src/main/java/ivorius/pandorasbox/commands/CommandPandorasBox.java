/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.commands;

import ivorius.mcopts.commands.CommandExpecting;
import ivorius.mcopts.commands.parameters.MCP;
import ivorius.mcopts.commands.parameters.Parameters;
import ivorius.mcopts.commands.parameters.expect.Expect;
import ivorius.mcopts.commands.parameters.expect.MCE;
import ivorius.pandorasbox.effectcreators.PBECRegistry;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;

public class CommandPandorasBox extends CommandExpecting
{
    @Override
    public String getName()
    {
        return "pandora";
    }

    @Override
    public void expect(Expect expect)
    {
        expect.named("player", "p").then(MCE::entity)
                .named("effect", "e").any((Object[]) PBECRegistry.getIDArray()).descriptionU("effect id")
                .flag("invisible", "i");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        Parameters parameters = Parameters.of(args, expect()::declare);

        Entity player = parameters.get("player").to(MCP.entity(server, server)).optional()
                .orElse(getCommandSenderAsPlayer(sender));

        EntityPandorasBox box;

        String effectName = parameters.get("effect").optional().orElse(null);

        if (effectName != null)
        {
            box = PBECRegistry.spawnPandorasBox(player.world, player.getEntityWorld().rand, effectName, player);

            if (box != null)
                box.setCanGenerateMoreEffectsAfterwards(false);
        }
        else
            box = PBECRegistry.spawnPandorasBox(player.world, player.getEntityWorld().rand, true, player);

        if (box != null)
        {
            if (parameters.has("invisible"))
            {
                box.setInvisible(true);
                box.stopFloating();
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 1;
    }
}
