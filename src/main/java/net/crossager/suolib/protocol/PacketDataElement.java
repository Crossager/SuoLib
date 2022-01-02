package net.crossager.suolib.protocol;

import net.minecraft.network.PacketDataSerializer;

import java.lang.reflect.InvocationTargetException;

public class PacketDataElement<T extends Object> {
    T element;

    public PacketDataElement(T element) {
        this.element = element;
    }
    public void write(PacketDataSerializer d) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<PacketDataSerializer> classP = PacketDataSerializer.class;
        classP.getMethod("a", element.getClass()).invoke(d, element);
    }
}
