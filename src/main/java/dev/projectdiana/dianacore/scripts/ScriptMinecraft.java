package dev.projectdiana.dianacore.scripts;

import static dev.projectdiana.dianacore.api.ModHandler.getModItem;
import static dev.projectdiana.dianacore.api.enums.Mods.*;

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
            getModItem(Minecraft.ID, "water_bucket", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing));

        addShapedRecipe(
            getModItem(Minecraft.ID, "brick_block", 2, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(IguanaTweaksTinkerConstruct.ID, "clayBucketWater", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing),
            getModItem(Minecraft.ID, "brick", 1, 0, missing));

    }

}
