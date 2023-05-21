import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Principal extends JFrame {
	
	static JScrollPane scrollPaneles;
	Juego pJuego;
	
	public Principal() {
		
		//Crear objetos
		pJuego = new Juego();
		
		//Añadir objetos
		scrollPaneles = new JScrollPane();
		scrollPaneles.setSize(new Dimension(900, 900));
		scrollPaneles.setViewportView(pJuego);
		add(scrollPaneles);
		
		//Visualizar objetos
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    	setTitle("Juego de puntería");
    	setSize(900,900);
    	setLocationRelativeTo(null);
    	setVisible(true);
	}
	
	public static void main(String[] args) {
		new Principal();	
	}

}
