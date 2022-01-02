package net.crossager.suolib;

import net.crossager.common.reflection.FieldUtils;
import net.crossager.common.reflection.MethodUtils;
import net.crossager.suolib.events.PlayerAttackInvalidEntityEvent;
import net.crossager.suolib.events.PlayerUseInvalidEntityEvent;
import net.crossager.suolib.events.ServerTickEvent;
import net.crossager.suolib.events.listeners.PlayerInventoryChangeEventListener;
import net.crossager.suolib.player.ServerUtility;
import net.crossager.suolib.protocol.PacketListener;
import net.crossager.suolib.protocol.PacketManager;
import net.crossager.suolib.util.NMSUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.spigotmc.AsyncCatcher;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;


public final class SuoLib extends JavaPlugin implements Listener {

    private static SuoLibServiceProvider provider;
    private static SuoLibPluginDataProvider data;

    private PlayerInventoryChangeEventListener playerInventoryChangeEventListener;

    public static SuoLibServiceProvider getProvider() {
        if(provider == null) provider = Bukkit.getServicesManager().getRegistration(SuoLibServiceProvider.class).getProvider();
        return provider;
    }

    public static SuoLibPluginDataProvider getData() {
        if(data == null) data = Bukkit.getServicesManager().getRegistration(SuoLibPluginDataProvider.class).getProvider();
        return data;
    }

    @Override
    public void onEnable() {
        provider = new SuoLibServiceProvider(this);
        data = new SuoLibPluginDataProvider(this);
        Bukkit.getServicesManager().register(SuoLibServiceProvider.class, provider, this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(SuoLibPluginDataProvider.class, data, this, ServicePriority.Normal);
        if(!(Bukkit.getPluginManager().getPlugin("SuoLib") instanceof SuoLib)){
            getLogger().log(Level.SEVERE, "SuoLib wont work after a server reload, please consider using server restart instead.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            getServer().getPluginManager().registerEvents(this, this);
            ServerUtility.init();
        }
        playerInventoryChangeEventListener = new PlayerInventoryChangeEventListener(this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getPluginManager().callEvent(new ServerTickEvent()),  0, 1);
        addPacketListeners();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(p -> provider.getPacketReader().uninject(p));
    }

    private void addPacketListeners(){
        provider.getPacketManager().PLAY.IN.UPDATE_SIGN.addListener(new PacketListener<>(PacketPlayInUpdateSign.class) {
            @Override
            public void listen(PacketPlayInUpdateSign packet, Player player) {
                if(data.getOpenSignGuis().get(player) != null){
                    if(!data.getOpenSignGuis().get(player).isEmpty()){
                        data.getOpenSignGuis().get(player).get(0).getOnGuiClose().close(player, packet.c());
                        data.getOpenSignGuis().get(player).remove(0);
                        setCancelled(true);
                    }
                } else
                    data.getOpenSignGuis().put(player, new LinkedList<>());
            }
        });
        provider.getPacketManager().PLAY.IN.USE_ENTITY.addListener(new PacketListener<>(PacketPlayInUseEntity.class) {
            @Override
            public void listen(PacketPlayInUseEntity packet, Player player) {
                try {
                    PlayerConnection con = NMSUtils.getHandle(player).b;
                    WorldServer worldServer = con.b.x();
                    int id = (int) FieldUtils.readField(packet, "a", true);
                    boolean enabled = AsyncCatcher.enabled;
                    AsyncCatcher.enabled = false;
                    boolean unknown = worldServer.I().a(id) == null;
                    AsyncCatcher.enabled = enabled;
                    Object b = FieldUtils.readField(packet, "b", true);
                    if(unknown)
                        MethodUtils.getMethod(b.getClass(), "a", true, PacketPlayInUseEntity.c.class).invoke(b, new PacketPlayInUseEntity.c() {
                            @Override
                            public void a(EnumHand enumHand) {

                            }

                            @Override
                            public void a(EnumHand enumHand, Vec3D vec3D) {
                                Bukkit.getScheduler().runTask(provider.getPlugin(), () -> {
                                    try {
                                        Bukkit.getPluginManager().callEvent(new PlayerUseInvalidEntityEvent(player, (Integer) FieldUtils.readField(packet, "a", true), new Vector(vec3D.b, vec3D.c, vec3D.d), enumHand == EnumHand.b ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND));
                                        setCancelled(true);
                                    } catch (NoSuchFieldException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }

                            @Override
                            public void a() {
                                Bukkit.getScheduler().runTask(provider.getPlugin(), () -> {
                                    try {
                                        Bukkit.getPluginManager().callEvent(new PlayerAttackInvalidEntityEvent(player, (Integer) FieldUtils.readField(packet, "a", true)));
                                        setCancelled(true);
                                    } catch (NoSuchFieldException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
