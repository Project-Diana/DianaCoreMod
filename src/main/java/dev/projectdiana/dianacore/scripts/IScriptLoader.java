package dev.projectdiana.dianacore.scripts;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import dev.projectdiana.dianacore.recipes.ShapedUniversalRecipe;
import dev.projectdiana.dianacore.recipes.ShapelessUniversalRecipe;

public interface IScriptLoader {

    int wildcard = 32767;

    String getScriptName();

    ItemStack missing = new ItemStack(Blocks.fire);

    List<String> getDependencies();

    void loadRecipes();

    default boolean addShapedRecipe(ItemStack aOutput, Object... inputs) {
        try {
            GameRegistry.addRecipe(new ShapedUniversalRecipe(aOutput, inputs));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default boolean addShapelessRecipe(ItemStack aOutput, Object... inputs) {
        try {
            GameRegistry.addRecipe(new ShapelessUniversalRecipe(aOutput, inputs));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default boolean isScriptLoadable() {
        for (String dep : getDependencies()) {
            if (!Loader.isModLoaded(dep)) {
                return false;
            }
        }
        return true;
    }
}
