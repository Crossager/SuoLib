package net.crossager.suolib.permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermissionManager {
    private JavaPlugin plugin;
    public PermissionManager(JavaPlugin plugin){
        this.plugin = plugin;
    }
    private Map<UUID, PermissionAttachment> permissionAttachments = new HashMap<>();

    public void setPermission(Player p, String s, boolean value) {
        if (!permissionAttachments.containsKey(p.getUniqueId()))
            permissionAttachments.put(p.getUniqueId(), p.addAttachment(plugin));
        ((PermissionAttachment)permissionAttachments.get(p.getUniqueId())).setPermission(s, value);
    }
    public void resetPermission(Player p, String s){
        permissionAttachments.get(p.getUniqueId()).unsetPermission(s);
    }
}
