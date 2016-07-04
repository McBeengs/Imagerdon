/* **********   SetupJPanel.java   **********
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

package com.panels.modal;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.util.UsefulMethods;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SetupDAJPanel extends javax.swing.JPanel {

    private final XmlManager xml;
    private final XmlManager language;
    private final PasswordManager pass;
    
    public SetupDAJPanel(XmlManager xml) {
        this.xml = xml;
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        pass = new PasswordManager();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        artistNameLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        String user = pass.decrypt(pass.stringToByte(xml.getContentById("DAuser")), "12345678".getBytes(), "12345678".getBytes());
        passwordLabel = new javax.swing.JLabel();
        passwordTextField = new javax.swing.JPasswordField();
        downloadsAlert = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(490, 125));

        artistNameLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 18)); // NOI18N
        artistNameLabel.setText("Alpha V3 - Setup (DeviantArt)");

        usernameLabel.setText(language.getContentById("username"));

        userTextField.setText(user);
        userTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                userTextFieldCaretUpdate(evt);
            }
        });

        passwordLabel.setText(language.getContentById("password"));

        passwordTextField.setText("example");
        passwordTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                passwordTextFieldCaretUpdate(evt);
            }
        });

        downloadsAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        downloadsAlert.setForeground(new java.awt.Color(51, 109, 243));
        downloadsAlert.setText(null);

        jButton1.setText("Try Connection");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(artistNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(downloadsAlert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(93, 93, 93))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(artistNameLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(passwordLabel)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(downloadsAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        downloadsAlert.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        downloadsAlert.setForeground(new java.awt.Color(51, 109, 243));
        downloadsAlert.setText(language.getContentById("loginIn"));

        new Thread("Submitting Form") {
            @Override
            public void run() {
                if (submittingForm()) {
                    downloadsAlert.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                    downloadsAlert.setForeground(new java.awt.Color(50, 205, 50));
                    downloadsAlert.setText(language.getContentById("connectionOk"));
                    SetupContainer.NOTIFIER.enableNext();
                } else {
                    downloadsAlert.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    downloadsAlert.setForeground(new java.awt.Color(238, 44, 44));
                    downloadsAlert.setText(language.getContentById("connectionFailed"));
                }
            }
        }.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private boolean submittingForm() {
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setAppletEnabled(false);

            HtmlPage page1 = webClient.getPage("https://www.deviantart.com/");
            HtmlForm form = page1.getHtmlElementById("form-login");

            HtmlTextInput usernameField = form.getInputByName("username");
            HtmlPasswordInput passwordField = form.getInputByName("password");
            HtmlSubmitInput button = form.getInputByName("action");

            String user = pass.decrypt(pass.stringToByte(xml.getContentById("DAuser")), "12345678".getBytes(), "12345678".getBytes());
            String passw = pass.decrypt(pass.stringToByte(xml.getContentById("DApass")), "12345678".getBytes(), "12345678".getBytes());

            usernameField.setValueAttribute(user);
            passwordField.setValueAttribute(passw);

            HtmlPage page2 = button.click();
            
            return !page2.asXml().contains("The password you entered was incorrect");

        } catch (FailingHttpStatusCodeException | java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Connection with the Internet has dropped", "Error", JOptionPane.OK_OPTION);
        } catch (IOException ex) {
            Logger.getLogger(SetupDAJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel artistNameLabel;
    private javax.swing.JLabel downloadsAlert;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JTextField userTextField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables

}
