package net.crossager.suolib.gui;

import net.crossager.suolib.gui.events.ButtonClick;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Button extends ItemStack {

    private ButtonClick click;
    private Button(){}
    public Button(Material item, ButtonClick c){
        super(item);
        click = c;
    }
    public Button(Material item){
        super(item);
    }

    public Button(ItemStack i) {
        super(i);
    }

    public void setButtonClick(ButtonClick c){
        click = c;
    }

    public ButtonClick getClick() {
        return click;
    }
}
