package at.ko.transport.persentation.rechnung;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import at.ko.transport.business.cmr.boundary.CmrManager;
import at.ko.transport.business.cmr.entity.Cmr;
import at.ko.transport.business.rechnung.boundary.RechnungsAnschriftManager;
import at.ko.transport.business.rechnung.boundary.RechnungsManager;
import at.ko.transport.business.rechnung.boundary.RechnungsPrinter;
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
	RechnungsManager rechungsManager;

	@Inject
	CmrManager cmrManager;

	private String beschreibung;
	private Double menge;
	private Double satz;

	Rechnung rechnung;
	List<RechnungsAnschrift> allAnschrift;
	private String print;
	private long rechungsZeileCmr;

	@PostConstruct
	public void init() {
		reset();
	}

	public void reset() {
		allAnschrift = anschriftManager.all();
		this.rechnung = new Rechnung();
		this.rechnung.setRechnungsZeile(new ArrayList<>());
		this.rechnung.setRechnungsdatum(new Date());
		this.rechnung.setFaelligAm(Date.from(
				LocalDate.now().plus(1, ChronoUnit.MONTHS).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		this.beschreibung = "T.P. am ";
		this.print = null;
		String rechungsNummerprefix = new SimpleDateFormat("yy").format(this.rechnung.getRechnungsdatum());
		String rechnungsNummer = new DecimalFormat("000")
				.format(rechungsManager.calcRechnungsnummer(rechungsNummerprefix));
		this.rechnung.setRechnungsNummer(
				rechungsNummerprefix + "-" + rechnungsNummer);
	}

	public void save() {
		this.rechungsManager.save(this.rechnung);
		downloadFile();
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
		List<RechnungsAnschrift> list = allAnschrift.stream()
				.filter(abs -> abs.getDisplayString().toLowerCase().contains(query.toLowerCase()))
				.collect(Collectors.toList());
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

	public String getPrint() {
		return this.print;
	}

	public void addZeile() {
		RechnungsZeile zeile = new RechnungsZeile();
		zeile.setMenge(menge);
		zeile.setSatz(satz);
		zeile.setBeschreibung(beschreibung);
		zeile.setCmrId(this.rechungsZeileCmr);
		rechnung.getRechnungsZeile().add(zeile);
	}

	public void removeZeile(RechnungsZeile zeile) {
		rechnung.getRechnungsZeile().remove(zeile);
	}

	public String downloadFile() {

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		String print = new RechnungsPrinter(rechnung).print();
		this.print = print;
		return "rechnungPrint";
	}

	public List<Cmr> getAllCmr() {
		return cmrManager.all();
	}

	public void setCmrText(Cmr cmr) {
		String nachOrt = StringUtils.isEmpty(cmr.getEmpfaenger().getLine4()) ? cmr.getEmpfaenger().getLine3()
				: cmr.getEmpfaenger().getLine4();
		String vonOrt = StringUtils.isEmpty(cmr.getAbsender().getLine4()) ? cmr.getAbsender().getLine3()
				: cmr.getAbsender().getLine4();

		this.beschreibung = "T.P am " + cmr.getAbfertigungsDatum() + " " + cmr.getLadungText() + " von " + vonOrt
				+ " nach: " + nachOrt;
		this.rechungsZeileCmr = cmr.getId();
	}

}
