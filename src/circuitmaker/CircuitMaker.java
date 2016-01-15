/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circuitmaker;

import circuitmaker.view.ProjectController;
import circuitmaker.model.ComponentListWrapper;
import circuitmaker.model.Component;
import java.io.File;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 *
 * @author litao_000
 */
public class CircuitMaker extends Application {
    HBox tempIV = new HBox();
    double x1 = 0,x2 = 0,y1 = 0,y2 = 0;
    GraphicsContext gc;
    private ObservableList<Component> componentData = FXCollections.observableArrayList();
    private ObservableList<Component> wireData = FXCollections.observableArrayList();
    Stage primaryStage;
    GridPane grid;
    
    public ObservableList<Component> getComponentData() {
        return componentData;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Circuit Maker");
        
        ProjectController pc = new ProjectController();
        pc.setCircuitMaker(this);
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 810, 560);
        root.setPadding(new Insets(5));
        scene.setFill(Color.WHITE);
        
        StackPane stack = new StackPane();
        stack.setPrefSize(400, 400);
        grid = new GridPane();
        Canvas wirePane = new Canvas();
        gc = wirePane.getGraphicsContext2D();
        wirePane.setHeight(400);
        wirePane.setWidth(400);
        grid.setStyle("-fx-background-color: transparent;");
        stack.getChildren().addAll(wirePane, grid);
        
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
        setupGestureSource(battery);
        components.getChildren().add(battery);
        
        final ImageView oSwitch = new ImageView();
        oSwitch.setFitHeight(40);
        oSwitch.setFitWidth(40); 
        oSwitch.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/OpenSwitch.png")));
        setupGestureSource(oSwitch);
        components.getChildren().add(oSwitch);
        
        final ImageView led = new ImageView();
        led.setFitHeight(40);
        led.setFitWidth(40); 
        led.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/LED.png")));
        setupGestureSource(led);
        components.getChildren().add(led);
        
        final ImageView resistor = new ImageView();
        resistor.setFitHeight(40);
        resistor.setFitWidth(40); 
        resistor.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/Resistor.png")));
        setupGestureSource(resistor);
        components.getChildren().add(resistor);
        
        final ImageView ammeter = new ImageView();
        ammeter.setFitHeight(40);
        ammeter.setFitWidth(40); 
        ammeter.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/Ammeter.png")));
        setupGestureSource(ammeter);
        components.getChildren().add(ammeter);
        
        final ImageView voltmeter = new ImageView();
        voltmeter.setFitHeight(40);
        voltmeter.setFitWidth(40); 
        voltmeter.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/Voltmeter.png")));
        setupGestureSource(voltmeter);
        components.getChildren().add(voltmeter);
        
