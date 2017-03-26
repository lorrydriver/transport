package at.ko.transport.business.cmr.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ko.transport.business.cmr.entity.Cmr;
import at.ko.transport.business.cmr.entity.CmrAnschrift;
import at.ko.transport.business.rechnung.boundary.RechnungsManager;

@Stateless
public class CmrManager {

    @PersistenceContext
    EntityManager em;
    
    @Inject
    RechnungsManager rechnungsManager;

    public Cmr save(Cmr cmr) {
        return this.em.merge(cmr);
    }

	public Cmr findById(long id) {
		return em.find(Cmr.class, id);
	}
	
    public List<Cmr> all() {
        return this.em.createNamedQuery(Cmr.findAllAktiv, Cmr.class).
                getResultList();
    }
    
    public List<Cmr> allAktiv() {
    
    	List<Long> cmrId = rechnungsManager.getAllCmrIds();
    	return this.em.createNamedQuery(Cmr.findAllNotInList, Cmr.class).setParameter(Cmr.cmrIds, cmrId).getResultList();
    }

	public void forceInaktiv(Cmr cmr) {
		Cmr aktiv = this.em.find(Cmr.class, cmr.getId());
		aktiv.setAktiv(false);
				
	}

}
