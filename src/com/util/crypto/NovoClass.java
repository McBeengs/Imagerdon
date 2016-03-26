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
package com.util.crypto;

import com.util.xml.XmlManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NovoClass {

    public static void main(String[] args) throws IOException {
        PasswordManager teste = new PasswordManager();
        XmlManager xml = new XmlManager();
        xml.loadFile("config\\options.xml");
        
        //lock username
        byte[] lock = teste.encrypt("TheMcBeenga10", "12345678".getBytes(), "12345678".getBytes());
        String temp = Arrays.toString(lock);
        xml.setContentById("DAuser", temp.substring(1, temp.length() - 1));
        
        //lock password
        byte[] lockPass = teste.encrypt("suckmyb44lz", "12345678".getBytes(), "12345678".getBytes());
        String temp2 = Arrays.toString(lockPass);
        xml.setContentById("DApass", temp2.substring(1, temp2.length() - 1));
        
        //unlock username
        String user = teste.decrypt(teste.stringToByte(xml.getContentById("DAuser")), "12345678".getBytes(), "12345678".getBytes());
        
        //unlock password
        String pass = teste.decrypt(teste.stringToByte(xml.getContentById("DApass")), "12345678".getBytes(), "12345678".getBytes());
        
        System.out.println(user);
        System.out.println(pass);
        xml.saveXml();
    }
    
    
}
