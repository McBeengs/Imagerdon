/* **********   NovoClass.java   **********
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NovoClass {

    public static void main(String arr[]) throws URISyntaxException {
        try {
            Map<String, Map<URI, String>> hashmap = new HashMap<>();
            Map<URI, String> sec = new HashMap<>();
            
            sec.put(new URI("google.com"), "dos");
            hashmap.put("lero", sec);

            FileOutputStream fos;

            fos = new FileOutputStream("list.txt");

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hashmap);
            oos.close();

            FileInputStream fis = new FileInputStream("list.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map<String, String> anotherList = (Map<String, String>) ois.readObject();

            ois.close();

            System.out.println(anotherList);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NovoClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NovoClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
