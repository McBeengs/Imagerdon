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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
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
    private List<String> values;
    private final List<Integer> numbers;
    private int start = 0;
    private int end = 0;
    private int fixer;

    public XmlManager() {
        values = new ArrayList<>();
        numbers = new ArrayList<>();
    }

    /**
     * Loads an already existing XML file and sets this instance with whatever
     * attributes and values that could be found there.
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
     * Loads an already existing XML file and sets this instance with whatever
     * attributes and values that could be found there.
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
     * Loads an already existing file, clears his content and automatically sets
     * this instance to work with it.
     *
     * @param file the file that will be cleared and 
     * @exception IOException if an external problem is impeding the correct
     * loading of the file
     */
    public void createFile(File file) throws IOException {
        filePath = file.getAbsolutePath();

        content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n\n</root>";
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Creates an new tag that will be attached to other pre-existing tag,
     * especifically as the last node.
     * <p>
     * Example: Say you have this structure:
     * <blockquote><pre>
     * {@code <?xml version="1.1" encoding="UTF-8"?>}
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
     * {@code <?xml version="1.1" encoding="UTF-8"?>}
     * {@code <yum>}
     * {@code     <fruit>Apple</fruit>}
     * {@code     <food>Hamburguer</food>}
     * {@code     <candy>Bubblegun</candy>}
     * {@code     <drink></drink>}
     * {@code </yum>}
     * </pre></blockquote>
     *
     * After created, use the method {@code setContentByName()} to add content to
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

    /**
     * Searchs the tag provided in this instance and returns {@code true} if,
     * and only if there's at least 1 perfect match
     *
     * @param tagName the tag that will be searched
     * @return the result
     */
    public boolean checkIfTagExists(String tagName) {
        return content.contains("<" + tagName);
    }

    /**
     * Searchs both the tag provided and his content in this instance and
     * returns {@code true} if, and only if there's at least 1 perfect match
     *
     * @param tagName the tag that will be searched
     * @param tagContent the content inside the tag
     * @return the result
     */
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

    /**
     * Searchs both the tag provided and his content in this instance and
     * returns the exact node number it is. If it doesn't exist, this
     * method will return {@code -1} instead.
     *
     * @param tagName the tag that will be searched
     * @param tagContent the content inside the tag
     * @return the node index
     */
    public int getTagIndex(String tagName, String tagContent) {
        if (tagName.isEmpty()) {
            throw new IllegalArgumentException("The tag name field is empty.");
        }

        values.clear();
        selectValues(tagName, this.content);

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equals(tagContent)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Searchs an specific tag name in this instance and returns the value
     * founded inside the tag
     *
     * @param tagName the tag that you want to obtain the value
     * @param item the occurence of the tag
     * @return the value founded
     */
    public String getContentByName(String tagName, int item) {
        values.clear();
        selectValues(tagName, content);
        String send = values.get(item);
        values.clear();

        return send;
    }

    /**
     * Searchs an specific tag name in this instance and returns all contents founded
     * within elements of the same name
     *
     * @param tagName the tag that you want to obtain the values
     * @return the values founded
     */
    public List<String> getAllContentsByName(String tagName) {
        if (content.contains(tagName)) {
            values.clear();
            temp = content;
            selectValues(tagName, temp);
        }

        List<String> out = new ArrayList<>();

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
        int fixer2 = temp.indexOf(">");
        temp = temp.substring(fixer2);

        if (temp.contains("<" + tagName)) {
            selectValues(tagName, temp);
        }

        return false;
    }

    /**
     * Searchs an specific tag ID in this instance and returns the content of the first
     * perfect match founded
     *
     * @param id the tag ID that you want to obtain the value
     * @return the value founded
     */
    public String getContentById(String id) {
        if (content.contains("id=\"" + id + "\"")) {
            temp = content.substring(content.indexOf("id=\"" + id + "\""));
            start = temp.indexOf(">") + 1;
            found = temp.substring(start);

            end = found.indexOf("</");
            found = found.substring(0, end);
        } else {
            throw new IllegalArgumentException("The ID \"" + id + "\" doesn't exist.");
        }

        return found;
    }

    /**
     * Searchs an specific tag in this instance with an attribute provided
     * ({@code <string foo="value"></string>}) and returns the value founded inside the tag
     *
     * @param tagName the tag that you want to obtain the values
     * @param item the tag index
     * @param attrName the tag attribute
     * @return the value founded
     */
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

    /**
     * Searchs an specific tag name in this instance and sets his content with the value
     * provided
     *
     * @param tagName the tag that you want to set the value
     * @param item the occurence of the tag
     * @param value the new content of the tag
     */
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

    /**
     * Searchs an specific tag ID in this instance and sets his content with the value
     * provided
     *
     * @param id the tag ID that you want to set the value
     * @param value the new content of the tag
     */
    public void setContentById(String id, String value) {
        StringBuilder builder = new StringBuilder(content);
        int c;
        int d;
        temp = content;

        c = content.indexOf("id=\"" + id + "\"") + id.length();
        temp = temp.substring(c);
        c = c + temp.indexOf(">") + 1;
        temp = content.substring(c);

        d = content.indexOf("</", c);
        builder.replace(c, d, value);

        content = builder.toString();
    }

    /**
     * Searchs an specific tag in this instance with an attribute provided
     * ({@code <string foo="value"></string>}) and sets sets his content with the value
     * provided
     * @param tagName the tag that you want to set the values
     * @param item the tag index
     * @param attrName the tag attribute
     * @param value the new content of the tag
     */
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
     * Searchs an specific tag name in this instance and removes it from his father,
     * erasing it from the node
     *
     * @param tagName the tag that you want to delete
     * @param item the occurence of the tag
     */
    public void deleteTagByName(String tagName, int item) {
        StringBuilder builder = new StringBuilder(content);
        int c = 0;
        temp = content;

        if (temp.contains("<" + tagName) || temp.contains("<" + tagName + " ")) {
            for (int i = 0; i <= item; i++) {
                start = temp.indexOf("<" + tagName);
                temp = temp.substring(start);
                c += start + end;

                end = temp.indexOf(">") + 1;
                temp = temp.substring(end);

            }
        } else {
            throw new IllegalArgumentException("The tag \"" + tagName + "\" wasn't found.");
        }

        String helper = (content.substring(c, content.length()));

        builder.replace(c, c + helper.indexOf("\n"), "");
        content = builder.toString();
    }

    /**
     * Searchs an specific tag ID in this instance and removes it from his father,
     * erasing it from the node
     *
     * @param id the tag ID that you want to delete
     */
    public void deleteTagById(String id) {
        if (content.contains("id=\"" + id + "\"")) {
            start = content.indexOf("id=\"" + id + "\"");
            temp = content.substring(start);

            for (int i = 1; i < 100; i++) {
                String first = content.substring(start - i, start - (i - 1));
                temp = first + temp;
                if (first.equals("<")) {
                    break;
                }
            }

            start = content.length() - temp.length();
            StringBuilder builder = new StringBuilder(content);
            String helper = (content.substring(start, content.length()));

            builder.replace(start, start + helper.indexOf("\n"), "");
            content = builder.toString();
        } else {
            throw new IllegalArgumentException("The ID \"" + id + "\" doesn't exist.");
        }
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
     * Saves the XML content of this instance on the file / path that was
     * indicated back on the instance loading. It automatically formats the content
     * before saving
     *
     * @throws java.io.IOException if an external problem is impeding the correct
     * saving of the file
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

    /**
     * Returns the XML content of this instance without formatting, indenting or
     * checking for errors in parsing. It can be use for debugging purposes as
     * how changes are being handled by the API, however, it can be
     * formatted with {@code formatXml()} before the call of this method
     *
     * @return the content
     */
    @Override
    public String toString() {
        return content;
    }
}
