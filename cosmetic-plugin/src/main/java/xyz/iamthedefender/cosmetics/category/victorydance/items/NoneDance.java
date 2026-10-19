package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;

import java.util.List;

public class NoneDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.BARRIER.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "none";
    }

    @Override
    public String getDisplayName() {
        return "无";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7选择此选项将停用", "&7你的胜利舞。");
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.NONE;
    }

    @Override
    public void execute(Player winner) {}
}
