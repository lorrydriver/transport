package at.ko.transport.business.cmr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = CmrAnschrift.findAll, query = "SELECT t FROM CmrAnschrift t WHERE t.aktiv = true")
public class CmrAnschrift {

	static final String PREFIX = "cmr.entity.CmrAnschrift.";
	public static final String findAll = PREFIX + "findAll";

	@Id
	@GeneratedValue
	long id;
	String line1;
	String line2;
	String line3;
	String line4;
	
	boolean aktiv = true;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getLine4() {
		return line4;
	}

	public void setLine4(String line4) {
		this.line4 = line4;
	}

	public String getDisplayString() {
		String seperator = ",";
		return line1+seperator+line2+seperator+line3+seperator+line4;
	}

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	
}
