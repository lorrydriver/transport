package at.ko.transport.business.cmr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = Cmr.findAllAktiv, query = "SELECT t FROM Cmr t WHERE t.aktiv = true"),
	@NamedQuery(name = Cmr.findAllNotInList, query = "SELECT t FROM Cmr t WHERE t.aktiv = true and t.id NOT IN :"+ Cmr.cmrIds)
})
public class Cmr {

	
	static final String PREFIX = "Cmr_";
	public static final String findAllAktiv = PREFIX + "findAll";
	public static final String findAllNotInList = PREFIX + "findAllNotInList";
	public static final String cmrIds = PREFIX + "cmrids";
	
	@Id
	@GeneratedValue
	long id;
	String kfzLkw;
	String kfzAnhaenger;
	String ladungGewicht;
	String ladungEinheit;
	String ladungText;
	String entladungsOrtPlz;
	String entladungsOrtLand;
	String beladungsOrtPlz;
	String beladungsOrtLand;
	String beladungsDatum;
	String abfertigungsOrt;
	String abfertigungsDatum;
	boolean printAbsenderForUnterschrift;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	Date creationTime;
	
	boolean aktiv = true;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ABSENDER_ID")
	CmrAnschrift absender;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPFAENGER_ID")
	CmrAnschrift empfaenger;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKfzLkw() {
		return kfzLkw;
	}

	public void setKfzLkw(String kfzLkw) {
		this.kfzLkw = kfzLkw;
	}

	public String getKfzAnhaenger() {
		return kfzAnhaenger;
	}

	public void setKfzAnhaenger(String kfzAnhaenger) {
		this.kfzAnhaenger = kfzAnhaenger;
	}

	public String getLadungGewicht() {
		return ladungGewicht;
	}

	public void setLadungGewicht(String ladungGewicht) {
		this.ladungGewicht = ladungGewicht;
	}

	public String getLadungEinheit() {
		return ladungEinheit;
	}

	public void setLadungEinheit(String ladungEinheit) {
		this.ladungEinheit = ladungEinheit;
	}

	public String getLadungText() {
		return ladungText;
	}

	public void setLadungText(String ladungText) {
		this.ladungText = ladungText;
	}


	public String getEntladungsOrtPlz() {
		return entladungsOrtPlz;
	}

	public void setEntladungsOrtPlz(String entladungsOrtPlz) {
		this.entladungsOrtPlz = entladungsOrtPlz;
	}

	public String getEntladungsOrtLand() {
		return entladungsOrtLand;
	}

	public void setEntladungsOrtLand(String entladungsOrtLand) {
		this.entladungsOrtLand = entladungsOrtLand;
	}

	public String getBeladungsOrtPlz() {
		return beladungsOrtPlz;
	}

	public void setBeladungsOrtPlz(String beladungsOrtPlz) {
		this.beladungsOrtPlz = beladungsOrtPlz;
	}

	public String getBeladungsOrtLand() {
		return beladungsOrtLand;
	}

	public void setBeladungsOrtLand(String beladungsOrtLand) {
		this.beladungsOrtLand = beladungsOrtLand;
	}

	public String getBeladungsDatum() {
		return beladungsDatum;
	}

	public void setBeladungsDatum(String beladungsDatum) {
		this.beladungsDatum = beladungsDatum;
	}

	public CmrAnschrift getAbsender() {
		return absender;
	}

	public void setAbsender(CmrAnschrift absender) {
		this.absender = absender;
	}

	public CmrAnschrift getEmpfaenger() {
		return empfaenger;
	}

	public void setEmpfaenger(CmrAnschrift empfaenger) {
		this.empfaenger = empfaenger;
	}

	public String getAbfertigungsOrt() {
		return abfertigungsOrt;
	}

	public void setAbfertigungsOrt(String abfertigungsOrt) {
		this.abfertigungsOrt = abfertigungsOrt;
	}

	public String getAbfertigungsDatum() {
		return abfertigungsDatum;
	}

	public void setAbfertigungsDatum(String abfertigungsDatum) {
		this.abfertigungsDatum = abfertigungsDatum;
	}

	public boolean isPrintAbsenderForUnterschrift() {
		return printAbsenderForUnterschrift;
	}

	public void setPrintAbsenderForUnterschrift(boolean printAbsenderForUnterschrift) {
		this.printAbsenderForUnterschrift = printAbsenderForUnterschrift;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	
}
