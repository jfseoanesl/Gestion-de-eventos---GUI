/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unicesar.trescapas.persistencia;

import edu.unicesar.trescapas.entidades.Universidad;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class AObjetoS implements IBaseDatos {
    private File archivo;
    private FileInputStream leerO;
    private FileOutputStream escribirO;

    public AObjetoS(String name) {
        this.archivo =new File(name);
    }

    public AObjetoS() {
        this("Universidad.obj");
    }
    
    
    private void guardar(ListaDeDatos lista){
       ObjectOutputStream oos=null;
       try{ 
         this.escribirO = new FileOutputStream(this.archivo);
         oos = new ObjectOutputStream(this.escribirO);
         oos.writeObject(lista);
         
       }catch(IOException fne){
           throw new NullPointerException("Error al abrir archivo en escritura");
       }
       finally{
           try{
            if(oos!=null)
                oos.close();
           }catch(IOException ioe){
               throw new NullPointerException("Error al cerrar archivo en escritura");
           } 
       }
        
    }
    
    private ListaDeDatos leer(){
        if(this.archivo.exists()){
            ObjectInputStream ois = null;
            try{
                this.leerO = new FileInputStream(this.archivo);
                ois = new ObjectInputStream(this.leerO);
                ListaDeDatos lista = (ListaDeDatos) ois.readObject();
                return lista;

            }catch(IOException ioe){
                throw new NullPointerException("Error al abrir archivo en lectura");
            } catch (ClassNotFoundException ex) {
                throw new NullPointerException("Error al leer archivo ");
            }    
        }
        else{
            return new ListaDeDatos();
        }
    }
    
    
    

    @Override
    public void add(Universidad u) {
        ListaDeDatos lista = this.leer();
        lista.add(u);
        this.guardar(lista);
    }

    @Override
    public Universidad buscar(String id) {
        return this.leer().buscar(id);
    }

    @Override
    public List<Universidad> consultar() {
        return this.leer().consultar();
        
    }

    @Override
    public Universidad eliminar(String id) {
        ListaDeDatos lista = this.leer();
        Universidad eliminado = lista.eliminar(id);
        this.guardar(lista);
        return eliminado;
    }
    
}
