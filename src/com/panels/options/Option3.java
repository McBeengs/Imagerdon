package com.panels.options;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Option3 extends javax.swing.JPanel {

    private final XmlManager xml;
    private final XmlManager language;
    private final PasswordManager pass;
    private final JFileChooser directory;
    private int buttonPressedResult;
    private String userOutput;

    public Option3(XmlManager xml) {
        this.xml = xml;
        language = new XmlManager();
        pass = new PasswordManager();
        userOutput = xml.getContentById("DAoutput");
        directory = new JFileChooser(userOutput);

        String set = xml.getContentByName("language", 0);
        set = set.substring(0, set.indexOf(","));
        language.loadFile("language\\" + set.toLowerCase() + ".xml");
        initComponents();

        if (Boolean.getBoolean(xml.getContentById("sub"))) {
            folderTextField.setEnabled(false);
            downloadsAlert.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        mainLabel = new javax.swing.JLabel();
        topSeparator = new javax.swing.JSeparator();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        folderLabel = new javax.swing.JLabel();
        folderTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        String user = pass.decrypt(pass.stringToByte(xml.getContentById("DAuser")), "12345678".getBytes(), "12345678".getBytes());
        passwordTextField = new javax.swing.JPasswordField();
        downloadsAlert = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setMinimumSize(new java.awt.Dimension(516, 434));

        mainLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainLabel.setText(language.getContentByName("mainLabel", 2) + " " + language.getContentById("options"));

        usernameLabel.setText(language.getContentById("username"));

        passwordLabel.setText(language.getContentById("password"));

        folderLabel.setText("Set default folder name");

        folderTextField.setEditable(false);
        folderTextField.setText(xml.getContentById("DAoutput"));
        folderTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                folderTextFieldMouseClicked(evt);
            }
        });

        jLabel1.setText(language.getContentById("changeLoginFA"));

        userTextField.setText(user);
        userTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                userTextFieldCaretUpdate(evt);
            }
        });

        passwordTextField.setText("example");
        passwordTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                passwordTextFieldCaretUpdate(evt);
            }
        });

        downloadsAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        downloadsAlert.setForeground(new java.awt.Color(51, 109, 243));
        downloadsAlert.setText(null);

        jButton2.setText("Try Login");
        jButton2.setFocusable(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jCheckBox1.setText("Use advance naming options");
        jCheckBox1.setFocusable(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Doesn't auto name the files with the image name, instead...");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Name then by order of upload");
        jRadioButton1.setFocusable(false);
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Name then by time of download");
        jRadioButton2.setFocusable(false);
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mainLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(folderLabel)
                                .addGap(18, 18, 18)
                                .addComponent(folderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(23, 23, 23)
                                .addComponent(downloadsAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBox1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton1))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(topSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(passwordLabel)
                    .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(downloadsAlert)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(folderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jButton2.setFocusPainted(false);
        jCheckBox1.setSelected(Boolean.parseBoolean(xml.getContentById("DAadvancedNaming")));
        jLabel2.setVisible(Boolean.parseBoolean(xml.getContentById("FAadvancedNaming")));
        jRadioButton1.setVisible(Boolean.parseBoolean(xml.getContentById("DAadvancedNaming")));
        jRadioButton1.setSelected(xml.getContentById("DAnamingOption").equals("0"));
        jRadioButton2.setVisible(Boolean.parseBoolean(xml.getContentById("DAadvancedNaming")));
        jRadioButton2.setSelected(xml.getContentById("DAnamingOption").equals("1"));
    }// </editor-fold>//GEN-END:initComponents

    private void userTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_userTextFieldCaretUpdate
        byte[] lock = pass.encrypt(userTextField.getText(), "12345678".getBytes(), "12345678".getBytes());
        String temp = Arrays.toString(lock);
        xml.setContentById("DAuser", temp.substring(1, temp.length() - 1));
    }//GEN-LAST:event_userTextFieldCaretUpdate

    private void passwordTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_passwordTextFieldCaretUpdate
        String getPass = "";
        char[] array = passwordTextField.getPassword();
        
        for (int i = 0; i < array.length; i++) {
            getPass += array[i];
        }
        
        byte[] lock = pass.encrypt(getPass, "12345678".getBytes(), "12345678".getBytes());
        String temp = Arrays.toString(lock);
        xml.setContentById("DApass", temp.substring(1, temp.length() - 1));
    }//GEN-LAST:event_passwordTextFieldCaretUpdate

    private void folderTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_folderTextFieldMouseClicked
        if (evt.getClickCount() == 2) {
            buttonPressedResult = directory.showOpenDialog(this);
            if (buttonPressedResult == JFileChooser.APPROVE_OPTION) {
                userOutput = directory.getSelectedFile().getPath();
                folderTextField.setText(userOutput);
                xml.setContentByName("directory", 0, userOutput);
            }
        }
    }//GEN-LAST:event_folderTextFieldMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        downloadsAlert.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        downloadsAlert.setForeground(new java.awt.Color(51, 109, 243));
        downloadsAlert.setText("lalalala");

        new Thread("Submitting Form") {
            @Override
            public void run() {
                if (submittingForm()) {
                    downloadsAlert.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                    downloadsAlert.setForeground(new java.awt.Color(50, 205, 50));
                    downloadsAlert.setText("Everything ok :)");
                } else {
                    downloadsAlert.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    downloadsAlert.setForeground(new java.awt.Color(238, 44, 44));
                    downloadsAlert.setText("Shit");
                }
            }
        }.start();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jLabel2.setVisible(true);
            jRadioButton1.setVisible(true);
            jRadioButton2.setVisible(true);
            xml.setContentById("DAadvancedNaming", "true");
        } else {
            jLabel2.setVisible(false);
            jRadioButton1.setVisible(false);
            jRadioButton2.setVisible(false);
            xml.setContentById("DAadvancedNaming", "false");
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        xml.setContentById("DAnamingOption", "0");
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        xml.setContentById("DAnamingOption", "1");
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void clearField() {
        userTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                userTextField.setText("");
            }
        });

        passwordTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                passwordTextField.setText("");
            }
        });
    }

    private boolean submittingForm() {
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setAppletEnabled(false);

            HtmlPage page1 = webClient.getPage("https://www.furaffinity.net/login/");
            HtmlForm form = page1.getFirstByXPath("//form [@method='post']");

            HtmlTextInput usernameField = form.getInputByName("name");
            HtmlPasswordInput passwordField = form.getInputByName("pass");
            HtmlSubmitInput button = form.getInputByName("login");
            
            String user = pass.decrypt(pass.stringToByte(xml.getContentById("FAuser")), "12345678".getBytes(), "12345678".getBytes());
            String passw = pass.decrypt(pass.stringToByte(xml.getContentById("FApass")), "12345678".getBytes(), "12345678".getBytes());

            usernameField.setValueAttribute(user.trim());
            passwordField.setValueAttribute(passw.trim());

            HtmlPage page2 = button.click();

            return !page2.getUrl().toString().equals("https://www.furaffinity.net/login/?msg=1");

        } catch (FailingHttpStatusCodeException | java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Connection with the Internet has dropped", "Error", JOptionPane.OK_OPTION);
        } catch (IOException ex) {
            Logger.getLogger(Option3.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel downloadsAlert;
    private javax.swing.JLabel folderLabel;
    private javax.swing.JTextField folderTextField;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JSeparator topSeparator;
    private javax.swing.JTextField userTextField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
