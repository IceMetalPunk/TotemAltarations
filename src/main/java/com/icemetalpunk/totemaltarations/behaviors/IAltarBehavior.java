package com.icemetalpunk.totemaltarations.behaviors;

import com.icemetalpunk.totemaltarations.tile.TileEntityTotemAltar;

public interface IAltarBehavior {
	/***
	 * Determines if the behavior is even capable of running.
	 * 
	 * @param altar
	 * @return True if the behavior applies now, false if it doesn't.
	 */
	public boolean canTrigger(TileEntityTotemAltar altar);

	/***
	 * Actually runs the behavior.
	 *
	 * @param altar
	 * @return the amount of damage to be dealt to the Totem item stack. Should
	 *         return 0 if the behavior doesn't actually trigger.
	 */
	public int trigger(TileEntityTotemAltar altar);
}
