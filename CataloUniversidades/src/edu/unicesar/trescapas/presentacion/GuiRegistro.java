/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unicesar.trescapas.presentacion;

import edu.unicesar.trescapas.entidades.EtapasProyecto;
import edu.unicesar.trescapas.entidades.Universidad;
import edu.unicesar.trescapas.modelo.GestionUniversidad;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author jairo
 */
public class GuiRegistro extends JDialog {
    
    private Container contenedor;
    private JPanel panelDatos;
    private JPanel panelBotones;
    private JPanel panelGrupo;
    
    private JLabel lbId, lbNombre, lbCiudad, lbCategoria, lbSedes, lbProgramas;
    private JTextField txtId, txtNombre, txtCiudad, txtSedes, txtProgramas;
    private JButton btnNuevo, btnBuscar, btnEliminar, btnCancelar;
    private ButtonGroup grupoCategoria;
    private JRadioButton rbPublica, rbPrivada;
    private JComboBox cmbEtapas;
    
    private GestionUniversidad modelo;
    

    public GuiRegistro(Frame frame, String string, boolean bln) {
        super(frame, string, bln);
        this.modelo = new GestionUniversidad();
        this.iniciarComponentes();
        //this.pack(); 
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true); 
        
    }
    
    public void iniciarComponentes(){
        
        this.contenedor = this.getContentPane();
        this.contenedor.setLayout(new BorderLayout());
        this.crearPanelDatos();
        this.crearPanelBotones();
        
        
    }
    public void crearPanelBotones(){
        
        this.panelBotones = new JPanel();
        this.panelBotones.setLayout(new FlowLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1,3,3));
        this.btnNuevo = new JButton("Nuevo");
        this.btnNuevo.addActionListener(new nuevoClick());
        this.btnBuscar= new JButton("Buscar");
        this.btnBuscar.addActionListener(new buscarClick());
        this.btnEliminar= new JButton("Eliminar");
        this.btnEliminar.setEnabled(false);
        this.btnEliminar.addActionListener(new eliminarClick());
        this.btnCancelar = new JButton("Cancelar");
        this.btnCancelar.addActionListener(new cancelarClick());
        
        panel.add(this.btnNuevo);
        panel.add(this.btnBuscar);
        panel.add(this.btnEliminar);
        panel.add(this.btnCancelar);
        
        this.panelBotones.add(panel);
        this.contenedor.add(this.panelBotones, BorderLayout.EAST);
        
    }
    public void crearPanelDatos(){
        this.panelDatos = new JPanel();
        this.panelDatos.setLayout(new GridLayout(7,2,5,5));
        
        this.lbId = new JLabel("Id: ");
        this.lbNombre = new JLabel("Nombre: ");
        this.lbCiudad= new JLabel("Ciudad: ");
        this.lbCategoria= new JLabel("Categoria: ");
        this.lbSedes= new JLabel("No Sedes: ");
        this.lbProgramas= new JLabel("No programas: ");
        
        this.txtId = new JTextField(null);
        this.txtNombre= new JTextField(null);
        this.txtCiudad= new JTextField(null);
        this.txtSedes= new JTextField(null);
        this.txtProgramas= new JTextField(null);
        
        this.grupoCategoria = new ButtonGroup();
        this.rbPublica = new JRadioButton("Publica");
        this.rbPublica.setSelected(true);
        this.rbPrivada = new JRadioButton("Privada");
        this.grupoCategoria.add(this.rbPublica);
        this.grupoCategoria.add(this.rbPrivada);
        
        panelGrupo = new JPanel();
        panelGrupo.add(this.rbPublica);
        panelGrupo.add(this.rbPrivada);
        
        this.cmbEtapas = new JComboBox();
        this.cmbEtapas.addItem(EtapasProyecto.ANALISIS);
        this.cmbEtapas.addItem(EtapasProyecto.DISEÑO);
        this.cmbEtapas.addItem(EtapasProyecto.IMPLEMENTACION);
        this.cmbEtapas.addItem(EtapasProyecto.PRUEBA);
        
        
        this.panelDatos.add(this.lbId);
        this.panelDatos.add(this.txtId);
        this.panelDatos.add(this.lbNombre);
        this.panelDatos.add(this.txtNombre);
        this.panelDatos.add(this.lbCiudad);
        this.panelDatos.add(this.txtCiudad);
        this.panelDatos.add(this.lbCategoria);
        this.panelDatos.add(this.panelGrupo);
        this.panelDatos.add(this.lbSedes);
        this.panelDatos.add(this.txtSedes);
        this.panelDatos.add(this.lbProgramas);
        this.panelDatos.add(this.txtProgramas);
        this.panelDatos.add(new JLabel("Etapas: "));
        this.panelDatos.add(this.cmbEtapas);
        
        this.contenedor.add(this.panelDatos, BorderLayout.CENTER);
       
    }
    
    public Universidad leerDatos() {
        String id = this.txtId.getText();
        if(id.isEmpty()){
          this.txtId.grabFocus();
          throw new NullPointerException("Debe registrar el Id");
        }
        
        String nombre = this.txtNombre.getText();
        if(nombre.isEmpty()){
          this.txtNombre.grabFocus();
          throw new NullPointerException("Debe registrar el nombre");
        }
        String ciudad = this.txtCiudad.getText();
        if(ciudad.isEmpty()){
            this.txtCiudad.grabFocus();
            throw new NullPointerException("Debe registrar la ciudad");
        }
        String categoria = (this.rbPublica.isSelected())?"Publica":"Privada";
        int sedes=0, programas=0, error=1;
        String msgError="";
        try{
            sedes = Integer.parseInt(this.txtSedes.getText());
            error=2;
            programas = Integer.parseInt(this.txtProgramas.getText());
        }catch(NumberFormatException nfe){
           if(error==1){
            this.txtSedes.grabFocus();
            msgError="Debe registrar un numero para la Sede";
           }
           if(error==2){
            this.txtProgramas.grabFocus();
            msgError="Debe registrar un numero para el programa"  ;
           }
           throw new NullPointerException(msgError); 
        }    
        
        return new Universidad(id, nombre, ciudad, categoria, sedes, programas);
       
    }
    
    public void registrarUniversidad(Universidad u){
      
            this.modelo.adicionar(u);
            mostrarMsg("Operacion exitosa", "La universidad se registro corretamente", 1);
        
    } 
    
    public void guardar(){
        try{
            Universidad u = leerDatos();
            registrarUniversidad(u);
            this.cancelar();
           }catch(NullPointerException npe){
               mostrarMsg("Error", npe.getMessage(), 0);
           }
    }
    
   
    
    public void buscar(){
        
        try{
            String id = this.txtId.getText();
            Universidad u = this.modelo.buscar(id);
            if(u!=null){
                this.txtNombre.setText(u.getNombre());
                this.txtCiudad.setText(u.getCiudad());
                if(u.getCategoria().equalsIgnoreCase("Publica"))
                    this.rbPublica.setSelected(true);
                else
                    this.rbPrivada.setSelected(true);
                this.txtSedes.setText(String.valueOf(u.getnSedes()));
                this.txtProgramas.setText(String.valueOf(u.getnProgramas()));
                this.btnEliminar.setEnabled(true);
                this.btnEliminar.grabFocus();
            }
            else{
               
                this.mostrarMsg("Resultado de busqueda", "No hay Universidad registrada con el Id " + id, 0);
                this.btnEliminar.setEnabled(false);
                this.cancelar();
                this.txtId.grabFocus();
            }
        }catch(NullPointerException npe){
           mostrarMsg("Error", npe.getMessage(), 0); 
        }    
    }
    
    public void eliminar(){
        try{
            String id = this.txtId.getText();
            Universidad u = this.modelo.buscar(id);
            if(u!=null){
                int confirma = JOptionPane.showConfirmDialog(this,  
                                "Los datos a eliminar son: \n"
                                +"Id: "+u.getId()+"\n"
                                +"Nombre: "+u.getNombre()+"\n"
                                +"Ciudad: "+u.getCiudad()+"\n"
                                +"Categoria: "+u.getCategoria()+"\n"
                                +"Sedes: "+u.getnSedes()+"\n"
                                +"Programas: "+u.getnProgramas()+"\n\n"
                                +"Desea cofirmar?");
                if(confirma==0){
                    Universidad eliminado = this.modelo.eliminar(id);
                    if(eliminado!=null){
                        mostrarMsg("Proceso exitos", "El registro ha sido eliminado con exito", 1); 
                        this.cancelar();
                        this.btnEliminar.setEnabled(false);
                        this.txtId.grabFocus();
                    }
                    else{
                        mostrarMsg("Error", "El elemento no pudo ser eliminado", 0); 
                        this.txtId.grabFocus();
                    }
                }
                
            }
            else{
               
                this.mostrarMsg("Resultado de busqueda", "No hay Universidad registrada con el Id " + id, 0);
                this.btnEliminar.setEnabled(false);
                this.cancelar();
                this.txtId.grabFocus();
            }
        }catch(NullPointerException npe){
           mostrarMsg("Error", npe.getMessage(), 0); 
        } 
    }
     
    public void mostrarMsg(String titulo, String contenido, int tipo){
        JOptionPane.showMessageDialog(this, contenido, titulo, tipo);
    }    
    
     public void cancelar(){
        
        this.txtId.setText(null);
        this.txtNombre.setText(null);
        this.txtCiudad.setText(null);
        this.txtProgramas.setText(null);
        this.txtSedes.setText(null);
        this.btnEliminar.setEnabled(false);
        this.txtId.grabFocus();
    }
    class nuevoClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
           guardar();
        }
    }
    class buscarClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            buscar();
        }
    }
    class eliminarClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            eliminar();
        }
    }
    class cancelarClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            cancelar();
        }
    }
    
    
    
}

