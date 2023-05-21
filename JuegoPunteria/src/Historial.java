import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Historial extends JPanel implements ActionListener {

	JLabel titulo;
	JButton volver;
	
	GridBagConstraints c = new GridBagConstraints();
	Juego pJuego;
	
	public Historial() {
		//GridBagLayout
		setBackground(Color.BLACK); 
		setLayout(new GridBagLayout()); 
    	c.weightx = 1;
    	c.weighty = 1;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(0,0,0,0);  //Margin
		c.gridwidth = 1; //Cuantas columnas va a ocupar  
    	c.gridheight = 1; //Cuantas filas va a ocupar 
    	
    	//Titulo
    	c.insets = new Insets(0,0,0,0);  //Margin0
    	titulo = new JLabel("HISTORIAL DE PARTIDAS");
    	titulo.setHorizontalAlignment(SwingConstants.CENTER);
    	titulo.setFont(new Font("Arial", Font.BOLD, 32)); //Tipografía
		titulo.setForeground(Color.GREEN); //Color Texto
    	addGB(titulo, 0,0);
  
    	//Tabla		
		List<Partida> partidas = leerXML();
		String[] columnas = {"Nombre", "Aciertos", "Tiempo", "Fecha"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Partida partida : partidas) {
            Object[] fila = {partida.getNombre(), partida.getAciertos(), partida.getTiempo(), partida.getFecha()};
            model.addRow(fila);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        c.insets = new Insets(0,2,0,2);  //Margin
        addGB(scrollPane, 0, 1);
        
        //Botón de volver
        c.insets = new Insets(2,2,0,2);  //Margin0
    	volver = new JButton("VOLVER");
    	volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pJuego = new Juego();
				Principal.scrollPaneles.setViewportView(pJuego);
			}
		});
    	volver.setHorizontalAlignment(SwingConstants.CENTER);
    	volver.setFont(new Font("Arial", Font.BOLD, 32)); //Tipografía
		volver.setForeground(Color.BLACK); //Color Texto
		volver.setBackground(Color.GREEN);
		addGB(volver, 0,2);
	}
	private List<Partida> leerXML() {
		List<Partida> partidas = new ArrayList<>();
		
		 try {
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
             DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
             Document document = dBuilder.parse("src//historialPartidas.XML");
             
             Element rootElement = document.getDocumentElement();
             NodeList partidaNodes = rootElement.getElementsByTagName("partida");
             
             for (int i = 0; i < partidaNodes.getLength(); i++) { //Recorre las partidas.
            	//Entra en una partida (i).
                 Element partidaElement = (Element) partidaNodes.item(i); 
                 Partida partida = new Partida(); 
                 //Guarda las partidas.
                 partida.setNombre(partidaElement.getElementsByTagName("nombre").item(0).getTextContent());
                 partida.setAciertos(partidaElement.getElementsByTagName("aciertos").item(0).getTextContent());
                 partida.setTiempo(partidaElement.getElementsByTagName("tiempo").item(0).getTextContent());
                 partida.setFecha(partidaElement.getElementsByTagName("fecha").item(0).getTextContent());

                 partidas.add(partida);
             }
             
             
		} catch (Exception e) {
			// TODO: handle exception
		}
		return partidas;
	}
	
	public class Partida {
	    private String nombre;
	    private String aciertos;
	    private String tiempo;
	    private String fecha;

	    public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	    public String getNombre() {
			return nombre;
		}
	    public void setAciertos(String aciertos) {
			this.aciertos = aciertos;
		}
	    public String getAciertos() {
			return aciertos;
		}
	    public String getTiempo() {
			return tiempo;
		}
	    public void setTiempo(String tiempo) {
			this.tiempo = tiempo;
		}
	    public String getFecha() {
			return fecha;
		}
	    public void setFecha(String fecha) {
			this.fecha = fecha;
		}
	    
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
