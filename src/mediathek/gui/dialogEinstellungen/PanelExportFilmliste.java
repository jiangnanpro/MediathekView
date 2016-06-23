/*
 * MediathekView
 * Copyright (C) 2008 W. Xaver
 * W.Xaver[at]googlemail.com
 * http://zdfmediathk.sourceforge.net/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package mediathek.gui.dialogEinstellungen;

import com.jidesoft.utils.SystemInfo;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mediathek.controller.Log;
import mediathek.daten.Daten;
import mediathek.gui.PanelVorlage;
import mediathek.res.GetIcon;
import mediathek.tool.Duration;
import mediathek.tool.MVConfig;
import mediathek.tool.MVMessageDialog;
import mSearch.filmlisten.WriteFilmlistJson;

public class PanelExportFilmliste extends PanelVorlage {

    public String ziel;

    public PanelExportFilmliste(Daten d, JFrame parent) {
        super(d, parent);
        initComponents();
        init();
    }

    private void init() {
        jButtonExportPfad.setIcon(GetIcon.getProgramIcon("fileopen_16.png"));
        jTextFieldPfad.setText(Daten.mVConfig.get(MVConfig.SYSTEM_EXPORT_DATEI));
        jTextFieldPfad.getDocument().addDocumentListener(new BeobTextFeld());
        jButtonExportieren.addActionListener(new BeobExport());
        jButtonExportPfad.addActionListener(new BeobPfad());
    }

    private void filmeExportieren() {
        int ret;
        String exporDatei = Daten.mVConfig.get(MVConfig.SYSTEM_EXPORT_DATEI);
        if (exporDatei.equals("")) {
            MVMessageDialog.showMessageDialog(parentComponent, "Keine Datei angegeben", "Pfad", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                if (new File(exporDatei).exists()) {
                    ret = JOptionPane.showConfirmDialog(parentComponent, "Datei:  " + "\"" + exporDatei + "\"" + "  existiert bereits", "Überschreiben?",
                            JOptionPane.YES_NO_OPTION);
                } else {
                    ret = JOptionPane.OK_OPTION;
                }
                if (ret == JOptionPane.OK_OPTION) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    updateUI();
                    Duration duration = new Duration(PanelExportFilmliste.class.getSimpleName());
                    duration.start("Filmliste schreiben: " + exporDatei);
                    new WriteFilmlistJson().filmlisteSchreibenJson(exporDatei, Daten.listeFilme);
                    duration.stop("fertig");
                    if (!new File(exporDatei).exists()) {
                        MVMessageDialog.showMessageDialog(parentComponent, "Datei:  " + "\"" + exporDatei + "\"" + "  Konnte nicht erstellt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                Log.fehlerMeldung(464589201, ex);
            }
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
        jTextFieldPfad = new javax.swing.JTextField();
        jButtonExportPfad = new javax.swing.JButton();
        javax.swing.JLabel jLabelDatei = new javax.swing.JLabel();
        jButtonExportieren = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonExportPfad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mediathek/res/programm/fileopen_16.png"))); // NOI18N

        jLabelDatei.setText("Datei:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDatei)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldPfad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExportPfad)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextFieldPfad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDatei)
                    .addComponent(jButtonExportPfad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonExportPfad, jTextFieldPfad});

        jButtonExportieren.setText("Exportieren");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(7);
        jTextArea1.setText("Es kann die programmeigene Liste mit den Filmen exportiert werden.\n\nDateien werden mit der Endung:\n\n\".zip\"\tals Zip-Dateien,\n\".xz\" \tim XZ-Container mit LZMA2 komprimiert.\n\nAlle anderen Dateien werden unkomprimiert gespeichert.\n");
        jTextArea1.setMargin(new java.awt.Insets(4, 4, 4, 4));
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonExportieren)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonExportieren)
                .addContainerGap(66, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExportPfad;
    private javax.swing.JButton jButtonExportieren;
    private javax.swing.JTextField jTextFieldPfad;
    // End of variables declaration//GEN-END:variables

    private class BeobTextFeld implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            tusEinfach(e);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            tusEinfach(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            tusEinfach(e);
        }

        void tusEinfach(DocumentEvent e) {
            Daten.mVConfig.add(MVConfig.SYSTEM_EXPORT_DATEI, jTextFieldPfad.getText());
        }
    }

    private class BeobPfad implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //we can use native chooser on Mac...
            if (SystemInfo.isMacOSX()) {
                FileDialog chooser = new FileDialog(daten.mediathekGui, "Filme exportieren");
                chooser.setMode(FileDialog.SAVE);
                chooser.setVisible(true);
                if (chooser.getFile() != null) {
                    try {
                        File destination = new File(chooser.getDirectory() + chooser.getFile());
                        jTextFieldPfad.setText(destination.getAbsolutePath());
                    } catch (Exception ex) {
                        Log.fehlerMeldung(679890147, ex);
                    }
                }
            } else {
                int returnVal;
                JFileChooser chooser = new JFileChooser();
                if (!jTextFieldPfad.getText().equals("")) {
                    chooser.setCurrentDirectory(new File(jTextFieldPfad.getText()));
                }
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileHidingEnabled(false);
                returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        jTextFieldPfad.setText(chooser.getSelectedFile().getAbsolutePath());
                    } catch (Exception ex) {
                        Log.fehlerMeldung(911025463, ex);
                    }
                }
            }
        }
    }

    private class BeobExport implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            filmeExportieren();
        }
    }
}
