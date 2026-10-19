package xyz.iamthedefender.cosmetics.category.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.FinalKillEffect;
import xyz.iamthedefender.cosmetics.category.victorydance.util.UsefulUtilsVD;

import java.util.Arrays;
import java.util.List;

public class FireworkEffect extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.FIREWORK_ROCKET.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "firework";
    }

    @Override
    public String getDisplayName() {
        return "烟花绽放";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7在受害者所在位置", "&7燃放烟花！");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.COMMON;
    }

    @Override
    public void execute(Player killer, Player victim, Location location, boolean onlyVictim) {
        UsefulUtilsVD.spawnFireWorks(victim, 1, Color.RED, Color.GREEN, location, onlyVictim);
    }
}
