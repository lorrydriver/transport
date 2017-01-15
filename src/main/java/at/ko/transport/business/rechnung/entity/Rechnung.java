package at.ko.transport.business.rechnung.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyTemporal;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Rechnung {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long rechnugsId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ANSCHRIFT_ID")
	private RechnungsAnschrift anschrift;

	@ManyToMany
	@JoinTable(name = "RECHNUNG_ZEILE", joinColumns = @JoinColumn(name = "RECHNUNG_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ZEILE_ID", referencedColumnName = "ID"))
	private List<RechnungsZeile> rechnungsZeile;
	
	@Temporal(TemporalType.DATE)
	private Date rechnungsdatum;
	
	@Temporal(TemporalType.DATE)
	private Date faelligAm;

	public Double getZwischensumme() {
		if(rechnungsZeile == null || rechnungsZeile.isEmpty()) {
			return 0d;
		}
		Double zwischenSumme = 0d;
		for(RechnungsZeile zeile : rechnungsZeile) {
			zwischenSumme+=zeile.getBetrag();
		}
		return zwischenSumme;
	}
	
	public Double getUst() {
		return getZwischensumme() * 0.2d;
	}
	
	public Double getGesamtSumme() {
		return getZwischensumme() + getUst();
	}
	
	
	public Date getRechnungsdatum() {
		return rechnungsdatum;
	}

	public void setRechnungsdatum(Date rechnungsdatum) {
		this.rechnungsdatum = rechnungsdatum;
	}

	public Long getRechnugsId() {
		return rechnugsId;
	}

	public void setRechnugsId(Long rechnugsId) {
		this.rechnugsId = rechnugsId;
	}

	public RechnungsAnschrift getAnschrift() {
		return anschrift;
	}

	public void setAnschrift(RechnungsAnschrift anschrift) {
		this.anschrift = anschrift;
	}

	public List<RechnungsZeile> getRechnungsZeile() {
		return rechnungsZeile;
	}

	public void setRechnungsZeile(List<RechnungsZeile> rechnungsZeile) {
		this.rechnungsZeile = rechnungsZeile;
	}

	public Date getFaelligAm() {
		return faelligAm;
	}

	public void setFaelligAm(Date faelligAm) {
		this.faelligAm = faelligAm;
	}

}
