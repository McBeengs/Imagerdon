/* **********   Serialize.java   **********
 *
 * This piece of garbage was brought to you by nothing less than the almighty lord
 * of programming, the Java God and ruler of all the non living things, McBeengs, 
 * A.K.A. myself. I don't mind anyone steal or using my codes at their own business,
 * but at least, and I meant VERY least, give me the proper credit for it. I really
 * don't know what the code below does at this point in time while I write this stuff, 
 * but if you took all this time to sit, rip the .java files and read all this 
 * unnecessary bullshit, you know for what you came, doesn't ?
 * 
 * Copyright(c) {YEAR!!!} Mc's brilliant mind. All Rights (kinda) Reserved.
 */

 /*
 * {Insert class description here}
 */
package com.util.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializeManager implements Serializable {

    private static final long serialVersionUID = 709115126951893280L;

    public void saveObject(String path, Object obj) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(obj);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SerializeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object loadObject(String path) {
        Object send = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            send = in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SerializeManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return send;
    }
}
