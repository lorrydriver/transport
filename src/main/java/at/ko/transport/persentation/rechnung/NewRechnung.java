package at.ko.transport.persentation.rechnung;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import at.ko.transport.business.rechnung.boundary.RechnungsAnschriftManager;
import at.ko.transport.business.rechnung.boundary.RechnungsPrinter;
import at.ko.transport.business.rechnung.boundary.RechungsManager;
import at.ko.transport.business.rechnung.entity.Rechnung;
import at.ko.transport.business.rechnung.entity.RechnungsAnschrift;
import at.ko.transport.business.rechnung.entity.RechnungsZeile;

/**
 * @author oliver
 *
 */
@Named
@SessionScoped
public class NewRechnung implements Serializable {

	@Inject
	RechnungsAnschriftManager anschriftManager;
	@Inject
	RechungsManager rechungsManager;
	
	private String beschreibung;
	private Double menge;
	private Double satz;
	
	Rechnung rechnung;
	List<RechnungsAnschrift> allAnschrift;
	
	@PostConstruct
	public void init() {
		reset();
	}

	public void reset() {
		allAnschrift = anschriftManager.all();
		this.rechnung = new Rechnung();
		this.rechnung.setRechnungsZeile(new ArrayList<>());
		this.rechnung.setRechnungsdatum(new Date());
	}
	
	public void save() {
		this.rechungsManager.save(this.rechnung);
	}

	public Rechnung getRechnung() {
		return rechnung;
	}

	public void setRechnung(Rechnung rechnung) {
		this.rechnung = rechnung;
	}

	public List<RechnungsAnschrift> getAllAnschrift() {
		return allAnschrift;
	}

	public void setAllAnschrift(List<RechnungsAnschrift> allAnschrift) {
		this.allAnschrift = allAnschrift;
	}
	
	
	public List<RechnungsAnschrift> complete(String query) {
		List<RechnungsAnschrift> list =  allAnschrift.stream().filter(
				abs -> abs.getDisplayString().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
		return list;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Double getMenge() {
		return menge;
	}

	public void setMenge(Double menge) {
		this.menge = menge;
	}

	public Double getSatz() {
		return satz;
	}

	public void setSatz(Double satz) {
		this.satz = satz;
	}
	
	public void addZeile() {
		RechnungsZeile zeile = new RechnungsZeile();
		zeile.setMenge(menge);
		zeile.setSatz(satz);
		zeile.setBeschreibung(beschreibung);
		System.out.println(rechnung.getRechnungsZeile().size());
		rechnung.getRechnungsZeile().add(zeile);
		menge = null;
		satz = null;
		beschreibung = null;
	} 
	public void removeZeile(RechnungsZeile zeile) {
		rechnung.getRechnungsZeile().remove(zeile);
	} 
	
	public void downloadFile() {

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		String print = new RechnungsPrinter(rechnung).print();
		response.setContentLength((int) print.length());
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(print.getBytes());
			out.flush();
			FacesContext.getCurrentInstance().getResponseComplete();
		} catch (IOException err) {
			err.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException err) {
				err.printStackTrace();
			}
		}

	}
}
