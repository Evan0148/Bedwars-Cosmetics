package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;

import java.util.List;

public class NightShiftDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.CLOCK.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "night-shift";
    }

    @Override
    public String getDisplayName() {
        return "昼夜交替";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7把时间拨到白天，再拨到黑夜，", "&7往复循环，如同神明。");
    }

    @Override
    public int getPrice() {
        return 10000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.RARE;
    }

    @Override
    public void execute(Player winner) {
        addTask(winner, Run.every((r) -> {
            if(CosmeticsPlugin.getInstance().getHandler().getArenaUtil().getArenaByPlayer(winner) == null) {
                r.cancel();
                return;
            }

            long time = winner.getWorld().getTime() + 1000;
            if (time > 24000) {
                time = 0;
            }
            winner.getWorld().setTime(time);
        }, 8L));
    }
}
