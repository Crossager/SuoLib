package net.crossager.suolib;

import net.crossager.suolib.protocol.PacketManager;
import net.crossager.suolib.protocol.PacketReader;
import net.crossager.suolib.protocol.ProtocolManager;

import java.util.Map;

public class SuoLibServiceProvider {
    private SuoLib plugin;
    private PacketManager packetManager;
    private ProtocolManager protocolManager;
    private PacketReader packetReader;

    private Map<String, Object> dataMap;

    public SuoLibServiceProvider(SuoLib plugin) {
        this.plugin = plugin;
        this.protocolManager = new ProtocolManager();
        this.packetManager = new PacketManager(protocolManager);
        this.packetReader = new PacketReader(plugin, packetManager);
    }

    public SuoLib getPlugin() {
        return plugin;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public PacketReader getPacketReader() {
        return packetReader;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
