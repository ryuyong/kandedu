package kr.co.kandedu.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.FileCopyUtils;

/**
 * excel write util
 * 
 * @author dev
 * 
 */
public class ExcelUtil {
	/**
	 * 일반셀스타일
	 * @param workbook
	 * @return
	 */
	public CellStyle getHeadCellStyle(Workbook workbook) {

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
	
	/**
	 * 헤더셀 스타일
	 * @param workbook
	 * @return
	 */
	public CellStyle getHeadTitleCellStyle(Workbook workbook){
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

	
	/**
	 * 시트 생성
	 * @param workbook
	 * @param sheetName
	 * @param colscnt 엑셀에 표시되는 타이틀 개수
	 * @param widths 한셀의 width크기
	 * @return
	 */
	public Sheet creatSheet(Workbook workbook,String sheetName,int colscnt,int widths){
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet(sheetName);
		for(int i=0;i<colscnt;i++) {
			sheet.setColumnWidth(i, (256 * widths));
		}
		return sheet;
	}
	
	/**
	 * 엑셀 내용 출력
	 * @param workbook
	 * @param sheet
	 * @param exceltitle 시트명, 엑셀저장명
	 * @param title 타이틀 리스트
	 * @param value 값리스트
	 * @param unit 단위표시 리스트
	 * @param firstindex 엑셀 내용 첫번째 표시줄
	 * @throws Exception
	 */
	public void createWorkBookBody(Workbook workbook,HSSFSheet sheet, String exceltitle, ArrayList<String> title ,ArrayList<String> value,ArrayList<String> unit,int firstindex) throws Exception {
		try {			
			//int cnt  = title.size();
			//HSSFSheet sheet = (HSSFSheet) creatSheet(workbook, exceltitle, cnt,25);
			CellStyle headTitleCellStyle = getHeadTitleCellStyle(workbook);
			CellStyle listCellStyle = getHeadCellStyle(workbook);
			//헤더 만들기
			HSSFRow row = sheet.createRow(firstindex);
			for(int i=0;i<title.size();i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(title.get(i));
				cell.setCellStyle(headTitleCellStyle);
			}
			for(int i=0;i<value.size();i++) {
				HSSFRow rowcontent = sheet.createRow(i+1+firstindex);
				String values = value.get(i);
				String[] setvalue = values.split(":::::");
				for(int j=0;j<setvalue.length;j++) {
					Cell cell = rowcontent.createCell(j);
					cell.setCellValue(setvalue[j]+" "+unit.get(j));
					cell.setCellStyle(listCellStyle);
				}
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 엑셀 다운로드
	 * @param workbook
	 * @param realPath
	 * @param exceltitle
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void writeExcel(Workbook workbook, String realPath, String exceltitle,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {	
			String fileName = realPath+"/"+System.currentTimeMillis()+".xls"; 
			FileOutputStream outFile = new FileOutputStream(fileName);
			workbook.write(outFile);
			outFile.close();
			
			setDisposition(exceltitle+".xls", request, response);
			downLoad(fileName, response);
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	
	
	/**
	 * Disposition 지정하기.
	 * 
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void setDisposition(String filename, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String browser = getBrowser(request);
	
			String dispositionPrefix = "attachment; filename=";
			String encodedFilename = null;
	
			if (browser.equals("MSIE")) {
				encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
						"\\+", "%20");
			} else if (browser.equals("Firefox")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			} else if (browser.equals("Opera")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			} else if (browser.equals("Chrome")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if (c > '~') {
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
			} else {
				// throw new RuntimeException("Not supported browser");
				throw new IOException("Not supported browser");
			}
	
			response.setHeader("Content-Disposition", dispositionPrefix
					+ encodedFilename);
	
			if ("Opera".equals(browser)) {
				response.setContentType("application/octet-stream;charset=UTF-8");
			}
		} catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 브라우져 종류 가져오기
	 * @param request
	 * @return
	 */
	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}
	
	
	public void downLoad(String fileName , HttpServletResponse response) throws IOException
	{
		File file = new File(fileName);

		response.setContentType("application/octet-stream;");
		response.setContentLength((int)file.length());
		response.setHeader("Content-Transfer-Encoding", "binary");
		//response.setHeader("Content-Disposition","attachment;fileName=\""+downLoadFileName+"\";");

		OutputStream outputStream = response.getOutputStream();
		FileInputStream fis = null;

		try{
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis,outputStream);
		}

		catch(java.io.IOException ioe){
			ioe.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script>alert('File Not Found');history.back();</script>");
			return;
		}
		finally{
			if(fis != null) fis.close();
		}
	}
}

