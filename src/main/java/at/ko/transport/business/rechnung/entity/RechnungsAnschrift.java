package at.ko.transport.business.rechnung.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import at.ko.transport.business.cmr.entity.CmrAnschrift;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = RechnungsAnschrift.findAll, query = "SELECT t FROM RechnungsAnschrift t WHERE t.aktiv = true")
public class RechnungsAnschrift {

	static final String PREFIX = "rechnung.entity.RechnungsAnschrift.";
	public static final String findAll = PREFIX + "findAll";
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	private String line1;
	private String line2;
	private String line3;
	private String line4;
	
	private boolean aktiv;
	
	

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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


}
