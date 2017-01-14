package at.ko.transport.persentation.rechnung;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import at.ko.transport.business.cmr.boundary.CmrAnschriftManager;
import at.ko.transport.business.cmr.boundary.CmrManager;
import at.ko.transport.business.cmr.entity.CmrAnschrift;
import at.ko.transport.business.rechnung.boundary.RechnungsAnschriftManager;
import at.ko.transport.business.rechnung.entity.RechnungsAnschrift;

@Model
public class NewRechnungAnschrift {

	@Inject
	RechnungsAnschriftManager manager;

	private RechnungsAnschrift anschrift;

	@PostConstruct
	public void init() {

		this.anschrift = new RechnungsAnschrift();
		this.anschrift.setAktiv(true);
	}

	public Object save() {
		this.manager.save(anschrift);
		return null;
	}
	
	public Object delete(RechnungsAnschrift anschrift) {
		this.manager.delete(anschrift);
		return null;
	}

	public RechnungsAnschrift getAnschrift() {
		return anschrift;
	}

	public void setAnschrift(RechnungsAnschrift anschrift) {
		this.anschrift = anschrift;
	}

	public List<RechnungsAnschrift> getAll() {
		return this.manager.all();
	}
	
	
}
