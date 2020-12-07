
package edu.unicesar.trescapas.presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;

/**
 *
 * @author jairo
 */
public class GuiPrincipal extends JFrame implements ActionListener{
    private JMenuBar barraMenu;
    private JMenu menu;
    private JMenuItem menuRegistro, mConsulta;
    private JPanel panelImg;
    private JLabel lImg;

    public GuiPrincipal() {
        this("University List Register - V 1.0");
    }

    public GuiPrincipal(String string) {
        super(string);
        this.iniciarComponentes();
        
    }
     public void iniciarComponentes(){
        Image img = new ImageIcon("src/Recursos/appImg.png").getImage();
        this.setIconImage(img);
        this.iniciarBarraMenu();
        this.crearPanelImg();
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
     }
     
     public void iniciarBarraMenu(){
         
         this.barraMenu = new JMenuBar();
         this.menu = new JMenu("Operaciones");
         this.menu.setIcon(new ImageIcon("src/Recursos/archivoImg.jpg"));
         this.menu.setSize(20, 20);
         this.menuRegistro = new JMenuItem("Registro");
         this.menuRegistro.addActionListener(this);
         
         
         this.menuRegistro.setIcon(new ImageIcon("src/Recursos/registroImg.png"));
         this.mConsulta = new JMenuItem("Consulta");
         this.mConsulta.addActionListener(this);
        
         this.mConsulta.setIcon(new ImageIcon("src/Recursos/consultaImg.png"));
         this.menu.add(this.menuRegistro);
         this.menu.add(this.mConsulta);
         this.barraMenu.add(this.menu);
         
         this.setJMenuBar(this.barraMenu);
     }
     
     public void crearPanelImg(){
         this.panelImg = new JPanel();
         this.panelImg.setLayout(new FlowLayout(FlowLayout.RIGHT));
         this.lImg = new JLabel();
         this.lImg.setIcon(new ImageIcon("src/Recursos/logoUpc.jfif"));
         this.panelImg.add(this.lImg);
         
         this.getContentPane().setLayout(new BorderLayout());
         this.getContentPane().add(this.panelImg, BorderLayout.SOUTH);
     }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==this.menuRegistro){
           new GuiRegistro(this, "Registro de Universidades", true); 
        }
        if(ae.getSource()==this.mConsulta){
            new GuiConsulta(this, "Consulta de universidades", true);
        }
    }

  
    
    
}
