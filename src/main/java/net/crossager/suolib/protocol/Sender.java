package net.crossager.suolib.protocol;

import net.minecraft.network.protocol.EnumProtocolDirection;

public enum Sender {
    SERVER,
    CLIENT;
    public Sender opposite(){
        return this == SERVER ? CLIENT : SERVER;
    }
    public EnumProtocolDirection asNMS(){
        return this == SERVER ? EnumProtocolDirection.a : EnumProtocolDirection.b;
    }
}
