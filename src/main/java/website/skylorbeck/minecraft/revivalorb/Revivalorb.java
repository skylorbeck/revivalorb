package website.skylorbeck.minecraft.revivalorb;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Revivalorb implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("revivalorb", "revivalorb"),REVIVALORB);
    }

    public static Item REVIVALORB = new RevivalOrbItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.EPIC));
}
