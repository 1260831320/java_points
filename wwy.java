import javax.swing.*;
import java.awt.*;

public class wwy {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setSize(300, 150);
        jf.setTitle("attention");
        Container container = jf.getContentPane();
        JLabel jl = new JLabel("Fuck you! WWY");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(jl);
        jf.setLocation(500, 500);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setAlwaysOnTop(true);
    }
}
