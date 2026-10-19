package xyz.iamthedefender.cosmetics.category.woodskin.items.log;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.WoodSkin;

import java.util.List;

public class BirchLog extends WoodSkin {
    @Override
    public ItemStack getItem() {
        return XMaterial.BIRCH_LOG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "birch-log";
    }

    @Override
    public String getDisplayName() {
        return "白桦原木";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7选择白桦原木皮肤", "&7放置木块时将使用", "&7对应的皮肤。");
    }

    @Override
    public int getPrice() {
        return 50000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public ItemStack woodSkin() {
        return XMaterial.BIRCH_LOG.parseItem();
    }
}
