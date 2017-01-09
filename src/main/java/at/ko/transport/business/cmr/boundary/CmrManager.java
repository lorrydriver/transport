package at.ko.transport.business.cmr.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ko.transport.business.cmr.entity.Cmr;
import at.ko.transport.business.cmr.entity.CmrAnschrift;

@Stateless
public class CmrManager {

    @PersistenceContext
    EntityManager em;

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

	public void forceInaktiv(Cmr cmr) {
		Cmr aktiv = this.em.find(Cmr.class, cmr.getId());
		aktiv.setAktiv(false);
				
	}

}
