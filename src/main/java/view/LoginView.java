package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    public LoginView(int p){ // if: 0 -> staff else client
        setTitle("Authentication");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField textField;

        if(p==0){
            textField = new JTextField("Badge number");
        } else {
            textField = new JTextField("");
        }

    }
}
