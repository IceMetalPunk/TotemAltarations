package com.icemetalpunk.totemaltarations.blocks;

import com.icemetalpunk.totemaltarations.tile.TileEntityTotemAltar;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockTotemAltar extends TABlock implements ITileEntityProvider {

	public BlockTotemAltar(String name) {
		super(name);
		Block p;
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTotemAltar();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (worldIn.isRemote) {
			return true;
		}

		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && te instanceof TileEntityTotemAltar) {
			TileEntityTotemAltar altar = (TileEntityTotemAltar) te;
			if (altar.canInteractWith(playerIn)
					&& altar.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
				IItemHandler handler = altar.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
				ItemStack existing = handler.extractItem(0, 1, false);
				if (existing != null && existing != ItemStack.EMPTY) {
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() - 1, pos.getZ(), existing));
				} else {
					ItemStack inHand = playerIn.getHeldItem(hand);
					ItemStack after = handler.insertItem(0, inHand, false);
					playerIn.setHeldItem(hand, after);
				}
			}
		}
		return true;
	}

}
