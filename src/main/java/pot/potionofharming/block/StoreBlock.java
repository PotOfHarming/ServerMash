package pot.potionofharming.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import pot.potionofharming.ServerMash;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreBlock {
    private static HashMap<World, HashMap<DimensionType, HashMap<ChunkPos, ArrayList<BlockPos>>>> blockList = new HashMap<>();
    public static void store(BlockPos bp, World w) {
        DimensionType d = w.getDimension();
        ChunkPos chunkPos = w.getChunk(bp).getPos();

        // check if the world already exists in the list
        if (!blockList.containsKey(w)) {
            HashMap<DimensionType, HashMap<ChunkPos, ArrayList<BlockPos>>> dimensions = new HashMap<>();
            HashMap<ChunkPos, ArrayList<BlockPos>> chunks = new HashMap<>();
            ArrayList<BlockPos> blocks = new ArrayList<>();

            blocks.add(bp);
            chunks.put(chunkPos, blocks);
            dimensions.put(d, chunks);
            blockList.put(w, dimensions);
            if (ServerMash.debug) ServerMash.LOGGER.info(blockList.toString());
            return;
        }
        // if world does exist
        HashMap<DimensionType, HashMap<ChunkPos, ArrayList<BlockPos>>> dimensionHashMap = blockList.get(w);



        // check if the dimension already exists in the list
        if (!dimensionHashMap.containsKey(d)) {
            HashMap<ChunkPos, ArrayList<BlockPos>> chunks = new HashMap<>();
            ArrayList<BlockPos> blocks = new ArrayList<>();

            blocks.add(bp);
            chunks.put(chunkPos, blocks);
            dimensionHashMap.put(d, chunks);
            blockList.put(w, dimensionHashMap);
            if (ServerMash.debug) ServerMash.LOGGER.info(blockList.toString());
            return;
        }
        // if dimension does exist
        HashMap<ChunkPos, ArrayList<BlockPos>> chunkHashMap = dimensionHashMap.get(d);



        // check if the chunk already exists in the list
        if (!chunkHashMap.containsKey(chunkPos)) {
            ArrayList<BlockPos> blocks = new ArrayList<>();

            blocks.add(bp);
            chunkHashMap.put(chunkPos, blocks);
            dimensionHashMap.put(d, chunkHashMap);
            blockList.put(w, dimensionHashMap);
            if (ServerMash.debug) ServerMash.LOGGER.info(blockList.toString());
            return;
        }
        // if chunk does exist
        ArrayList<BlockPos> blockPosList = chunkHashMap.get(chunkPos);



        if (blockPosList.contains(bp)) return;
        blockPosList.add(bp);
        chunkHashMap.put(chunkPos, blockPosList);
        dimensionHashMap.put(d, chunkHashMap);
        blockList.put(w, dimensionHashMap);
        if (ServerMash.debug) ServerMash.LOGGER.info(blockList.toString());
    }
}
