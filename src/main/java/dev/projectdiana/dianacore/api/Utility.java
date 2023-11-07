package dev.projectdiana.dianacore.api;

import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;

import com.google.auto.value.AutoValue;

public class Utility {

    public static boolean isStackValid(Object aStack) {
        return (aStack instanceof ItemStack) && ((ItemStack) aStack).getItem() != null
            && ((ItemStack) aStack).stackSize >= 0;
    }

    public static boolean isStackInvalid(Object aStack) {
        return !(aStack instanceof ItemStack) || ((ItemStack) aStack).getItem() == null
            || ((ItemStack) aStack).stackSize < 0;
    }

    public static boolean isStringInvalid(Object aString) {
        return aString == null || aString.toString()
            .isEmpty();
    }

    public static ItemStack copy(Object... aStacks) {
        for (Object tStack : aStacks) if (isStackValid(tStack)) return ((ItemStack) tStack).copy();
        return null;
    }

    public static ItemStack copyAmount(long aAmount, Object... aStacks) {
        ItemStack rStack = copy(aStacks);
        if (isStackInvalid(rStack)) return null;
        if (aAmount > 64) aAmount = 64;
        else if (aAmount == -1) aAmount = 111;
        else if (aAmount < 0) aAmount = 0;
        rStack.stackSize = (byte) aAmount;
        return rStack;
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2) {
        return areStacksEqual(aStack1, aStack2, false);
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
        return aStack1 != null && aStack2 != null
            && aStack1.getItem() == aStack2.getItem()
            && (aIgnoreNBT || (((aStack1.getTagCompound() == null) == (aStack2.getTagCompound() == null))
                && (aStack1.getTagCompound() == null || aStack1.getTagCompound()
                    .equals(aStack2.getTagCompound()))))
            && (Items.feather.getDamage(aStack1) == Items.feather.getDamage(aStack2)
                || Items.feather.getDamage(aStack1) == OreDictionary.WILDCARD_VALUE
                || Items.feather.getDamage(aStack2) == OreDictionary.WILDCARD_VALUE);
    }

    @AutoValue
    public abstract static class ItemId {

        public static ItemId create(NBTTagCompound tag) {
            return new AutoValue_Utility_ItemId(
                Item.getItemById(tag.getShort("item")),
                tag.getShort("meta"),
                tag.hasKey("tag", Constants.NBT.TAG_COMPOUND) ? tag.getCompoundTag("tag") : null);
        }

        /** This method copies NBT, as it is mutable. */
        public static ItemId create(ItemStack itemStack) {
            NBTTagCompound nbt = itemStack.getTagCompound();
            if (nbt != null) {
                nbt = (NBTTagCompound) nbt.copy();
            }

            return new AutoValue_Utility_ItemId(itemStack.getItem(), itemStack.getItemDamage(), nbt);
        }

        /** This method copies NBT, as it is mutable. */
        public static ItemId create(Item item, int metaData, @Nullable NBTTagCompound nbt) {
            if (nbt != null) {
                nbt = (NBTTagCompound) nbt.copy();
            }
            return new AutoValue_Utility_ItemId(item, metaData, nbt);
        }

        /** This method does not copy NBT in order to save time. Make sure not to mutate it! */
        public static ItemId createNoCopy(ItemStack itemStack) {
            return new AutoValue_Utility_ItemId(
                itemStack.getItem(),
                itemStack.getItemDamage(),
                itemStack.getTagCompound());
        }

        /** This method does not copy NBT in order to save time. Make sure not to mutate it! */
        public static ItemId createNoCopy(Item item, int metaData, @Nullable NBTTagCompound nbt) {
            return new AutoValue_Utility_ItemId(item, metaData, nbt);
        }

        protected abstract Item item();

        protected abstract int metaData();

        @Nullable
        protected abstract NBTTagCompound nbt();

        public NBTTagCompound writeToNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setShort("item", (short) Item.getIdFromItem(item()));
            tag.setShort("meta", (short) metaData());
            if (nbt() != null) tag.setTag("tag", nbt());
            return tag;
        }
    }
}
