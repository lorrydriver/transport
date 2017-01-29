package at.ko.transport.business.rechnung.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RechnungsZeile {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	private String beschreibung;
	private Double menge;
	private Double satz;
	
	private long cmrId;
	
	public Double getBetrag() {
		return menge*satz;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Double getMenge() {
		return menge;
	}

	public void setMenge(Double menge) {
		this.menge = menge;
	}

	public Double getSatz() {
		return satz;
	}

	public void setSatz(Double satz) {
		this.satz = satz;
	}

	public long getCmrId() {
		return cmrId;
	}

	public void setCmrId(long cmrId) {
		this.cmrId = cmrId;
	}

}
