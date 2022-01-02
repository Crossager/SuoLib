package net.crossager.suolib;

import net.crossager.suolib.gui.SignGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuoLibPluginDataProvider {

    private Map<Player, List<SignGui>> openSignGuis;

    public SuoLibPluginDataProvider(SuoLib plugin) {
        this.openSignGuis = new HashMap<>();
    }

    public Map<Player, List<SignGui>> getOpenSignGuis() {
        return openSignGuis;
    }
}
