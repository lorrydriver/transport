package at.ko.transport.persentation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import at.ko.transport.business.cmr.entity.CmrAnschrift;

@FacesConverter("CmrAnschriftConverter")
public class CmrAnschriftConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		NewCmr service = fc.getApplication().evaluateExpressionGet(fc, "#{newCmr}", NewCmr.class);
		if (value != null && value.trim().length() > 0) {
			try {
				for (CmrAnschrift anschrift : service.allAnschrift) {
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
			return String.valueOf(((Long) object).toString());
		} else {
			return null;
		}
	}

}
