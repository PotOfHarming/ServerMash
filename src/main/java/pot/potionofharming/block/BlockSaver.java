package pot.potionofharming.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import pot.potionofharming.ServerMash;
import pot.potionofharming.config.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockSaver {

    public static void checkBlockLists(ArrayList<HashMap<World, ArrayList<HashMap<DimensionType, ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>>>>>> blocks) {
        blocks.forEach(worldHashMap -> {
            worldHashMap.keySet().forEach(world -> {

            });

        });
    }
}
