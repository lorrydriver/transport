package at.ko.transport.business.rechnung.boundary;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import at.ko.transport.business.rechnung.entity.Rechnung;

public class RechnungsPrinter {
	

	private Rechnung rechnung;

	public RechnungsPrinter(Rechnung rechnung) {
		this.rechnung = rechnung;
	}

	public String print() {
		String templateString = "rechnung_template.html";
		Properties p = new Properties();
//		p.setProperty("file.resource.loader.path", pfad);
//		p.setProperty("resource.loader", "file");
//		p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		// p.setProperty("file.resource.loader.path", "");
		System.out.println(ClasspathResourceLoader.class.getName());
		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
		p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		p.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
		Velocity.init(p);
	    
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		NumberFormat nf = new DecimalFormat("#.00");
		
	    NumberFormat euronf = new DecimalFormat("#,###.00\u00A4");
		VelocityContext context = new VelocityContext();
		context.put( "rechnung", this.rechnung );
		context.put("rechnungsdatum" ,sdf.format(rechnung.getRechnungsdatum()));
		context.put("faelligam", sdf.format(rechnung.getFaelligAm()));
		context.put("faelligam", sdf.format(rechnung.getFaelligAm()));

		context.put("printUst", !new Double(0.0d).equals(rechnung.getUst()));
		context.put("euronf", euronf);
		context.put("nf", nf);
								

		StringWriter sw = new StringWriter();
		System.out.println(templateString);
		System.out.println(RechnungsPrinter.class.getResourceAsStream("rechnung_template.html"));
		System.out.println(RechnungsPrinter.class.getResourceAsStream("templates/rechnung_template.html"));
		Velocity.evaluate(context, sw, "rechnung", RechnungsPrinter.class.getResourceAsStream(templateString));
		//template.merge( context, sw );
		return sw.toString();
	}
}
