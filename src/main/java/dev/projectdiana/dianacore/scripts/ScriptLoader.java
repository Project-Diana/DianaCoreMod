package dev.projectdiana.dianacore.scripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;

import dev.projectdiana.dianacore.main.DianaCoreMod;

public class ScriptLoader {

    public static void run() {
        IScriptLoader.missing.setStackDisplayName(EnumChatFormatting.RED + "Missing item! Please report it on github!");

        List<IScriptLoader> scripts = new ArrayList<>(Arrays.asList(new ScriptMinecraft()));

        ArrayList<String> errored = new ArrayList<>();
        final long totalTimeStart = System.currentTimeMillis();
        for (IScriptLoader script : scripts) {
            if (script.isScriptLoadable()) {
                try {
                    final long timeStart = System.currentTimeMillis();
                    script.loadRecipes();
                    final long timeToLoad = System.currentTimeMillis() - timeStart;
                    DianaCoreMod.LOG.info("Loaded " + script.getScriptName() + " script in " + timeToLoad + " ms.");
                } catch (Exception e) {
                    errored.add(script.getScriptName());
                    DianaCoreMod.LOG
                        .error("There was an error while loading " + script.getScriptName() + "! Printing stacktrace:");
                    e.printStackTrace();
                }
            } else {
                DianaCoreMod.LOG
                    .info("Missing dependencies to load " + script.getScriptName() + " script. It won't be loaded.");
            }
        }
        final long totalTimeToLoad = System.currentTimeMillis() - totalTimeStart;
        DianaCoreMod.LOG.info("Script loader took " + totalTimeToLoad + " ms.");
        if (!errored.isEmpty()) throw new RuntimeException(
            "Scripts " + errored + " threw an exception! Scroll up the log to see the stacktraces!");
    }
}
