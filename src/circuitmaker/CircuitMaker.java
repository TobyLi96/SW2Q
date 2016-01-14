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
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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

    ImageView tempIV = new ImageView();
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Circuit Maker");
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 810, 660);
        root.setPadding(new Insets(5));
        scene.setFill(Color.WHITE);
        
        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        
        HBox title = new HBox();
        title.setPrefSize(800, 100);
        title.setStyle("-fx-background-color: #99ccff;");
        title.setAlignment(Pos.CENTER);
        VBox components = new VBox();
        components.setPrefSize(200, 400);
        components.setStyle("-fx-background-color: #00ff80;");
        
        components.setSpacing(10);
        components.setAlignment(Pos.CENTER);
        
        final ImageView battery = new ImageView();
        battery.setFitHeight(40);
        battery.setFitWidth(40); 
        battery.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/Cell.png")));
        setupGestureSource(battery, TransferMode.COPY);
        components.getChildren().add(battery);
        
        final ImageView cSwitch = new ImageView();
        cSwitch.setFitHeight(40);
        cSwitch.setFitWidth(40); 
        cSwitch.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/ClosedSwitch.png")));
        setupGestureSource(cSwitch, TransferMode.COPY);
        components.getChildren().add(cSwitch);
        
        final ImageView oSwitch = new ImageView();
        oSwitch.setFitHeight(40);
        oSwitch.setFitWidth(40); 
        oSwitch.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/OpenSwitch.png")));
        setupGestureSource(oSwitch, TransferMode.COPY);
        components.getChildren().add(oSwitch);
        
        final ImageView led = new ImageView();
        led.setFitHeight(40);
        led.setFitWidth(40); 
        led.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/LED.png")));
        setupGestureSource(led, TransferMode.COPY);
        components.getChildren().add(led);
        
        final ImageView resistor = new ImageView();
        resistor.setFitHeight(40);
        resistor.setFitWidth(40); 
        resistor.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/Resistor.png")));
        setupGestureSource(resistor, TransferMode.COPY);
        components.getChildren().add(resistor);
        
        final ImageView ammeter = new ImageView();
        ammeter.setFitHeight(40);
        ammeter.setFitWidth(40); 
        ammeter.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/Ammeter.png")));
        setupGestureSource(ammeter, TransferMode.COPY);
        components.getChildren().add(ammeter);
        
        final ImageView voltmeter = new ImageView();
        voltmeter.setFitHeight(40);
        voltmeter.setFitWidth(40); 
        voltmeter.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/Voltmeter.png")));
        setupGestureSource(voltmeter, TransferMode.COPY);
        components.getChildren().add(voltmeter);
        
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
                setupGestureTarget(cell);
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
    
    void setupDelete(ImageView currentImage){
        currentImage.setOnMouseClicked(new EventHandler <MouseEvent>() {

           @Override
           public void handle(MouseEvent event) {
               if (event.getButton() == MouseButton.SECONDARY) {
                    currentImage.setImage(null);
                }
               
            
           }
       });
    }

    void setupGestureSource(final ImageView source, TransferMode mode) {
         
        source.setOnDragDetected(new EventHandler <MouseEvent>() {

           @Override
           public void handle(MouseEvent event) {

               /* allow any transfer mode */
               Dragboard db = source.startDragAndDrop(mode);
                
               /* put a image on dragboard */
               ClipboardContent content = new ClipboardContent();
                
               Image sourceImage = source.getImage();
               content.putImage(sourceImage);
               db.setContent(content);
               if (mode == TransferMode.MOVE) {
                   tempIV = source;
               }
               event.consume();
           }
       });
        
        source.setOnMouseEntered(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                source.setCursor(Cursor.HAND);
            }
        });
        
        
    }
    
    void setupGestureTarget(final HBox target) {
        
        target.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasImage()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasImage()) {
                    target.setStyle("-fx-background-color: #ffff4d;");
                }
                
                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
//                target.setImage(new Image("ress/logo-ballon.jpg"));
                target.setStyle("-fx-background-color: #ffffff;");
                event.consume();
            }
        });
        
        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasImage()) {
                    target.getChildren().clear();
                    ImageView iv = new ImageView();
                    target.getChildren().add(iv);
                    iv.setImage(db.getImage());
                    iv.setFitHeight(40);
                    iv.setFitWidth(40);
                    tempIV.setImage(null);
                    setupGestureSource(iv, TransferMode.MOVE);
                    setupDelete(iv);
       
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
