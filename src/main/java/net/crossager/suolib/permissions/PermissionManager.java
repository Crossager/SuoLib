package net.crossager.suolib.permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermissionManager {
    private Plugin plugin;
    private Map<UUID, PermissionAttachment> permissionAttachments = new HashMap<>();
    public PermissionManager(Plugin plugin){
        this.plugin = plugin;
    }

    public void setPermission(Player p, String s, boolean value) {
        if (!permissionAttachments.containsKey(p.getUniqueId()))
            permissionAttachments.put(p.getUniqueId(), p.addAttachment(plugin));
        permissionAttachments.get(p.getUniqueId()).setPermission(s, value);
    }
    public void resetPermission(Player p, String s){
        permissionAttachments.get(p.getUniqueId()).unsetPermission(s);
    }
}
