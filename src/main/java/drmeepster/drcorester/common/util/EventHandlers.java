package drmeepster.drcorester.common.util;

import drmeepster.drcorester.ModDrCorester;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventHandlers{
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event){
		if(ModDrCorester.devStuff)
			event.getRegistry().registerAll();
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event){
		if(ModDrCorester.devStuff)
			event.getRegistry().registerAll();
	}
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event){
		if(ModDrCorester.devStuff)
			event.getRegistry().registerAll();
	}
	
	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event){
		if(ModDrCorester.devStuff)
			event.getRegistry().registerAll();
	}
}
