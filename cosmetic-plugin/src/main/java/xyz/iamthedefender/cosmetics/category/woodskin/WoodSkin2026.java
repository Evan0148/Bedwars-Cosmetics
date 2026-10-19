package xyz.iamthedefender.cosmetics.category.woodskin;

import com.cryptomorin.xseries.XMaterial;
import me.horiciastko.bedwars.api.events.BedWarsShopPurchaseEvent;
import me.horiciastko.bedwars.gui.ShopGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.WoodSkin;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class WoodSkin2026 implements Listener {

    @EventHandler
    public void onShopPurchase(BedWarsShopPurchaseEvent e) {
        boolean isWoodSkinsEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("wood-skins.enabled");
        if (!isWoodSkinsEnabled) return;

        Player p = e.getBuyer();
        ItemStack stack = e.getPurchasedStack();

        if (stack == null || !Utility.isWoodOrLogBlock(stack.getType())) return;

        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.WoodSkins);
        WoodSkin selectedWoodSkin = find(selected);

        if (selectedWoodSkin == null) return;

        ItemStack configItem = (ItemStack) selectedWoodSkin.getField(FieldsType.ITEM_STACK, p);

        if (configItem == null) return;

        DebugUtil.addMessage("Applying Wood Skin " + selected + " for purchased item of " + p.getName());
        stack.setType(configItem.getType());
    }

    @EventHandler
    public void onShopOpen(InventoryOpenEvent e) {
        boolean isWoodSkinsEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("wood-skins.enabled");
        if (!isWoodSkinsEnabled) return;

        if (!(e.getInventory().getHolder() instanceof ShopGUI)) return;

        Player p = (Player) e.getPlayer();

        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.WoodSkins);
        WoodSkin selectedWoodSkin = find(selected);

        if (selectedWoodSkin == null) return;

        ItemStack item = (ItemStack) selectedWoodSkin.getField(FieldsType.ITEM_STACK, p);

        if (item == null) return;

        for (ItemStack itemStack : e.getInventory().getContents()) {
            if (itemStack == null) continue;
            if (itemStack.getType() == XMaterial.AIR.parseMaterial()) continue;
            if (!Utility.isWoodOrLogBlock(itemStack.getType())) continue;

            itemStack.setType(item.getType());
        }
    }

    private WoodSkin find(String selected) {
        if (selected == null) return null;

        for (WoodSkin woodSkin : StartupUtils.woodSkinsList) {
            if (woodSkin.getIdentifier().equals(selected)) {
                return woodSkin;
            }
        }
        return null;
    }
}
