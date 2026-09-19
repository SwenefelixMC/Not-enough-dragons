package net.sweningteam.not_enough_dragons.common.item.egg;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sweningteam.not_enough_dragons.common.entity.egg.base.DragonEgg;
import net.sweningteam.not_enough_dragons.common.entity.egg.base.DragonEggData;
import net.sweningteam.not_enough_dragons.common.registry.ModDataComponentTypes;
import org.jetbrains.annotations.NotNull;

public class DragonEggItem extends Item {
    private final EntityType<? extends DragonEgg>  ENTITY;

    public DragonEggItem(EntityType<? extends DragonEgg> entity, Properties properties) {
        super(properties);
        ENTITY = entity;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if(!(level instanceof ServerLevel serverLevel)){
            return InteractionResult.SUCCESS;
        }else{
            ItemStack itemStack = context.getItemInHand();
            BlockPos blockPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);
            BlockEntity BlockEntity = level.getBlockEntity(blockPos);
            DragonEgg egg = ENTITY.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
            if(egg == null){
                return InteractionResult.FAIL;
            }else{
                if(BlockEntity instanceof Spawner spawner){
                    if (!serverLevel.isSpawnerBlockEnabled()){
                        Player player = context.getPlayer();
                        if(player instanceof ServerPlayer serverPlayer){
                            serverPlayer.sendSystemMessage(Component.translatable("advMode.notEnabled.spawner"));
                        }
                        return InteractionResult.FAIL;
                    }else{
                        spawner.setEntityId(egg.getDragon(level).getType(), level.getRandom());
                        level.sendBlockUpdated(blockPos, blockState, blockState, 3);
                        level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                        itemStack.shrink(1);
                        return InteractionResult.SUCCESS;
                    }
                }else{
                    BlockPos blockPos2;
                    if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                        blockPos2 = blockPos;
                    } else {
                        blockPos2 = blockPos.relative(direction);
                    }
                    return spawnEgg(egg,itemStack,blockPos2,level);
                }
            }
        }
    }

    public InteractionResult spawnEgg(DragonEgg egg, ItemStack stack, BlockPos pos,Level level) {
        if (stack.get(ModDataComponentTypes.IS_DEAD.get()) == null || stack.get(ModDataComponentTypes.EMBRYO_AGE.get()) == null || stack.get(ModDataComponentTypes.EGG_TICK.get()) == null) {
            egg.setDragonEgg(
                    false,
                    0,
                    0,
                    new DragonEggData(6.5,1.25,0.8,0.1D,0.38,1.1,0.7D,0.25,2.2,100.0D)
            );
        } else {
            egg.setDragonEgg(
                    stack.get(ModDataComponentTypes.IS_DEAD.get()),
                    stack.get(ModDataComponentTypes.EMBRYO_AGE.get()),
                    stack.get(ModDataComponentTypes.EGG_TICK.get()),
                    stack.get(ModDataComponentTypes.DRAGON_EGG_DATA.get())
            );
        }
        egg.setPos(pos.getX() + (double)0.5F, (double)pos.getY(), (double)pos.getZ() + (double)0.5F);
        level.addFreshEntity(egg);
        return InteractionResult.SUCCESS;
    }

    public static void SetDragon(Boolean IsDead, Integer EmbryoAge, Integer HatchTick, ItemStack stack, DragonEggData DragonEggData){
           stack.set(ModDataComponentTypes.IS_DEAD.get(), IsDead);
           stack.set(ModDataComponentTypes.EMBRYO_AGE.get(),EmbryoAge);
           stack.set(ModDataComponentTypes.EGG_TICK.get(),HatchTick);
           stack.set(ModDataComponentTypes.DRAGON_EGG_DATA.get(),DragonEggData);
    }
}
