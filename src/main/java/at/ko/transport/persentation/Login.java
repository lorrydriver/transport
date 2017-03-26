package at.ko.transport.persentation;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "login")
public class Login {

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login";
    }
}