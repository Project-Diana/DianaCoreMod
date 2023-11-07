package dev.projectdiana.dianacore.scripts;

import static dev.projectdiana.dianacore.api.ModHandler.getModItem;
import static dev.projectdiana.dianacore.api.enums.Mods.Minecraft;
import static dev.projectdiana.dianacore.api.enums.Mods.TinkersConstruct;

import java.util.Arrays;
import java.util.List;

public class ScriptTinkersConstruct implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Tinkers Construct";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(TinkersConstruct.ID);
    }

    @Override
    public void loadRecipes() {
        craftingRecipes();
    }

    private void craftingRecipes() {
        addShapedRecipe(
            getModItem(TinkersConstruct.ID, "blankPattern", 2, 0, missing),
            "plankWood",
            getModItem(Minecraft.ID, "paper", 1, 0, missing),
            getModItem(Minecraft.ID, "paper", 1, 0, missing),
            "plankWood");
    }
}
