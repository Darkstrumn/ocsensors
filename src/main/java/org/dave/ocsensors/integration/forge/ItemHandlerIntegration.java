package org.dave.ocsensors.integration.forge;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.dave.ocsensors.integration.*;

import javax.annotation.Nullable;

@Integrate(name = "items")
public class ItemHandlerIntegration extends AbstractCapabilityIntegration {
    @Override
    protected Capability getCompatibleCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public void init() {
        PrefixRegistry.addSupportedPrefix(ItemHandlerIntegration.class, "items");
    }

    @Override
    public void addScanData(ScanDataList data, TileEntity entity, @Nullable EnumFacing side) {
        IItemHandler itemHandler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);

        ItemStack[] allStacks = new ItemStack[itemHandler.getSlots()];
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            allStacks[slot] = itemHandler.getStackInSlot(slot);
        }
        data.add("items", allStacks);
    }
}
