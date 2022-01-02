package net.crossager.suolib.protocol;

import net.crossager.common.reflection.ClassUtils;
import net.minecraft.network.PacketDataSerializer;

import java.util.function.Function;

public class ProtocolUtils {
    public static <T> Function<PacketDataSerializer, T> getConstructorFunction(Class<T> packet) throws NoSuchMethodException {
        return ClassUtils.getConstructorFunction(PacketDataSerializer.class, packet);
    }
}
