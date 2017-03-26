package at.ko.transport.business.rechnung.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.airhacks.rulz.em.EntityManagerProvider;

public class RechnungTest {


    @Rule
    public EntityManagerProvider provider = EntityManagerProvider.persistenceUnit("it");

    @Test
    public void verifyMappings() {
        this.provider.tx().begin();
        List<Rechnung> rechnungen = this.provider.em().createNamedQuery(Rechnung.allOrderdByDate,Rechnung.class).getResultList();
        for(Rechnung r : rechnungen) {
        	if (! rechnungen.get(0).getRechnungsdatum().equals(r.getRechnungsdatum())) {
        		assertThat(rechnungen.get(0).getRechnungsdatum().after(r.getRechnungsdatum()), is(Boolean.TRUE));
        	}
        }
        this.provider.tx().commit();
    }
}
