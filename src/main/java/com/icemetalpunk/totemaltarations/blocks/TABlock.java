package com.icemetalpunk.totemaltarations.blocks;

import com.icemetalpunk.totemaltarations.TotemAltarations;
import com.icemetalpunk.totemessentials.blocks.BasicBlock;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class TABlock extends BasicBlock {

	public TABlock(String name, Material mat, MapColor col) {
		super(TotemAltarations.MODID, name, mat, col, TotemAltarations.tab);
	}

	public TABlock(String name, Material mat) {
		super(TotemAltarations.MODID, name, mat, TotemAltarations.tab);
	}

	// Rock by default, because why not?
	public TABlock(String name) {
		super(TotemAltarations.MODID, name, Material.ROCK, TotemAltarations.tab);
	}

}
