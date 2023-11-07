package dev.projectdiana.dianacore.scripts;

import static dev.projectdiana.dianacore.api.ModHandler.getModItem;
import static dev.projectdiana.dianacore.api.enums.Mods.ExtraUtilities;
import static dev.projectdiana.dianacore.api.enums.Mods.ImmersiveEngineering;
import static dev.projectdiana.dianacore.api.enums.Mods.Minecraft;

import java.util.Arrays;
import java.util.List;

public class ScriptImmersiveEngineering implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Immersive Engineering";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(ImmersiveEngineering.ID);
    }

    @Override
    public void loadRecipes() {
        craftingRecipes();
    }

    private void craftingRecipes() {
        // Coke Oven Bricks require clay blocks, brick blocks, and compressed sand
        addShapedRecipe(
            getModItem(ImmersiveEngineering.ID, "stoneDecoration", 3, 1, missing),
            getModItem(Minecraft.ID, "clay", 1, 0, missing),
            getModItem(Minecraft.ID, "brick_block", 1, 0, missing),
            getModItem(Minecraft.ID, "clay", 1, 0, missing),
            getModItem(Minecraft.ID, "brick_block", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 14, missing),
            getModItem(Minecraft.ID, "brick_block", 1, 0, missing),
            getModItem(Minecraft.ID, "clay", 1, 0, missing),
            getModItem(Minecraft.ID, "brick_block", 1, 0, missing),
            getModItem(Minecraft.ID, "clay", 1, 0, missing));
    }
}
