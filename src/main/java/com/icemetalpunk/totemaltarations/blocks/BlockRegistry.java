package com.icemetalpunk.totemaltarations.blocks;

import java.util.HashMap;

import com.icemetalpunk.totemessentials.blocks.BasicBlock;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class BlockRegistry {
	public static final HashMap<String, BasicBlock> registry = new HashMap<String, BasicBlock>();

	static {
		registry.put("block_totem_altar", new BlockTotemAltar("block_totem_altar"));
	}

	public BlockRegistry() {
	}

	public void registerAll(RegistryEvent.Register<Block> ev) {
		for (BasicBlock block : registry.values()) {
			block.getItemBlock().setRegistryName(((Block) block).getRegistryName());
			ev.getRegistry().register((Block) block);
		}
	}

	public void registerItemBlocks(RegistryEvent.Register<Item> ev) {
		for (BasicBlock block : registry.values()) {
			ev.getRegistry().register(block.getItemBlock());
		}
	}

	public static BasicBlock get(String name) {
		return registry.get(name);
	}

	public void registerModels() {
		for (BasicBlock block : registry.values()) {
			block.registerModel();
		}
	}
}
