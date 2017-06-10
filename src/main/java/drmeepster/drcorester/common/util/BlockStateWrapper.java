package drmeepster.drcorester.common.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

/**
 * Allows getting a <code>IBlockState</code> from a <code>Block</code> before it is constructed.
 * 
 * @author DrMeepster
 */
public class BlockStateWrapper{
	private IBlockState state = null;
	private boolean evaluated = false;
	
	private final ResourceLocation block;
	private final int meta;
	
	/**
	 * Creates a pre-evaluated <code>BlockStateWrapper</code>.
	 * 
	 * @param state The IBlockState of this wrapper.
	 */
	public BlockStateWrapper(IBlockState state){
		this(state.getBlock().getRegistryName(), state.getBlock().getMetaFromState(state));
		this.state = state;
		evaluated = true;
	}
	
	/**
	 * Creates a new <code>BlockStateWrapper</code>
	 *
	 * @param block This <code>BlockStateWrapper</code>'s <code>Block</code>'s <code>ResourceLocation</code>.
	 */
	public BlockStateWrapper(ResourceLocation block){
		this(block, 0);
	}
	
	/**
	 * Creates a new <code>BlockStateWrapper</code>
	 *
	 * @param block This <code>BlockStateWrapper</code>'s <code>Block</code>'s <code>ResourceLocation</code>.
	 * @param meta The metadata value of the state.
	 */
	public BlockStateWrapper(ResourceLocation block, int meta){
		if(block == null){
			throw new NullPointerException("\"block\" cannot be null!");
		}
		if(meta < 0 || meta > 15){
			throw new IllegalArgumentException("\"meta\" must be between 0 and 15 (inclusive), not " + meta + "!");
		}
		this.block = block;
		this.meta = meta;
	}
	
	/**
	 * @return The <code>IBlockState</code> associated with this <code>BlockStateWrapper</code>.
	 */
	public IBlockState getState(){
		if(!evaluated){
			throw new IllegalStateException("This BlockStateWrapper must have \"evaluated\" be true to access the IBlockState.");
		}
		return state;
	}
	
	/**
	 * @return Whether this <code>BlockStateWrapper</code> is evaluated or not.
	 */
	public boolean isEvaluated(){
		return evaluated;
	}

	/**
	 * @return The <code>Block</code> associated with this <code>BlockStateWrapper</code>.
	 */
	public ResourceLocation getBlock(){
		return block;
	}

	/**
	 * @return The metadata value associated with this <code>BlockStateWrapper</code>.
	 */
	public int getMeta(){
		return meta;
	}
	
	/**
	 * "Evaluates" this instance. This means that the <code>state</code> value is generated from <code>block</code> and <code>meta</code>.
	 * 
	 * @throws EvaluationException A wrapper for any exception that occurs in this method.
	 */
	@SuppressWarnings("deprecation")
	public void evaluate() throws EvaluationException{
		try{
			if(evaluated){
				throw new IllegalStateException("Cannot evaluate an evaluated BlockStateWrapper!");
			}
			state = Block.REGISTRY.getObject(block).getStateFromMeta(meta);
			evaluated = true;
		}catch(Exception e){
			throw new EvaluationException(e);
		}
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + (evaluated ? 1231 : 1237);
		result = prime * result + meta;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		BlockStateWrapper other = (BlockStateWrapper)obj;
		if(!block.equals(other.block))
			return false;
		if(meta != other.meta)
			return false;
		return true;
	}

	public static class EvaluationException extends RuntimeException{

		private static final long serialVersionUID = -3948779907025439331L;

		public EvaluationException() {
	        super();
	    }

	    public EvaluationException(String message){
	        super(message);
	    }

	    public EvaluationException(String message, Throwable cause){
	        super(message, cause);
	    }

	    public EvaluationException(Throwable cause){
	        super(cause);
	    }

	    protected EvaluationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
	        super(message, cause, enableSuppression, writableStackTrace);
	    }
	}
}