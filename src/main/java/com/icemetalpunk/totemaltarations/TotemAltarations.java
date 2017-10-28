package com.icemetalpunk.totemaltarations;

import com.icemetalpunk.totemaltarations.events.TARegistryEvents;
import com.icemetalpunk.totemaltarations.proxies.TACommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TotemAltarations.MODID, version = TotemAltarations.VERSION, dependencies = "after:totemessentials")
public class TotemAltarations {
	public static final String MODID = "totemaltarations";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "com.icemetalpunk.totemaltarations.proxies.TAClientProxy", serverSide = "com.icemetalpunk.totemaltarations.proxies.TAServerProxy")
	public static TACommonProxy proxy;

	public static CreativeTabs tab = new CreativeTabs("totemaltarations") {

		@Override
		public ItemStack getTabIconItem() {
			// TODO: Make this a totem altarations item.
			return new ItemStack(Items.TOTEM_OF_UNDYING);
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
