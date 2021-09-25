/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerm.cargadorexcel.resources;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class Herramientas {

    public static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat ddMMyyyy = new SimpleDateFormat("ddMMyyyy");
    public static SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat yyyymmdd2 = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    public static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat df = new DecimalFormat("#.00");
    //public static Calendar calendar = Calendar.getInstance();
    private static int entero;
    //----ESQUEMA DE BASE DE DATOS
    //---ESQUEMA DE PRODUCCION
    public static String esquema="elctex.";
    //---ESQUEMA DE PRUEBA
    //public static String esquema="CTEX.";

    //SumarRestarAñosDiasMeses YEAR - 0 , MONTH -2 DAYOFTHEYEAR-6
    public static Date sumarRestarTime(int periodo, int tiempo, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, tiempo);
        return calendar.getTime();
    }

    // Validacion para campo vacio
    public static boolean verificarCampoVacio(String mensaje) {
        return mensaje.trim().equals("");
    }

    // Validacion para cuenta contable
    public static boolean verificarPartidaContable(String cadena) {
        Pattern pat = Pattern.compile("[0-9]{1}-[0-9]{1}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }

    //Formateo de moneda de Dolar
    public static NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("EN", "us"));

    //Validacion para que solo ingrese numeros
    public static boolean esEnteroPositivo(String cadena) {
        try {
            entero = Integer.parseInt(cadena.trim());
            if (entero <= 0) {
                return false;
            }
            return true;
        } catch (Exception a) {
            return false;
        }
    }

    public static String separarString(String parte, int indice) {
        //Separamos la data selecionada del subtipo para quedarnos solo con el codigo
        String sub;
        String[] parts = parte.split("-");
        return sub = parts[indice];
    }

     public static String separarString2(String parte, int indice,String expresion) {
        //Separamos la data selecionada del subtipo para quedarnos solo con el codigo
        String sub;
        String[] parts = parte.split(expresion);
        return sub = parts[indice];
    }
   
    
     public static void mensaje(String mensaje, int icono) {
        JOptionPane.showMessageDialog(null, mensaje, "", icono);
    }
}
