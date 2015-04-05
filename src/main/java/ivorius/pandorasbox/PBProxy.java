package ivorius.pandorasbox;

/**
 * Created by lukas on 29.07.14.
 */
public interface PBProxy
{
    void preInit();

    void load();

    void loadConfig(String categoryID);
}
