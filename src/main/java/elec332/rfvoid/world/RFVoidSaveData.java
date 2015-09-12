package elec332.rfvoid.world;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import elec332.core.server.ServerHelper;
import elec332.core.util.EventHelper;
import elec332.core.world.WorldHelper;
import elec332.rfvoid.RFVoid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Created by Elec332 on 12-9-2015.
 */
public class RFVoidSaveData {

    private static RFVoidSaveData instance;

    public static RFVoidSaveData getSaveData(){
        if (instance == null){
            instance = new RFVoidSaveData();
            instance.readFromNBT(getRFVoidData());
            instance.usedTime = System.currentTimeMillis();
        }
        return instance;
    }

    private static final int reliabilityCheck = 5000;

    private double totalStoredRF;

    private int lastRFTick;
    private long usedTime;
    private int countingRFTick;
    private boolean valid;

    public int getStoredRFInt(){
        return 0;
    }

    public int getMaxEnergyStoredInt(){
        return Math.min(RFVoid.config.maxRFTick, (int)(getTotalNeededRF()-totalStoredRF));
    }

    public int getLastRecordedRFTick(){
        return lastRFTick;
    }

    public int receiveEnergy(int maxEnergy, boolean extract){
        int maxCanReceive = getMaxEnergyStoredInt();
        if (maxCanReceive >= maxEnergy){
            if (extract) {
                totalStoredRF += maxEnergy;
                addTickEnergy(maxEnergy);
            }
            return maxEnergy;
        }
        if (extract) {
            totalStoredRF += maxCanReceive;
            addTickEnergy(maxCanReceive);
        }
        return maxCanReceive;
    }

    private void addTickEnergy(int energy){
        if (System.currentTimeMillis() - usedTime > reliabilityCheck){
            lastRFTick = countingRFTick / (reliabilityCheck / 20);
            usedTime = System.currentTimeMillis();
            countingRFTick = 0;
            if (!valid)
                valid = true;
        }
        countingRFTick += energy;
    }

    public boolean isValid() {
        return valid;
    }

    public double getStoredRF(){
        return totalStoredRF;
    }

    private double getTotalNeededRF(){
        return RFVoid.config.neededRF;
    }

    private void writeToNBT(NBTTagCompound tagCompound){
        tagCompound.setDouble("power", totalStoredRF);
    }

    private void readFromNBT(NBTTagCompound tagCompound){
        totalStoredRF = tagCompound.getDouble("power");
    }

    public final void save(){
        NBTTagCompound save = new NBTTagCompound();
        writeToNBT(save);
        ServerHelper.instance.getGlobalData().addToTag(save, "RFVoid");
    }

    private static NBTTagCompound getRFVoidData(){
        return ServerHelper.instance.getGlobalData().toNBT().getCompoundTag("RFVoid");
    }

    public static class EventHandler{

        @SubscribeEvent
        public void onWorldUnload(WorldEvent.Unload event){
            if (!event.world.isRemote && WorldHelper.getDimID(event.world) == 0){
                getSaveData().save();
                instance = null;
            }
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void onWorldSave(WorldEvent.Save event){
            if (!event.world.isRemote && WorldHelper.getDimID(event.world) == 0){
                getSaveData().save();
            }
        }

    }

    static {
        EventHelper.registerHandlerForge(new EventHandler());
    }
}
