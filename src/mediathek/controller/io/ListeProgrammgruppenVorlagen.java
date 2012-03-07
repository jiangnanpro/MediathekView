/*    
 *    MediathekView
 *    Copyright (C) 2008   W. Xaver
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
package mediathek.controller.io;

import mediathek.controller.filme.filmeImportieren.filmUpdateServer.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import mediathek.Daten;
import mediathek.Konstanten;
import mediathek.Log;
import mediathek.daten.DDaten;
import mediathek.tool.TModel;

public class ListeProgrammgruppenVorlagen extends LinkedList<String[]> {

    public static final String PGR = "Programmgruppe";
    public static final int PGR_MAX_ELEM = 4;
    public static final String PGR_NAME = "Name";
    public static final int PGR_NAME_NR = 0;
    public static final String PGR_BESCHREIBUNG = "Beschreibung";
    public static final int PGR_BESCHREIBUNG_NR = 1;
    public static final String PGR_VERSION = "Version";
    public static final int PGR_VERSION_NR = 2;
    public static final String PGR_URL = "URL";
    public static final int PGR_URL_NR = 3;
    public static final String[] PGR_COLUMN_NAMES = {PGR_NAME, PGR_BESCHREIBUNG, PGR_VERSION, PGR_URL};
    // private
    private final int timeout = 10000;
    private final String url = Konstanten.ADRESSE_VORLAGE_PROGRAMMGRUPPEN;

    public ListeProgrammgruppenVorlagen() {
    }

    public TModel getTModel() {
        String[][] object;
        object = new String[this.size()][PGR_MAX_ELEM];
        for (int i = 0; i < this.size(); i++) {
            object[i] = this.get(i);
        }
        TModel model = new TModel(object, PGR_COLUMN_NAMES);
        return model;
    }

    public boolean getListe() {
        try {
            //String parsername = "";
            int event;
            XMLInputFactory inFactory = XMLInputFactory.newInstance();
            inFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
            XMLStreamReader parser;
            InputStreamReader inReader;
            URLConnection conn;
            conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", Daten.getUserAgent());
            conn.setReadTimeout(timeout);
            conn.setConnectTimeout(timeout);
            inReader = new InputStreamReader(conn.getInputStream(), Konstanten.KODIERUNG_UTF);
            parser = inFactory.createXMLStreamReader(inReader);
            while (parser.hasNext()) {
                event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (parser.getLocalName().equals(PGR)) {
                        //wieder ein neuer Server, toll
                        String[] p = new String[PGR_MAX_ELEM];
                        get(parser, event, PGR, PGR_COLUMN_NAMES, p);
                        if (!p[PGR_URL_NR].equals("")) {
                            this.add(p);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.fehlerMeldung("VorlageProgrammgruppen.getListe", ex);
            return false;
        }
        return true;
    }

    private boolean get(XMLStreamReader parser, int event, String xmlElem, String[] xmlNames, String[] strRet) {
        boolean ret = true;
        int maxElem = strRet.length;
        for (int i = 0; i < maxElem; ++i) {
            strRet[i] = "";
        }
        try {
            while (parser.hasNext()) {
                event = parser.next();
                if (event == XMLStreamConstants.END_ELEMENT) {
                    if (parser.getLocalName().equals(xmlElem)) {
                        break;
                    }
                }
                if (event == XMLStreamConstants.START_ELEMENT) {
                    for (int i = 0; i < maxElem; ++i) {
                        if (parser.getLocalName().equals(xmlNames[i])) {
                            strRet[i] = parser.getElementText();
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ret = false;
            Log.fehlerMeldung("VorlageProgrammgruppen.get", ex);
        }
        return ret;
    }
}
