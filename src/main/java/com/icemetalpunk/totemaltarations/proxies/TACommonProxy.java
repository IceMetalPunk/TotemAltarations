package com.icemetalpunk.totemaltarations.proxies;

import com.icemetalpunk.totemaltarations.blocks.BlockRegistry;
import com.icemetalpunk.totemaltarations.items.ItemRegistry;
import com.icemetalpunk.totemaltarations.tile.TileEntityTotemAltar;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TACommonProxy {
	public BlockRegistry blocks;
	public ItemRegistry items;

	public void preInit(FMLPreInitializationEvent e) {
		blocks = new BlockRegistry();
		items = new ItemRegistry();
		GameRegistry.registerTileEntity(TileEntityTotemAltar.class, "totem_altar");
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {
	}
}
