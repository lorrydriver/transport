package at.ko.transport.business.rechnung.boundary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ko.transport.business.cmr.entity.CmrAnschrift;
import at.ko.transport.business.rechnung.entity.RechnungsAnschrift;

public class RechnungsManager {

	@PersistenceContext
    EntityManager em;

    public CmrAnschrift save(CmrAnschrift cmrAnschrift) {
        return this.em.merge(cmrAnschrift);
    }
	public CmrAnschrift save(RechnungsAnschrift rechnungsAnschrift) {
		// TODO Auto-generated method stub
		return null;
	}

}
