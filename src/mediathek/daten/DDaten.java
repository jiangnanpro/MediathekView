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
package mediathek.daten;

import java.io.File;
import javax.swing.JOptionPane;
import mediathek.MediathekGui;
import mediathek.controller.io.ErledigteAbos;
import mediathek.controller.io.History;
import mediathek.controller.io.IoXmlLesen;
import mediathek.controller.io.IoXmlSchreiben;
import mediathek.controller.io.starter.StarterClass;
import mediathek.gui.GuiAbo;
import mediathek.gui.GuiDownloads;
import mediathek.gui.GuiFilme;
import mediathek.gui.dialog.MVFilmInformation;
import mediathek.tool.DatumZeit;
import mediathek.tool.Konstanten;
import mediathek.tool.Log;
import mediathek.tool.MVMessageDialog;

/**
 * Diese Klasse enthält zusätzliche Konstanten, Systemeinstellungen und alles was wichtig ist für
 * {@link mediathek.MediathekGui} und {@link mediathek.MediathekAuto}.
 * Sie erweitert {@link mediathek.daten.Daten} für die Gui und Auto-Versionen
 */
public final class DDaten extends Daten {
    // Daten.listeFilme sind alle Filme,
    // DDaten.listeFilmeNachBlackList sind die Filme mit denen das Programm arbeitet, eben ohne die Blacklist

    public static ListeFilme listeFilmeNachBlackList = null;
    public ListeBlacklist listeBlacklist = null;
    public ListePset listePset = null;
    public ListeAbo listeAbo = null;
    public ListeDownloads listeDownloads = null;
    public History history = null;
    public ErledigteAbos erledigteAbos = null;
    // globale Objekte
    public IoXmlLesen ioXmlLesen = null;
    public IoXmlSchreiben ioXmlSchreiben = null;
    public StarterClass starterClass = null; // Klasse zum Ausführen der Programme: VLC, flvstreamer, ...
    // Panel
    public MediathekGui mediathekGui = null; // JFrame der Gui
    public GuiFilme guiFilme = null; // Tab mit den Filmen
    public GuiDownloads guiDownloads = null; // Tab mit den Downloads
    public GuiAbo guiAbo = null; // Tab mit den Abos
    // für die Tabellen
    public boolean nachDownloadShutDown = false;
    public MVFilmInformation filmInfoHud = null;

    public DDaten(String basis, boolean gui) {
        super(basis);
        listeFilmeNachBlackList = new ListeFilme();
        listeBlacklist = new ListeBlacklist();
        listePset = new ListePset();
        listeAbo = new ListeAbo(this);
        listeDownloads = new ListeDownloads(this);
        erledigteAbos = new ErledigteAbos(this);
        //initialisieren
        ioXmlLesen = new IoXmlLesen();
        ioXmlSchreiben = new IoXmlSchreiben();
        history = new History(getBasisVerzeichnis(true) + Konstanten.LOG_DATEI_HISTORY);
        starterClass = new StarterClass(this);
    }

    @Override
    public void allesLaden() {
        super.allesLaden();
        ioXmlLesen.datenLesen(this);
        history.laden();
    }

    @Override
    public void allesSpeichern() {
        super.allesSpeichern();
        ioXmlSchreiben.datenSchreiben(this);
        if (Daten.RESET) {
            // das Programm soll beim nächsten Start mit den Standardeinstellungen gestartet werden
            // dazu wird den Ordner mit den Einstellungen umbenannt
            String dir1 = DDaten.getBasisVerzeichnis();
            if (dir1.endsWith(File.separator)) {
                dir1 = dir1.substring(0, dir1.length() - 1);
            }
            String dir2 = dir1 + "--" + DatumZeit.getJetzt_yyyy_MM_dd__HH_mm_ss();
            try {
                if (new File(dir1).renameTo(new File(dir2))) {
                    // erster Versuch
                    return;
                }
                // für Win weitere Versuche
                if (new File(dir1).delete()) {
                    return;
                }
                Log.systemMeldung("Die Einstellungen konnten nicht zurückgesetzt werden.");
                MVMessageDialog.showMessageDialog(this.mediathekGui, "Die Einstellungen konnten nicht zurückgesetzt werden.\n"
                        + "Es wird versucht die Einstellungen beim Beenden zu löschen.\n"
                        + "Sollte auch das nicht klappen,\n"
                        + "finden Sie im Forum weitere Hilfe.", "Fehler", JOptionPane.ERROR_MESSAGE);
                new File(dir1).deleteOnExit();
            } catch (Exception e) {
                Log.fehlerMeldung(465690123, Log.FEHLER_ART_PROG, DDaten.class.getName(), e);
            }
        }
    }
}
