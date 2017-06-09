package drmeepster.drcorester.util

@Mod.EventBusSubscriber
public class EventHandlers{
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
    event.getRegistry().registerAll();
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll();
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll();
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Biome> event) {
		event.getRegistry().registerAll();
	}
}
