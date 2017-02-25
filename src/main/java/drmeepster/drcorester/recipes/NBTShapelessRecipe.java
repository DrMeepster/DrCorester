package drmeepster.drcorester.recipes;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import drmeepster.drcorester.util.Util;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class NBTShapelessRecipe extends ShapelessRecipes {
	
	public final Predicate <ItemStack> condition;
	public final ItemStack inputNbt;
	public final NBTTagCompound tag;
	
	/**
	 * 
	 * @param inputList The list of crafting {@code ItemStack}s including {@code inputNbt}.
	 * @param inputNbt The {@code ItemStack} that gives its {@code NBTTagCompound} to {@code outputStack}
	 * @param outputStack The {@code ItemStack} that is crafted and given {@code inputNbt}'s {@code NBTTagCompound} plus {@code tag}.
	 * @param tag The {@code NBTTagCompound} to give to {@code outputStack}.
	 * @param condition The condition that needs to be true when given {@code inputNbt}. May be {@code null}.
	 */
	public NBTShapelessRecipe(ItemStack[] inputList, ItemStack inputNbt, ItemStack outputStack, NBTTagCompound tag, Predicate<ItemStack> condition){
		super(Util.setNbtData(outputStack, tag), Util.arrayToList(inputList));
		this.condition = condition;
		this.inputNbt = inputNbt;
		this.tag = tag;
	}
	
	/**
	 * @param inputList The list of crafting {@code ItemStack}s including {@code outputStack}.
	 * @param outputStack The {@code ItemStack} that is given {@code tag}.
	 * @param tag The {@code NBTTagCompound} to change the output {@code ItemStack} with.
	 * @param condition The condition that needs to be true when given {@code outputStack}. May be {@code null}.
	 */
	public NBTShapelessRecipe(ItemStack[] inputList, ItemStack outputStack, NBTTagCompound tag, Predicate<ItemStack> condition){
		this(inputList, outputStack, outputStack, tag, condition);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return super.matches(inv, worldIn) && (condition == null || condition.test(inputNbt));
	}
	
	@Nullable
    public ItemStack getCraftingResult(InventoryCrafting inv){
		NBTTagCompound tag = null;
		ItemStack stackOut = null;
		
		for(int i = 0; i < inv.getInventoryStackLimit(); i++){
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null && stack.isItemEqual(inputNbt)){
				tag = stack.getTagCompound();
				stackOut = new ItemStack(this.getRecipeOutput().getItem(), 1, stack.getItemDamage());
				stackOut.setTagCompound(stack.getTagCompound());
				break;
			}
		}
		return Util.addNbtData(Util.addNbtData(stackOut, tag), this.tag);
    }
}
