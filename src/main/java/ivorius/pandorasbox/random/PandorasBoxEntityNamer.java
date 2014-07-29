/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

import java.util.Random;

public class PandorasBoxEntityNamer
{
    public static String[] prefixesB = new String[]{"The", "The one and only", "Our", "Everyone's", "Woodcutter's", "Notch's", "Miner's", "Steve's", "Creeper's", "A", "Similar to the", "Your", "A copy of the"};
    public static String[] prefixes = new String[]{"Ominous", "Creepy", "Awesome", "Odd", "Stinking", "Uncontrollable", "Controllable", "Catastrophical", "Talking", "Silent", "Furious", "Banned", "Illegal", "Vast", "Huge", "Tiny", "Holographic", "Metaphorical", "#l~^", "Secret", "Unknown", "Popular", "Seecret", "Ivorius's", "Overrated", "Underrated", "Forgotten", "Lost", "Epic", "Heroic", "Famous", "Collossal", "Stupidly Strong", "Heroic", "Mad", "Wise", "Uncontrollable", "Glorious", "Shiny", "Unprecedented", "Unbelievable", "Incredible", "Problematic", "Distinct", "Lesser", "Greater", "Striking"};

    public static String[] mainNames = new String[]{"Dinnerbone", "Grumm", "jeb_", "Killer", "Savior", "Lord", "King", "Champion", "Bum", "Friend", "Enemy", "Solider", "Captain", "Thing", "Prophet", "Guide", "Knight", "Leader", "Despot", "Machine", "Droid", "Shapeshifter", "Boss", "Weirdo", "Outcast"};

    public static String[] suffixes = new String[]{"of Blasphemy", "of Destruction", "of Happy Fun Times", "of Anger", "of Ultimate Evil", "of Gold", "of Wealth", "of Nyan", "of Lords and Knights", "of Water", "of Fire", "of Notch", "of Mojang", "of Light", "of Empty Fridges", "of Cold", "of Icecream", "of Cold Blooded Murder", "of Secret Organizations", "of Computers", "of 4th Wall Breaking", "#_::/", "of Electricity", "of Alien Technology", "of Political Correctness", "with a Twist", "of Teasing Grammer Nazis", "of Updates", "with 3 heads", "of manleyness", "that has a far too long name, greatly to the dismay of the holding player", "- deal with it", "of Luck"};

    public static String[] casualNamesMale = new String[]{"Peter", "James", "Jim", "Lukas", "Lennart", "Caleb", "Notch", "Hank", "Jerry", "Jill", "Max", "Jude", "Jeb", "Archer", "Bobby", "Nick", "Toby", "Felix", "Tiberius", "Wilbur", "Ahmed", "Albert", "Barry", "Edmund", "Benedict", "Boris", "Desmond", "Clint", "Clive", "Chuck", "Eugene", "George", "Hector", "Igor", "Hubert", "Jamien", "Kenny", "Kyle", "Michael", "Maxwell", "Link", "Linus", "Oli", "Remy", "Alex", "Rider", "Sergio", "Shaun", "Stevie", "Terry", "Truman", "Wallace", "Will", "Zachary"};
    public static String[] casualNamesFemale = new String[]{"Anne", "Amely", "July", "Jade", "Abbey", "Adele", "Amy", "Deeba", "Avery", "Barbie", "Caprice", "Cathy", "Celeste", "Carlotte", "Chrissy", "Corina", "Diamond", "Edith", "Eileen", "Erica", "Florence", "Gloria", "Heidi", "Helen", "Honey", "Izzy", "Julia", "Kate", "Kelsey", "Kitty", "Lana", "Leonie", "Lilith", "Liv", "Lisa", "Lorraine", "Lyra", "Maddison", "Maggie", "Maria", "Lisbeth", "Nancy", "Nicole", "Paris", "Rachelle", "Rosie", "Sabina", "Sally", "Scarlet", "Selene", "Star", "Suzie", "Tess", "Tracie", "Yasmin", "Zoey"};

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

    public static String getRandomCasualName(Random random)
    {
        if (random.nextBoolean())
        {
            return casualNamesFemale[random.nextInt(casualNamesFemale.length)];
        }

        return casualNamesMale[random.nextInt(casualNamesMale.length)];
    }
}
