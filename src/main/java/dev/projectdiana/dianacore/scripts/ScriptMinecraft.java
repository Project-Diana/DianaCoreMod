package dev.projectdiana.dianacore.scripts;

import static dev.projectdiana.dianacore.api.ModHandler.getModItem;
import static dev.projectdiana.dianacore.api.enums.Mods.ExtraUtilities;
import static dev.projectdiana.dianacore.api.enums.Mods.ImmersiveEngineering;
import static dev.projectdiana.dianacore.api.enums.Mods.Minecraft;
import static dev.projectdiana.dianacore.api.enums.Mods.MinefactoryReloaded;
import static dev.projectdiana.dianacore.api.enums.Mods.PamsHarvestCraft;

import java.util.Arrays;
import java.util.List;

public class ScriptMinecraft implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Minecraft";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(ImmersiveEngineering.ID, ExtraUtilities.ID);
    }

    @Override
    public void loadRecipes() {
        craftingRecipes();
    }

    private void craftingRecipes() {
        addShapedRecipe(
            getModItem(Minecraft.ID, "chest", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing),
            getModItem(Minecraft.ID, "flint", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing),
            getModItem(ImmersiveEngineering.ID, "treatedWood", 1, 0, missing));

        addShapedRecipe(
            getModItem(Minecraft.ID, "furnace", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing),
            getModItem(Minecraft.ID, "flint", 1, 0, missing),
            getModItem(Minecraft.ID, "clay_ball", 1, 0, missing),
            getModItem(Minecraft.ID, "flint", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing));

        addShapedRecipe(
            getModItem(Minecraft.ID, "brick_block", 2, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            "bucketWater",
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing));

        addShapedRecipe(
            getModItem(Minecraft.ID, "paper", 1, 0, missing),
            "pulpWood",
            "pulpWood",
            "pulpWood",
            "pulpWood",
            "bucketWater",
            "pulpWood",
            "pulpWood",
            "pulpWood",
            "pulpWood");

        addShapedRecipe(getModItem(Minecraft.ID, "torch", 1, 0, missing), "coalAndCharcoal", null, "woodStick", null);

        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 0, missing),
            getModItem(Minecraft.ID, "log", 1, 0, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 1, missing),
            getModItem(Minecraft.ID, "log", 1, 1, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 1, missing),
            getModItem(PamsHarvestCraft.ID, "pamMaple", 1, 0, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 2, missing),
            getModItem(Minecraft.ID, "log", 1, 2, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 3, missing),
            getModItem(Minecraft.ID, "log", 1, 3, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 3, missing),
            getModItem(PamsHarvestCraft.ID, "pamPaperbark", 1, 0, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 3, missing),
            getModItem(PamsHarvestCraft.ID, "pamCinnamon", 1, 0, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 3, missing),
            getModItem(MinefactoryReloaded.ID, "rubberwood.log", 1, 0, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 4, missing),
            getModItem(Minecraft.ID, "log2", 1, 0, missing));
        addShapedRecipe(
            getModItem(Minecraft.ID, "planks", 1, 5, missing),
            getModItem(Minecraft.ID, "log2", 1, 1, missing));
    }
}
