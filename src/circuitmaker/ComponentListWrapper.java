package circuitmaker;
 
import java.util.List;
 
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 *
 * @author Marco Jakob
 */
public class ComponentListWrapper {
 
    private List<Component> components;
 
    public List<Component> getComponents() {
        return components;
    }
 
    public void setComponents(List<Component> components) {
        this.components = components;
    }
}