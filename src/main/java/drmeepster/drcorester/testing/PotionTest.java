package drmeepster.drcorester.testing;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.potion.BasicPotion;
import net.minecraft.entity.EntityLivingBase;

public class PotionTest extends BasicPotion {

	public static final String NAME = "potion_test";
	public static final int COLOR = 0x000000;
	
	protected PotionTest() {
		super(false, COLOR, NAME, ModDrCorester.MODID);
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier){
		entity.setFire(1);
	}
}
