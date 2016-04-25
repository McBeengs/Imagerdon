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
package testArea;

import com.util.xml.XmlManager;

public class NovoClass {

    public static void main(String[] args) throws Exception {
        XmlManager xml = new XmlManager();
        xml.loadFile("C:\\Users\\Mc\\Desktop\\delete.txt");
        
        xml.setContentById("delete", "ayyy");
        System.out.println(System.getProperty("file.separator"));
    }
}
