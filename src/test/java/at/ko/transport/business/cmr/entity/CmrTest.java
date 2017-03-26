package at.ko.transport.business.cmr.entity;

import java.io.File;
import java.util.Collections;


import org.junit.Rule;
import org.junit.Test;

import com.airhacks.rulz.em.EntityManagerProvider;

public class CmrTest {


    @Rule
    public EntityManagerProvider provider = EntityManagerProvider.persistenceUnit("it");

    @Test
    public void verifyMappings() {
        this.provider.tx().begin();
        this.provider.em().createNamedQuery(Cmr.findAllNotInList).
        setParameter(Cmr.cmrIds,  Collections.EMPTY_LIST).getResultList();
        this.provider.tx().commit();
    	File file = new File("./META-INF");
		String out = file.getAbsolutePath();
    	out = out + " exists "+file.exists(); 
    	System.out.println(out);
    }

}
