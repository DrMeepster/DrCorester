package drmeepster.drcorester.common.testing;

import drmeepster.drcorester.common.potion.BasicPotion;
import net.minecraft.entity.EntityLivingBase;

public class PotionTest extends BasicPotion {

	public static final String NAME = "potion_test";
	public static final int COLOR = 0x000000;
	
	protected PotionTest() {
		super(false, COLOR, NAME);
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier){
		entity.setArrowCountInEntity(256);
	}
}
