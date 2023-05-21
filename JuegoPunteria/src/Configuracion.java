import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Configuracion extends JPanel implements ActionListener {
	
	JLabel titulo;
	JLabel descripcion;
	JLabel rondas;
	static int totalRondas = 10;
	JButton restar, sumar;
	JButton volver;
	
	Juego pJuego;

	GridBagConstraints c = new GridBagConstraints();
	
	public Configuracion() {
		titulo = new JLabel("¿CÓMO JUGAR?");
		descripcion = new JLabel("<html>¡Gracias por descargar el <font color='green'><b>JUEGO DE LA PUNTERÍA</b></font>!. <br>"
				+ "Este juego es muy simple, consiste en presionar la <font color='red'>casilla roja</font> que aparece aleatoriamente <br>"
				+ "en la pantalla en el menor tiempo posible, lo que te ayudará a mejorar la precisión, aumentar <br>"
				+ "la velocidad de reacción y estimular tus capacidades cognitivas. Además, puedes personalizar <br>"
				+ "el número de rondas según tus preferencias. <br>"
				+ "Ahora que ya sabes cómo jugar, !Disfruta de esta emocionante experiencia!<html>");
		
		rondas = new JLabel(totalRondas + " RONDAS");
		restar = new JButton("-");
		restar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (totalRondas != 10)
				totalRondas = totalRondas - 10;
				rondas.setText(totalRondas + " RONDAS");
			}
		});
		sumar = new JButton("+");
		sumar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				totalRondas = totalRondas + 10;
				rondas.setText(totalRondas + " RONDAS");
			}
		});
		volver = new JButton("ACEPTAR");
		volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pJuego = new Juego();
				Principal.scrollPaneles.setViewportView(pJuego);
			}
		});
		
		//GridBagLayout
		setBackground(Color.BLACK); 
		setLayout(new GridBagLayout()); 
    	c.weightx = 1;
    	c.weighty = 1;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(30,0,0,0);  //Margin
		c.gridwidth = 1; //Cuantas columnas va a ocupar  
    	c.gridheight = 1; //Cuantas filas va a ocupar  
    	
    	titulo.setFont(new Font("Arial", Font.BOLD, 32)); //Tipografía
    	titulo.setHorizontalAlignment(SwingConstants.CENTER);
    	titulo.setForeground(Color.GREEN);
    	addGB(titulo, 0, 0);
    	
    	c.insets = new Insets(0,0,50,0);  //Margin
    	descripcion.setFont(new Font("Arial", Font.PLAIN, 18)); //Tipografía
    	descripcion.setHorizontalAlignment(SwingConstants.CENTER);
    	descripcion.setForeground(Color.WHITE);
    	addGB(descripcion, 0, 1);
		
    	c.insets = new Insets(5,200,5,550);  //Margin
    	restar.setFont(new Font("Arial", Font.BOLD, 42)); //Tipografía
    	restar.setBackground(Color.WHITE);
    	restar.setForeground(Color.BLACK);
    	addGB(restar, 0, 2);
    	
    	c.insets = new Insets(0,0,0,0);  //Margin
    	rondas.setHorizontalAlignment(SwingConstants.CENTER);
    	rondas.setFont(new Font("Arial", Font.BOLD, 20)); //Tipografía
    	rondas.setForeground(Color.WHITE);
    	addGB(rondas, 0, 2);
    	
    	c.insets = new Insets(5,550,5,200);  //Margin
    	sumar.setFont(new Font("Arial", Font.BOLD, 42)); //Tipografía
    	sumar.setBackground(Color.WHITE);
    	sumar.setForeground(Color.BLACK);
    	addGB(sumar, 0, 2);
    	
    	c.insets = new Insets(100,2,2,2);  //Margin
    	volver.setFont(new Font("Arial", Font.BOLD, 42)); //Tipografía
    	volver.setBackground(Color.GREEN);
    	volver.setForeground(Color.BLACK);
    	addGB(volver, 0, 3);
		
		
	}
	
	void addGB(Component component, int x, int y) {
		c.gridx = x; //posicion columna
		c.gridy = y; //posicion fila	
		add(component, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
