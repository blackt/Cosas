import java.awt.*;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class Lienzo extends JPanel {	
	public Lienzo(){	
		 setSize(200, 200);
	       setVisible(true);		
	}	
	public void paint(Graphics g){
		ImageIcon a=new ImageIcon(new File("out.gif").getAbsolutePath());		
		  g.fillRect(0,0,200,200);
		  g.drawImage(a.getImage(),0, 0,a.getIconWidth()*2,a.getIconWidth()*2, this);
		  
	}

	public void repaint(){}
}
