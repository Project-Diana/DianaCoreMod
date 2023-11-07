package dev.projectdiana.dianacore.scripts;

import static dev.projectdiana.dianacore.api.ModHandler.getModItem;
import static dev.projectdiana.dianacore.api.enums.Mods.AppliedEnergistics2;
import static dev.projectdiana.dianacore.api.enums.Mods.BuildCraftCore;
import static dev.projectdiana.dianacore.api.enums.Mods.ExtraUtilities;
import static dev.projectdiana.dianacore.api.enums.Mods.Mekanism;
import static dev.projectdiana.dianacore.api.enums.Mods.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;
import appeng.core.features.registries.entries.AppEngGrinderRecipe;

public class ScriptAE2 implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "AE2";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(AppliedEnergistics2.ID, BuildCraftCore.ID, Mekanism.ID);
    }

    @Override
    public void loadRecipes() {
        craftingRecipes();
        grindstoneRecipes();
    }

    private void grindstoneRecipes() {
        List<IGrinderEntry> recipes = new ArrayList<IGrinderEntry>();
        for (int i = 0; i < 4; i++) {
            IGrinderEntry recipe = new AppEngGrinderRecipe(
                getModItem(Minecraft.ID, "log", 1, i),
                getModItem(Mekanism.ID, "Sawdust", 1, 0),
                getModItem(Mekanism.ID, "Sawdust", 1, 0),
                getModItem(Mekanism.ID, "Sawdust", 1, 0),
                0.4F,
                0.1F,
                4);
            AEApi.instance()
                .registries()
                .grinder()
                .getRecipes()
                .add(recipe);
        }
        IGrinderEntry recipe = new AppEngGrinderRecipe(
            getModItem(Minecraft.ID, "log2", 1, 0),
            getModItem(Mekanism.ID, "Sawdust", 1, 0),
            getModItem(Mekanism.ID, "Sawdust", 1, 0),
            getModItem(Mekanism.ID, "Sawdust", 1, 0),
            0.4F,
            0.1F,
            4);
        AEApi.instance()
            .registries()
            .grinder()
            .getRecipes()
            .add(recipe);
        recipe = new AppEngGrinderRecipe(
            getModItem(Minecraft.ID, "log2", 1, 1),
            getModItem(Mekanism.ID, "Sawdust", 1, 0),
            getModItem(Mekanism.ID, "Sawdust", 1, 0),
            getModItem(Mekanism.ID, "Sawdust", 1, 0),
            0.4F,
            0.1F,
            4);
        AEApi.instance()
            .registries()
            .grinder()
            .getRecipes()
            .add(recipe);
    }

    private void craftingRecipes() {
        addShapedRecipe(
            getModItem(AppliedEnergistics2.ID, "tile.BlockGrinder", 1, 0, missing),
            "stone",
            getModItem(BuildCraftCore.ID, "woodenGearItem", 1, 0, missing),
            "stone",
            getModItem(Minecraft.ID, "quartz", 1, 0, missing),
            "stone",
            getModItem(Minecraft.ID, "quartz", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing),
            getModItem(Minecraft.ID, "quartz", 1, 0, missing),
            getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1, 0, missing));
    }
}
