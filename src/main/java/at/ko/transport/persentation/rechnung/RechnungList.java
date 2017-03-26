package at.ko.transport.persentation.rechnung;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import at.ko.transport.business.rechnung.boundary.RechnungsManager;
import at.ko.transport.business.rechnung.entity.Rechnung;

@Model
public class RechnungList {

	@Inject
	private RechnungsManager rechnungsManager;
	
	@Inject
	private NewRechnung newRechnung;
	
	private List<Rechnung> rechnungen;
	

	private Rechnung selectedRechnung;
	
	@PostConstruct
	public void init() {
		rechnungen = rechnungsManager.getRechnungenByDate();
	}
	
	public Rechnung getSelectedRechnung() {
		return selectedRechnung;
	}

	public void setSelectedRechnung(Rechnung selectedRechnung) {
		this.selectedRechnung = selectedRechnung;
	}

	public List<Rechnung> getRechnungen() {
		return rechnungen;
	}

	public void setRechnungen(List<Rechnung> rechnungen) {
		this.rechnungen = rechnungen;
	} 
	
	public String editRechnung(Rechnung r) {
		if(r.getRechnugsId() == null) {
			return "";
		}
		r = rechnungsManager.load(r);
		r.getRechnungsZeile();
		r.getAnschrift();
		newRechnung.setRechnung(r);
		return "newRechnung";
	}

}
