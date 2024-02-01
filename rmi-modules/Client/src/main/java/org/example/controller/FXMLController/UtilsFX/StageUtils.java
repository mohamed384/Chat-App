package org.example.controller.FXMLController.UtilsFX;

import javafx.application.Platform;
import javafx.stage.Stage;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;

public class StageUtils {
    static  Stage mainStage = null;
    public static void setMainStage( Stage stage){
         mainStage = stage;

    }
    public static Stage getMainStage(){
        return  mainStage ;
    }
}

