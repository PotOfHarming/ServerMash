package pot.potionofharming.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import pot.potionofharming.ServerMash;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreBlocks {
    private static ArrayList<HashMap<World, ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>>>> blocks = new ArrayList<>();
    public static void checkBlock(BlockPos bp, World w) {
        ChunkPos chunkPos = w.getChunk(bp).getPos();
        // if blocks list is empty create a new hashmap
        if (blocks.isEmpty()) {
            HashMap<World, ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>>> worldHashMap = new HashMap<>();
            ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>> chunkListHashMap = new ArrayList<>();
            HashMap<ChunkPos, ArrayList<BlockPos>> chunkHashMap = new HashMap<>();
            ArrayList<BlockPos> blockPosList = new ArrayList<>();


            blockPosList.add(bp);
            chunkHashMap.put(w.getChunk(bp).getPos(), blockPosList);
            chunkListHashMap.add(chunkHashMap);
            worldHashMap.put(w, chunkListHashMap);
            blocks.add(worldHashMap);
            if (ServerMash.debug) ServerMash.LOGGER.info(blocks.toString());
            return;
        }





        // if blocks list is not empty check if world is already in blocks list
        int foundWorld = -1;
        for (int i = 0; i<blocks.size(); i++) {
            HashMap<World, ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>>> world = blocks.get(i);
            if (world.containsKey(w)) {
                foundWorld=i;
                break;
            }
        }

        // if not find make a new hashmap for the world and put it in the blocks list
        if (foundWorld==-1) {
            HashMap<World, ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>>> worldHashMap = new HashMap<>();
            ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>> chunkListHashMap = new ArrayList<>();
            HashMap<ChunkPos, ArrayList<BlockPos>> chunkHashMap = new HashMap<>();
            ArrayList<BlockPos> blockPosList = new ArrayList<>();


            blockPosList.add(bp);
            chunkHashMap.put(w.getChunk(bp).getPos(), blockPosList);
            chunkListHashMap.add(chunkHashMap);
            worldHashMap.put(w, chunkListHashMap);
            blocks.add(worldHashMap);
            if (ServerMash.debug) ServerMash.LOGGER.info(blocks.toString());
            return;
        }

        // if world hashmap was found use that hashmap
        HashMap<World, ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>>> worldHashMap = blocks.get(foundWorld);
        ArrayList<HashMap<ChunkPos, ArrayList<BlockPos>>> chunksList = worldHashMap.get(w);





        int foundChunk = -1;
        for (int i = 0; i<chunksList.size(); i++) {
            HashMap<ChunkPos, ArrayList<BlockPos>> chunk = chunksList.get(i);
            if (chunk.containsKey(chunkPos)) {
                foundChunk = i;
                break;
            }
        }

        // if not found chunk make a new hashmap
        if (foundChunk==-1) {
            HashMap<ChunkPos, ArrayList<BlockPos>> chunkHashMap = new HashMap<>();
            ArrayList<BlockPos> blockPosList = new ArrayList<>();

            blockPosList.add(bp);
            chunkHashMap.put(chunkPos, blockPosList);
            chunksList.add(chunkHashMap);
            blocks.set(foundWorld, worldHashMap);
            if (ServerMash.debug) ServerMash.LOGGER.info(blocks.toString());
            return;
        }

        // if chunk found update the hashmap
        HashMap<ChunkPos, ArrayList<BlockPos>> chunkHashMap = chunksList.get(foundChunk);
        ArrayList<BlockPos> blockPosList = chunkHashMap.get(chunkPos);





        // check if blockPos is in the list, then ignore
        if (blockPosList.contains(bp)) {
            if (ServerMash.debug) ServerMash.LOGGER.info(blocks.toString());
            return;
        }

        // add block to list
        blockPosList.add(bp);

        // correct all the lists
        chunkHashMap.replace(chunkPos, blockPosList);
        chunksList.set(foundChunk, chunkHashMap);
        worldHashMap.replace(w, chunksList);
        blocks.set(foundWorld ,worldHashMap);

        if (ServerMash.debug) ServerMash.LOGGER.info(blocks.toString());
    }
}
