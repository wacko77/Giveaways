package me.wacko.giveaways.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractGUI {

    public static Map<UUID, AbstractGUI> inventoriesByUUID = new HashMap<>();
    public static Map<UUID, UUID> openInventories = new HashMap<>();

    private static UUID uuid;
    private Inventory inventory;
    private Map<Integer, GUIAction> actions;

    public AbstractGUI(int invSize, String invName){
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, invSize, invName);
        actions = new HashMap<>();
        inventoriesByUUID.put(getUuid(), this);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public interface GUIAction {
        void click(Player player);
    }

    public void setItem(int slot, ItemStack stack, GUIAction action){
        inventory.setItem(slot, stack);
        if (action != null){
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack stack){
        setItem(slot, stack, null);
    }

    public void open(Player p){
        p.openInventory(inventory);
        openInventories.put(p.getUniqueId(), getUuid());
    }

    public static UUID getUuid(){
        return uuid;
    }

    public static Map<UUID, AbstractGUI> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public Map<Integer, GUIAction> getActions() {
        return actions;
    }


}
