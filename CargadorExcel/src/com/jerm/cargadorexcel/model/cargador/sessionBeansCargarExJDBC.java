/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerm.cargadorexcel.model.cargador;

import com.jerm.cargadorexcel.model.conn.Db;
import com.jerm.conexion.controller.Conexiones;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author jerodas
 */
public class sessionBeansCargarExJDBC {

    public String existeTabla(Conexiones conn,String nombre, List<String> headers) throws IOException, SQLException {
        String name = "";
        sessionBeansCargarEx BeansCargarEx = new sessionBeansCargarEx();
        try {           
            if (BeansCargarEx.existeTabla(conn, nombre) > 0) {
                name = conn.getEsquema()+"."+nombre;
            } else {
                String esquema ="dbo";              
                name = esquema +"."+ crearTable(Db.conectar(), headers, nombre, esquema);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
            System.out.println("== sessionBeansCargarExJDBC existeTabla "+e);
        } 
        return name;
    }

    public String crearTable(Conexiones conn,List<String> headers, String name, String esquema) throws IOException, ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        String nombre = "";
        try {
            sessionBeansCargarEx BeansCargarEx = new sessionBeansCargarEx();
            nombre = BeansCargarEx.crearTable(conn, headers, name, esquema);
            if (!nombre.trim().equals("")) {
                conn.confirmar();
            } else {
                conn.deshacer();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
            System.out.println("== sessionBeansCargarExJDBC crearTable "+e);
        }finally
        {
            if (conn.getCnx()!=null) {
                conn.desconectarBD();
            }
        }
        return nombre;
    }

    public int insertData(Conexiones conn,List<String> headers, List<String> data, String table) throws Exception {
        int resp = 0;        
        try {
            sessionBeansCargarEx BeansCargarEx = new sessionBeansCargarEx();
            resp = BeansCargarEx.InsertarData(conn, headers, data, table);
            if (resp > 0) {
               conn.confirmar();
            } else {
                conn.deshacer();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
            System.out.println("== sessionBeansCargarExJDBC insertData "+e);
        } finally {
            if(conn.getCnx()!=null)
             {
                 conn.desconectarBD();
             }            
        }
        return resp;
    }
    
       public List<String> getMetadosTabla(Conexiones conn,String nombre) throws IOException, SQLException {
        List<String> metadatos = new ArrayList<>();       
        sessionBeansCargarEx BeansCargarEx = new sessionBeansCargarEx();
        try {           
           metadatos=BeansCargarEx.metadatosTabla(conn, nombre);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
            System.out.println("== sessionBeansCargarExJDBC existeTabla "+e);
        } 
        return metadatos;
    }

}
