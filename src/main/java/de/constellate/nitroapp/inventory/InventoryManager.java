package de.constellate.nitroapp.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryManager {

    private ItemMeta itemMeta;
    private ItemStack itemStack;
    public void ItemBuilder(Material mat){
        itemStack = new ItemStack(mat);
        itemMeta = itemStack.getItemMeta();
    }
    public InventoryManager setDisplayname(String s){
        itemMeta.setDisplayName(s);
        return this;
    }
    public InventoryManager setLocalizedName(String s){
        itemMeta.setLocalizedName(s);
        return this;
    }
    public InventoryManager setLore(String... s){
        itemMeta.setLore(Arrays.asList(s));
        return this;
    }
    public InventoryManager setUnbreakable(boolean s){
        itemMeta.setUnbreakable(s);
        return this;
    }
    public InventoryManager addItemFlags(ItemFlag... s){
        itemMeta.addItemFlags(s);
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }
    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
