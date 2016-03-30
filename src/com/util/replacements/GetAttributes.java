/* **********   GetOptions.java   **********
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
package com.util.replacements;

import javax.swing.JOptionPane;

public class GetAttributes {

    public static String getOptions() {
        //get OS
        String os = System.getProperty("os.name").toLowerCase();
        String path;

        //set folders arc.
        if (os.contains("win")) {
            path = System.getProperty("user.home") + "/Documents/Repository";
        } else if (os.contains("uni") || os.contains("nux") || os.contains("aix")) {
            path = System.getProperty("user.home") + "/Repository";
        } else if (os.contains("sunos")) {
            path = System.getProperty("user.home") + "";
        } else if (os.contains("mac")) {
            path = System.getProperty("user.home") + "";
        } else {
            JOptionPane.showMessageDialog(null, "Your OS doesn't support Java's JRE 7 or above.");
            return null;
        }

        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<root>\n"
                + "  <optionsPane>\n"
                + "    <gui>\n"
                + "      <language>English, 0</language>\n"
                + "      <style>Metal, 0</style>\n"
                + "    </gui>\n"
                + "    <downloads>\n"
                + "      <scroll id=\"simult\">5</scroll>\n"
                + "      <directory id=\"mainOutput\">" + path + "</directory>\n"
                + "      <boolean id=\"sub\">true</boolean>\n"
                + "      <dropdown id=\"existed\">2</dropdown>\n"
                + "    </downloads>\n"
                + "    <deviantArt>\n"
                + "      <string id=\"DAuser\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <string id=\"DApass\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <directory id=\"DAoutput\">" + path + "/DeviantArt" + "</directory>\n"
                + "      <boolean id=\"DAadvancedNaming\">false</boolean>\n"
                + "      <number id=\"DAnamingOption\">0</number>\n"
                + "    </deviantArt>\n"
                + "    <tumblr>\n"
                + "      <string id=\"TUuser\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <string id=\"TUpass\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <directory id=\"TUoutput\">" + path + "/Tumblr" + "</directory>\n"
                + "      <boolean id=\"TUadvancedNaming\">false</boolean>\n"
                + "      <number id=\"TUnamingOption\">0</number>\n"
                + "    </tumblr>\n"
                + "    <galleryHentai>\n"
                + "      <directory id=\"GHoutput\">" + path + "/Gallery Hentai" + "</directory>\n"
                + "      <boolean id=\"GHadvancedNaming\">false</boolean>\n"
                + "      <number id=\"GHnamingOption\">0</number>\n"
                + "    </galleryHentai>\n"
                + "    <furAffinity>\n"
                + "      <string id=\"FAuser\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <string id=\"FApass\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <directory id=\"FAoutput\">" + path + "/FurAffinity" + "</directory>\n"
                + "      <boolean id=\"FAadvancedNaming\">false</boolean>\n"
                + "      <number id=\"FAnamingOption\">0</number>\n"
                + "    </furAffinity>\n"
                + "    <e621>\n"
                + "      <directory id=\"E621output\">" + path + "/e621" + "</directory>\n"
                + "      <boolean id=\"E621advancedNaming\">false</boolean>\n"
                + "      <number id=\"E621namingOption\">0</number>\n"
                + "    </e621>\n"
                + "  </optionsPane>\n"
                + "</root>\n"
                + "";
    }
}
