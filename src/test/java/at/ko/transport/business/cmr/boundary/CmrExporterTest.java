package at.ko.transport.business.cmr.boundary;

import java.io.IOException;

import org.junit.Test;

import at.ko.transport.business.cmr.entity.Cmr;
import at.ko.transport.business.cmr.entity.CmrAnschrift;

public class CmrExporterTest {

	@Test
	public void test() throws IOException {
		Cmr cmr = new Cmr();
		cmr.setAbsender(getAnschrift("absender"));
		cmr.setEmpfaenger(getAnschrift("empfaenger"));
		cmr.setAbfertigungsDatum("20.08.2016");
		cmr.setAbfertigungsOrt("Habruck");
		cmr.setBeladungsDatum("19.08.2016");
		cmr.setBeladungsOrtPlz("3522");
		cmr.setBeladungsOrtLand("AUT");
		cmr.setEntladungsOrtLand("Habruck");
		cmr.setEntladungsOrtPlz("3611");
		cmr.setKfzAnhaenger("KR 819CL");
		cmr.setKfzLkw("KR 747AJ");
		cmr.setLadungEinheit("kg");
		cmr.setLadungGewicht("38000");
		cmr.setLadungText("Weizen lose");
		new CmrExporter().exportXls(cmr);
	}
	
	public CmrAnschrift getAnschrift(String prefix) {
		CmrAnschrift a = new CmrAnschrift();
		a.setLine1(prefix+"line1");
		a.setLine2(prefix+"line2");
		a.setLine3(prefix+"line3");
		return a;
	}

}
