package com.jerm.cargadorexcel.controller.formularios;

import com.jerm.cargadorexcel.controller.CrearExcel;
import com.jerm.cargadorexcel.model.cargador.sessionBeansCargarExJDBC;
import com.jerm.cargadorexcel.model.conn.Db;
import com.jerm.cargadorexcel.resources.Herramientas;

import com.jerm.cargadorexcel.view.CargarExInternalForm;
import java.awt.Dimension;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jerodas
 */
public class CargarExController implements ActionListener, KeyListener {

    private  CargarExInternalForm formInter;
    //private Frame frame = JOptionPane.getFrameForComponent(formInter);   
    public static final char SEPARATOR = ';';
    public static final char QUOTE = '"';
    private List<String> headers = new ArrayList<>();
    private List<String> contenido = new ArrayList<>();
    private String nombreArchivo = "";
    private int hoja = 0;
    private sessionBeansCargarExJDBC CargarExJDBC = new sessionBeansCargarExJDBC();

    public CargarExController(CargarExInternalForm formInter) {
        this.formInter = formInter;
    }

    public void abrirApp() {    
            //Herramientas.centrarFormayVisualizar(formInter);       
            agregarListener();            
            setDatosIniciales();              
            formInter.setVisible(true);
            formInter.getTxtRuta().requestFocus();            
   }  
 

    private void setDatosIniciales() {
        formInter.getTxtHoja().setText("1");       
        formInter.getMensajeInformativo().setText("Presiones F1 o doble click para abrir el dialogo");       
        formInter.getTxtRuta().requestFocus();
    }

    private void agregarListener() {
        formInter.getTxtRuta().addKeyListener(this);
        formInter.getTxtRuta().addActionListener(this);
        formInter.getBtnCancelar().addActionListener(this);
        formInter.getBtnCargar().addActionListener(this);
        formInter.getTxtHoja().addKeyListener(this);
        formInter.getTxtRuta().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    fileChooser();
                }
            }

        });
    }

   

    public void limpiar() {
        headers = new ArrayList<>();
        contenido = new ArrayList<>();
        formInter.getTxtRuta().setText("");
    }

    public void Excel(int hoja) throws IOException, InvalidFormatException, Exception {
        //<editor-fold defaultstate="collapsed" desc="EXCEL">
        String path = formInter.getTxtRuta().getText().trim();
        FileInputStream excelStream = null;
        if (path.equals("") || path.length() > 3) {
            try {
                excelStream = new FileInputStream(path);                
                // Representación del más alto nivel de la hoja excel.
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelStream);               
                // Elegimos la hoja que se pasa por parámetro.
                //XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(hoja);                
                // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
                XSSFRow xssfRow;    
                // Obtengo el número de filas ocupadas en la hoja
                int rows = xssfSheet.getLastRowNum();               
                // Obtengo el número de columnas ocupadas en la hoja
                //int cols = 0;              
                //Objeto que usamos para almacenar la lectura de la celda            
                Object cellValue;               
                //recorremos las filas obteniendo los datos que queremos            
                for (int r = 0; r <= rows; r++) {
                    xssfRow = xssfSheet.getRow(r);
                    if (xssfRow == null) {
                        //break;
                    } else {
                        //System.out.print("Row: " + r + " -> ");
                        
                         System.out.println("== ");
                        //for (int c = 0; c < (cols = xssfRow.getLastCellNum()); c++) {
                        for (int c = 0; c < xssfRow.getLastCellNum(); c++) {
                            /*
                            We have those cell types (tenemos estos tipos de celda): 
                                CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                             */

                            switch (r) {
                                case 0:
                                    cellValue = xssfRow.getCell(c) == null ? ""
                                            : (xssfRow.getCell(c).getCellType() == CellType.STRING) ? "" + xssfRow.getCell(c).getStringCellValue() : "";
                                    //System.out.print(" " + cellValue);
                                    //--Encabezados
                                    headers.add((String)cellValue);
                                    break;
                                    //---En el numeric cree una validacion para saber si es fecha
                                default:
                                    cellValue = xssfRow.getCell(c) == null ? ""
                                            : (xssfRow.getCell(c).getCellType() == CellType.STRING) ? "'" + xssfRow.getCell(c).getStringCellValue() + "'"
                                            : (xssfRow.getCell(c).getCellType() == CellType.NUMERIC) ? "" + (DateUtil.isCellDateFormatted(xssfRow.getCell(c))? "'"+Herramientas.formatoFechaHora.format(xssfRow.getCell(c).getDateCellValue())+ "'": xssfRow.getCell(c).getNumericCellValue())
                                            : (xssfRow.getCell(c).getCellType() == CellType.BOOLEAN) ? "" + xssfRow.getCell(c).getBooleanCellValue()
                                            : (xssfRow.getCell(c).getCellType() == CellType.BLANK) ? "'NULL'" //"BLANK"
                                            : (xssfRow.getCell(c).getCellType() == CellType.FORMULA) ? "FORMULA" //"FORMULA"
                                            : (xssfRow.getCell(c).getCellType() == CellType.ERROR) ? "ERROR" : "";
                                    contenido.add((String)cellValue);
                                    System.out.println("[Column " + c + ": " + cellValue + "] ");
                                    break;
                            }
                            //System.out.print(cellValue);
                        }
                        //System.out.println();

                    }
                }
                //---
                //System.out.println("" + Herramientas.separarString2(path, 1, ".xlsx"));
                String tabla = CargarExJDBC.existeTabla(Db.conectar(),nombreArchivo, headers);
                if (!tabla.trim().equals("")) {
                    if (CargarExJDBC.insertData(Db.conectar(),headers, contenido, tabla.trim()) > 0) {
                        Herramientas.mensaje("CARGA COMPLETADA, "+contenido.size()+" REGISTROS INSERTADOS ", 1);
                        limpiar();
                    } else {
                        Herramientas.mensaje("ERROR DE CARGA\n REVISE LOS ENCABEZADOS DEL DOCUMENTO", 2);
                        limpiar();
                    }
                } else {
                    Herramientas.mensaje("Problemas al consultar la existencia de la tabla", 2);
                }

                //System.out.println(headers.size() + "  " + contenido.size());
            } catch (FileNotFoundException fileNotFoundException) {
                Herramientas.mensaje("No se encontró el fichero: " , 2);
                //System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
            } finally {
                excelStream.close();
            }
        }
        //</editor-fold>
    }

    public void fileChooser() {
        //<editor-fold defaultstate="collapsed" desc="FILECHOOSER">
        /**
         * llamamos el metodo que permite cargar la ventana
         */
        try {
            JFileChooser file = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx", "xlsx");
            file.setFileFilter(filter);
            int returnVal = file.showOpenDialog(formInter);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                formInter.getTxtRuta().setText(file.getSelectedFile().getAbsolutePath());
                nombreArchivo = file.getSelectedFile().getName();
                boolean returnVal2 = ("xlsx".equals(Herramientas.separarString2(nombreArchivo, 1, "\\.")));
                if (!returnVal2) {
                    nombreArchivo = "";
                    formInter.getTxtRuta().setText("");
                    Herramientas.mensaje("Extension del archivo no es valida", 0);
                } else {
                    nombreArchivo = (Herramientas.separarString2(nombreArchivo, 0, "\\."));
                }
            }
        } catch (Exception ex) {
            Herramientas.mensaje("" + ex, 0);
            //ex.printStackTrace();
        }
