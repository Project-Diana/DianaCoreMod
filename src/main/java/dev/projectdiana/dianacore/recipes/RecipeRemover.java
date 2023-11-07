package dev.projectdiana.dianacore.recipes;

import static dev.projectdiana.dianacore.api.ModHandler.getModItem;
import static dev.projectdiana.dianacore.api.enums.Mods.Minecraft;
import static dev.projectdiana.dianacore.scripts.IScriptLoader.wildcard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import dev.projectdiana.dianacore.api.Utility;
import dev.projectdiana.dianacore.main.DianaCoreMod;

public class RecipeRemover {

    private static boolean bufferingRecipes = true;

    @SuppressWarnings("unchecked")
    private static final ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance()
        .getRecipeList();

    private static final HashMap<Utility.ItemId, List<Function<IRecipe, Boolean>>> bufferMap = new HashMap<>();

    private static void addToBuffer(HashSet<Utility.ItemId> outputs, Function<IRecipe, Boolean> whenToRemove) {
        for (Utility.ItemId output : outputs) {
            bufferMap.computeIfAbsent(output, o -> new ArrayList<>())
                .add(whenToRemove);
        }
        if (!bufferingRecipes) stopBuffering();
    }

    private static void stopBuffering() {
        int i = tList.size();
        tList.removeIf(r -> {
            ItemStack rCopy = r.getRecipeOutput();
            if (rCopy == null) return false; // ????????????????????
            if (rCopy.stackTagCompound != null) {
                rCopy = rCopy.copy();
                rCopy.stackTagCompound = null;
            }
            Utility.ItemId key = Utility.ItemId.createNoCopy(rCopy);
            rCopy = rCopy.copy();
            Items.feather.setDamage(rCopy, wildcard);
            Utility.ItemId keyWildcard = Utility.ItemId.createNoCopy(rCopy);
            List<Function<IRecipe, Boolean>> listWhenToRemove = bufferMap.get(key);
            if (listWhenToRemove == null) listWhenToRemove = bufferMap.get(keyWildcard);
            if (listWhenToRemove == null) return false;
            for (Function<IRecipe, Boolean> whenToRemove : listWhenToRemove) {
                if (whenToRemove.apply(r)) return true;
            }
            return false;
        });
        DianaCoreMod.LOG.info("Removed " + (i - tList.size()) + " recipes!");
        bufferMap.clear();
    }

    private static HashSet<Utility.ItemId> getItemsHashed(Object item, boolean includeWildcardVariants) {
        HashSet<Utility.ItemId> hashedItems = new HashSet<>();
        if (item instanceof ItemStack) {
            ItemStack iCopy = ((ItemStack) item).copy();
            iCopy.stackTagCompound = null;
            hashedItems.add(Utility.ItemId.createNoCopy(iCopy));
            if (includeWildcardVariants) {
                iCopy = iCopy.copy();
                Items.feather.setDamage(iCopy, wildcard);
                hashedItems.add(Utility.ItemId.createNoCopy(iCopy));
            }
        } else if (item instanceof String) {
            for (ItemStack stack : OreDictionary.getOres((String) item)) {
                hashedItems.add(Utility.ItemId.createNoCopy(stack));
                if (includeWildcardVariants) {
                    stack = stack.copy();
                    Items.feather.setDamage(stack, wildcard);
                    hashedItems.add(Utility.ItemId.createNoCopy(stack));
                }
            }
        } else if (item instanceof ArrayList) {
            // noinspection unchecked
            for (ItemStack stack : (ArrayList<ItemStack>) item) {
                ItemStack iCopy = stack.copy();
                iCopy.stackTagCompound = null;
                hashedItems.add(Utility.ItemId.createNoCopy(iCopy));
                if (includeWildcardVariants) {
                    iCopy = iCopy.copy();
                    Items.feather.setDamage(iCopy, wildcard);
                    hashedItems.add(Utility.ItemId.createNoCopy(iCopy));
                }
            }
        } else throw new IllegalArgumentException("Invalid input");
        return hashedItems;
    }

