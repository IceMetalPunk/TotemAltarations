package com.icemetalpunk.totemaltarations.events;

import com.icemetalpunk.totemaltarations.TotemAltarations;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TARegistryEvents {
	public TARegistryEvents() {

	}

	@SubscribeEvent
	public void modelHandler(ModelRegistryEvent ev) {
		TotemAltarations.proxy.blocks.registerModels();
		TotemAltarations.proxy.items.registerModels();
	}

	@SubscribeEvent
	public void itemHandler(RegistryEvent.Register<Item> ev) {
		TotemAltarations.proxy.items.registerAll(ev);
		TotemAltarations.proxy.blocks.registerItemBlocks(ev);
	}

	@SubscribeEvent
	public void blockHandler(RegistryEvent.Register<Block> ev) {
		TotemAltarations.proxy.blocks.registerAll(ev);
	}
}
