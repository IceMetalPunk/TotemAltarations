package com.icemetalpunk.totemaltarations.tile;

import java.util.HashMap;

import javax.annotation.Nonnull;

import com.icemetalpunk.totemaltarations.behaviors.AltarBehaviorReaper;
import com.icemetalpunk.totemaltarations.behaviors.IAltarBehavior;
import com.icemetalpunk.totemessentials.TotemEssentials;
import com.icemetalpunk.totemessentials.items.essences.ItemEssenceBase;
import com.icemetalpunk.totemessentials.items.totems.ItemTotemBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityTotemAltar extends TileEntity implements ITickable {

	private int size = 3;
	public int cooldown = 100;
	public int maxCooldown = 100;
	private static HashMap<Item, IAltarBehavior> behaviorMap = new HashMap<>();

	public static void loadBehaviors() {
		behaviorMap.put(TotemEssentials.proxy.items.get("reaping_totem"), new AltarBehaviorReaper());
	}

	private ItemStackHandler stackHandler = new ItemStackHandler(2) {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityTotemAltar.this.syncUpdates();
		}

		@Override
		@Nonnull
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if (stack.getItem() instanceof ItemTotemBase) {
				return super.insertItem(0, stack, simulate);
			} else if (stack.getItem() instanceof ItemEssenceBase) {
				ItemStack ins = super.insertItem(1, stack, simulate);
				return ins;
			}
			return stack;
		}

		@Override
		public int getSlotLimit(int slot) {
			if (slot == 0) {
				return 1;
			} else {
				return 64;
			}
		}
	};

	public TileEntityTotemAltar() {
		super();
	}

	public void syncUpdates() {
		this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
		this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos),
				3);
		this.world.scheduleBlockUpdate(this.pos, this.getBlockType(), 0, 0);
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			stackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", stackHandler.serializeNBT());
		return compound;
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) stackHandler;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), 0, this.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.setPos(pkt.getPos());
		this.readFromNBT(pkt.getNbtCompound());
	}

	/* TODO: Scan and construct altar from blocks around. */

	public int getSize() {
		return this.size;
	}

	@Override
	public void update() {
		if (--this.cooldown <= 0) {
			this.cooldown = this.maxCooldown;
			ItemStack stack = this.stackHandler.getStackInSlot(0);
			Item item = stack.getItem();
			if (item != null && behaviorMap.containsKey(item)) {
				IAltarBehavior behave = behaviorMap.get(item);
				if (behave.canTrigger(this)) {
					int damage = behave.trigger(this);
					if (damage > 0) {
						stack.setItemDamage(stack.getItemDamage() + damage);
						if (stack.getItemDamage() >= stack.getMaxDamage()) {
							stack.shrink(1);
						}
						if (stack.getCount() <= 0) {
							this.stackHandler.setStackInSlot(0, ItemStack.EMPTY);
							this.world.playSound((EntityPlayer) null, this.pos, SoundEvents.ENTITY_ITEM_BREAK,
									SoundCategory.BLOCKS, 1.0f, 1.0f);
						}
					}
				}
			}
		}
	}

}
