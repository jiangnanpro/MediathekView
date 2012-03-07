/*    
 *    MediathekView
 *    Copyright (C) 2012   W. Xaver
 *    W.Xaver[at]googlemail.com
 *    http://zdfmediathk.sourceforge.net/
 *    
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mediathek.gui.dialogEinstellungen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mediathek.Log;
import mediathek.controller.filme.filmeImportieren.filmUpdateServer.FilmUpdateServer;
import mediathek.controller.io.IoXmlLesen;
import mediathek.controller.io.ListeProgrammgruppenVorlagen;
import mediathek.daten.DDaten;
import mediathek.daten.DatenPgruppe;
import mediathek.gui.PanelVorlage;
import mediathek.tool.GuiFunktionen;
import mediathek.tool.TModel;

public class PanelImportPgruppe extends PanelVorlage {

    public PanelImportPgruppe(DDaten d) {
        super(d);
        initComponents();
        init();
    }

    private void init() {
        jButtonImportDatei.setEnabled(false);
        jButtonImportText.setEnabled(false);
        jButtonPfad.addActionListener(new BeobPfad());
        jTextFieldDatei.getDocument().addDocumentListener(new BeobPfadDoc());
        jTextAreaImport.getDocument().addDocumentListener(new BeobTextArea());
        jButtonImportDatei.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                importDatei();
            }
        });
        jButtonImportText.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                importText();
            }
        });
        tabelleLaden();
    }

    private void importDatei() {
        DatenPgruppe[] pGruppe;
        pGruppe = IoXmlLesen.importPgruppe(jTextFieldDatei.getText(), true);
        if (pGruppe != null) {
            if (ddaten.listePgruppe.addPgruppe(pGruppe)) {
                JOptionPane.showMessageDialog(null, pGruppe.length + " Programmgruppe(n) importiert!",
                        "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Die Datei konnte nicht importiert werden!",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importText() {
        DatenPgruppe[] pGruppe;
        pGruppe = IoXmlLesen.importPgruppeText(jTextAreaImport.getText(), true);
        if (pGruppe != null) {
            if (ddaten.listePgruppe.addPgruppe(pGruppe)) {
                JOptionPane.showMessageDialog(null, pGruppe.length + " Programmgruppe(n) importiert!",
                        "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Der Import war nicht möglich!",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tabelleLaden() {
        ListeProgrammgruppenVorlagen l = new ListeProgrammgruppenVorlagen();
        l.getListe();
        jTableVorlagen.setModel(l.getTModel());
        for (int i = 0; i < jTableVorlagen.getColumnCount(); ++i) {
            if (i == FilmUpdateServer.FILM_UPDATE_SERVER_URL_NR) {
                jTableVorlagen.getColumnModel().getColumn(i).setMinWidth(10);
                jTableVorlagen.getColumnModel().getColumn(i).setMaxWidth(3000);
                jTableVorlagen.getColumnModel().getColumn(i).setPreferredWidth(350);
            } else {
                jTableVorlagen.getColumnModel().getColumn(i).setMinWidth(10);
                jTableVorlagen.getColumnModel().getColumn(i).setMaxWidth(3000);
                jTableVorlagen.getColumnModel().getColumn(i).setPreferredWidth(100);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableVorlagen = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTextFieldDatei = new javax.swing.JTextField();
        jButtonPfad = new javax.swing.JButton();
        jButtonImportDatei = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaImport = new javax.swing.JTextArea();
        jButtonImportText = new javax.swing.JButton();

        jTableVorlagen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableVorlagen);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Vorlagen", jPanel3);

        jButtonPfad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mediathek/res/fileopen_16.png"))); // NOI18N

        jButtonImportDatei.setText("Importieren");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldDatei)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPfad))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 471, Short.MAX_VALUE)
                        .addComponent(jButtonImportDatei)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonPfad)
                    .addComponent(jTextFieldDatei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonImportDatei)
                .addContainerGap(262, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonPfad, jTextFieldDatei});

        jTabbedPane1.addTab("Aus Datei imporieren", jPanel1);

        jTextAreaImport.setColumns(20);
        jTextAreaImport.setRows(5);
        jScrollPane1.setViewportView(jTextAreaImport);

        jButtonImportText.setText("Importieren");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonImportText)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonImportText)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Als Text importieren", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonImportDatei;
    private javax.swing.JButton jButtonImportText;
    private javax.swing.JButton jButtonPfad;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableVorlagen;
    private javax.swing.JTextArea jTextAreaImport;
    private javax.swing.JTextField jTextFieldDatei;
    // End of variables declaration//GEN-END:variables

    private class BeobPfadDoc implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent arg0) {
            eingabe();
        }

        @Override
        public void removeUpdate(DocumentEvent arg0) {
            eingabe();
        }

        @Override
        public void changedUpdate(DocumentEvent arg0) {
            eingabe();
        }

        private void eingabe() {
            jButtonImportDatei.setEnabled(!jTextFieldDatei.getText().equals(""));
            if (jTextFieldDatei.getText().equals("")) {
                jTextFieldDatei.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.background"));
            } else {
                if (IoXmlLesen.importPgruppe(jTextFieldDatei.getText(), false) != null) {
                    jTextFieldDatei.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.background"));
                } else {
                    jTextFieldDatei.setBackground(new Color(255, 200, 200));
                }
            }
        }
    }

    private class BeobTextArea implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent arg0) {
            eingabe();
        }

        @Override
        public void removeUpdate(DocumentEvent arg0) {
            eingabe();
        }

        @Override
        public void changedUpdate(DocumentEvent arg0) {
            eingabe();
        }

        private void eingabe() {
            jButtonImportText.setEnabled(!jTextAreaImport.getText().equals(""));
            if (jTextAreaImport.getText().equals("")) {
                jTextAreaImport.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.background"));
            } else {
                if (IoXmlLesen.importPgruppeText(jTextAreaImport.getText(), false) != null) {
                    jTextAreaImport.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.background"));
                    jButtonImportText.setEnabled(true);
                } else {
                    jTextAreaImport.setBackground(new Color(255, 200, 200));
                    jButtonImportText.setEnabled(false);
                }
            }
        }
    }

    private class BeobPfad implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal;
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileHidingEnabled(false);
            if (jTextFieldDatei.getText().equals("")) {
                chooser.setCurrentDirectory(new File(GuiFunktionen.getHomePath()));
            } else {
                chooser.setCurrentDirectory(new File(jTextFieldDatei.getText()));
            }
            returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    jTextFieldDatei.setText(chooser.getSelectedFile().getAbsolutePath());
                } catch (Exception ex) {
                    Log.fehlerMeldung("PanelImportProgramme.BeobPfad", ex);
                }
            }
        }
    }
}
