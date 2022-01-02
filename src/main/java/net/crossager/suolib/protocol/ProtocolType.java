package net.crossager.suolib.protocol;

import net.crossager.suolib.SuoLib;
import net.crossager.suolib.SuoLibServiceProvider;
import net.minecraft.network.EnumProtocol;

public enum ProtocolType {
    HANDSHAKE(-1),
    PLAY(0),
    STATUS(1),
    LOGIN(2);
    private int id;
    ProtocolType(int id){
        this.id = id;
        provider = SuoLib.getProvider();
    }
    private SuoLibServiceProvider provider;
    public ProtocolContainer<?> getProtocol(Sender sender){
        switch (id){
            case -1: return sender == Sender.CLIENT ? provider.getProtocolManager().HANDSHAKING.IN : null;
            case 0: return sender == Sender.CLIENT ? provider.getProtocolManager().PLAY.IN : provider.getProtocolManager().PLAY.OUT;
            case 1: return sender == Sender.CLIENT ? provider.getProtocolManager().STATUS.IN : provider.getProtocolManager().STATUS.OUT;
            case 2: return sender == Sender.CLIENT ? provider.getProtocolManager().LOGIN.IN : provider.getProtocolManager().LOGIN.OUT;
        }
        return null;
    }
    public EnumProtocol asNMS(){
        switch (id){
            case -1: return EnumProtocol.a;
            case 0: return EnumProtocol.b;
            case 1: return EnumProtocol.c;
            case 2: return EnumProtocol.d;
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
