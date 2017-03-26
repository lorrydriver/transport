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
import at.ko.transport.business.rechnung.boundary.RechnungsPrinter;
import at.ko.transport.business.rechnung.boundary.RechnungsManager;
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

	private RechnungsZeile rechnungsZeile;

	Rechnung rechnung;
	List<RechnungsAnschrift> allAnschrift;
	private String print;
	private long rechungsZeileCmr;

	@PostConstruct
	public void init() {
		if(rechnung == null) {
		  reset();
		}
	}

	public void reset() {
		allAnschrift = anschriftManager.all();
		this.rechnung = new Rechnung();
		this.rechnungsZeile = new RechnungsZeile();
		this.rechnung.setRechnungsZeile(new ArrayList<>());
		this.rechnung.setRechnungsdatum(new Date());
		this.rechnung.setFaelligAm(Date.from(
				LocalDate.now().plus(1, ChronoUnit.MONTHS).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		this.print = null;
		String rechungsNummerprefix = new SimpleDateFormat("yy").format(this.rechnung.getRechnungsdatum());
		String rechnungsNummer = new DecimalFormat("000")
				.format(rechungsManager.calcRechnungsnummer(rechungsNummerprefix));
		this.rechnung.setRechnungsNummer(
				rechungsNummerprefix + "-" + rechnungsNummer);
	}

	public void save() {
		if(this.rechnung.getRechnugsId() == null) {
			this.rechungsManager.save(this.rechnung);
		} else {
			this.rechungsManager.update(rechnung);
		}
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

	

	public RechnungsZeile getRechnungsZeile() {
		return rechnungsZeile;
	}

	public void setRechnungsZeile(RechnungsZeile rechnungsZeile) {
		this.rechnungsZeile = rechnungsZeile;
	}

	public String getPrint() {
		return this.print;
	}

	public void addZeile() {
		rechnungsZeile.setCmrId(this.rechungsZeileCmr);
		if(!rechnung.getRechnungsZeile().contains(this.rechnungsZeile)) {
			rechnung.getRechnungsZeile().add(rechnungsZeile);
		}
		this.rechnungsZeile = new RechnungsZeile();
	}

	public void removeZeile(RechnungsZeile zeile) {
		rechnung.getRechnungsZeile().remove(zeile);
	}
	
	public void editZeile(RechnungsZeile zeile) {
		this.rechnungsZeile = zeile;
	}
	
	public void newZeile() {
		this.rechnungsZeile = new RechnungsZeile();
	}
	
	

	public String downloadFile() {

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		String print = new RechnungsPrinter(rechnung).print();
		this.print = print;
		return "rechnungPrint";
	}

	public List<Cmr> getAllCmr() {
		return cmrManager.allAktiv();
	}

	public void setCmrText(Cmr cmr) {
		String nachOrt = StringUtils.isEmpty(cmr.getEmpfaenger().getLine4()) ? cmr.getEmpfaenger().getLine3()
				: cmr.getEmpfaenger().getLine4();
		String vonOrt = StringUtils.isEmpty(cmr.getAbsender().getLine4()) ? cmr.getAbsender().getLine3()
				: cmr.getAbsender().getLine4();

		this.rechnungsZeile.setBeschreibung("T.P am " + cmr.getAbfertigungsDatum() + " " + cmr.getLadungText() + " von " + vonOrt
				+ " nach: " + nachOrt);
		this.rechungsZeileCmr = cmr.getId();
	}
	
	public boolean isSaved() {
		return this.rechnung.getRechnugsId() != null;
	}

}
