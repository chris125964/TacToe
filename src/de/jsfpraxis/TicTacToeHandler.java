/*
 *  (c) Bernd Müller, www.jsfpraxis.de
 */

package de.jsfpraxis;

import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "tttHandler")
@SessionScoped
public class TicTacToeHandler {

	private static Logger logger = Logger.getLogger("de.jsfpraxis.TicTacToeHandler");

	private static final String KREIS = "/images/Rot.png";
	private static final String KREUZ = "/images/Gruen.png";
	private static final String LEER = "/images/Weiss.png";

	private Brett brett;
	private String meldung;

	public TicTacToeHandler() {
		this.brett = new BrettImpl();
	}

	public void zug(final ActionEvent ae) {
		logger.info("zug(ActionEvent) gedrückt");
		if (this.brett.isFertig()) {
			return;
		}
		try {
			this.brett.setze(new Integer(ae.getComponent().getId().split("-")[1]));
			if (this.brett.isVerloren()) {
				this.meldung = "Herzlichen Glückwunsch, Sie haben gewonnen !";
				return;
			}
			this.brett.waehleZug();
			if (this.brett.isGewonnen()) {
				this.meldung = "Sie haben leider verloren";
			}
		} catch (Exception e) {
			logger.info("Kein Spielerzug ausgeführt");
		}
	}

	public String[] getImage() {
		String[] feld = new String[9];
		for (int i = 0; i < this.brett.getBrett().length; i++) {
			if (this.brett.getBrett()[i] == Brett.KREIS) {
				feld[i] = KREIS;
			} else if (this.brett.getBrett()[i] == Brett.KREUZ) {
				feld[i] = KREUZ;
			} else {
				feld[i] = LEER;
			}
		}
		return feld;
	}

	public String neuesSpiel() {
		logger.info("neuesSpiel()");
		this.brett = new BrettImpl();
		this.meldung = "";
		return "success";
	}

	public String getMeldung() {
		return this.meldung;
	}

	public void setMeldung(final String meldung) {
		this.meldung = meldung;
	}

}
