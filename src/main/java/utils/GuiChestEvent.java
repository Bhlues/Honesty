package utils;

import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GuiChestEvent extends Event {
    
    private List<Slot> slots;
    private String name;
    private int size;

    public GuiChestEvent(List<Slot> slots, String name) {
        this.slots = slots;
        this.name = name.trim();
        this.size = slots.size();
    }
    
    public List<Slot> getSlots() {
        return this.slots;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getSize() {
        return this.size;
    }
}
