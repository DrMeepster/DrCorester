package drmeepster.drcorester.common.entity;

import net.minecraft.util.ResourceLocation;

/**
 * A "Basic" {@code Entity} for easy registration.<br><br>
 * 
 * <b>DO NOT IMPLEMENT IN A CLASS THAT DOES NOT EXTEND {@code Entity}</b>
 * 
 * @author DrMeepster
 */
public interface IBasicEntity{
	
	/**
	 * Gets this {@code Object}'s name. Usually some kind of unlocalized name. <b>DO NOT HARD CODE!</b>
	 * 
	 * @return this Object's name
	 */
	public String getName();
	
	/**
	 * Gets this {@code Entity}'s id.
	 * 
	 * @return this {@code Entity}'s id
	 */
	public ResourceLocation getId();
}
