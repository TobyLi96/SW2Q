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
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author litao_000
 */
public class CircuitMaker extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Circuit Maker");
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 810, 660);
        root.setPadding(new Insets(5));
        scene.setFill(Color.WHITE);
        
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        
        HBox title = new HBox();
        title.setPrefSize(800, 100);
        title.setStyle("-fx-background-color: #99ccff;");
        title.setAlignment(Pos.CENTER);
        VBox components = new VBox();
        components.setPrefSize(200, 400);
        components.setStyle("-fx-background-color: #00ff80;");
        VBox data = new VBox();
        data.setPrefSize(200, 400);
        data.setStyle("-fx-background-color: #00ff80;");
        HBox projects = new HBox();
        projects.setPrefSize(800, 150);
        projects.setStyle("-fx-background-color: #99ccff;");
        
        Text titleText = new Text("Circuit Maker");
        titleText.setTextOrigin(VPos.TOP);
        titleText.setFont(Font.font(null, FontWeight.BOLD, 30));
        titleText.setFill(Color.WHITE);
        title.getChildren().add(titleText);
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HBox cell = new HBox();
                cell.setPrefSize(40, 40);
                if (i == 5 && j == 5) {
                    cell.setStyle("-fx-background-color: #ffff4d;");
                }
                grid.add(cell, i, j);
            }
        }
        
        root.setCenter(grid);
        root.setTop(title);
        root.setBottom(projects);
        root.setLeft(components);
        root.setRight(data);
                ;
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
