package at.ko.transport.business.rechnung.boundary;

import java.io.StringWriter;
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
		String templateString = "templates/rechnung_template.html";
		Properties p = new Properties();
//		p.setProperty("file.resource.loader.path", pfad);
//		p.setProperty("resource.loader", "file");
//		p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		// p.setProperty("file.resource.loader.path", "");
		System.out.println(ClasspathResourceLoader.class.getName());
		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
		p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.init(p);
	    

		VelocityContext context = new VelocityContext();
		context.put( "rechnung", this.rechnung );

		Template template = null;

		try
		{
		  template = Velocity.getTemplate(templateString);
		}
		catch( Exception e )
		{
			throw e;
		}

		StringWriter sw = new StringWriter();

		template.merge( context, sw );
		return sw.toString();
	}
}
