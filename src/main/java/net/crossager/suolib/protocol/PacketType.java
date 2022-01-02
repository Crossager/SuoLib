package net.crossager.suolib.protocol;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketType<P extends Packet<T>, T extends net.minecraft.network.PacketListener> {
    private ProtocolContainer<T> protocol;
    private Class<P> type;
    private List<PacketListener<P>> listeners;
    private int id = -1;

    public PacketType(ProtocolContainer<T> protocol, Class<P> type){
        this.protocol = protocol;
        this.type = type;
        if(protocol.contains(type))
            this.id = protocol.getId(type);
        listeners = new ArrayList<>();
    }

    public PacketType(ProtocolContainer<T> protocol, int id){
        this.protocol = protocol;
        this.type = (Class<P>) protocol.getPacket(id);
        this.id = id;
        listeners = new ArrayList<>();
    }

    public int register() {
        try {
            return this.register(ProtocolUtils.getConstructorFunction(type), true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int register(Function<PacketDataSerializer, P> func){
        return this.register(func, true);
    }

    public int register(Function<PacketDataSerializer, P> func, boolean override){
        try{
            protocol.register(this, func, override);
            id = protocol.getId(type);
            return protocol.getId(type);
        } catch (Exception e){
            Logger.getLogger("SuoLib").log(Level.SEVERE, "Failed to register invalid packet, " + type);
            return -1;
        }
    }

    public int getId() {
        return id;
    }

    public ProtocolContainer<T> getProtocol() {
        return protocol;
    }

    public Class<P> getType() {
        return type;
    }

    public boolean addListener(PacketListener<P> listener){
        return listeners.add(listener);
    }

    public boolean removeListener(PacketListener<P> listener){
        return listeners.remove(listener);
    }

    public PacketListener removeListener(int id){
        return listeners.remove(id);
    }

    public List<PacketListener<P>> getListeners() {
        return listeners;
    }
}
