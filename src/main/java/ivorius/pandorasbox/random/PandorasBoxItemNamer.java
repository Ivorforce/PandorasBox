/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

import java.util.Random;

public class PandorasBoxItemNamer
{
    public static String[] prefixesB = new String[]{"The", "The one and only", "Our", "Everyone's", "Woodcutter's", "Notch's", "Miner's", "Steve's", "Creeper's", "A", "Similar to the", "Your", "A copy of the"};
    public static String[] prefixes = new String[]{"Ominous", "Creepy", "Awesome", "Odd", "Stinking", "Uncontrollable", "Controllable", "Catastrophical", "Talking", "Silent", "Furious", "Banned", "Illegal", "Vast", "Huge", "Tiny", "Holographic", "Metaphorical", "#l~^", "Secret", "Unknown", "Popular", "Seecret", "Ivorius's", "Overrated", "Underrated", "Forgotten", "Lost", "Epic", "Heroic", "Famous", "Collossal", "Stupidly Strong", "Heroic", "Mad", "Wise", "Uncontrollable", "Glorious", "Shiny", "Unprecedented", "Unbelievable", "Incredible", "Problematic", "Distinct", "Lesser", "Greater", "Striking"};

    public static String[] mainNames = new String[]{"Tool", "Eye", "Board", "Weapon", "Toy", "Memory", "Relic", "Orb", "Block", "Thingy", "Device", "Killer", "Eraser", "Diminisher", "Supporter", "Spreader", "Eater", "Devourer", "XXa=})", "Helper", "Teacher", "Inventor", "Partner", "Friday", "Catapult", "Peak", "Player", "Remedy", "Destination", "Constructor", "Prophet"};

    public static String[] suffixes = new String[]{"of Blasphemy", "of Destruction", "of Happy Fun Times", "of Anger", "of Ultimate Evil", "of Gold", "of Wealth", "of Nyan", "of Lords and Knights", "of Water", "of Fire", "of Notch", "of Mojang", "of Light", "of Empty Fridges", "of Cold", "of Icecream", "of Cold Blooded Murder", "of Secret Organizations", "of Computers", "of 4th Wall Breaking", "#_::/", "of Electricity", "of Alien Technology", "of Political Correctness", "with a Twist", "of Teasing Grammer Nazis", "of Updates", "with 3 Heads", "of Manleyness", "that has a far too long name, greatly to the dismay of the holding player", "- deal with it", "of Luck"};

    public static String getRandomName(Random random)
    {
        StringBuilder nameBuilder = new StringBuilder();

        if (random.nextFloat() < 0.7)
        {
            nameBuilder.append(prefixesB[random.nextInt(prefixesB.length)]);
            nameBuilder.append(" ");
        }

        if (random.nextFloat() < 0.7)
        {
            nameBuilder.append(prefixes[random.nextInt(prefixes.length)]);
            nameBuilder.append(" ");
        }

        nameBuilder.append(mainNames[random.nextInt(mainNames.length)]);

        if (random.nextFloat() < 0.7)
        {
            nameBuilder.append(" ");
            nameBuilder.append(suffixes[random.nextInt(suffixes.length)]);
        }

        return nameBuilder.toString();
    }
}
