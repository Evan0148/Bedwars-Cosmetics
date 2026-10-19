package xyz.iamthedefender.cosmetics.category.victorydance.items;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.VictoryDance;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.category.victorydance.util.UsefulUtilsVD;

import java.util.List;

public class HauntedDance extends VictoryDance {
    @Override
    public ItemStack getItem() {
        return XMaterial.GHAST_TEAR.parseItem();
    }

    @Override
    public String base64() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "haunted";
    }

    @Override
    public String getDisplayName() {
        return "亡魂之夜";
    }

    @Override
    public List<String> getLore() {
        return List.of("&7诡异时刻到！胜利舞", "&7会释放出那些你在这几局", "&7游戏里干掉的倒霉鬼的灵魂，", "&7来一起庆祝", "&7你的胜利。");
    }

    @Override
    public int getPrice() {
        return 30000;
    }

    @Override
    public RarityType getRarity() {
        return RarityType.LEGENDARY;
    }

    @Override
    public void execute(Player winner) {
        addTask(winner, Run.every(() -> {
            Location loc = UsefulUtilsVD.getRandomLocation(winner.getLocation(), 10);
            Bat bat = (Bat)winner.getWorld().spawnEntity(loc, EntityType.BAT);
            bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            addEntity(winner, bat);

            ArmorStand stand = (ArmorStand)winner.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            stand.setVisible(false);

            addEntity(winner, stand);

            bat.setPassenger(stand);
            bat.setNoDamageTicks(Integer.MAX_VALUE);

            ItemStack leather = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta lm = (LeatherArmorMeta)leather.getItemMeta();
            lm.setColor(Color.GRAY);
            leather.setItemMeta(lm);
            stand.setChestplate(leather);
            stand.setHelmet(UsefulUtilsVD.gethead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhkMjE4MzY0MDIxOGFiMzMwYWM1NmQyYWFiN2UyOWE5NzkwYTU0NWY2OTE2MTllMzg1NzhlYTRhNjlhZTBiNiJ9fX0="));

        }, 8L));
    }
}
