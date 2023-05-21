import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Juego extends JPanel implements ActionListener {
	
	//Declarar los objetos
	JLabel rondas;
//	int totalRondas = 10;
	int contadorRondas = 0;
	JLabel calificacion;
	JLabel tiempo;
	
	JButton [] botones = new JButton[64];
	int aleatorio = -1;
	
	JButton historial;
	JButton jugar;
	JButton configuracion;
	
	GridBagConstraints c = new GridBagConstraints();  //GridBagConstraints
	
	int min, sec, msec;
	boolean pararTiempo = false;
	private Thread timerThread;

	boolean terminar = false;
	
	int aciertos = 0;
	
	Historial pHistorial;
	Configuracion pConfiguracion;
	
	public Juego() {
		
		//GridBagLayout 
		setBackground(Color.BLACK); 
		setLayout(new GridBagLayout()); 
    	c.weightx = 1;
    	c.weighty = 1;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(0,0,0,0);  //Margin
		c.gridwidth = 1; //Cuantas columnas va a ocupar  
    	c.gridheight = 1; //Cuantas filas va a ocupar  
    	
		//Crear objetos
		rondas = new JLabel (contadorRondas + "/" + Configuracion.totalRondas);
		calificacion = new JLabel ("¡Presiona jugar para comenzar!");
		tiempo = new JLabel ("00:00:000");
		
		
		ImageIcon iconoHistorial = new ImageIcon("src//iconos//historial.png");
		historial = new JButton(iconoHistorial);
		historial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pHistorial = new Historial();
				Principal.scrollPaneles.setViewportView(pHistorial);
			}
		});
		
		jugar = new JButton("JUGAR");
		jugar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pararTiempo = false;
				terminar = false;
				aciertos = 0;
				empezarTiempo();
				calificacion.setForeground(Color.GREEN);
				calificacion.setText("¡Empieza el tiempo!");
				for (int i = 0; i < botones.length; i++) {
					botones[i].setEnabled(true);
				}
				colorearCasilla();
				contadorRondas = 0;
				rondas.setText(contadorRondas + "/" + Configuracion.totalRondas);
				tiempo.setText("00:00:000");
				jugar.setText("REINICIAR");
			}
		});
		ImageIcon iconoConfiguracion = new ImageIcon("src//iconos//configuracion.png");
		configuracion = new JButton(iconoConfiguracion);
		configuracion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pConfiguracion = new Configuracion();
				Principal.scrollPaneles.setViewportView(pConfiguracion);
			}
		});
		
		for (int i = 0; i < botones.length; i++) {
			botones[i] = new JButton();
			botones[i].addActionListener(this);
			botones[i].setEnabled(false);
		}
		
		//Añadir objetos
		
		//Fila 0
		c.gridwidth = 8; //Cuantas columnas va a ocupar  
		c.insets = new Insets(-30,5,-20,0);  //Margin
		rondas.setFont(new Font("Arial", Font.PLAIN, 24)); //Tipografía
		rondas.setForeground(Color.WHITE); //Color Texto
		addGB(rondas, 0, 0);
		
		
		c.insets = new Insets(-30,0,-20,0);  //Margin
		calificacion.setFont(new Font("Arial", Font.BOLD, 32)); //Tipografía
		calificacion.setForeground(Color.GREEN); //Color Texto
		calificacion.setHorizontalAlignment(SwingConstants.CENTER);
		addGB(calificacion, 0, 0);
		
		c.gridwidth = 8; //Cuantas columnas va a ocupar  
		c.insets = new Insets(-30,0,-20,5);  //Margin
		tiempo.setFont(new Font("Arial", Font.PLAIN, 24)); //Tipografía
		tiempo.setForeground(Color.WHITE); //Color Texto
		tiempo.setHorizontalAlignment(SwingConstants.RIGHT);
		addGB(tiempo, 0, 0);
		
		//Fila 1-8
		c.gridwidth = 1; //Cuantas columnas va a ocupar  
		int columna = 0, fila = 0;
		for (int i = 0; i < botones.length; i++) {
			c.insets = new Insets(2,2,2,2);  //Margin
			botones[i].setBackground(Color.DARK_GRAY);
			if(i % 8 == 0) {
				columna = 0;
				fila++;
			}		
			addGB(botones[i], columna, fila);
			columna++;
		}	
		
		//Fila 9
		c.gridwidth = 8; //Cuantas columnas va a ocupar  
		c.insets = new Insets(2,2,0,665);  //Margin0
		historial.setFont(new Font("Arial", Font.PLAIN, 24)); //Tipografía
		historial.setForeground(Color.WHITE); //Color Texto
		addGB(historial, 0, 9);
		
		c.insets = new Insets(2,340,0,340);  //Margin0
		
		jugar.setFont(new Font("Arial", Font.BOLD, 32)); //Tipografía
		jugar.setForeground(Color.BLACK); //Color Texto
		jugar.setBackground(Color.GREEN);
		jugar.setHorizontalAlignment(SwingConstants.CENTER);
		addGB(jugar, 0, 9);
		
		c.insets = new Insets(2,665,0,2);  //Margin0
		configuracion.setFont(new Font("Arial", Font.PLAIN, 24)); //Tipografía
		configuracion.setForeground(Color.WHITE); //Color Texto
		addGB(configuracion, 0, 9);
		
	}
	private void empezarTiempo() {
	    min = 0;
	    sec = 0;
	    msec = 0;
	    if (timerThread != null && timerThread.isAlive()) {
	        timerThread.interrupt(); // Detener el hilo anterior
	    }

	    timerThread = new Thread(() -> {
	        while (!pararTiempo) {
	            try {
	                Thread.sleep(1); // Cambiamos a 1 milisegundo
	            } catch (InterruptedException e) {
	                return;
	            }
	            msec++;
	            if (msec == 1000) {
	                sec++;
	                msec = 0;
	            }
	            if (sec == 60) {
	                min++;
	                sec = 0;
	            }
	            SwingUtilities.invokeLater(() -> {
	                tiempo.setText(String.format("%02d:%02d:%03d", min, sec, msec));
	            });
	        }
	    });
	    timerThread.start();
	}


	
	private void colorearCasilla() {
		aleatorio = (int)(Math.random()*botones.length);
		for (int i = 0; i < botones.length; i++) {
			botones[i].setBackground(Color.DARK_GRAY);
		}
		botones[aleatorio].setBackground(Color.RED);
	}
	
	void addGB(Component component, int x, int y) {
		c.gridx = x; //posicion columna
		c.gridy = y; //posicion fila
		add(component, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (aleatorio != -1) {	
			if (e.getSource() == botones[aleatorio]) {
				calificacion.setForeground(Color.GREEN);
				aciertos++;
				calificacion.setText("¡ACIERTO!");
				if(!terminar)
				colorearCasilla();
			}
			else
				for (int i = 0; i < botones.length; i++) {
					if (e.getSource() == botones[i]) {
						calificacion.setForeground(Color.RED);
						calificacion.setText("FALLO");
						if(!terminar)
						colorearCasilla();
					}
				}
		}
		for (int i = 0; i < botones.length; i++) {
			if (e.getSource() == botones[i]) {
				contadorRondas++;
				rondas.setText(contadorRondas + "/" + Configuracion.totalRondas);
				if(contadorRondas == Configuracion.totalRondas) {
					finalizar();
				}
			}
		}
	}
	private void finalizar() {
		terminar = true;
		pararTiempo = true;
		for (int i = 0; i < botones.length; i++) {
			botones[i].setBackground(Color.DARK_GRAY);
			botones[i].setEnabled(false);
		}
		calificacion.setForeground(Color.GREEN);
		calificacion.setText("!Has completado todas las rondas!");
		
		String nombre = JOptionPane.showInputDialog(null, "Debes ingresar un nombre para guardar las estadísticas:", "¿Deseas guardar tus estadísticas?", JOptionPane.PLAIN_MESSAGE);
        if (nombre != null && !nombre.equals("")) {
        	String strAciertos = aciertos + "/" + Configuracion.totalRondas;
        	insertarXML(nombre, strAciertos, tiempo.getText());
        	calificacion.setText("¡Estadísitcas guardadas!");
        } else {
        	calificacion.setText("Estadísticas no guardadas");
            System.out.println("El usuario ha cancelado la operación o no ha ingresado un nombre.");
        }	
	}

	public void insertarXML(String nombre, String aciertos, String tiempo) {
	        try {
	        	// Cargar el archivo XML existente
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document document = builder.parse(new File("src//historialPartidas.XML"));

	            // Obtener el elemento raíz existente
	            Element partidasElement = document.getDocumentElement();

	            // Crear un nuevo elemento partida
	            Element partidaElement = document.createElement("partida");
	            partidasElement.appendChild(partidaElement);

	            // Crear y agregar los elementos hijos a la partida
	            Element nombreElement = document.createElement("nombre");
	            nombreElement.setTextContent(nombre);
	            partidaElement.appendChild(nombreElement);

	            Element aciertosElement = document.createElement("aciertos");
	            aciertosElement.setTextContent(aciertos);
	            partidaElement.appendChild(aciertosElement);

	            Element tiempoElement = document.createElement("tiempo");
	            tiempoElement.setTextContent(tiempo);
	            partidaElement.appendChild(tiempoElement);

	            Element fechaElement = document.createElement("fecha");
	            LocalDate fecha = LocalDate.now();
	            fechaElement.setTextContent(fecha.toString());
	            partidaElement.appendChild(fechaElement);

	            // Guardar el documento XML actualizado
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
//	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	            DOMSource source = new DOMSource(document);
	            StreamResult result = new StreamResult(new File("src//historialPartidas.XML"));
	            transformer.transform(source, result);
	            
	            System.out.println("Datos agregados al archivo XML existente.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}


}
