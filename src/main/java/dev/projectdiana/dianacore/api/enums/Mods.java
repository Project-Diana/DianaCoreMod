package dev.projectdiana.dianacore.api.enums;

import cpw.mods.fml.common.Loader;

public enum Mods {

    ExtraUtilities("ExtraUtilities"),
    IguanaTweaksTinkerConstruct("IguanaTweaksTConstruct"),
    ImmersiveEngineering("ImmersiveEngineering"),
    Minecraft("minecraft");

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
