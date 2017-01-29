package at.ko.transport.business.rechnung.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.inject.Inject;

import at.ko.transport.business.rechnung.entity.Rechnung;
import at.ko.transport.business.rechnung.entity.RechnungsZeile;

@Stateless
public class RechungsManager {

	@PersistenceContext
	private EntityManager em;

	public void save(Rechnung rechnung) {
		for (RechnungsZeile zeile : rechnung.getRechnungsZeile()) {
			em.persist(zeile);
		}
		em.persist(rechnung);
	}

	public Long calcRechnungsnummer(String rechungsNummerprefix) {
		try {
			return 1 + em
					.createQuery("SELECT COUNT(r.rechnungsNummer) from Rechnung r "
							+ "WHERE r.rechnungsNummer like :prefix", Long.class)
					.setParameter("prefix", rechungsNummerprefix+"%").getSingleResult();
		} catch (NoResultException e) {
			return 1L;
		}
	}

}
