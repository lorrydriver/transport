package at.ko.transport.business.cmr.boundary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import at.ko.transport.business.cmr.entity.Cmr;
import at.ko.transport.business.cmr.entity.CmrAnschrift;

@Stateless
public class CmrExporter {

	
	public byte [] exportXls(Cmr cmr) throws IOException {
		File tmp = File.createTempFile("CMR_"+cmr.getId(), "xlsx");
		Workbook workbook = new XSSFWorkbook(CmrExporter.class.getResourceAsStream("cmr.xlsx"));
		 Sheet firstSheet = workbook.getSheetAt(0);
		 firstSheet.getRow(0).getCell(0).setCellValue(concatAnschrift(cmr.getAbsender()));
		 firstSheet.getRow(2).getCell(0).setCellValue(concatAnschrift(cmr.getEmpfaenger()));
		 firstSheet.getRow(4).getCell(1).setCellValue(cmr.getEntladungsOrtPlz());
		 firstSheet.getRow(4).getCell(4).setCellValue(cmr.getEntladungsOrtLand());
		 firstSheet.getRow(5).getCell(1).setCellValue(cmr.getBeladungsDatum());
		 firstSheet.getRow(6).getCell(1).setCellValue(cmr.getBeladungsOrtPlz());
		 firstSheet.getRow(6).getCell(4).setCellValue(cmr.getBeladungsOrtLand());
		 firstSheet.getRow(7).getCell(2).setCellValue("AUT");
		 firstSheet.getRow(9).getCell(5).setCellValue(cmr.getKfzLkw());
		 firstSheet.getRow(10).getCell(5).setCellValue(cmr.getKfzAnhaenger());
		 firstSheet.getRow(12).getCell(0).setCellValue(cmr.getLadungText());
		 firstSheet.getRow(12).getCell(6).setCellValue(cmr.getLadungGewicht());
		 firstSheet.getRow(12).getCell(7).setCellValue(cmr.getLadungEinheit());
		 firstSheet.getRow(34).getCell(0).setCellValue(cmr.getAbfertigungsOrt()+", "+ cmr.getAbfertigungsDatum());
		 if(cmr.isPrintAbsenderForUnterschrift()) {
			 firstSheet.getRow(37).getCell(0).setCellValue(concatAnschrift(cmr.getAbsender()));
		 }
		 printnl(firstSheet);
	     workbook.write(new FileOutputStream(tmp));
	     workbook.close();
	     return IOUtils.toByteArray(new FileInputStream(tmp));
	}
	
	private String concatAnschrift(CmrAnschrift anschrift) {
		String delimiter = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append(anschrift.getLine1());
		sb.append(delimiter);
		sb.append(anschrift.getLine2());
		sb.append(delimiter);
		sb.append(anschrift.getLine3());
		if (anschrift.getLine4() != null) {
			sb.append(delimiter);
			sb.append(anschrift.getLine4());
		}
		return sb.toString();
	}

	private void printnl(Sheet firstSheet) {
		Iterator<Row> iterator = firstSheet.iterator();	
		 while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	             
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                 
	                switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                        System.out.print(cell.getStringCellValue());
	                        break;
	                    case Cell.CELL_TYPE_BOOLEAN:
	                        System.out.print(cell.getBooleanCellValue());
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                        System.out.print(cell.getNumericCellValue());
	                        break;
	                }
	                System.out.print(" - ");
	            }
	            System.out.println();
	        }
	}
	
}
