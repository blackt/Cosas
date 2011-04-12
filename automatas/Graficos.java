import java.awt.*;

import java.util.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;
public class Graficos extends JFrame {

	Analizador automata;
	JTextArea Escribe;
	JButton crear;
	JButton probar;
	JButton cargarArchivo;
	JTextField cadena;
	JFrame a=new JFrame();	
	public Graficos(){
		super("crea Automatas");
		setLayout(new BorderLayout(5,5));
		Escribe=new JTextArea(30, 30);
		crear=new JButton("crear Automata");
		probar=new JButton("Probar cadena");
		cadena=new JTextField(25);
		cargarArchivo=new JButton("cargar archivo");
		
		add(Escribe,BorderLayout.NORTH);
		add(crear,BorderLayout.SOUTH);
		add(cadena,BorderLayout.EAST);
		add(probar,BorderLayout.WEST);
		add(cargarArchivo,BorderLayout.CENTER);
		crear.addActionListener(
		
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(Escribe.getText().isEmpty())
							JOptionPane.showMessageDialog(null,"Se necesitan datos");
						else
							automata=new Analizador(Escribe.getText());
						
					a=new JFrame();
				        a.setLayout(new  BorderLayout());	

				        a.add(new JLabel( new ImageIcon("out.gif")));
				        a.pack();
				     
				        a.setVisible(true);
				        a.setSize(300, 300);
				       
					}
				}
		);
		
	
		probar.addActionListener(
				
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(Escribe.getText().isEmpty())
							JOptionPane.showMessageDialog(null,"Se necesitan datos");
						else
							automata=new Analizador(Escribe.getText());

						automata.Prueba(cadena.getText());
						
					}
				}
		);
		
		cargarArchivo.addActionListener(
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String arc=Archivo();
					if(!arc.equals(""))
						Escribe.setText(arc);
					
				}
			}		
		);
		
		setVisible(true);
		setSize(600,600);	
	}
	
	public static void main(String[] args){
		Graficos graf=new Graficos();
		graf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static String Archivo(){
		Scanner lector;
		String cadena="";
		int estado;
		JFileChooser elegir=new JFileChooser();
		File arch;
		
		elegir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        estado= elegir.showOpenDialog(null);
        
	   if(estado==JFileChooser.CANCEL_OPTION){
	        JOptionPane.showMessageDialog(null,"se necesita una ruta");
	        return "";
	       }
	       
	    arch=elegir.getSelectedFile();
		
		try {
			lector=new Scanner(arch);
			while(lector.hasNext()) 
				cadena+=lector.nextLine()+"\n";
			
		} catch (FileNotFoundException e) {
			
				JOptionPane.showMessageDialog(null, "no hay archivo");
		}
		return cadena;
	} 
	
}



