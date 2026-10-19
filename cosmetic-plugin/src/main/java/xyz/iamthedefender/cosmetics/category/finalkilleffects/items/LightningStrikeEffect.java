package xyz.iamthedefender.cosmetics.category.finalkilleffects.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.FinalKillEffect;
import xyz.iamthedefender.cosmetics.util.EntityUtil;

import java.util.Arrays;
import java.util.List;

public class LightningStrikeEffect extends FinalKillEffect {
    @Override
    public ItemStack getItem() {
        return XMaterial.STRING.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "lightning-strike";
    }

    @Override
    public String getDisplayName() {
        return "雷霆一击";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7在受害者所在位置", "&7召来一道天雷！");
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
        if (!onlyVictim) {
            location.getWorld().strikeLightningEffect(location);
        } else {
            // Create a non-NMS lightning entity
            LightningStrike lightning = location.getWorld().strikeLightningEffect(location);
            EntityUtil.entityForPlayerOnly(lightning, victim);
        }
    }
}