        Button addWireBtn = new Button();
        addWireBtn.setText("ADD WIRE");
        addWireBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stack.getChildren().clear();
                stack.getChildren().addAll(grid, wirePane);
                setUpDrawWire(wirePane, stack);
                event.consume();
            }
        }); 
        
        components.getChildren().add(addWireBtn);
        
        Button deleteWireBtn = new Button();
        deleteWireBtn.setText("DELETE WIRE");
        deleteWireBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gc.clearRect(0, 0, 400, 400);
                wireData.clear();
            }
        }); 
        components.getChildren().add(deleteWireBtn);
        
        VBox data = new VBox();
        data.setPrefSize(200, 400);
        data.setStyle("-fx-background-color: #00ff80;");
        
        HBox projects = new HBox();
        projects.setPrefSize(800, 50);
        projects.setStyle("-fx-background-color: #99ccff;");
        projects.setSpacing(10);
        projects.setAlignment(Pos.CENTER);
        
        Button newProjBtn = new Button();
        newProjBtn.setText("New Project");
        newProjBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pc.handleNew(grid, gc);
            }
        }); 
        projects.getChildren().add(newProjBtn);
        
        Button openProjBtn = new Button();
        openProjBtn.setText("Open Project");
        openProjBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pc.handleOpen(grid, gc);
            }
        }); 
        projects.getChildren().add(openProjBtn);
        
        Button saveProjBtn = new Button();
        saveProjBtn.setText("Save Project");
        saveProjBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pc.handleSave();
            }
        }); 
        projects.getChildren().add(saveProjBtn);
        
        Button saveasProjBtn = new Button();
        saveasProjBtn.setText("Save Project As");
        saveasProjBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pc.handleSaveAs();
            }
        }); 
        projects.getChildren().add(saveasProjBtn);
        
        Button deleteProjBtn = new Button();
        deleteProjBtn.setText("Delete Project");
        deleteProjBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pc.handleNew(grid, gc);
            }
        }); 
        projects.getChildren().add(deleteProjBtn);
        
        Text titleText = new Text("Circuit Maker");
        titleText.setTextOrigin(VPos.TOP);
        titleText.setFont(Font.font(null, FontWeight.BOLD, 30));
        titleText.setFill(Color.WHITE);
        title.getChildren().add(titleText);
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HBox cell = new HBox();
                cell.setPrefSize(40, 40);
                cell.setStyle("-fx-background-color: transparent;");
                setupGestureTarget(cell);
                grid.add(cell, i, j);
            }
        }
        
        root.setCenter(stack);
        root.setTop(title);
        root.setBottom(projects);
        root.setLeft(components);
        root.setRight(data);
                ;
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        pc.handleNew(grid, gc);
    }
    
    boolean imageEqual(Image image1, Image image2) {
        PixelReader pr1 = image1.getPixelReader();
        PixelReader pr2 = image2.getPixelReader();
        boolean isEqual = true;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (pr1.getArgb(i, j) != pr2.getArgb(i, j)) {
                    isEqual = false;
                }
            }
        }
        return isEqual;
    }
    
    void setupComponent(HBox currentImage){
        currentImage.setOnMouseClicked(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    currentImage.getChildren().clear();
                    currentImage.setOnDragDetected(null);
                    currentImage.setOnMouseEntered(null);
                    currentImage.setOnMouseClicked(null);
                }
                else {
                    ImageView switchImage = (ImageView)currentImage.getChildren().get(0);
                    if (imageEqual(switchImage.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/ClosedSwitch.png")))) {
                        switchImage.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/OpenSwitch.png")));
                        currentImage.getChildren().clear();
                        currentImage.getChildren().add(switchImage);
                    }
                    else if (imageEqual(switchImage.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/OpenSwitch.png")))) {
                        switchImage.setImage(new Image(CircuitMaker.class.getResourceAsStream("rsrc/ClosedSwitch.png")));
                        currentImage.getChildren().clear();
                        currentImage.getChildren().add(switchImage);
                    }
               }
           }
       });
    }
    
    void drawWire() {
        wireData.add(new Component("wire", (int)x1, (int)y1));
        wireData.add(new Component("wire", (int)x2, (int)y2));
        
        if (x2 >= (x1 - 20.0) && x2 <= (x1 + 20.0)){
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(x1, y1, x2, y2);
        }
        
        if (y2 >= (y1 - 20.0) && y2 <= (y1 + 20.0)){
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(x1, y1, x2, y2);
        }
                
        if (x2 > (x1 + 20.0) && y2 < (y1 - 20.0)){
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(x1, y1, x1, y2);
            gc.strokeLine(x1, y2, x2, y2);
        }

        if (x2 > (x1 + 20.0) && y2 > (y1 + 20.0)){
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(x1, y1, x2, y1);
            gc.strokeLine(x2, y1, x2, y2);
        }

        if (x2 < (x1 - 20.0) && y2 > (y1 + 20.0)){
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(x1, y1, x1, y2);
            gc.strokeLine(x1, y2, x2, y2);
        }

        if (x2 < (x1 - 20.0) && y2 < (y1 - 20.0)){
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(x1, y1, x2, y1);
            gc.strokeLine(x2, y1, x2, y2);
        }
    }
    
    void setUpDrawWire(Canvas wirePane, StackPane stack) {
        wirePane.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent event) {
                x1 = event.getX();
                y1 = event.getY();
                x1 = ((int)x1 / 40) * 40 + 20;
                y1 = ((int)y1 / 40) * 40 + 20;
                
                //y1 = ((Ellipse) event.getTarget()).getCenterY();
                System.out.println("the first postition" + x1 + y1);
                event.consume();           
            }
        });
        wirePane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent event) {
                x2 = event.getX();
                y2 = event.getY();
                x2 = ((int)x2 / 40) * 40 + 20;
                y2 = ((int)y2 / 40) * 40 + 20;
                System.out.println("the second postition" + x2 + y2); 
                
                drawWire();
                
                stack.getChildren().clear();
                stack.getChildren().addAll(wirePane, grid);
                wirePane.setOnMousePressed(null);
                wirePane.setOnMouseReleased(null);
                event.consume();
            }
        });
        
       
    }
    
    void setupGestureSource(final ImageView source) {
         
        source.setOnDragDetected(new EventHandler <MouseEvent>() {
           @Override
            public void handle(MouseEvent event) {
                Dragboard db = source.startDragAndDrop(TransferMode.COPY);
                
                ClipboardContent content = new ClipboardContent();
                
                Image sourceImage = source.getImage();
                content.putImage(sourceImage);
                db.setContent(content);
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
    
    void setupGestureSource(final HBox source) {
         
        source.setOnDragDetected(new EventHandler <MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                
               ClipboardContent content = new ClipboardContent();
                
               ImageView imv  = (ImageView)source.getChildren().get(0);
               content.putImage(imv.getImage());
               db.setContent(content);
               tempIV = source;
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
                System.out.println("onDragOver");
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasImage()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });
        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragEntered");
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasImage()) {
                    target.setStyle("-fx-background-color: #ffff4d;");
                }
                
                event.consume();
            }
        });
        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                target.setStyle("-fx-background-color: transparent;");
                event.consume();
            }
        });
        
        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragDropped");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasImage()) {
                    if (target.getChildren().isEmpty()) {
                        target.getChildren().clear();
                        ImageView iv = new ImageView();
                        target.getChildren().add(iv);
                        iv.setImage(db.getImage());
                        iv.setFitHeight(40);
                        iv.setFitWidth(40);
                        setupGestureSource(target);
                        setupComponent(target);
                        success = true;
                    }
                }
                if (success == true) {
                    tempIV.getChildren().clear();
                }
                else {
                    tempIV = new HBox();
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
    private void storeComponents() {
        componentData.clear();
        for (Component component: wireData) {
            componentData.add(component);
        }
        wireData.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HBox hb = (HBox)getNodeFromGridPane(grid, i, j);
                if (!hb.getChildren().isEmpty()) {
                    ImageView imv  = (ImageView)hb.getChildren().get(0);
                    if (imageEqual(imv.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/ClosedSwitch.png")))) {
                        componentData.add(new Component("ClosedSwitch", i, j));
                    }
                    else if (imageEqual(imv.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/OpenSwitch.png")))) {
                        componentData.add(new Component("OpenSwitch", i, j));
                    }
                    else if (imageEqual(imv.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/Cell.png")))) {
                        componentData.add(new Component("Cell", i, j));
                    }
                    else if (imageEqual(imv.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/LED.png")))) {
                        componentData.add(new Component("LED", i, j));
                    }
                    else if (imageEqual(imv.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/Resistor.png")))) {
                        componentData.add(new Component("Resistor", i, j));
                    }
                    else if (imageEqual(imv.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/Ammeter.png")))) {
                        componentData.add(new Component("Ammeter", i, j));
                    }
                    else if (imageEqual(imv.getImage(), new Image(CircuitMaker.class.getResourceAsStream("rsrc/Voltmeter.png")))) {
                        componentData.add(new Component("Voltmeter", i, j));
                    }
                }
            }
        }
    }
    
    private void loadComponents() {
        int wireCount = 0;
        for (Component component: componentData) {
            String type = component.getComponent();
            int XCoor = component.getXCoor();
            int YCoor = component.getYCoor();
            if (!"wire".equals(type)) {
                HBox hb = (HBox)getNodeFromGridPane(grid, XCoor, YCoor);
                Image im = new Image(CircuitMaker.class.getResourceAsStream("rsrc/" + type +".png"));
                ImageView imv = new ImageView();
                imv.setImage(im);
                imv.setFitHeight(40);
                imv.setFitWidth(40);
                hb.getChildren().add(imv);
                setupGestureSource(hb);
                setupComponent(hb);
            }
            else {
                wireCount++;
                if (wireCount == 1) {
                    x1 = (double)XCoor;
                    y1 = (double)YCoor;
                }
                else if (wireCount == 2) {
                    x2 = (double)XCoor;
                    y2 = (double)YCoor;
                    drawWire();
                    
                    wireCount = 0;
                }
            }  
        }
    }
    
    public void setComponentFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(CircuitMaker.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
 
            // Update the stage title.
            primaryStage.setTitle("CircuitMaker - " + file.getName());
        } else {
            prefs.remove("filePath");
 
            // Update the stage title.
            primaryStage.setTitle("Circuit Maker");
        }
    }
    
    public File getComponentFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(CircuitMaker.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    public void loadComponentDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ComponentListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
 
            // Reading XML from the file and unmarshalling.
            ComponentListWrapper wrapper = (ComponentListWrapper) um.unmarshal(file);
 
            componentData.clear();
            componentData.addAll(wrapper.getComponents());
            
//            for (Component component: componentData) {
//                System.out.println(component.getComponent() + " " + component.getXCoor() + " " + component.getYCoor());
//            }
            
            loadComponents();
            
            // Save the file path to the registry.
            setComponentFilePath(file);
 
        } catch (Exception e) { // catches ANY exception
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Could not load data");
//            alert.setContentText("Could not load data from file:\n" + file.getPath());
//           
//            alert.showAndWait();20
        }
    }
    
    public void saveComponentDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ComponentListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            storeComponents();
            
            // Wrapping our person data.
            ComponentListWrapper wrapper = new ComponentListWrapper();
            wrapper.setComponents(componentData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setComponentFilePath(file);
        } catch (Exception e) { // catches ANY exception
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Could not save data");
//            alert.setContentText("Could not save data to file:\n" + file.getPath());
//           
//            alert.showAndWait();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}