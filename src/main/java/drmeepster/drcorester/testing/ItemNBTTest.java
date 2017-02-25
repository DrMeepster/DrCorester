package drmeepster.drcorester.testing;

import java.util.List;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.item.BasicItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNBTTest extends BasicItem {
	
	public static final String NAME = "nbt";	
	
	public ItemNBTTest(){
		super(NAME, CreativeTabs.MISC, ModDrCorester.MODID);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
    	super.addInformation(stack, playerIn, tooltip, advanced);
    	NBTTagCompound tag = stack.getTagCompound();
    	if(tag != null && tag.getBoolean("testnbt")){
			tooltip.add("\u00A7a" + "test1234567890");
		}

    }
}
