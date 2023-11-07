package dev.projectdiana.dianacore.api.enums;

import cpw.mods.fml.common.Loader;

public enum Mods {

    AppliedEnergistics2("appliedenergistics2"),
    BuildCraftCore("BuildCraft|Core"),
    ExtraUtilities("ExtraUtilities"),
    IguanaTweaksTinkerConstruct("IguanaTweaksTConstruct"),
    ImmersiveEngineering("ImmersiveEngineering"),
    Mekanism("Mekanism"),
    Minecraft("minecraft"),
    MinefactoryReloaded("MineFactoryReloaded"),
    PamsHarvestCraft("harvestcraft"),
    TinkersConstruct("TConstruct");

    public final String ID;
    private Boolean modLoaded;

    Mods(String ID) {
        this.ID = ID;
    }

    public boolean isModLoaded() {
        if (this.modLoaded == null) {
            this.modLoaded = Loader.isModLoaded(ID);
        }
        return this.modLoaded;
    }

}
