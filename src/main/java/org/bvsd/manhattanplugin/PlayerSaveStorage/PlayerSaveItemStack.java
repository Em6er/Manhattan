/*
 * This file is part of ManhattanPlugin.
 * 
 * ManhattanPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ManhattanPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ManhattanPlugin.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 */

package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 *
 * @author Donovan
 */
public class PlayerSaveItemStack {
    @Getter @Setter
    private String material;
    @Getter @Setter
    private int amount;
    @Getter @Setter
    private String displayName;
    @Getter @Setter
    private short durability;
    @Getter @Setter
    private List<String> lore;
    @Getter @Setter
    private PlayerSaveBookMeta bookMeta;
    @Getter @Setter
    private PlayerSaveLeatherArmorMeta armorMeta;
    @Getter @Setter
    private PlayerSaveEnchantmentMeta enchantmentMeta;

    public PlayerSaveItemStack(ItemStack i) {
        if (i == null) {
            this.material = Material.AIR.name();
            this.amount = 1;
            return;
        }
        this.material = i.getType().name();
        this.amount = i.getAmount();
        this.durability = i.getDurability();
        if (i.hasItemMeta()) {
            if (i.getItemMeta().hasDisplayName()) {
                this.displayName = i.getItemMeta().getDisplayName();
            }
            if (i.getItemMeta().hasLore()) {
                this.lore = i.getItemMeta().getLore();
            }
            if (i.getItemMeta() instanceof BookMeta) {
                this.bookMeta = new PlayerSaveBookMeta((BookMeta) i.getItemMeta());
            }
            if (i.getItemMeta() instanceof LeatherArmorMeta) {
                this.armorMeta = new PlayerSaveLeatherArmorMeta((LeatherArmorMeta) i.getItemMeta());
            }
            if (i.getItemMeta().hasEnchants()) {
                this.enchantmentMeta = new PlayerSaveEnchantmentMeta(i.getItemMeta());
            }
        }
    }
    public PlayerSaveItemStack(){
        
    }
    public ItemStack toItemStack() {
        ItemStack out = new ItemStack(Material.valueOf(material));
        out.setAmount(amount);
        out.setDurability(durability);
        if (displayName != null || lore != null) {
            ItemMeta meta = out.getItemMeta();
            if (lore != null) {
                meta.setLore(lore);
            }
            if (displayName != null) {
                meta.setDisplayName(displayName);
            }
            out.setItemMeta(meta);
        }
        if (bookMeta != null) {
            BookMeta meta = (BookMeta) out.getItemMeta();
            meta.setAuthor(bookMeta.getTitle());
            meta.setPages(bookMeta.getPages());
            meta.setTitle(bookMeta.getTitle());
            if (bookMeta.getLore() != null) {
                meta.setLore(bookMeta.getLore());
            }
            out.setItemMeta(meta);
        }
        if (armorMeta != null) {
            LeatherArmorMeta meta = (LeatherArmorMeta) out.getItemMeta();
            meta.setColor(Color.fromRGB(armorMeta.getRgb()));
            if (armorMeta.getDisplayName() != null) {
                meta.setDisplayName(armorMeta.getDisplayName());
            }
            if (armorMeta.getLore() != null) {
                meta.setLore(armorMeta.getLore());
            }
            out.setItemMeta(meta);
        }
        if (enchantmentMeta != null) {
            for (Map.Entry<String, Integer> entry : enchantmentMeta.getEnchants().entrySet()) {
                out.addUnsafeEnchantment(Enchantment.getByName(entry.getKey()), entry.getValue());
            }
        }
        return out;
    }
}