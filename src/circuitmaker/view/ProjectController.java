package circuitmaker.view;

import circuitmaker.CircuitMaker;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class ProjectController {
    private CircuitMaker circuitMaker;
    
    public void setCircuitMaker(CircuitMaker circuitMaker) {
        this.circuitMaker = circuitMaker;
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
    public void handleNew(GridPane grid, GraphicsContext gc) {
        gc.clearRect(0, 0, 400, 400);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HBox hb = (HBox)getNodeFromGridPane(grid, i, j);
                hb.getChildren().clear();
                hb.setOnDragDetected(null);
                hb.setOnMouseEntered(null);
                hb.setOnMouseClicked(null);
            }
        }
        circuitMaker.getComponentData().clear();
        circuitMaker.setComponentFilePath(null);
    }
    
    public void handleOpen(GridPane grid, GraphicsContext gc) {
        FileChooser fileChooser = new FileChooser();
 
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
 
        // Show save file dialog
        File file = fileChooser.showOpenDialog(circuitMaker.getPrimaryStage());
 
        if (file != null) {
            gc.clearRect(0, 0, 400, 400);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    HBox hb = (HBox)getNodeFromGridPane(grid, i, j);
                    hb.getChildren().clear();
                    hb.setOnDragDetected(null);
                    hb.setOnMouseEntered(null);
                    hb.setOnMouseClicked(null);
                }
            }
            circuitMaker.getComponentData().clear();
            circuitMaker.loadComponentDataFromFile(file);
        }
    }
 
    public void handleSave() {
        File personFile = circuitMaker.getComponentFilePath();
        if (personFile != null) {
            circuitMaker.saveComponentDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }
 
    public void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
 
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
 
        // Show save file dialog
        File file = fileChooser.showSaveDialog(circuitMaker.getPrimaryStage());
 
        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            circuitMaker.saveComponentDataToFile(file);
        }
    }
}
