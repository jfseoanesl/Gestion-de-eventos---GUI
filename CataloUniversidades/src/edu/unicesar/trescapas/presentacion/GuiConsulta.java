
package edu.unicesar.trescapas.presentacion;

import edu.unicesar.trescapas.entidades.Universidad;
import edu.unicesar.trescapas.modelo.GestionUniversidad;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jairo
 */
public class GuiConsulta extends JDialog {
    private Container contenedor;
    private JPanel pFiltro;
    private JScrollPane pTabla;
    private JTable tabla;
    private DefaultTableModel modelo;
    private String titulos[]={"Id", "Nombre", "Ciudad", "Categoria","No sedes", "No programas"};
    private String datos[][]={{"123","UPC","Vpar", "Publica", "2", "15"},{null},{null}};
    private JLabel lBusqueda;
    private JTextField tBusqueda;
    
    private GestionUniversidad modeloGU;
    

    public GuiConsulta(JFrame frame, boolean bln) {
        this(frame, "Consulta de Registros", bln);
        
    }

    public GuiConsulta(JFrame frame, String string, boolean bln) {
        super(frame, string, bln);
        this.modeloGU = new GestionUniversidad();
        this.iniciarComponentes();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void iniciarComponentes()
    {   this.setIconImage(new ImageIcon("src/Recursos/consultaImg.png").getImage());
        this.contenedor = this.getContentPane();
        this.contenedor.setLayout(new BorderLayout());
        this.crearPanelFiltro();
        this.crearPanelTabla();
        this.actualizarTabla(this.modeloGU.consultar());
    }
    
    public void crearPanelFiltro(){
        this.pFiltro = new JPanel();
        this.pFiltro.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.lBusqueda = new JLabel("Filtro de busqueda: ");
        this.tBusqueda = new JTextField(20);
        
        
        this.pFiltro.add(this.lBusqueda);
        this.pFiltro.add(this.tBusqueda);
        
        this.contenedor.add(this.pFiltro, BorderLayout.NORTH);
    }
    
    
    
    public void crearPanelTabla(){
        this.tabla = new JTable();
        this.modelo = new DefaultTableModel(null, titulos);
        this.tabla.setModel(modelo);
        
        this.pTabla = new JScrollPane();
        this.pTabla.setViewportView(this.tabla);
        
        this.contenedor.add(this.pTabla, BorderLayout.CENTER);
    
    }
    
    public void actualizarTabla(List<Universidad> datos){
        this.modelo.setNumRows(0);
        for(Universidad u : datos){
            String linea[]={u.getId(), u.getNombre(), u.getCiudad(),
                            u.getCategoria(), String.valueOf(u.getnSedes()),
                            ""+u.getnProgramas()};
            this.modelo.addRow(linea);
        }
        
    }
    
   
    
    
}
