/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerm.cargadorexcel.model.conn;

import com.jerm.conexion.controller.Conexiones;
import java.io.IOException;

/**
 *
 * @author Jymmy
 */
public class Db  {
    
    public static Conexiones conectar() throws IOException
    {
    final Conexiones conn = new Conexiones(System.getProperty("user.dir") + "/Conexion.properties".replace("\\", "/"));
    return conn;
    }
}
