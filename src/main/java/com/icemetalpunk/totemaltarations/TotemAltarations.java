package com.icemetalpunk.totemaltarations;

import com.icemetalpunk.totemaltarations.events.TARegistryEvents;
import com.icemetalpunk.totemaltarations.proxies.TACommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TotemAltarations.MODID, version = TotemAltarations.VERSION, dependencies = "required-after:totemessentials@[1.0.1,)")
public class TotemAltarations {
	public static final String MODID = "totemaltarations";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "com.icemetalpunk.totemaltarations.proxies.TAClientProxy", serverSide = "com.icemetalpunk.totemaltarations.proxies.TAServerProxy")
	public static TACommonProxy proxy;

	public static CreativeTabs tab = new CreativeTabs("totemaltarations") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(TotemAltarations.proxy.blocks.get("block_totem_altar").getItemBlock());
		}

	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		MinecraftForge.EVENT_BUS.register(new TARegistryEvents());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
