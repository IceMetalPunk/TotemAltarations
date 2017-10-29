package com.icemetalpunk.totemaltarations.proxies;

import com.icemetalpunk.totemaltarations.render.AltarPedestalTESR;
import com.icemetalpunk.totemaltarations.tile.TileEntityTotemAltar;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TAClientProxy extends TACommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTotemAltar.class, new AltarPedestalTESR());
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
}
