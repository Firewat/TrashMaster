import javax.swing.*;
import java.awt.*;

public class ResourceLoader {

    static ResourceLoader rl = new ResourceLoader();

    public static Image getImage(String fileName){
        return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource("res/"+ fileName));

    }

}
