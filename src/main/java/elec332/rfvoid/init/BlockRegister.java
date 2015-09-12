package elec332.rfvoid.init;

import elec332.core.baseclasses.tileentity.BlockTileBase;
import elec332.core.main.ElecCTab;
import elec332.rfvoid.RFVoid;
import elec332.rfvoid.tileentity.TileRFVoid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by Elec332 on 24-2-2015.
 */
public class BlockRegister {
    public static final BlockRegister instance = new BlockRegister();

    public static Block blockRFVoid;

    public void init(){
        blockRFVoid = new BlockTileBase(Material.rock, TileRFVoid.class, "RFVoid", RFVoid.ModID).registerTile().register().setCreativeTab(ElecCTab.ElecTab);
    }
}
