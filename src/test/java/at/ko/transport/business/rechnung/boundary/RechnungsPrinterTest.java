package at.ko.transport.business.rechnung.boundary;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import at.ko.transport.business.rechnung.entity.Rechnung;
import at.ko.transport.business.rechnung.entity.RechnungsAnschrift;
import at.ko.transport.business.rechnung.entity.RechnungsZeile;

public class RechnungsPrinterTest {

	RechnungsPrinter cut;
	@Before
	public void setUp() throws Exception {
		Rechnung rechnung = new Rechnung();
		rechnung.setRechnugsId(5l);
		rechnung.setFaelligAm(new Date());
		rechnung.setRechnungsdatum(new Date());
		rechnung.setAnschrift(new RechnungsAnschrift());
		rechnung.getAnschrift().setAktiv(true);
		rechnung.getAnschrift().setId(1l);
		rechnung.getAnschrift().setLine1("line 1");
		rechnung.getAnschrift().setLine2("line 2");
		rechnung.getAnschrift().setLine3("line 3");
		rechnung.getAnschrift().setLine4("line 4");
		
		rechnung.setRechnungsZeile(new ArrayList<>());
		rechnung.getRechnungsZeile().add(createZeile());
		rechnung.getRechnungsZeile().add(createZeile());
		rechnung.getRechnungsZeile().add(createZeile());
		rechnung.getRechnungsZeile().add(createZeile());
		
		
		cut = new RechnungsPrinter(rechnung);
	}

	private RechnungsZeile createZeile() {
		RechnungsZeile zeile = new RechnungsZeile();
		zeile.setBeschreibung("T.P. am 13.12.2016 von Krems nach Massing, Bio-Mais, lose T. PA- NR.: 1668554865, Wiegeschein Nr.: 16451");
		zeile.setMenge(40.1d);
		zeile.setSatz(20.8d);
		return zeile;
	}

	@Test
	public void test() {
		String html = cut.print();
		assertThat(html, not(containsString("$item")));
		assertThat(html, not(containsString("$rechnung")));
		System.out.println(html);
	}

}
