package xyz.iamthedefender.cosmetics.category.woodskin.items.log;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.WoodSkin;

import java.util.List;

public class AcaciaLog extends WoodSkin {
    @Override
    public ItemStack getItem() {
        return XMaterial.ACACIA_LOG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "acacia-log";
    }

    @Override
    public String getDisplayName() {
        return "金合欢原木";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7选择金合欢原木皮肤", "&7放置木块时将使用", "&7对应的皮肤。");
    }

    @Override
    public int getPrice() {
        return 60000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public ItemStack woodSkin() {
        return XMaterial.ACACIA_LOG.parseItem();
    }
}
