/* **********   XmlManager.java   **********
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
package com.util.xml;

import org.apache.xml.serialize.OutputFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlManager {

    private String content;
    private String filePath;
    private String temp;
    private String found;
    private ArrayList<String> values;
    private final ArrayList<Integer> numbers;
    private int start = 0;
    private int end = 0;
    private int fixer;

    public XmlManager() {
        values = new ArrayList<>();
        numbers = new ArrayList<>();
    }

    /**
     * Loads an already existing XML file and sets the content of this instance
     * with whatever attributes and values that could be found there.
     *
     * @param path the path that leads to the desired file
     */
    public void loadFile(String path) {
        FileReader fileReader;
        try {
            filePath = path;
            File xml = new File(path);
            fileReader = new FileReader(xml);
            BufferedReader reader = new BufferedReader(fileReader);
            content = reader.readLine();

            while (!content.endsWith("null")) {
                content += "\n";
                content += reader.readLine();
            }

            //Removes the "null" occurence of the XML
            content = content.substring(0, content.length() - 5);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loads an already existing XML file and sets the content of this instance
     * with whatever attributes and values that could be found there.
     *
     * @param file the desired file
     * @exception FileNotFoundException if the file provided couldn't be founded
     * @exception IOException if an external problem is impeding the correct
     * editing of the file
     */
    public void loadFile(File file) throws FileNotFoundException, IOException {
        filePath = file.getAbsolutePath();
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        content = reader.readLine();

        while (!content.endsWith("null")) {
            content += "\n";
            content += reader.readLine();
        }

        //Removes the "null" occurence of the XML
        content = content.substring(0, content.length() - 5);
    }

    /**
     * Creates an new XML file and automatically sets this instance to work with
     * it.
     *
     * @param path the path that the file will be created. Make sure that the
     * file name is also incluided on the final path.
     * @exception IOException if an external problem is impeding the correct
     * creation of the file
     */
    public void createFile(String path) throws IOException {
        filePath = path;

        content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n\n</root>";
    }

    /**
     * Creates an new tag that will not be attached to any other pre-existing
     * tags, essentialy adding another root element.
     * <p>
     * Example: Say you have this structure:
     * <blockquote><pre>
     * {@code <yum>}
     * {@code     <fruit>Apple</fruit>}
     * {@code     <food>Hamburguer</food>}
     * {@code     <candy>Bubblegun</candy>}
     * {@code </yum>}
     * </pre></blockquote>
     *
     * While {@code <yum>} is the only root element on this file, the command
     * {@code addIndependedTag("drink")} would produce the following output:
     *
     * <blockquote><pre>
     * {@code <yum>}
     * {@code     <fruit>Apple</fruit>}
     * {@code     <food>Hamburguer</food>}
     * {@code     <candy>Bubblegun</candy>}
     * {@code </yum>}
     * {@code <drink>}
     * {@code     }
     * {@code </drink>}
     * </pre></blockquote>
     *
     * @param tagName the tag name that will be creaded
     */
    public void addIndependedTag(String tagName) {
        content += "\n<" + tagName + ">\n    \n</" + tagName + ">";
    }

    /**
     * Creates an new tag that will be attached to other pre-existing tag,
     * especifically as the last node.
     * <p>
     * Example: Say you have this structure:
     * <blockquote><pre>
     * {@code <yum>}
     * {@code     <fruit>Apple</fruit>}
     * {@code     <food>Hamburguer</food>}
     * {@code     <candy>Bubblegun</candy>}
     * {@code </yum>}
     * </pre></blockquote>
     *
     * Considering {@code <yum>} as the node that will receive the new tag, the
     * command {@code addSubordinatedTag("drink", "yum", "0")} would produce the
     * following output:
     *
     * <blockquote><pre>
     * {@code <yum>}
     * {@code     <fruit>Apple</fruit>}
     * {@code     <food>Hamburguer</food>}
     * {@code     <candy>Bubblegun</candy>}
     * {@code     <drink></drink>}
     * {@code </yum>}
     * </pre></blockquote>
     *
     * After created, use the method{@code setContentByName()} to add content to
     * the tag.
     *
     * @param tagName the tag name that will be creaded
     * @param rootTagName the tag that will receive the new one
     * @param rootOccurance the occurence of the tag
     */
    public void addSubordinatedTag(String tagName, String rootTagName, int rootOccurance) {
        if (tagName.isEmpty()) {
            throw new IllegalArgumentException("The tag name field is empty.");
        }

        if (rootTagName.isEmpty()) {
            throw new IllegalArgumentException("The root tag name field is empty.");
        } else {
            fixer = 0;
            setRootsPositions(rootTagName, content);
        }

        if (numbers.size() <= rootOccurance) {
            throw new ArrayIndexOutOfBoundsException("The tag \"" + rootTagName + "\" only has " + numbers.size() + " occurences");
        }

        start = numbers.get(rootOccurance) - (rootTagName.length() + 3);
        end = content.substring(start).indexOf("</" + rootTagName) + start;
        temp = content.substring(end, content.length());
        content = content.substring(0, end);
        content += "<" + tagName + "></" + tagName + ">\n" + temp;

        numbers.clear();
    }

    private int pos;

    private boolean setRootsPositions(String tagName, String str) {
        if (str.contains("<" + tagName + ">") || str.contains("<" + tagName + " ")) {
            start = str.indexOf("<" + tagName);

            if (str.contains("</" + tagName)) {
                end = str.indexOf("</" + tagName) + tagName.length() + 3;
                temp = str.substring(end);
            } else {
                throw new IllegalArgumentException("The tag \"" + tagName + "\" is unclosed.");
            }
        } else {
            throw new IllegalArgumentException("The tag \"" + tagName + "\" wasn't found.");
        }
        pos = content.substring(0, end).length();
        if (numbers.size() > 0) {
            numbers.add(pos + numbers.get(fixer - 1));
        } else {
            numbers.add(pos);
        }
        fixer++;

        if (temp.contains("<" + tagName)) {
            setRootsPositions(tagName, temp);
        }

        pos = 0;
        return false;
    }

    public boolean checkIfTagExists(String tagName) {
        return content.contains("<" + tagName);
    }

    public boolean checkIfTagExists(String tagName, String tagContent) {
        if (!checkIfTagExists(tagName)) {
            return false;
        }

        values = getAllContentsByName(tagName);
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equals(tagContent)) {
                return true;
            }
        }

        values.clear();
        return false;
    }

    public int getTagIndex(String tagName, String content) {
        if (tagName.isEmpty()) {
            throw new IllegalArgumentException("The tag name field is empty.");
        }

        selectValues(tagName, this.content);

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equals(content)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Searchs the provided file for an specific tag name and returns the value
     * founded inside the tag
     *
     * @param tagName the tag that you wants to obtain the value
     * @param item the occurence of the tag; say you wanted to get the third of
     * four occurences, so you would type "2" on this variable
     * @return the value founded on the content
     */
    public String getContentByName(String tagName, int item) {
        selectValues(tagName, content);
        String send = values.get(item);
        values.clear();

        return send;
    }

    public ArrayList<String> getAllContentsByName(String tagName) {
        if (content.contains(tagName)) {
            temp = content;
            selectValues(tagName, temp);
        }

        ArrayList<String> out = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            out.add(values.get(i));
        }

        values.clear();

        return out;
    }

    private boolean selectValues(String tagName, String str) {
        if (str.contains("<" + tagName + ">") || str.contains("<" + tagName + " ")) {
            fixer = str.indexOf("<" + tagName, 0);
            temp = str.substring(fixer);
            start = temp.indexOf(">") + 1;

            if (temp.contains("</" + tagName)) {
                end = temp.indexOf("</" + tagName, start);
            } else {
                throw new IllegalArgumentException("The tag \"" + tagName + "\" is unclosed.");
            }
        } else {
            throw new IllegalArgumentException("The tag \"" + tagName + "\" wasn't found.");
        }

        found = temp.substring(start, end);
        values.add(found);

        temp = temp.substring(end);
        int fixer2 = temp.indexOf(">");  // Aqui costumava ter um + 2
        temp = temp.substring(fixer2);

        if (temp.contains("<" + tagName)) {
            selectValues(tagName, temp);
        }

        return false;
    }

    public String getContentById(String id) {
        if (content.contains("id=\"" + id + "\"")) {
            temp = content.substring(content.indexOf(id));
            start = temp.indexOf(">") + 1;
            found = temp.substring(start);

            end = found.indexOf("<");
            found = found.substring(0, end);
        } else {
            throw new IllegalArgumentException("The ID \"" + id + "\" doesn't exist.");
        }

        return found;
    }

    public String getContentByAttribute(String tagName, int item, String attrName) {
        if (tagName.isEmpty()) {
            throw new IllegalArgumentException("The tag name field is empty.");
        }

        if (attrName.isEmpty()) {
            throw new IllegalArgumentException("The attribute field is empty");
        }

        found = null;
        selectAttr(tagName, content, attrName);

        if (values.size() <= item) {
            throw new ArrayIndexOutOfBoundsException("The tag \"" + tagName + "\" only has " + numbers.size() + " occurences");
        }

        if (item + 1 > values.size()) {
            throw new IllegalArgumentException("The index provided doesn't exist");
        } else {
            found = values.get(item);
            values.clear();
        }

        return found;
    }

    private boolean selectAttr(String tagName, String str, String attrName) {
        if (str.contains("<" + tagName + " ")) {
            fixer = str.indexOf("<" + tagName, 0);
            temp = str.substring(fixer);
            if (temp.contains("/>")) {
                end = temp.indexOf("/>");
            } else if (temp.contains("</")) {
                end = temp.indexOf("</");
            }

            if (temp.contains(attrName + "=\"")) {
                found = temp.substring(0, end);
                start = found.indexOf(attrName + "=\"") + attrName.length() + 2;
                end = found.indexOf("\"", tagName.length() + attrName.length() + 4);
                found = found.substring(start, end);
            } else {
                throw new IllegalArgumentException("The Attribute \"" + attrName + "\" wasn't found on the tag \"" + tagName + "\"");
            }
        } else {
            throw new IllegalArgumentException("The tag \"" + tagName + "\" wasn't found.");
        }

        start = temp.indexOf(found);
        temp = temp.substring(start);
        values.add(found);

        if (temp.contains("<" + tagName)) {
            selectAttr(tagName, temp, attrName);
        }

        return false;
    }

    public void setContentByName(String tagName, int item, String value) {
        StringBuilder builder = new StringBuilder(content);
        int c = 0;
        int d;
        temp = content;

        if (temp.contains("<" + tagName) || temp.contains("<" + tagName + " ")) {
            for (int i = 0; i <= item; i++) {
                start = temp.indexOf("<" + tagName);
                temp = temp.substring(start);

                start = temp.indexOf(">") + 1;
                temp = temp.substring(start);
                c = content.length() - temp.length();
            }
        } else {
            throw new IllegalArgumentException("The tag \"" + tagName + "\" wasn't found.");
        }

        String helper = (content.substring(c, content.length()));
        d = helper.indexOf("<");

        builder.replace(c, d + c, value);

        content = builder.toString();
    }

    public void setContentById(String id, String value) {
        StringBuilder builder = new StringBuilder(content);
        int c;
        int d;
        temp = content;

        c = content.indexOf(id) + id.length();
        temp = temp.substring(c);
        c = c + temp.indexOf(">") + 1;
        temp = content.substring(c);

        d = content.indexOf("</", c);
        builder.replace(c, d, value);

        content = builder.toString();
    }

    public void setContentByAttribute(String tagName, String attrName, int item, String value) {
        if (tagName.isEmpty()) {
            throw new NoSuchFieldError("The tag name field is empty");
        }

        if (attrName.isEmpty()) {
            throw new NoSuchFieldError("The attribute field is empty");
        }

        StringBuilder builder = new StringBuilder(content);
        int c = 0;
        int d;
        temp = content;

        for (int i = 0; i <= item; i++) {
            start = temp.indexOf("<" + tagName + " ");
            temp = temp.substring(start);
            start = temp.indexOf(attrName);
            temp = temp.substring(start + attrName.length() + 2);

            c = content.length() - temp.length();
        }

        String helper = content.substring(c, content.length());
        d = helper.indexOf("\"");

        builder.replace(c, d + c, value);

        content = builder.toString();
    }

    /**
     * Formats the XML content of this instance to nicely re-arrange the nodes,
     * adding indents and line-breaks as needed. This method is automatically
     * called when saving.
     *
     */
    public void formatXml() {
        Document document = parseXmlFile();
        if (document == null) {
            throw new UnsupportedOperationException("Error while parsing the content");
        }

        OutputFormat format = new OutputFormat(document);
        format.setIndenting(true);
        format.setIndent(2);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        try {
            serializer.serialize(document);
        } catch (IOException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        content = out.toString();
    }

    private Document parseXmlFile() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(content));
            return db.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Saves
     *
     * @throws java.io.IOException
     */
    public void saveXml() throws IOException {
        formatXml();
        File newXml = new File(filePath);
        if (newXml.exists()) {
            newXml.delete();
        }
        
        newXml.createNewFile();
        FileUtils.writeStringToFile(newXml, content);
    }

    public String getContent() {
        return content;
    }
}
