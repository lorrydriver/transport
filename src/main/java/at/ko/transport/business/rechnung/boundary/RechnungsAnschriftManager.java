package at.ko.transport.business.rechnung.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ko.transport.business.cmr.entity.CmrAnschrift;
import at.ko.transport.business.rechnung.entity.RechnungsAnschrift;

@Stateless
public class RechnungsAnschriftManager {

	@PersistenceContext
    EntityManager em;

	public RechnungsAnschrift save(RechnungsAnschrift rechnungsAnschrift) {
		return this.em.merge(rechnungsAnschrift);
	}
	
    public List<RechnungsAnschrift> all() {
        return this.em.createNamedQuery(RechnungsAnschrift.findAll, RechnungsAnschrift.class).
                getResultList();
    }

	public void delete(RechnungsAnschrift anschrift) {
		RechnungsAnschrift aktiv = this.em.find(RechnungsAnschrift.class, anschrift.getId());
		aktiv.setAktiv(false);
	}

}
