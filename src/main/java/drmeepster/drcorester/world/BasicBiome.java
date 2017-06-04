package drmeepster.drcorester.world;

import drmeepster.drcorester.util.IBasicObject;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BasicBiome extends Biome implements IBasicObject<Biome> {

	public final Type[] types;
	
	public BasicBiome(BiomeProperties properties, Type... types) {
		super(properties);
		this.types = types;
	}

	@Override
	public String getName() {
		return this.getBiomeName();
	}

	@Override
	public String getId() {
		return this.getRegistryName().getResourcePath();
	}
}
