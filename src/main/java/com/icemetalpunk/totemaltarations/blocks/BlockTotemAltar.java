package com.icemetalpunk.totemaltarations.blocks;

import java.util.Random;

import com.icemetalpunk.totemaltarations.tile.TileEntityTotemAltar;
import com.icemetalpunk.totemessentials.items.essences.ItemEssenceBase;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BlockTotemAltar extends TABlock implements ITileEntityProvider {

	public BlockTotemAltar(String name) {
		super(name);
		this.setSoundType(SoundType.STONE).setHardness(1.5f);
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
				ItemStack existing = handler.extractItem(0, 1, true);
				ItemStack inHand = playerIn.getHeldItem(hand);
				if (existing != null && !existing.isEmpty() && !(inHand.getItem() instanceof ItemEssenceBase)) {
					handler.extractItem(0, 1, false);
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), existing));
				} else {
					ItemStack after = handler.insertItem(0, inHand, false);
					playerIn.setHeldItem(hand, after);
				}
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityTotemAltar) {
			ItemStackHandler handler = (ItemStackHandler) te
					.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			ItemStack stack;
			for (int i = 0; i < handler.getSlots(); i++) {
				stack = handler.getStackInSlot(i);
				if (!stack.isEmpty()) {
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
				}
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		// TODO: Make particles land inside block and not fall through.
		for (int i = 0; i < 3; ++i) {
			int j = rand.nextInt(2) * 2 - 1;
			int k = rand.nextInt(2) * 2 - 1;
			double x = (double) pos.getX() + 0.5D + 0.25D * (double) j;
			double y = (double) ((float) pos.getY() + rand.nextFloat() * 0.625);
			double z = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
			worldIn.spawnParticle(EnumParticleTypes.TOTEM, x, y, z, 0, 0, 0);
		}
	}
}