    /**
     * Removes only shapeless recipes by output and inputs, supports OreDictionary tags
     *
     * @author kuba6000
     */
    public static void removeRecipeShapelessDelayed(Object aOutput, Object... aRecipe) {
        ArrayList<Object> aRecipeList = new ArrayList<>(Arrays.asList(aRecipe));
        addToBuffer(getItemsHashed(aOutput, false), r -> {
            if (!(r instanceof ShapelessOreRecipe) && !(r instanceof ShapelessRecipes)) return false;
            if (aRecipeList.isEmpty()) return true;
            @SuppressWarnings("unchecked")
            ArrayList<Object> recipe = (ArrayList<Object>) aRecipeList.clone();
            @SuppressWarnings("unchecked")
            List<Object> rInputs = (r instanceof ShapelessOreRecipe ? ((ShapelessOreRecipe) r).getInput()
                : ((ShapelessRecipes) r).recipeItems);
            for (Object rInput : rInputs) {
                HashSet<Utility.ItemId> rInputHashed;
                HashSet<Utility.ItemId> rInputHashedNoWildcard;
                try {
                    rInputHashed = getItemsHashed(rInput, true);
                    rInputHashedNoWildcard = getItemsHashed(rInput, false);
                } catch (Exception ex) {
                    return false;
                }
                boolean found = false;
                for (Iterator<Object> iterator = recipe.iterator(); iterator.hasNext();) {
                    Object o = iterator.next();
                    for (Utility.ItemId id : getItemsHashed(o, false)) {
                        if (rInputHashed.contains(id)) {
                            found = true;
                            iterator.remove();
                            break;
                        }
                    }
                    if (!found) {
                        for (Utility.ItemId id : getItemsHashed(o, true)) {
                            if (rInputHashedNoWildcard.contains(id)) {
                                found = true;
                                iterator.remove();
                                break;
                            }
                        }
                    }
                    if (found) break;
                }
                if (!found) return false;
            }
            return recipe.isEmpty();
        });
    }

    private static Field recipeWidthField = null;

    /**
     * Removes only shaped recipes by output and inputs, supports OreDictionary tags
     *
     * @author kuba6000
     */
    public static void removeRecipeShapedDelayed(Object aOutput, Object[] row1, Object[] row2, Object[] row3) {
        if (recipeWidthField == null) {
            try {
                recipeWidthField = ShapedOreRecipe.class.getDeclaredField("width");
                recipeWidthField.setAccessible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        Object[][] recipe = new Object[][] { row1, row2, row3 };
        addToBuffer(getItemsHashed(aOutput, false), r -> {
            if (!(r instanceof ShapedOreRecipe) && !(r instanceof ShapedRecipes)) return false;
            Object[] inputs = (r instanceof ShapedOreRecipe ? ((ShapedOreRecipe) r).getInput()
                : ((ShapedRecipes) r).recipeItems);
            int width;
            try {
                width = (r instanceof ShapedOreRecipe ? (int) recipeWidthField.get(r)
                    : ((ShapedRecipes) r).recipeWidth);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            int height = (r instanceof ShapedOreRecipe ? r.getRecipeSize() / width : ((ShapedRecipes) r).recipeHeight);

            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    Object rStack = (y >= height || x >= width ? null : inputs[x + y * width]);
                    Object rRecipe = (x >= recipe[y].length ? null : recipe[y][x]);
                    if (rStack == null ^ rRecipe == null) return false;
                    if (rStack == null) continue;
                    HashSet<Utility.ItemId> rInputHashed;
                    try {
                        rInputHashed = getItemsHashed(rStack, true);
                    } catch (Exception ex) {
                        return false;
                    }
                    boolean found = false;
                    for (Utility.ItemId id : getItemsHashed(rRecipe, false)) {
                        if (rInputHashed.contains(id)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        rInputHashed = getItemsHashed(rStack, false);
                        for (Utility.ItemId id : getItemsHashed(rRecipe, true)) {
                            if (rInputHashed.contains(id)) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) return false;
                }
            }

            return true;
        });
    }

    /**
     * Removes only shaped recipes by output, supports OreDictionary tag
     *
     * @author kuba6000
     */
    public static void removeRecipeShapedDelayed(Object aOutput) {
        addToBuffer(getItemsHashed(aOutput, false), r -> r instanceof ShapedOreRecipe || r instanceof ShapedRecipes);
    }

    /**
     * Removes recipes by output, supports OreDictionary tags
     *
     * @author kuba6000
     */
    public static void removeRecipeByOutputDelayed(Object aOutput) {
        addToBuffer(getItemsHashed(aOutput, false), r -> true);
    }

    public static void run() {

        final long timeStart = System.currentTimeMillis();

        removeRecipeByOutputDelayed(getModItem(Minecraft.ID, "brick_block", 1, 0));
        removeRecipeByOutputDelayed(getModItem(Minecraft.ID, "brick_block", 2, 0));
        removeRecipeByOutputDelayed(getModItem(Minecraft.ID, "furnace", 1, 0));
        removeRecipeByOutputDelayed(getModItem(Minecraft.ID, "chest", 1, 0));
        removeRecipeShapelessDelayed(getModItem(Minecraft.ID, "flint", 1, 0), getModItem(Minecraft.ID, "gravel", 1, 0));

        stopBuffering();
        bufferingRecipes = false;

        final long timeToLoad = System.currentTimeMillis() - timeStart;
        DianaCoreMod.LOG.info("Recipe Removal Took " + timeToLoad + " ms.");
    }

}
