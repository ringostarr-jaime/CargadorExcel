/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerm.cargadorexcel.controller;

import com.jerm.cargadorexcel.controller.formularios.CargarExController;
import com.jerm.cargadorexcel.view.PrincipalForm;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Jymmy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {
        // TODO code application logic here
//        Test t = new Test();
//        for (int i = 0; i < 1; i++) {
//        t.consultar();    
//        }
     PrincipalForm form = new PrincipalForm();        
     new principalFormController(form).abrirApp();
       

    }

}
