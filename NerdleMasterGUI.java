import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class NerdleMasterGUI {
	
	public static void main(String[] args) {
        JFrame frame = new JFrame ("NerdleMaster Version 1.0 by Christopher Reis");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new NerdleMasterPanel());
        frame.pack();
        frame.setVisible(true);
	}
	
}
