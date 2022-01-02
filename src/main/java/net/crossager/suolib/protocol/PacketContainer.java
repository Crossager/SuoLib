package net.crossager.suolib.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import net.crossager.common.reflection.MethodUtils;
import net.crossager.suolib.util.Utils;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class PacketContainer<P extends Packet<PacketListenerPlayOut>> {
    private PacketType<P, PacketListenerPlayOut> type;
    private ByteBuf buf;
    private Map<Integer, PacketDataElement<?>> data = new HashMap<>();
    public PacketContainer(PacketType<P, PacketListenerPlayOut> type){
        this.type = type;
        buf = UnpooledByteBufAllocator.DEFAULT.buffer();
    }

    public PacketType<P, PacketListenerPlayOut> getType() {
        return type;
    }

    public Map<Integer, PacketDataElement<?>> getData() {
        return data;
    }

    private P createPacket(){
        try {
            PacketDataSerializer serializer = new PacketDataSerializer(buf);
            for(int i = 0; i < data.size(); i++){
                PacketDataElement<?> element = data.get(i);
                try {
                    if(element == null) continue;
                    element.write(serializer);
                } catch (ReflectiveOperationException e){
                    Utils.log("Failed to write data: " + element + ", to " + serializer + e);
                }
            }

            if (type.getType().getConstructor(PacketDataSerializer.class) == null) throw new NoSuchMethodException();
            type.getType().getConstructor(PacketDataSerializer.class).setAccessible(true);
            return type.getType().getConstructor(PacketDataSerializer.class).newInstance(data);
        } catch (NoSuchMethodException e) {
            Utils.log(Level.SEVERE, "Failed to create packet, " + type.getType() + ". No PacketDataSerializer constructor found.");
        } catch (InvocationTargetException | InstantiationException  | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean send(Player p){
        try {
            P packet = createPacket();
            if(packet == null) return false;
            ((EntityPlayer)MethodUtils.getMethod(p.getClass(), "getHandle", true).invoke(p)).b.a(packet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Writing data to packet
     */

    public <T> void write(T t){
        write(data.size(), t);
    }

    public <T> void write(int i, T t){
        data.put(i, new PacketDataElement<T>(t));
    }
}
