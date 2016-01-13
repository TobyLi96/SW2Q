/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circuitmaker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author litao_000
 */
public class CircuitMaker extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Circuit Maker");
        Group root = new Group();
        Scene scene = new Scene(root, 410,410);
        scene.setFill(Color.WHITE);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setGridLinesVisible(true);
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HBox cell = new HBox();
                cell.setPrefSize(40, 40);
                grid.add(cell, i, j);
            }
        }
        
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
