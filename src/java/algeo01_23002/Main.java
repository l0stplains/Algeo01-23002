package algeo01_23002;


import algeo01_23002.cli.MainDriver;
import algeo01_23002.gui.MainApp;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        for(String x : args){
            if(Objects.equals(x, "-cli")){
                MainDriver.main(args);
            }
        }
        MainApp.show();
    }

}
