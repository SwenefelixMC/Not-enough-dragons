package net.sweningteam.neoforge.platform;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.NetworkHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class NeoForgeNetworkHelper implements NetworkHelper {
    private final Map<Class<?>, Binding<?>> bindings = new ConcurrentHashMap<>();

    private enum Direction {
        SERVERBOUND,
        CLIENTBOUND
    }

    private static final class PayloadWrapper<T> implements CustomPacketPayload {
        private final Type<PayloadWrapper<T>> type;
        private final T message;

        private PayloadWrapper(Type<PayloadWrapper<T>> type, T message) {
            this.type = type;
            this.message = message;
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return type;
        }
    }

    private static final class Binding<T> {
        final CustomPacketPayload.Type<PayloadWrapper<T>> type;
        final PacketEncoder<T> encoder;
        final Direction direction;

        Binding(CustomPacketPayload.Type<PayloadWrapper<T>> type, PacketEncoder<T> encoder, Direction direction) {
            this.type = type;
            this.encoder = encoder;
            this.direction = direction;
        }
    }

    private Binding<Object> bindingFor(Object message) {
        Class<?> messageClass = message.getClass();
        @SuppressWarnings("unchecked")
        Binding<Object> binding = (Binding<Object>) bindings.get(messageClass);
        if (binding == null) {
            throw new IllegalStateException("No network binding registered for " + messageClass.getName());
        }
        return binding;
    }

    private static final List<Consumer<PayloadRegistrar>> REGISTRATIONS = new ArrayList<>();

    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1"); // set your network protocol version
        for (Consumer<PayloadRegistrar> registration : REGISTRATIONS) {
            registration.accept(registrar);
        }
    }

    @Override
    public <T> void registerServerbound(Class<T> type, ResourceLocation id, PacketEncoder<T> encoder, PacketDecoder<T> decoder, ServerboundHandler<T> handler) {
        CustomPacketPayload.Type<PayloadWrapper<T>> payloadType = new CustomPacketPayload.Type<>(id);
        bindings.put(type, new Binding<>(payloadType, encoder, Direction.SERVERBOUND));
        REGISTRATIONS.add(registrar -> {
            StreamCodec<FriendlyByteBuf, PayloadWrapper<T>> codec = CustomPacketPayload.codec(
                    (msg, buf) -> encoder.encode(msg.message, buf),
                    buf -> new PayloadWrapper<>(payloadType, decoder.decode(buf))
            );

            registrar.playToServer(
                    payloadType,
                    codec,
                    (payload, context) -> context.enqueueWork(() -> handler.handle(payload.message, (ServerPlayer) context.player()))
            );
        });
    }


    @Override
    public <T> void registerClientbound(Class<T> type, ResourceLocation id, PacketEncoder<T> encoder, PacketDecoder<T> decoder, ClientboundHandler<T> handler) {
        CustomPacketPayload.Type<PayloadWrapper<T>> payloadType = new CustomPacketPayload.Type<>(id);

        REGISTRATIONS.add(registrar -> {
            StreamCodec<FriendlyByteBuf, PayloadWrapper<T>> codec = CustomPacketPayload.codec(
                    (msg, buf) -> encoder.encode(msg.message, buf),
                    buf -> new PayloadWrapper<>(payloadType, decoder.decode(buf))
            );

            registrar.playToClient(
                    payloadType,
                    codec,
                    (payload, context) -> context.enqueueWork(() -> handler.handle(payload.message, context.player()))
            );
        });
    }

    @Override
    public void sendToServer(Object message) {
        if(!FMLEnvironment.getDist().isClient()){
            throw new IllegalStateException("Client-only networking method invoked on a non-client environment");
        }
        Binding<Object> binding = bindingFor(message);
        if(binding.direction != Direction.SERVERBOUND){
            throw new IllegalStateException("Attempted to send clientbound packet to server: " + message.getClass());
        }
        ClientPacketDistributor.sendToServer(new PayloadWrapper<>(binding.type, message));
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
