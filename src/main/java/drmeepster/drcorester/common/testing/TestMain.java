package drmeepster.drcorester.common.testing;

import static drmeepster.drcorester.common.util.Util.register;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.client.testing.RenderEntityTest;
import drmeepster.drcorester.common.block.BasicBlock;
import drmeepster.drcorester.common.block.BasicBlockFalling;
import drmeepster.drcorester.common.item.BasicItem;
import drmeepster.drcorester.common.recipes.NBTShapedRecipe;
import drmeepster.drcorester.common.recipes.NBTShapelessRecipe;
import drmeepster.drcorester.common.util.Util;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Contains all of the testing items for DrCorester. <b>DO NOT ASSUME THESE ARE NOT NULL!</b>
 * 
 * @author DrMeepster
 */
public class TestMain{
	
	public static BasicItem itemTest;
	public static ItemNBTTest itemNbtTest;
	
	public static PotionTest potionTest;
	public static TabTesting tabTesting;
	
	public static BasicBlock blockNormTest;
	public static BasicBlockFalling blockTestFalling;

	public static void preInit(){
		tabTesting = new TabTesting();
		
		itemTest = register(new BasicItem("test", tabTesting));
		itemNbtTest = register(new ItemNBTTest());
		
		potionTest = register(new PotionTest());
		
		blockNormTest = register(new BasicBlock(MapColor.OBSIDIAN, Material.CLOTH, "test_norm", tabTesting));
		blockTestFalling = register(new BasicBlockFalling(Material.CLOTH, "test_falling", TestMain.tabTesting));
	
		register(EntityTest.class, EntityTest.ID, "test", 0, ModDrCorester.instance, 0, 1, false, 0x5500ff, 0x000000, RenderEntityTest.factory);
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
		
		ItemStack s1 = new ItemStack(itemNbtTest, 64);
		s1.setTagCompound(tag1);
		ItemStack s2 = new ItemStack(itemNbtTest, 32);
		s2.setTagCompound(tag2);
		
		GameRegistry.addRecipe(new NBTShapelessRecipe(stacks1, s1, null));
		GameRegistry.addRecipe(new NBTShapedRecipe(stacks2, s2, null));
	}
}
