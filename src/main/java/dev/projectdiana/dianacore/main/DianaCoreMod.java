package dev.projectdiana.dianacore.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dev.projectdiana.dianacore.Tags;
import dev.projectdiana.dianacore.recipes.RecipeRemover;
import dev.projectdiana.dianacore.scripts.ScriptLoader;

@Mod(
    modid = Tags.MODID,
    version = Tags.VERSION,
    name = Tags.MODNAME,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = "required-after:Forge@[10.13.2.1291,);")
public class DianaCoreMod {

    public static final Logger LOG = LogManager.getLogger(Tags.MODID);

    @SidedProxy(
        clientSide = "dev.projectdiana.dianacore.main.ClientProxy",
        serverSide = "dev.projectdiana.dianacore.main.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        RecipeRemover.run();
        ScriptLoader.run();
    }
}
