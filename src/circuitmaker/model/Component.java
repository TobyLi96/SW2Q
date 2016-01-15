package circuitmaker.model;

import java.time.LocalDate;
 
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
 

public class Component {
 
    private final StringProperty component;
   
    private final IntegerProperty xcoor;
    private final IntegerProperty ycoor;
   
    public Component() {
        this(null, 0, 0);
    }
 
    public Component(String component, int xcoor, int ycoor) {
        this.component = new SimpleStringProperty(component);
        this.xcoor = new SimpleIntegerProperty(xcoor);
        this.ycoor = new SimpleIntegerProperty(ycoor);
    }
 
    public String getComponent() {
        return component.get();
    }
 
    public void setComponent(String component) {
        this.component.set(component);
    }
 
    public StringProperty componentProperty() {
        return component;
    }
 
    public int getXCoor() {
        return xcoor.get();
    }
 
    public void setXCoor(int xcoor) {
        this.xcoor.set(xcoor);
    }
 
    public IntegerProperty XCoorProperty() {
        return xcoor;
    }
   
    public int getYCoor() {
        return ycoor.get();
    }
 
    public void setYCoor(int ycoor) {
        this.ycoor.set(ycoor);
    }
 
    public IntegerProperty YCoorProperty() {
        return ycoor;
    }
   
   
 
   
 
}