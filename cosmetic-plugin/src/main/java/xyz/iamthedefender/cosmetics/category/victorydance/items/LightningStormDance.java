package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.ShopKeeperHandler1058;
import xyz.iamthedefender.cosmetics.category.victorydance.util.UsefulUtilsVD;

import java.util.List;

public class LightningStormDance extends VictoryDance {

    @Override
    public ItemStack getItem() {
        return XMaterial.LIGHTNING_ROD.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "lightning-storm";
    }

    @Override
    public String getDisplayName() {
        return "雷霆风暴";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7在你的胜利现场", "&7召来一场雷暴！");
    }

    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.EPIC;
    }

    @Override
    public void execute(Player winner) {
        addTask(winner, Run.every((r) -> {
            if(!ShopKeeperHandler1058.arenas.containsKey(winner.getWorld().getName())) {
                r.cancel();
                return;
            }

            Location loc = UsefulUtilsVD.getRandomLocation(winner.getLocation(), 12);
            // Visual lightning only - no damage
            winner.getWorld().strikeLightningEffect(loc);
        }, 3L));
    }

}