//</editor-fold>
    }

    public boolean validar() {
        boolean resp = false;
        hoja = Integer.parseInt(formInter.getTxtHoja().getText());
        if (!nombreArchivo.equals("") && hoja > 0) {
            resp = true;
            hoja = hoja - 1;
        } else {
            Herramientas.mensaje("Revise que la hoja tenga un valor mayor a 0\n o la ruta del archivo no este vacia", 2);
            formInter.getTxtRuta().requestFocus();
        }
        return resp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formInter.getBtnCancelar()) {
            formInter.dispose();
        }

        if (e.getSource() == formInter.getBtnCargar()) {
            //--datos de prueba
            //CrearExcel ex1 = new CrearExcel();                    
                 //ex1.crear();
            if (validar()) {
            try {
                    Excel(hoja);
            } catch (Exception ex) {
                Herramientas.mensaje("Error " + ex, 0);
                //Logger.getLogger(CargarExController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        /*try {
            List<String> metadatos = new ArrayList<>();            
             metadatos=CargarExJDBC.getMetadosTabla(Db.conectar(),"ctx");
                for (String metadato : metadatos) {
                    System.out.println("= "+metadato);
                }
            } catch (Exception ex) {
                Logger.getLogger(CargarExController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == formInter.getTxtHoja()) {
            char c = e.getKeyChar();
            if ((!Character.isDigit(c))
                    || (c == KeyEvent.VK_ENTER)
                    || (c == KeyEvent.VK_BACK_SPACE)
                    || (c == KeyEvent.VK_DELETE)) {
                e.consume();
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == formInter.getTxtRuta()) {
            if (e.getKeyCode() == e.VK_F1) {
                fileChooser();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
