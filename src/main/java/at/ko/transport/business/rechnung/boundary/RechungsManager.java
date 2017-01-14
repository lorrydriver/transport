package at.ko.transport.business.rechnung.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.inject.Inject;

import at.ko.transport.business.rechnung.entity.Rechnung;

@Stateless
public class RechungsManager {

	@PersistenceContext
	private EntityManager em;
	
	public void save(Rechnung rechnung) {
		em.merge(rechnung);
	}

}
