package xyz.iamthedefender.cosmetics.category.woodskin.items.log;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.WoodSkin;

import java.util.List;

public class SpruceLog extends WoodSkin {
    @Override
    public ItemStack getItem() {
        return XMaterial.SPRUCE_LOG.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "spruce-log";
    }

    @Override
    public String getDisplayName() {
        return "云杉原木";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7选择云杉原木皮肤", "&7放置木块时将使用", "&7对应的皮肤。");
    }

    @Override
    public int getPrice() {
        return 45000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public ItemStack woodSkin() {
        return XMaterial.SPRUCE_LOG.parseItem();
    }
}
