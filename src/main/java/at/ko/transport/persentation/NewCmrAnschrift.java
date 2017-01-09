package at.ko.transport.persentation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import at.ko.transport.business.cmr.boundary.CmrAnschriftManager;
import at.ko.transport.business.cmr.boundary.CmrManager;
import at.ko.transport.business.cmr.entity.CmrAnschrift;

@Model
public class NewCmrAnschrift {

	@Inject
	CmrManager cmrBoundary;
	@Inject
	CmrAnschriftManager manager;

	private CmrAnschrift anschrift;

	@PostConstruct
	public void init() {

		this.anschrift = new CmrAnschrift();
	}

	public Object save() {
		this.manager.save(anschrift);
		return null;
	}
	
	public Object delete(CmrAnschrift anschrift) {
		this.manager.delete(anschrift);
		return null;
	}

	public CmrAnschrift getAnschrift() {
		return anschrift;
	}

	public void setAnschrift(CmrAnschrift anschrift) {
		this.anschrift = anschrift;
	}

	public List<CmrAnschrift> getAll() {
		return this.manager.all();
	}

}
