package com.icemetalpunk.totemaltarations.render;

import com.icemetalpunk.totemaltarations.tile.TileEntityTotemAltar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class AltarPedestalTESR extends TileEntitySpecialRenderer<TileEntityTotemAltar> {

	protected final RenderEntityItem rei;
	protected final RenderEntityItem diamondREI;
	protected final EntityItem entity = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0,
			new ItemStack(Items.TOTEM_OF_UNDYING, 1));
	protected final EntityItem innerDiamond = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0,
			new ItemStack(Items.DIAMOND, 1));
	protected int rotationControl = 0;
	protected final int INV_ROTATION_SPEED = 2;
	protected long lastTickTime = 0;

	public AltarPedestalTESR() {
		super();
		diamondREI = new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(),
				Minecraft.getMinecraft().getRenderItem()) {
			@Override
			public boolean shouldSpreadItems() {
				return false;
			}

			@Override
			public boolean shouldBob() {
				return false;
			}
		};
		rei = new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(),
				Minecraft.getMinecraft().getRenderItem()) {
			@Override
			public boolean shouldSpreadItems() {
				return false;
			}
		};
	}

	@Override
	public void render(TileEntityTotemAltar te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {

		long deltaTicks = te.getWorld().getTotalWorldTime() - this.lastTickTime;
		this.lastTickTime = te.getWorld().getTotalWorldTime();

		innerDiamond.setWorld(te.getWorld());
		entity.setWorld(te.getWorld());
		if (deltaTicks > 0) {
			innerDiamond.onUpdate();

			rotationControl = (rotationControl + 1) % this.INV_ROTATION_SPEED;
			if (rotationControl == 0) {
				entity.onUpdate();
			}
		}
		this.setLightmapDisabled(true);
		diamondREI.doRender(innerDiamond, x + 0.5, y, z + 0.5, 0, partialTicks);
		this.setLightmapDisabled(false);

		// FIXME: If totem in slot breaks, it continues to render even though
		// it's not really there.

		if (!te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
			return;
		}
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		ItemStack stack = handler.getStackInSlot(0);

		if (stack == null || stack.isEmpty()) {
			return;
		}

		entity.setItem(stack);

		this.setLightmapDisabled(true);
		rei.doRender(entity, x + 0.5, y + 1.0f, z + 0.5, 0, partialTicks);
		this.setLightmapDisabled(false);

	}

}