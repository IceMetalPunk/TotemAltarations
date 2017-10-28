package com.icemetalpunk.totemaltarations.items;

import java.util.HashMap;

import com.icemetalpunk.totemessentials.IOreDicted;
import com.icemetalpunk.totemessentials.ModeledObject;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
	public static HashMap<String, ModeledObject> registry = new HashMap<String, ModeledObject>();

	static {
		// TODO: Add items to registry here.
	}

	public void put(String name, ModeledObject val) {
		registry.put(name, val);
	}

	public Item get(String name) {
		return (Item) registry.get(name);
	}

	public void registerAll(RegistryEvent.Register<Item> ev) {
		for (ModeledObject item : registry.values()) {
			ev.getRegistry().register((Item) item);
			if (item instanceof IOreDicted) {
				IOreDicted oreDict = (IOreDicted) item;
				oreDict.registerOreDict();
			}
		}
	}

	public void registerModels() {
		for (ModeledObject item : registry.values()) {
			item.registerModel();
		}
	}
}
