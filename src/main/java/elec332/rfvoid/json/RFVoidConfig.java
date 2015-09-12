package elec332.rfvoid.json;

import java.io.Serializable;

/**
 * Created by Elec332 on 12-9-2015.
 */
public class RFVoidConfig implements Serializable {

    public double neededRF = 1E10;
    public int maxRFTick = 10000;

    public String firstLine = "You are ";
    public double divideOne = 1E8;
    public String secondLine = "% from your goal. You are currently inputting ";
    public float divideTwo = 1;
    public String thirdLine = " rf/t.";

}
