package drmeepster.drcorester.testing;

import static drmeepster.drcorester.util.Util.register;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.block.BasicBlock;
import drmeepster.drcorester.item.BasicItem;
import drmeepster.drcorester.recipes.NBTShapedRecipe;
import drmeepster.drcorester.recipes.NBTShapelessRecipe;
import drmeepster.drcorester.util.Util;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TestMain{
	
	public static BasicItem itemTest;
	public static ItemNBTTest itemNbtTest;
	
	public static PotionTest potionTest;
	
	public static BasicBlock blockNormTest;
	public static BlockTestFalling blockTestFalling;
	
	public static void preInit(){
		itemTest = register(new BasicItem("test", CreativeTabs.MISC, ModDrCorester.MODID));
		itemNbtTest = register(new ItemNBTTest());
		
		potionTest = register(new PotionTest());
		
		blockNormTest = register(new BasicBlock(MapColor.OBSIDIAN, Material.CLOTH, "test_norm", null, ModDrCorester.MODID));
		blockTestFalling = register(new BlockTestFalling());
	}
	
	public static void init(){
		ItemStack[] stacks1 = {new ItemStack(itemNbtTest)};
		ItemStack[] stacks2 = Util.stuffToItemstackArray(
				 "DTD"
				,"SNS"
				,"DTD"
				,'D', Items.DIAMOND
				,'T', Blocks.TNT
				,'S', Items.STRING
				,'N', itemNbtTest
		);
		NBTTagCompound tag1 = new NBTTagCompound();
		NBTTagCompound tag2 = new NBTTagCompound();
		tag1.setBoolean("testnbt-bool", true);
		tag2.setString("testnbt-str", "google");
		
		ItemStack s1 = Util.setNbtData(new ItemStack(itemNbtTest, 64), tag1);
		ItemStack s2 = Util.setNbtData(new ItemStack(itemNbtTest, 32), tag2);
		
		GameRegistry.addRecipe(new NBTShapelessRecipe(stacks1, s1, null));
		GameRegistry.addRecipe(new NBTShapedRecipe(stacks2, s2, null));
	}
}
