/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerm.cargadorexcel.controller;

import com.jerm.cargadorexcel.controller.formularios.CargarExController;
import com.jerm.cargadorexcel.view.CargarExInternalForm;
import com.jerm.cargadorexcel.view.PrincipalForm;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;


/**
 *
 * @author Jymmy
 */
public class principalFormController  implements ActionListener{
   
    private PrincipalForm principal=null;    
    private  CargarExInternalForm cargarExcelForma=null;

    public principalFormController(PrincipalForm principal) {
        this.principal =principal;
    }
    
    
    public void abrirApp()
    {
        agregarListener();
        centrarForm();
        //---Carga pantalla inicial
        cargarExcelForma= new CargarExInternalForm();
       new CargarExController(cargarExcelForma).abrirApp();
       cargarPantallas(cargarExcelForma);
        
    }
    
     public void centrarForm() {       
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (Math.round(screenSize.getHeight() * 0.80));
        int width = (int) (Math.round(screenSize.getWidth() * 0.80));
        principal.setSize(new Dimension(width, height));
        principal.setLocationRelativeTo(null);
         principal.setVisible(true);
    }
    
    
    public void agregarListener()
    {
    principal.getOpcion1().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == principal.getOpcion1()) {  
        if(cargarExcelForma==null||!cargarExcelForma.isVisible())
        {
        cargarExcelForma= new CargarExInternalForm();
        new CargarExController(cargarExcelForma).abrirApp();
        cargarPantallas(cargarExcelForma);        
         }
       
        }
    }
    
    public void cargarPantallas(JInternalFrame frame)
    {       
        principal.getEscritorio().add(frame);
        Dimension desktopSize = principal.getSize();
        Dimension FrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);        
        principal.toFront();
        frame.show();
       
    }
    
    
}
