import javax.swing.*;
import java.awt.*;

public class Update_info {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setSize(300, 150);
        jf.setTitle("Update information");
        Container container = jf.getContentPane();
        JLabel jl = new JLabel("");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(jl);
        jf.setLocation(500, 500);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
        jf.setAlwaysOnTop(true);
    }
}
