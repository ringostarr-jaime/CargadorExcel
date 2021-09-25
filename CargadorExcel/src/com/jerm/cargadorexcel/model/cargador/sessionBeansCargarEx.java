package com.jerm.cargadorexcel.model.cargador;


import com.jerm.cargadorexcel.model.conn.Db;
import com.jerm.conexion.controller.Conexiones;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author jerodas
 */
public class sessionBeansCargarEx {

    public int existeTabla(Conexiones conn, String name) {
        int existe = 0;
        ResultSet rs;
        String query = "if exists (SELECT * FROM sysobjects WHERE type = 'U' AND name = '" + name + "') "
                + "select 1 as 'EXISTE' "
                + "else "
                + "select 0 as 'EXISTE'";
        try {
            if(conn.getCnx()==null)
            {
            conn.conectarBD();
            }  
            rs = conn.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    existe = rs.getInt("EXISTE");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
             System.out.println("==sessionBeansCargarEx existeTabla "+e); 
        }
        return existe;
    }

    public String crearTable(Conexiones conn, List<String> headers, String name, String esquema) {
        String nombre = "";
        String query = "";
        String head = "";
        for (int i = 0; i < headers.size(); i++) {
            if (i + 1 == headers.size()) {
                head = head + headers.get(i) + " VARCHAR(MAX) ";
            } else {
                head = head + headers.get(i) + " VARCHAR(MAX), ";
            }
        }
        query = "create table " + esquema + "." + name + " (" + head + ")";
        try {
            if(conn.getCnx()==null)
            {
            conn.conectarBD();
            }  
            conn.executeUpdate(query);
            nombre = name;
        } catch (Exception e) {
            nombre = "";
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
             System.out.println("==sessionBeansCargarEx crearTable "+e);      
        }
        return nombre;
    }

    public int InsertarData(Conexiones conn, List<String> headers, List<String> data, String table) {
        int resp = 0;
        int salto = 0;
        String values = "";
        String query = "";
        String campo = "";
        try {
            if(conn.getCnx()==null)
            {
            conn.conectarBD();
            }            
            for (int i = 0; i < headers.size(); i++) {
                //Es para saber cual es el ultimo y no agregarle la coma de separacion al insert
                if (i + 1 == headers.size()) {
                    campo = campo + headers.get(i);
                } else {
                    //agrego , de separacion
                    campo = campo + headers.get(i) + ", ";
                }
            }
            for (int i = 0; i < data.size(); i++) {
                salto++;
                //Es para saber cual es el ultimo y no agregarle la coma de separacion al insert
                if (salto == headers.size()) {
                    values = values + data.get(i);
                } else {
                    //agrego , de separacion
                    values = values + data.get(i) + ", ";
                }
                if (salto == headers.size()) {
                    salto = 0;
                    query = "INSERT INTO " + table.trim() + " (" + campo + ") values(" + values + ") ";
                    resp = resp + conn.executeUpdate(query);
                //--Ejemplo de Uso operador ternario ?
                //String query = "update dbo.ctx.EXISTENCIA_BODEGA  set CANT_DISPONIBLE = ? where ARTICULO=? and BODEGA=?";
                //conn.executeUpdatePS(query, new Object[]{ entidad.getCANTIDAD(),entidad.getARTICULO(),entidad.getBODEGA()});                   
                 values = "";
                }
            }
        } catch (Exception e) {
            resp = 0;
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
            System.out.println("==sessionBeansCargarEx InsertarData "+e);            
        }
        return resp;
    }
    
     public List<String> metadatosTabla(Conexiones conn, String name) {
       List<String> metados = new ArrayList<>();
        ResultSet rs;
        String query = "select * from sys.all_columns where object_id =  object_id('"+name+"') ";
        try {
            if(conn.getCnx()==null)
            {
            conn.conectarBD();
            }  
            rs = conn.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {                  
                    metados.add(rs.getString("name"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e, "", 0);
             System.out.println("==sessionBeansCargarEx metadatosTabla "+e); 
        }
        return metados;
    }

}
