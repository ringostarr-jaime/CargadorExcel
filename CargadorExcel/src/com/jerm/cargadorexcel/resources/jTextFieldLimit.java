
package com.jerm.cargadorexcel.resources;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author jerodas
 */
public class jTextFieldLimit extends PlainDocument {

    public int limit;
    
    //Llamarlo desde la propiedad documents de los jTextField con (property using custom code), agregar el import en la vista
    //new jTextFieldLimit(?) donde ? se remplaza con el numero de caracteres maximo
     public jTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

     /*jTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }*/

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}

