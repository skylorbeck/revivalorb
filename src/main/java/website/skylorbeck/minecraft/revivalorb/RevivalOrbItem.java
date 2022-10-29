package website.skylorbeck.minecraft.revivalorb;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RevivalOrbItem extends Item {
    public RevivalOrbItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            List<ServerPlayerEntity> players = new java.util.ArrayList<ServerPlayerEntity>();
            for (ServerPlayerEntity player : ((ServerWorld) world).getPlayers()) {
                if (player.isSpectator()) {
                    players.add(player);
                }
            }

            if (players.size() > 0) {
                ServerPlayerEntity player = players.get(world.random.nextInt(players.size()));
                player.changeGameMode(GameMode.SURVIVAL);
                world.getPlayers().forEach(playerEntity -> {
                    playerEntity.sendMessage(player.getDisplayName().copyContentOnly().append(" has been revived by ").append(user.getDisplayName().copyContentOnly()), true);
                    playerEntity.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, 1.0F, 1.0F);
                });
                BlockPos spawn = world.getSpawnPos();
                player.refreshPositionAndAngles(spawn.getX(), spawn.getY(), spawn.getZ(), world.getSpawnAngle(),0.0F);
                player.refreshPositionAfterTeleport(spawn.getX(), spawn.getY(), spawn.getZ());
                ItemStack itemStack = user.getStackInHand(hand);
                itemStack.decrement(1);
                return TypedActionResult.success(itemStack);
            }
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Revives a random"));
        tooltip.add(Text.of("Spectator at Spawn"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}