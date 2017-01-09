package at.ko.transport.business.cmr.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ko.transport.business.cmr.entity.Cmr;
import at.ko.transport.business.cmr.entity.CmrAnschrift;

@Stateless
public class CmrAnschriftManager {

	@PersistenceContext
    EntityManager em;

    public CmrAnschrift save(CmrAnschrift cmrAnschrift) {
        return this.em.merge(cmrAnschrift);
    }
    
    public List<CmrAnschrift> all() {
        return this.em.createNamedQuery(CmrAnschrift.findAll, CmrAnschrift.class).
                getResultList();
    }

	public void delete(CmrAnschrift cmr) {
		CmrAnschrift aktiv = this.em.find(CmrAnschrift.class, cmr.getId());
		aktiv.setAktiv(false);
	}
    
}
