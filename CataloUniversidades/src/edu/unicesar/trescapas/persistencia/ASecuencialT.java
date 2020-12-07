/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unicesar.trescapas.persistencia;

import edu.unicesar.trescapas.entidades.Universidad;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jairo
 */
public class ASecuencialT implements IBaseDatos{
    private File archivo;
    private FileWriter escribir ;
    private Scanner leer;

    public ASecuencialT(String name) {
        this.archivo = new File(name);
    }

    public ASecuencialT() {
        this("Universidades.dat");
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public FileWriter getEscribir() {
        return escribir;
    }

    public void setEscribir(FileWriter escribir) {
        this.escribir = escribir;
    }

    public Scanner getLeer() {
        return leer;
    }

    public void setLeer(Scanner leer) {
        this.leer = leer;
    }
    
     
    
    @Override
    public void add(Universidad u) {
        
        try{
            this.escribir = new FileWriter(this.archivo,true); // archivo en modo escritura
            PrintWriter pw = new PrintWriter(this.escribir);
            pw.println(u);
            
        }catch(IOException ioe){
            throw new NullPointerException("Error al abrir archivo para escritura");
        }
        finally{
            if(this.escribir!=null)
                try{
                    this.escribir.close();
                }catch(IOException ioe){
                    throw new NullPointerException("Error al cerrar archivo de escritura");
                }    
        }
        
        
    }

    @Override
    public Universidad buscar(String id) {
       Universidad buscado = null;
        try {
            this.leer = new Scanner(this.archivo);
            while(this.leer.hasNext()){
             String datos[] = this.leer.nextLine().split(";");
             Universidad u = leerUniversidad(datos);
             if(u.getId().equalsIgnoreCase(id)){
                 buscado = u;
                 break;
             }
            }
            return buscado;
            
        }catch(FileNotFoundException fne){
            throw new NullPointerException("El archivo no fue encontrado/ no existe");
        } 
        finally{
            if(this.leer!=null)
                this.leer.close();
        }
    }

    public Universidad leerUniversidad(String [] datos){
        String id = datos[0];
        String nombre = datos[1];
        String ciudad = datos[2];
        String categoria = datos[3];
        int sedes = Integer.valueOf(datos[4]);
        int programas = Integer.valueOf(datos[5]);
        
        return new Universidad(id, nombre, ciudad, categoria, sedes, programas);
    }
    
    
    @Override
    public List<Universidad> consultar() {
        List<Universidad> lista = new ArrayList();
        try {
            this.leer = new Scanner(this.archivo);
            while(this.leer.hasNext()){
             String datos[] = this.leer.nextLine().split(";");
             Universidad u = leerUniversidad(datos);
             lista.add(u);
            }
            return lista;
            
        }catch(FileNotFoundException fne){
            throw new NullPointerException("El archivo no fue encontrado/ no existe");
        } 
        finally{
            if(this.leer!=null)
                this.leer.close();
        }
        
    }
    
    public void renombrarArchivo(File nvoArchivo) throws IOException{
        
        if(!nvoArchivo.exists())
            nvoArchivo.createNewFile();
        
        if(this.archivo.delete()){
            if(!nvoArchivo.renameTo(this.archivo))
                throw new NullPointerException("No fue posible renombrar el archivo temporal"); 
            
        }else{
           throw new NullPointerException("No fue posible liminar archivo original"); 
        }
        
    }

    @Override
    public Universidad eliminar(String id) {
        Universidad res = null;
        try{
            this.leer = new Scanner(this.archivo);
            ASecuencialT aTemp = new ASecuencialT("UniversidadesTmp.dat");
            while(this.leer.hasNext()){
              Universidad u = this.leerUniversidad(this.leer.nextLine().split(";"));
              if(u.getId().equalsIgnoreCase(id)){
                  res = u;
              }
              else{
                  aTemp.add(u);
              }
            }
            this.leer.close();
            this.renombrarArchivo(aTemp.getArchivo());
            return res;
            
        }catch(FileNotFoundException fne){
            throw new NullPointerException("El archivo no fue encontrado/ no existe");
        }
        catch(IOException ioe){
            throw new NullPointerException("Error al actualizar archivo");
        }
        catch(NullPointerException ne){
            throw ne;
        }
    }
    
}
