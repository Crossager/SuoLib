package net.crossager.suolib.protocol;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.crossager.common.reflection.FieldUtils;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ProtocolContainer<T extends PacketListener> {
    private Object handle;

    private final Object2IntMap<Class<? extends Packet<T>>> packetIds;
    private final List<Function<PacketDataSerializer, ? extends Packet<T>>> packetInits;
    private EnumProtocol protocol;
    private EnumProtocolDirection direction;

    protected List<PacketType<? extends Packet<T>, T>> customPackets = new ArrayList<>();

    public ProtocolContainer(EnumProtocol protocol, EnumProtocolDirection direction) throws NoSuchFieldException, IllegalAccessException {
        this.protocol = protocol;
        this.direction = direction;
        handle = ((Map<EnumProtocolDirection, ?>) FieldUtils.readField(protocol, "j", true)).get(direction);
        packetIds = (Object2IntMap<Class<? extends Packet<T>>>) FieldUtils.readField(handle, "a", true);
        packetInits = (List<Function<PacketDataSerializer, ? extends Packet<T>>>) FieldUtils.readField(handle, "b", true);
    }

    public <P extends Packet<T>> void register(PacketType<P, T> type, Function<PacketDataSerializer, P> var1, boolean replaceIfExist){
        customPackets.add(type);
        add(type.getType(), var1, replaceIfExist);
    }

    public <P extends Packet<T>> ProtocolContainer<T> add(Class<P> var0, Function<PacketDataSerializer, P> var1, boolean replaceIfExist) {
        if(replaceIfExist)
        for(Class<? extends Packet<T>> pa : packetIds.keySet()){
            if(pa.getName().equals(var0.getName())) remove(pa);
        }

        int var2 = this.packetInits.size();
        int var3 = this.packetIds.put(var0, var2);

        if (var3 != -1) {
            String var4 = "Packet " + var0 + " is already registered to ID " + var3;
            LogManager.getLogger().fatal(var4);
            throw new IllegalArgumentException(var4);
        }

        this.packetInits.add(var1);
        return this;
    }

    public <P extends Packet<T>> ProtocolContainer<T> add(Class<P> var0) throws NoSuchMethodException {
        add(var0, ProtocolUtils.getConstructorFunction(var0), true);
        return this;
    }

    public <P extends Packet<T>> ProtocolContainer<T> add(Class<P> var0, Function<PacketDataSerializer, P> var1) {
        return add(var0, var1, true);
    }

    public Class<? extends Packet<T>> remove(int id){
        int p = 0;
        Class<? extends Packet<T>> packet = null;
        for(Class<? extends Packet<T>> pa : packetIds.keySet()){
            if(p == id) {
                packet = pa;
                packetIds.removeInt(pa);
            }
            p++;
        }
        return packet;
    }

    public <P extends Packet<T>> int remove(Class<P> clazz){
        int id = packetIds.getInt(clazz);
        packetIds.removeInt(clazz);
        packetInits.remove(id);
        return id;
    }

    public <P extends Packet<T>> int remove(Class<P> clazz, Function<PacketDataSerializer, P> func){
        int id = packetIds.getInt(clazz);
        packetIds.removeInt(clazz);
        packetInits.remove(func);
        return id;
    }

    @Nullable
    public Integer getId(Class<?> var0) {
        int var1 = this.packetIds.getInt(var0);
        return (var1 == -1) ? null : Integer.valueOf(var1);
    }

    public Class<? extends Packet<T>> getPacket(int id){
        if(!packetIds.containsValue(id)) return null;
        for(Class<? extends Packet<T>> cl : packetIds.keySet()){
            if(packetIds.getInt(cl) == id) return cl;
        }
        return null;
    }

    public boolean contains(Class<? extends Packet<T>> clazz){
        return packetIds.containsKey(clazz);
    }

    public boolean contains(int id){
        return packetIds.containsValue(id);
    }

    @Nullable
    public Packet<?> getInstance(int var0, PacketDataSerializer var1) {
        Function<PacketDataSerializer, ? extends Packet<T>> var2 = this.packetInits.get(var0);
        return (var2 != null) ? var2.apply(var1) : null;
    }

    public Set<Class<? extends Packet<T>>> getPackets(){
        return packetIds.keySet();
    }

    public Iterable<Class<? extends Packet<?>>> iteratePackets() {
        return Iterables.unmodifiableIterable(this.packetIds.keySet());
    }

    public ProtocolType getType(){
        switch (protocol){
            case a: return ProtocolType.HANDSHAKE;
            case b: return ProtocolType.PLAY;
            case c: return ProtocolType.STATUS;
            case d: return ProtocolType.LOGIN;
        }
        return null;
    }
    public Sender getSender(){
        return direction == EnumProtocolDirection.a ? Sender.SERVER : Sender.CLIENT;
    }

    public Object getHandle() {
        return handle;
    }
}

