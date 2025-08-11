package pot.potionofharming.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pot.potionofharming.ServerMash;
import pot.potionofharming.block.StoreBlock;

import java.util.ArrayList;

public class ModifyBlock {
    private static class BlockChecker {
        private final World world;
        private final BlockPos blockPos;
        private final Block block;

        public BlockChecker(World world, BlockPos blockPos, Block block) {
            this.world = world;
            this.blockPos = blockPos;
            this.block = block;
        }
    }
    public static void initDetectionBlocks() {
        PlayerBlockBreakEvents.AFTER.register(((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if (world.isClient) return;
            if (ServerMash.debug) ServerMash.LOGGER.info("BLOCK BROKEN");
            if (ServerMash.debug) ServerMash.LOGGER.info(blockState.getBlock().toString());

            StoreBlock.store(blockPos, world);
        }));


        ArrayList<BlockChecker> blockChecks = new ArrayList<>();
        UseBlockCallback.EVENT.register(((playerEntity, world, hand, blockHitResult) -> {
            if (world.isClient) return ActionResult.PASS;
            BlockPos bp = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            Block b = world.getBlockState(bp).getBlock();
            BlockChecker bc = new BlockChecker(world, bp, b);
            blockChecks.add(bc);
            return ActionResult.PASS;
        }));

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (blockChecks.isEmpty()) return;
            blockChecks.forEach(blockChecker -> {
                Block b1 = blockChecker.block;
                Block b2 = server.getWorld(blockChecker.world.getRegistryKey()).getBlockState(blockChecker.blockPos).getBlock();
                if (ServerMash.debug) ServerMash.LOGGER.info(b1.toString());
                if (ServerMash.debug) ServerMash.LOGGER.info(b2.toString());
                if (b1!=b2) {
                    if (ServerMash.debug) ServerMash.LOGGER.info("BLOCK PLACED");
                    StoreBlock.store(blockChecker.blockPos, blockChecker.world);
                }
            });
            blockChecks.clear();
        });
    }
}
