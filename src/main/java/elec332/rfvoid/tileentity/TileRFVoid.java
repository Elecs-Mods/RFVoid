package elec332.rfvoid.tileentity;

import cofh.api.energy.IEnergyReceiver;
import com.google.common.collect.Lists;
import elec332.core.baseclasses.tileentity.TileBase;
import elec332.core.compat.handlers.WailaCompatHandler;
import elec332.core.player.PlayerHelper;
import elec332.rfvoid.RFVoid;
import elec332.rfvoid.world.RFVoidSaveData;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Elec332 on 12-9-2015.
 */
public class TileRFVoid extends TileBase implements IEnergyReceiver, WailaCompatHandler.IWailaInfoTile{

    @Override
    public boolean onBlockActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!worldObj.isRemote && RFVoidSaveData.getSaveData().isValid()){
            PlayerHelper.sendMessageToPlayer(player, RFVoid.instance.getMessage());
        }
        return super.onBlockActivated(player, side, hitX, hitY, hitZ);
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return RFVoidSaveData.getSaveData().receiveEnergy(i, !b);
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return RFVoidSaveData.getSaveData().getStoredRFInt();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return RFVoidSaveData.getSaveData().getMaxEnergyStoredInt();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return true;
    }

    @Override //No data for you
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return Lists.newArrayList();
    }
}
