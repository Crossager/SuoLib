package net.crossager.suolib.protocol;

import net.minecraft.network.EnumProtocol;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.game.PacketListenerPlayIn;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.handshake.PacketHandshakingInListener;
import net.minecraft.network.protocol.login.PacketLoginInListener;
import net.minecraft.network.protocol.login.PacketLoginOutListener;
import net.minecraft.network.protocol.status.PacketStatusInListener;
import net.minecraft.network.protocol.status.PacketStatusOutListener;

public final class ProtocolManager {
    public final Play PLAY = new Play();
    public final Status STATUS = new Status();
    public final Login LOGIN = new Login();
    public final Handshaking HANDSHAKING = new Handshaking();
    public final class Play {
        public final ProtocolContainer<PacketListenerPlayOut> OUT;
        public final ProtocolContainer<PacketListenerPlayIn> IN;

        private Play(){
            ProtocolContainer IN1;
            try {
                IN1 = new ProtocolContainer<PacketListenerPlayIn>(EnumProtocol.b, EnumProtocolDirection.a);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                IN1 = null;
                e.printStackTrace();
            }
            IN = IN1;
            ProtocolContainer OUT1;
            try {
                OUT1 = new ProtocolContainer<PacketListenerPlayOut>(EnumProtocol.b, EnumProtocolDirection.b);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                OUT1 = null;
                e.printStackTrace();
            }
            OUT = OUT1;
        }
    }
    public final class Status {
        public final ProtocolContainer<PacketStatusOutListener> OUT;
        public final ProtocolContainer<PacketStatusInListener> IN;
        private Status(){
            ProtocolContainer IN1;
            try {
                IN1 = new ProtocolContainer<PacketStatusInListener>(EnumProtocol.c, EnumProtocolDirection.a);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                IN1 = null;
                e.printStackTrace();
            }
            IN = IN1;
            ProtocolContainer OUT1;
            try {
                OUT1 = new ProtocolContainer<PacketStatusOutListener>(EnumProtocol.c, EnumProtocolDirection.b);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                OUT1 = null;
                e.printStackTrace();
            }
            OUT = OUT1;
        }
    }
    public final class Login {
        public final ProtocolContainer<PacketLoginOutListener> OUT;
        public final ProtocolContainer<PacketLoginInListener> IN;
        private Login(){
            ProtocolContainer IN1;
            try {
                IN1 = new ProtocolContainer<PacketLoginInListener>(EnumProtocol.d, EnumProtocolDirection.a);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                IN1 = null;
                e.printStackTrace();
            }
            IN = IN1;
            ProtocolContainer OUT1;
            try {
                OUT1 = new ProtocolContainer<PacketLoginOutListener>(EnumProtocol.d, EnumProtocolDirection.b);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                OUT1 = null;
                e.printStackTrace();
            }
            OUT = OUT1;
        }
    }
    public final class Handshaking {
        public final ProtocolContainer<PacketHandshakingInListener> IN;
        private Handshaking(){
            ProtocolContainer IN1;
            try {
                IN1 = new ProtocolContainer<PacketHandshakingInListener>(EnumProtocol.a, EnumProtocolDirection.a);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                IN1 = null;
                e.printStackTrace();
            }
            IN = IN1;
        }
    }
    public static ProtocolContainer<?> getProtocol(ProtocolType type, Sender sender){
        return type.getProtocol(sender);
    }
}
