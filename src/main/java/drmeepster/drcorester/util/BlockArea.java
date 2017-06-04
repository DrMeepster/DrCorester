package drmeepster.drcorester.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class BlockArea implements Cloneable{

	protected int north;
	protected int south;
	protected int up;
	protected int down;
	protected int west;
	protected int east;
	
	public static final BlockArea MAX_AREA = new BlockArea(Byte.MAX_VALUE);
	
	/**
	 * Creates an empty BlockArea with a volume of 1 m<sup>3</sup>.
	 */
	public BlockArea(){
		this((byte)0);
	}
	
	/**
	 * Creates a new BlockArea
	 * 
	 * @param dist Edge distance from origin.
	 */
	public BlockArea(byte dist){
		this(dist, dist, dist);
	}
	
	/**
	 * Creates a new BlockArea.
	 * 
	 * @param x Edge distance from origin in the positive and negative x directions.
	 * @param y Edge distance from origin in the positive and negative y directions.
	 * @param z Edge distance from origin in the positive and negative z directions.
	 */
	public BlockArea(byte x, byte y, byte z){
		this(x, x, y, y, z, z);
	}
	
	/**
	 * Creates a new BlockArea.
	 * 
	 * @param east Edge distance from origin in the positive x direction.
	 * @param west Edge distance from origin in the negative x direction.
	 * @param up Edge distance from origin in the positive y direction.
	 * @param down Edge distance from origin in the negative y direction.
	 * @param south Edge distance from origin in the positive z direction.
	 * @param north Edge distance from origin in the negative z direction.
	 */
	public BlockArea(byte east, byte west, byte up, byte down, byte south, byte north){
		if(north < 0 || south < 0 || up < 0 || down < 0 || west < 0 || east < 0){
			throw new IllegalArgumentException("Arguments cannot be negative!");
		}
		this.north = north;
		this.south = south;
		
		this.up = up;
		this.down = down;
		
		this.west = west;
		this.east = east;
	}
	
	public int length(Axis axis){
		if(axis == null){
			throw new NullPointerException("\"axis\" cannot be null!");
		}
		
		switch(axis){
		case X:
			return getEast() + getWest() + 1;
		case Y:
			return getUp() + getDown() + 1;
		case Z:
			return getSouth() + getNorth() + 1;
		default:
			throw new IllegalArgumentException("A BlockArea does not have a length in the \"" + axis.toString() + "\" length.");
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BlockArea[north=" + north + ", south=" + south + ", up=" + up + ", down=" + down + ", west=" + west+ ", east=" + east + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + down;
		result = prime * result + east;
		result = prime * result + north;
		result = prime * result + south;
		result = prime * result + up;
		result = prime * result + west;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlockArea other = (BlockArea) obj;
		if (down != other.down) {
			return false;
		}
		if (east != other.east) {
			return false;
		}
		if (north != other.north) {
			return false;
		}
		if (south != other.south) {
			return false;
		}
		if (up != other.up) {
			return false;
		}
		if (west != other.west) {
			return false;
		}
		return true;
	}

	/**
	 * @return the north
	 */
	public int getNorth() {
		return north;
	}

	/**
	 * @param north the north to set
	 */
	public void setNorth(byte north) {
		if(north < 0){
			throw new IllegalArgumentException("Arguments cannot be negative!");
		}
		this.north = north;
	}

	/**
	 * @return the south
	 */
	public int getSouth() {
		return south;
	}

	/**
	 * @param south the south to set
	 */
	public void setSouth(byte south) {
		if(south < 0){
			throw new IllegalArgumentException("Arguments cannot be negative!");
		}
		this.south = south;
	}

	/**
	 * @return the up
	 */
	public int getUp() {
		return up;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(byte up) {
		if(up < 0){
			throw new IllegalArgumentException("Arguments cannot be negative!");
		}
		this.up = up;
	}

	/**
	 * @return the down
	 */
	public int getDown() {
		return down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(byte down) {
		if(down < 0){
			throw new IllegalArgumentException("Arguments cannot be negative!");
		}
		this.down = down;
	}

	/**
	 * @return the west
	 */
	public int getWest() {
		return west;
	}

	/**
	 * @param west the west to set
	 */
	public void setWest(byte west) {
		if(west < 0){
			throw new IllegalArgumentException("Arguments cannot be negative!");
		}
		this.west = west;
	}

	/**
	 * @return the east
	 */
	public int getEast() {
		return east;
	}

	/**
	 * @param east the east to set
	 */
	public void setEast(byte east) {
		if(east < 0){
			throw new IllegalArgumentException("Arguments cannot be negative!");
		}
		this.east = east;
	}
	
	public static final class BlockAreaApplied{
		
		public final BlockPos pos;
		private final BlockArea area;
		
		@Override
		public String toString() {
			return "BlockAreaApplied [x=" + pos.getX() + ", y=" + pos.getY() + ", z=" + pos.getZ() + ", area=" + area + "]";
		}

		public BlockAreaApplied(byte x, byte y, byte z, BlockPos pos){
			this(x, x, y, y, z, z, pos);
		}

		public BlockAreaApplied(byte north, byte south, byte up, byte down, byte west, byte east, BlockPos pos){
			this(new BlockArea(north, south, up, down, east, west));
		}
		
		public BlockAreaApplied(BlockArea area, BlockPos pos){
			this.area = area;
			this.pos = pos;
		}
		
		public BlockAreaApplied(BlockArea area){
			this(area, BlockPos.ORIGIN);
		}
		
		/**
		 * @return the BlockArea
		 */
		public BlockArea getArea(){
			try{
				return (BlockArea)area.clone();
			}catch(CloneNotSupportedException e){}
			return null;
		}
		
		public int bound(EnumFacing dir){
			if(dir == null){
				throw new NullPointerException("\"dir\" cannot be null!");
			}
			
			switch(dir){
			case NORTH:
				return pos.getZ() - area.getNorth();
			case SOUTH:
				return pos.getZ() + area.getSouth();
			case UP:
				return pos.getY() + area.getUp();
			case DOWN:
				return pos.getY() - area.getDown();
			case WEST:
				return pos.getX() - area.getWest();
			case EAST:
				return pos.getX() + area.getEast();
			default:
				throw new IllegalArgumentException("A BlockAreaApplied does not have a bound in the \"" + dir.toString() + "\" direction.");
			}
		}
		
		public int volume(){
			return area.length(Axis.X) * area.length(Axis.Y) * area.length(Axis.Z);
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((area == null) ? 0 : area.hashCode());
			result = prime * result + ((pos == null) ? 0 : pos.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			BlockAreaApplied other = (BlockAreaApplied) obj;
			if (area == null) {
				if (other.area != null) {
					return false;
				}
			} else if (!area.equals(other.area)) {
				return false;
			}
			if (pos == null) {
				if (other.pos != null) {
					return false;
				}
			} else if (!pos.equals(other.pos)) {
				return false;
			}
			return true;
		}
	}
}
