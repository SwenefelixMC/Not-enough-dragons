package net.sweningteam.neoforge.platform;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.NetworkHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class NeoForgeNetworkHelper implements NetworkHelper {


    private static final List<Consumer<PayloadRegistrar>> REGISTRATIONS = new ArrayList<>();

    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1"); // set your network protocol version
        for (Consumer<PayloadRegistrar> registration : REGISTRATIONS) {
            registration.accept(registrar);
        }
    }

    @Override
    public <T> void registerServerbound(Class<T> type, ResourceLocation id, PacketEncoder<T> encoder, PacketDecoder<T> decoder, ServerboundHandler<T> handler) {
        CustomPacketPayload.Type<GenericPayload<T>> payloadType = new CustomPacketPayload.Type<>(id);

        REGISTRATIONS.add(registrar -> {
            StreamCodec<FriendlyByteBuf, GenericPayload<T>> codec = CustomPacketPayload.codec(
                    (msg, buf) -> encoder.encode(msg.value(), buf),
                    buf -> new GenericPayload<>(payloadType, decoder.decode(buf))
            );

            registrar.playToServer(
                    payloadType,
                    codec,
                    (payload, context) -> context.enqueueWork(() -> handler.handle(payload.value(), (ServerPlayer) context.player()))
            );
        });
    }


    @Override
    public <T> void registerClientbound(Class<T> type, ResourceLocation id, PacketEncoder<T> encoder, PacketDecoder<T> decoder, ClientboundHandler<T> handler) {
        CustomPacketPayload.Type<GenericPayload<T>> payloadType = new CustomPacketPayload.Type<>(id);

        REGISTRATIONS.add(registrar -> {
            StreamCodec<FriendlyByteBuf, GenericPayload<T>> codec = CustomPacketPayload.codec(
                    (msg, buf) -> encoder.encode(msg.value(), buf),
                    buf -> new GenericPayload<>(payloadType, decoder.decode(buf))
            );

            registrar.playToClient(
                    payloadType,
                    codec,
                    (payload, context) -> context.enqueueWork(() -> handler.handle(payload.value(), context.player()))
            );
        });
    }

    @Override
    public void sendToServer(Object message) {
        if (message instanceof CustomPacketPayload payload) {
            ClientPacketDistributor.sendToServer(payload);
        }
    }

    @Override
    public void sendToPlayer(ServerPlayer player, Object message) {
        if (message instanceof CustomPacketPayload payload) {
            PacketDistributor.sendToPlayer(player, payload);
        }
    }

    @Override
    public void sendToTracking(Entity entity, Object message) {
        if (message instanceof CustomPacketPayload payload) {
            PacketDistributor.sendToPlayersTrackingEntity(entity, payload);
        }
    }

    @Override
    public void sendToDimension(Level level, Object message) {
        if (message instanceof CustomPacketPayload payload) {
            PacketDistributor.sendToPlayersInDimension((ServerLevel) level, payload);
        }
    }

    public record GenericPayload<T>(CustomPacketPayload.Type<GenericPayload<T>> type, T value) implements CustomPacketPayload {
        @Override
        public Type<GenericPayload<T>> type() {
            return type;
        }
    }
}
