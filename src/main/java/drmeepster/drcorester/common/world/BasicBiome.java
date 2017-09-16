package drmeepster.drcorester.common.world;

import drmeepster.drcorester.common.util.interfaces.IBasicObject;
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
