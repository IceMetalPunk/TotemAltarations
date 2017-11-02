package com.icemetalpunk.totemaltarations.behaviors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.icemetalpunk.totemaltarations.tile.TileEntityTotemAltar;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class AltarBehaviorReaper implements IAltarBehavior {

	protected final Method dropLoot = ReflectionHelper.findMethod(EntityLiving.class, "dropLoot", "func_184610_a",
			boolean.class, int.class, DamageSource.class);
	protected int damagePerReap = 5;

	@Override
	public boolean canTrigger(TileEntityTotemAltar altar) {

		if (altar.getWorld().isRemote) {
			return false;
		}

		World world = altar.getWorld();
		AxisAlignedBB bb = new AxisAlignedBB(altar.getPos());
		int size = altar.getSize();
		List<EntityLiving> mobs = world.getEntitiesWithinAABB(EntityLiving.class, bb.expand(size, size, size));
		return (mobs.size() > 0);
	}

	@Override
	public int trigger(TileEntityTotemAltar altar) {
		World world = altar.getWorld();

		if (world.isRemote) {
			return 0;
		}

		AxisAlignedBB bb = new AxisAlignedBB(altar.getPos());
		int size = altar.getSize();
		int count = 0;
		List<EntityLiving> mobs = world.getEntitiesWithinAABB(EntityLiving.class, bb.expand(size, size, size));
		for (EntityLiving ent : mobs) {
			ent.setEntityInvulnerable(true); // So when totem breaks, fighting
												// goes down.
			try {
				dropLoot.invoke(ent, false, 0, DamageSource.MAGIC);
				++count;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// manager.getLootTableFromLocation(resourcelocation);
				e.printStackTrace();
			}
		}

		return count * this.damagePerReap;
	}

}
