package elec332.rfvoid;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import elec332.core.helper.FileHelper;
import elec332.core.helper.MCModInfo;
import elec332.core.json.JsonHandler;
import elec332.core.modBaseUtils.ModInfo;
import elec332.rfvoid.init.BlockRegister;
import elec332.rfvoid.init.CommandRegister;
import elec332.rfvoid.json.RFVoidConfig;
import elec332.rfvoid.proxies.CommonProxy;
import elec332.rfvoid.world.RFVoidSaveData;

import java.io.File;
import java.io.IOException;

/**
 * Created by Elec332 on 24-2-2015.
 */
@Mod(modid = RFVoid.ModID, name = RFVoid.ModName, dependencies = ModInfo.DEPENDENCIES+"@[#ELECCORE_VER#,)",
        acceptedMinecraftVersions = ModInfo.ACCEPTEDMCVERSIONS, useMetadata = true, canBeDeactivated = true)
public class RFVoid {

    public static final String ModName = "RFVoid";
    public static final String ModID = "RFVoid";

    @SidedProxy(clientSide = "elec332.rfvoid.proxies.ClientProxy", serverSide = "elec332.rfvoid.proxies.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(ModID)
    public static RFVoid instance;
    public static File configFolder;
    public static File configFile;
    public static RFVoidConfig config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException{
        configFolder = FileHelper.getElecConfigFolder(event);
        configFile = new File(configFolder, "RFVoid.json");
        if (!configFile.exists()){
            JsonHandler.createFile(configFile);
            JsonHandler.toFile(configFile, (JsonObject) JsonHandler.toJsonElement(new RFVoidConfig(), RFVoidConfig.class));
        }
        config = JsonHandler.getGson().fromJson(JsonHandler.getMainFileObject(configFile), RFVoidConfig.class);
        //setting up mod stuff

        MCModInfo.CreateMCModInfo(event, "Created by Elec332",
                "RFVoid",
                "website link", "logo",
                new String[]{"Elec332"});
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        BlockRegister.instance.init();
        //register items/blocks

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

        //Mod compat stuff

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        CommandRegister.instance.init(event);
    }

    public final String getMessage(){
        return config.firstLine+(RFVoidSaveData.getSaveData().getStoredRF()/config.divideOne)+config.secondLine+(RFVoidSaveData.getSaveData().getLastRecordedRFTick()/config.divideTwo)+config.thirdLine;
    }

}
