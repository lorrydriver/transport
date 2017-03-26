package at.ko.transport.business.rechnung.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.inject.Inject;

import at.ko.transport.business.rechnung.entity.Rechnung;
import at.ko.transport.business.rechnung.entity.RechnungsZeile;

@Stateless
public class RechnungsManager {

	@PersistenceContext
	private EntityManager em;

	public void save(Rechnung rechnung) {
		em.persist(rechnung);
	}
	
	
	public Rechnung load(Rechnung rechnung) {
		return em.find(Rechnung.class, rechnung.getRechnugsId());
	}
	
	public void update(Rechnung rechnung) {
		em.merge(rechnung);
	}
	
	public List<Rechnung> getRechnungenByDate() {
		return em.createNamedQuery(Rechnung.allOrderdByDate,Rechnung.class).getResultList();
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
	
	public List<Long> getAllCmrIds() {
		return this.em.createNamedQuery(RechnungsZeile.allCmrIds, Long.class).getResultList();
	}
			

}
