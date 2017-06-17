package kr.co.kandedu.base.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import kr.co.kandedu.base.domain.AnswerVo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;


/**
 * excel write util 
 * @author dev
 *
 */
@Component("ExcelWriteUtil")
public class ExcelWriteUtil {
	/**
	 * excel parsing
	 * @param fileName
	 * @param measureList 
	 * @param result_total 
	 * @param file 
	 * @param path 
	 * @param contents 
	 * @return
	 * @throws IOException 
	 */
	public void write(String fileName , List<AnswerVo> studAnswerList) throws IOException
	{
		Workbook workbook = new HSSFWorkbook();

		CellStyle listCellStyle = getListCellStyle(workbook);
		CellStyle headTitleCellStyle = getHeadTitleCellStyle(workbook);
		String prestudcd = "";
		
		HSSFSheet sheet = null;
		int cnt = 1;
		for(int i=0;i<studAnswerList.size();i++) {
			AnswerVo detail = studAnswerList.get(i);
			if(!detail.getStud_cd().equals(prestudcd)) {
				String sheetname = detail.getHakwon_nm() + "(" + detail.getHakneon() + "학년_" + detail.getStud_nm() +"_" + detail.getStud_cd() +")";
				sheet = (HSSFSheet) creatSheet(workbook, sheetname);
				creatHeaderCell(sheet, headTitleCellStyle);
				cnt = 1;
			}
			creatListCell(sheet, detail, listCellStyle, cnt);

			cnt++;
			prestudcd = detail.getStud_cd();
		}
		
		
	    
		FileOutputStream outFile = new FileOutputStream(fileName);
		workbook.write(outFile);
		outFile.close();

	}

	private Sheet creatSheet(Workbook workbook,String sheetName){
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(sheetName);
		sheet.setColumnWidth(0, (256 * 20));
		sheet.setColumnWidth(1, (256 * 20));
		sheet.setColumnWidth(2, (256 * 20));
		sheet.setColumnWidth(3, (256 * 20));
		sheet.setColumnWidth(4, (256 * 20));
		sheet.setColumnWidth(5, (256 * 20));
		sheet.setColumnWidth(6, (256 * 20));
		sheet.setColumnWidth(7, (256 * 20));
		sheet.setColumnWidth(8, (256 * 20));
		sheet.setColumnWidth(9, (256 * 20));
		sheet.setColumnWidth(10, (256 * 20));
		sheet.setColumnWidth(11, (256 * 20));
		return sheet;
	}
	
	// header 생성
	private void creatHeaderCell(HSSFSheet sheet, CellStyle headTitleCellStyle) {
		HSSFRow rowtitle = sheet.createRow(0);
		Cell r0cell0 = rowtitle.createCell(0);
		r0cell0.setCellValue("과목명");
		r0cell0.setCellStyle(headTitleCellStyle);
		
		Cell r0cell1 = rowtitle.createCell(1);
		r0cell1.setCellValue("주차");
		r0cell1.setCellStyle(headTitleCellStyle);
		
		Cell r0cell2 = rowtitle.createCell(2);
		r0cell2.setCellValue("일차");
		r0cell2.setCellStyle(headTitleCellStyle);
		
		Cell r0cell3 = rowtitle.createCell(3);
		r0cell3.setCellValue("질문");
		r0cell3.setCellStyle(headTitleCellStyle);
		
		Cell r0cell4 = rowtitle.createCell(4);
		r0cell4.setCellValue("정답");
		r0cell4.setCellStyle(headTitleCellStyle);
		
		Cell r0cell5 = rowtitle.createCell(5);
		r0cell5.setCellValue("학생제출답");
		r0cell5.setCellStyle(headTitleCellStyle);
		
		Cell r0cell6 = rowtitle.createCell(6);
		r0cell6.setCellValue("정답여부");
		r0cell6.setCellStyle(headTitleCellStyle);
		
	}
	
	private void creatListCell(HSSFSheet sheet, AnswerVo answerVo , CellStyle listCellStyle, int cnt) {
		HSSFRow rowcontent = sheet.createRow(cnt);
		
		Cell r0cell0 = rowcontent.createCell(0);
		r0cell0.setCellValue(answerVo.getKwamok_nm());
		r0cell0.setCellStyle(listCellStyle);
		
		Cell r0cell1 = rowcontent.createCell(1);
		r0cell1.setCellValue(answerVo.getWeekvalue());
		r0cell1.setCellStyle(listCellStyle);
		
		Cell r0cell2 = rowcontent.createCell(2);
		r0cell2.setCellValue(answerVo.getDayvalue());
		r0cell2.setCellStyle(listCellStyle);
		
		Cell r0cell3 = rowcontent.createCell(3);
		r0cell3.setCellValue(answerVo.getQuestionvalue());
		r0cell3.setCellStyle(listCellStyle);
		
		Cell r0cell4 = rowcontent.createCell(4);
		r0cell4.setCellValue(answerVo.getRightanswer());
		r0cell4.setCellStyle(listCellStyle);
		
		Cell r0cell5 = rowcontent.createCell(5);
		r0cell5.setCellValue(answerVo.getStudanswer());
		r0cell5.setCellStyle(listCellStyle);
		
		Cell r0cell6 = rowcontent.createCell(6);
		String setvalue = "";
		if(answerVo.getRightanswer().equals(answerVo.getStudanswer())) {
			setvalue = "○";
		} else {
			setvalue = "×";
		}
		r0cell6.setCellValue(setvalue);
		r0cell6.setCellStyle(listCellStyle);
	}
	
	/*
	 * title cell style
	 */
	private CellStyle getListCellStyle(Workbook workbook){
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setWrapText(true);
	    
	    cellStyle.setLocked(true);
	    return cellStyle;
	}
	
	private CellStyle getHeadTitleCellStyle(Workbook workbook){
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //채움
	    cellStyle.setWrapText(true);
	    
	    cellStyle.setLocked(true);
	    return cellStyle;
	}
}

