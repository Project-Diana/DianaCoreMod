package dev.projectdiana.dianacore.main;

import static dev.projectdiana.dianacore.api.ModHandler.getModItem;
import static dev.projectdiana.dianacore.api.enums.Mods.IguanaTweaksTinkerConstruct;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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
    public void init(FMLInitializationEvent event) {
        // Create custom bucketWater ore dictionary. The listAllwater entry includes bottles and Pam's fresh water
        // which we don't want to be usable in a lot of recipes
        // but clay bucket and iron bucket should be interchangeable.
        OreDictionary.registerOre("bucketWater", Items.water_bucket);
        if (IguanaTweaksTinkerConstruct.isModLoaded()) OreDictionary
            .registerOre("bucketWater", getModItem(IguanaTweaksTinkerConstruct.ID, "clayBucketWater", 1, 0));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        // Add a combined Coal and Charcoal ore dictionary. This is useful because usually these are interchangeable
        for (ItemStack s : OreDictionary.getOres("coal")) {
            OreDictionary.registerOre("coalAndCharcoal", s);
        }
        for (ItemStack s : OreDictionary.getOres("charcoal")) {
            OreDictionary.registerOre("coalAndCharcoal", s);
        }

        // Run modification scripts
        RecipeRemover.run();
        ScriptLoader.run();
    }
}
