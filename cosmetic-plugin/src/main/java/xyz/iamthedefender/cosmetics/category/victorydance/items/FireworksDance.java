package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.victorydance.util.UsefulUtilsVD;

import java.util.List;

public class FireworksDance extends VictoryDance {
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
        return "fireworks";
    }

    @Override
    public String getDisplayName() {
        return "烟花庆典";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7来一场绚烂的", "&7烟花庆典吧！");
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
    public void execute(Player winner) {
        addTask(winner, Run.every(() -> {
            Location loc = winner.getEyeLocation();
            UsefulUtilsVD.spawnFireWorks(winner, 1, Color.RED, Color.BLUE, loc, false);

            addTask(winner, Run.delayed(() -> {
                UsefulUtilsVD.spawnFireWorks(winner, 1, Color.ORANGE, Color.YELLOW, loc, false);
            }, 300L));
        }, 60L));
    }
}
