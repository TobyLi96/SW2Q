package circuitmaker.model;
 
import java.util.List;
 
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
 
 @XmlRootElement(name = "components")
public class ComponentListWrapper {
 
    private List<Component> components;
 
    @XmlElement(name = "components")
    public List<Component> getComponents() {
        return components;
    }
 
    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
