package ivorius.pandorasbox.utils;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

/**
 * Created by lukas on 02.02.15.
 */
public class IvBlockPosHelper
{
    public static AxisAlignedBB expandBlockPos(BlockPos pos, double x, double y, double z)
    {
        return AxisAlignedBB.fromBounds(pos.getX() - x, pos.getY() - y, pos.getZ() - z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);
    }
}
