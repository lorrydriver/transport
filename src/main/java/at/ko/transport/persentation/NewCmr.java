package at.ko.transport.persentation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;

import at.ko.transport.business.cmr.boundary.CmrAnschriftManager;
import at.ko.transport.business.cmr.boundary.CmrExporter;
import at.ko.transport.business.cmr.boundary.CmrManager;
import at.ko.transport.business.cmr.entity.Cmr;
import at.ko.transport.business.cmr.entity.CmrAnschrift;

@Model
public class NewCmr {

	@Inject
	CmrAnschriftManager manager;
	
	@Inject
	CmrManager cmrManager;
	
	@Inject
	CmrExporter cmrExporter;
	
	private boolean printAbsenderForUnterschrift = false;

	List<CmrAnschrift> allAnschrift;
	List<Cmr> allCmr;
	Cmr cmr;

	@PostConstruct
	public void init() {
		allAnschrift = manager.all();
		allCmr = cmrManager.all();
		this.cmr = new Cmr();
		this.cmr.setKfzAnhaenger("KR 819 CL");
		this.cmr.setKfzLkw("KR 747 AJ");
		this.cmr.setBeladungsOrtLand("AUT");
		this.cmr.setEntladungsOrtLand("AUT");
		cmr.setBeladungsDatum(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
		cmr.setAbfertigungsDatum(cmr.getBeladungsDatum());
	}
	
	public Object save() {
		this.cmrManager.save(cmr);
		allCmr = cmrManager.all();
		return null;
	}
	
	public DefaultStreamedContent exportCmr(Cmr cmr) throws IOException {
		byte [] data = cmrExporter.exportXls(cmr);
	    String attachmentName = "Cmr_"+cmr.getId()+".xlsx";
		return new DefaultStreamedContent(new ByteArrayInputStream(data), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", attachmentName);
	}
	
    public void onItemSelectEmpfaenger(SelectEvent event) {
    	if(event.getObject() instanceof CmrAnschrift) {
    		CmrAnschrift anschrfit = (CmrAnschrift) event.getObject();
    		this.cmr.setEntladungsOrtPlz(anschrfit.getLine3());
    		
    	}
    }
    
    public void onItemSelectAbsender(SelectEvent event) {
    	if(event.getObject() instanceof CmrAnschrift) {
    		CmrAnschrift anschrfit = (CmrAnschrift) event.getObject();
    		this.cmr.setBeladungsOrtPlz(anschrfit.getLine3());
    		
    	}
    }
	
	public void edit(Cmr cmr) {
		this.cmr = cmr;
	}
	
	public void delete(Cmr cmr) {
		cmrManager.forceInaktiv(cmr);
		allCmr = cmrManager.all();
	}

	public List<CmrAnschrift> complete(String query) {
		List<CmrAnschrift> list =  allAnschrift.stream().filter(
				abs -> abs.getDisplayString().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
		return list;
	}

	public List<CmrAnschrift> getAllAnschrift() {
		return allAnschrift;
	}

	public void setAllAnschrift(List<CmrAnschrift> allAnschrift) {
		this.allAnschrift = allAnschrift;
	}

	public Cmr getCmr() {
		return cmr;
	}

	public void setCmr(Cmr cmr) {
		this.cmr = cmr;
	}

	public List<Cmr> getAllCmr() {
		return allCmr;
	}

	public void setAllCmr(List<Cmr> allCmr) {
		this.allCmr = allCmr;
	}

	public boolean isPrintAbsenderForUnterschrift() {
		return printAbsenderForUnterschrift;
	}

	public void setPrintAbsenderForUnterschrift(boolean printAbsenderForUnterschrift) {
		this.printAbsenderForUnterschrift = printAbsenderForUnterschrift;
	}
	
}
