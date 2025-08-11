package pot.potionofharming.Utilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

public class Utils {
    public static BlockPos getTopBlock(BlockPos b, World w) {
        return new BlockPos(b.getX(), w.getTopY(Heightmap.Type.WORLD_SURFACE, b)-1, b.getZ());
    }
}
