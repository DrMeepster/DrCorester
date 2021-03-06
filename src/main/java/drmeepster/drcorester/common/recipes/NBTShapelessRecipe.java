package drmeepster.drcorester.common.recipes;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.common.util.Util;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class NBTShapelessRecipe extends ShapelessRecipes {
	
	public final Predicate <ItemStack> condition;
	public final ItemStack inputNbt;
	
	/**
	 * @param inputList The list of crafting {@code ItemStack}s including {@code inputNbt}.
	 * @param inputNbt The {@code ItemStack} that gives its {@code NBTTagCompound} to {@code outputStack}
	 * @param outputStack The {@code ItemStack} that is crafted and given {@code inputNbt}'s {@code NBTTagCompound}.
	 * @param condition The condition that needs to be true when given {@code inputNbt}. May be {@code null}.
	 */
	public NBTShapelessRecipe(ItemStack[] inputList, ItemStack inputNbt, ItemStack outputStack, Predicate<ItemStack> condition){
		super(outputStack, Util.iterableToList(inputList));
		this.condition = condition;
		this.inputNbt = inputNbt;
	}
	
	/**
	 * @param inputList The list of crafting {@code ItemStack}s including {@code outputStack}.
	 * @param outputStack The {@code ItemStack} that is given {@code tag}.
	 * @param condition The condition that needs to be true when given {@code outputStack}. May be {@code null}.
	 */
	public NBTShapelessRecipe(ItemStack[] inputList, ItemStack outputStack, Predicate<ItemStack> condition){
		this(inputList, outputStack, outputStack, condition);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return super.matches(inv, worldIn) && (condition == null || condition.test(inputNbt));
	}
	
	@Nullable
    public ItemStack getCraftingResult(InventoryCrafting inv){
		NBTTagCompound tag = new NBTTagCompound();
		ItemStack is1 = new ItemStack(ModDrCorester.placeholder);
		
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack is2 = inv.getStackInSlot(i) != null ? inv.getStackInSlot(i).copy() : null;
			
			if(is2 == null){
				continue;
			}
			
			if(is2.isItemEqual(inputNbt)){
				is1 = is2;
				tag = is2.getTagCompound() != null ? is2.getTagCompound().copy() : tag;
			}
		}
		tag.merge(this.getRecipeOutput().getTagCompound().copy());
		is1.setCount(inputNbt.getCount());
		return Util.addNbtData(is1, tag);
    }
}
