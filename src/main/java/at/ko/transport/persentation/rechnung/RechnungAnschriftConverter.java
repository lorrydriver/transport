package at.ko.transport.persentation.rechnung;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import at.ko.transport.business.cmr.entity.CmrAnschrift;
import at.ko.transport.business.rechnung.entity.RechnungsAnschrift;

@FacesConverter("RechnungAnschriftConverter")
public class RechnungAnschriftConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		NewRechnung service = fc.getApplication().evaluateExpressionGet(fc, "#{newRechnung}", NewRechnung.class);
		if (value != null && value.trim().length() > 0) {
			try {
				for (RechnungsAnschrift anschrift : service.allAnschrift) {
					if (value.equals(String.valueOf(anschrift.getId()))) {
						return anschrift;
					}
				}
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Anschrift."));
			}
		} 
		return null;
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			if(object instanceof RechnungsAnschrift) {
				return String.valueOf(((RechnungsAnschrift)object).getId());
			}
				return String.valueOf(((Long) object));
		} else {
			return null;
		}
	}

}
