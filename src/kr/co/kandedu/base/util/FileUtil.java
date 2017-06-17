package kr.co.kandedu.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import kr.co.kandedu.base.domain.WeekVo;

@Component("FileUtil")
public class FileUtil {
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
	private static int ANSLEN = 5;
	/**
	 * PDF파일 패스명을 이용해 주차 전체 리스트를 가져옴.
	 * @param file_path PDF파일 패스
	 * @return
	 */
	public List<WeekVo> getWeekList(String file_path) {
		File file = new File(file_path);
		File[] files = file.listFiles();
		//폴더명 정렬을 위해서 임시 리스트 생성
		List<String> tempfolder = new ArrayList<String>();
		for(int i=0;i<files.length;i++) {
			String filename = files[i].getName();
			tempfolder.add(filename);
		}
		Collections.sort(tempfolder);
		
		List<WeekVo> folderlist = new ArrayList<WeekVo>();
		for(int i=0;i<tempfolder.size();i++) {
			String filename = tempfolder.get(i);
			String[] filenames = filename.split("_");
			int firstindex = Integer.parseInt(filenames[0]);
			int lastindex = Integer.parseInt(filenames[1]);
			for (int j=firstindex;j<=lastindex;j++) {
				WeekVo week = new WeekVo();
				if(j < 10) {
					week.setWeekvalue(filename+":::::0"+j);
					week.setWeekview("0"+j);
				} else {
					week.setWeekvalue(filename+":::::"+j);
					week.setWeekview(""+j);
				}
				folderlist.add(week);
			}
		}
		return folderlist;
	}
	
	public List<String> getWeekListNoFolder(String file_path) {
		File file = new File(file_path);
		File[] files = file.listFiles();
		List<String> folderlist = new ArrayList<String>();
		for(int i=0;i<files.length;i++) {
			String filename = files[i].getName();
			String[] filenames = filename.split("_");
			int firstindex = Integer.parseInt(filenames[0]);
			int lastindex = Integer.parseInt(filenames[1]);
			for (int j=firstindex;j<=lastindex;j++) {
				//WeekVo week = new WeekVo();
				String setvalue = "";
				if(j < 10) {
					setvalue = "0"+j;
				} else {
					setvalue = ""+j;
				}
				if(!folderlist.contains(filename)) {
					folderlist.add(setvalue);
				}
			}
		}
		return folderlist;
	}
	
	/**
	 * 파일명으로 일차데이터 리스트를 추출
	 * @param file_path PDF 파일 패스
	 * @param weekvalue 주차
	 * @return
	 */
	public List<String> getDaylist(String file_path, String hakneon, String weekvalue) {
		File file = new File(file_path);
		File[] files = file.listFiles();
		List<String> filelist = new ArrayList<String>();
		for(int i=0;i<files.length;i++) {
			String filename = files[i].getName();
			if(filename.indexOf("0"+hakneon+"_" + weekvalue+"_") == 0) {
				//파일명 01_01_01_1.pdf에서 두번째 일자값 취득
				filename = filename.substring(6,8);
				//일차에 문제가 여러가지가 있기 때문에 있을경우만 Array에 넣음
				if(!filelist.contains(filename)) {
					filelist.add(filename);
				}
			}
		}
		//리스값 정렬
		Collections.sort(filelist);
		return filelist;
	}
	
	
	/**
	 * 주차, 일차에 대한 질문리스트를 취득한다.
	 * @param file_path PDF파일폴더
	 * @param weekvalue
	 * @param dayvalue
	 * @return
	 */
	public List<String> getQuestionlist(String file_path, String hakneon, String weekvalue, String dayvalue) {
		File file = new File(file_path);
		File[] files = file.listFiles();
		List<String> filelist = new ArrayList<String>();
		for(int i=0;i<files.length;i++) {
			String filename = files[i].getName();
			if(filename.indexOf("0"+hakneon+"_"+weekvalue+"_") == 0) {
				//System.out.println(filename.indexOf(dayvalue+"_"));
				if(filename.indexOf("0"+hakneon+"_"+weekvalue+"_"+dayvalue+"_") == 0) {
					filename = filename.substring(9,11);
					if(!filelist.contains(filename)) {
						filelist.add(filename);
					}
				}
			}
		}
		//리스값 정렬
		Collections.sort(filelist);
		return filelist;
	}
	
	public void makepdfmergeback(List<String> filenames, String savefile) {
		try {
			PDFMergerUtility util = new PDFMergerUtility();
			for(int i=0;i<filenames.size();i++) {
				util.addSource(filenames.get(i));
			}
			util.setDestinationFileName(savefile);
			System.out.println("start:" +StringUtil.getNow() );
			util.mergeDocuments();
			System.out.println("end  :" +StringUtil.getNow() );
		} catch (Exception e) {
			System.out.println("PDFInfo-printPDFInfo ERROR : " + e.getMessage());
		}
	}
	
	
	public void makestudpdfs(String file_nm, String stud_nm, String weekval, String fontfile) {
		try {
			BaseFont bfont = BaseFont.createFont(fontfile, BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
			catFont = new Font(bfont, 18, Font.BOLD);
			redFont = new Font(bfont, 12, Font.NORMAL, BaseColor.RED);
			subFont = new Font(bfont, 16, Font.BOLD);
			smallBold = new Font(bfont, 12, Font.BOLD);
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file_nm));
			document.open();
			Paragraph preface = new Paragraph();
			addEmptyLine(preface, 1);
			preface.add(new Paragraph(stud_nm+"님의 오답노트", catFont));
			addEmptyLine(preface, 1);
			Calendar today = Calendar.getInstance();
			int year = today.get(Calendar.YEAR);
			int month = today.get(Calendar.MONTH)+1;
			int day = today.get(Calendar.DATE)+1;
			preface.add(new Paragraph("작성일 : " + year+"/"+month+"/"+day ,smallBold));
			addEmptyLine(preface, 3);
			preface.add(new Paragraph(weekval+" 오답노트 입니다.",smallBold));
			addEmptyLine(preface, 8);
			preface.add(new Paragraph("문제를 다시한번 풀어 주세요.",redFont));
			document.add(preface);
			// Start a new page
			document.newPage();
			document.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	public void makeblankpdfs(String file_nm) {
		try {
			PDDocument document = new PDDocument();
			PDPage blankpage = new PDPage();
			document.addPage(blankpage);

			// PDFont font = PDType1Font.HELVETICA_BOLD;
			PDFont font = PDType1Font.TIMES_ROMAN;
			//Font fonts = new Font("D:/gulim.ttc", 12, 12);
			
			document.save(file_nm);
			document.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param file_path //C:/pdf/question/kuk/
	 * @param hakneon 01
	 * @param file_nm 01_01_01 ANSLEN
	 * @return
	 */
	public String getRightAnswer(String file_path, String hakneon, String file_nm) {
//		File filefolder = new File(file_path);
//		//01-04,05-08...
//		File[] filefolders = filefolder.listFiles();
//		String foldername = "";
//		String rightanswer="";
//		for(int i=0;i<filefolders.length;i++) {
//			String filefoldername = filefolders[i].getName();
//			File file = new File(file_path+filefoldername);
//			File[] files = file.listFiles();
//			for(int j=0;j<files.length;j++) {
//				String filename = files[j].getName();
//				if(filename.indexOf("0"+hakneon+"_"+file_nm) == 0) {
//					rightanswer = filename.substring(12,13);
//					foldername = filefoldername;
//					break;
//				}
//			}
//		}
		
		String file_nm_week = file_nm.substring(0,2);
		String[] foldernames = getWeekFolderValue("04", file_nm_week);
		String foldername = foldernames[0] + "_" + foldernames[1];
		String rightanswer="";
		
		
		
		for(int i=1;i<=ANSLEN;i++) {
			String filename = file_path + foldername + "/0" + hakneon + "_" + file_nm + "_" + i + ".pdf";  
			File file = new File(filename);
			if(file.exists()) {
				String filenameonly = file.getName();
				rightanswer = filenameonly.substring(12,13);
				break;
			}
		}

		return foldername+":::::"+rightanswer;
	}
	
	
	
	public String getExistClinicFile(String file_path, String clinictype) {
		File filefolder = new File(file_path);
		File[] files = filefolder.listFiles();
		if(files == null) {
			return "";
		}
		String day = "";
		for(int i=0;i<files.length;i++) {
			String filename = files[i].getName();
			if(filename.indexOf(clinictype) == 0) {
				String[] names = filename.split("_");
				day = names[1];
				//break;
			}
		}
		return day;
	}
	
	
	public List<String> getClinicWeekList(String file_path) {
		File file = new File(file_path);
		File[] files = file.listFiles();
		List<String> filelist = new ArrayList<String>();
		for(int i=0;i<files.length;i++) {
			String filename = files[i].getName();
//			if(filename.indexOf("A") < 0) {
//				continue;
//			}
//			if(weekvalue.length() == 1) {
//				weekvalue = "0"+weekvalue;
//			}
			String[] names = filename.split("_");
			//filename = filename.substring(2,4);
			if(!filelist.contains(names[2])) {
				filelist.add(names[2]);
			}
		}
		//리스값 정렬
		Collections.sort(filelist);
		return filelist;
	}
	
	
	public List<String> getClinicTitleList(String file_path) {
		File file = new File(file_path);
		File[] files = file.listFiles();
		List<String> filelist = new ArrayList<String>();
		for(int i=0;i<files.length;i++) {
			String filename = files[i].getName();
			if(!filelist.contains(filename)) {
				filelist.add(filename);
			}
		}
		//리스값 정렬
		Collections.sort(filelist);
		return filelist;
	}
	
	
	public void getClinicTitlepdf(String temp_path, String titlefile,String weekvalue,String stud_cd, String stud_nm, String font) throws Exception {
		try {
		      PdfReader pdfReader = new PdfReader(titlefile);
		      PdfStamper pdfStamper = new PdfStamper(pdfReader,
		            new FileOutputStream(temp_path+"/"+stud_nm+stud_cd+".pdf"));

		      for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
		    	  if(i==1) {
			          PdfContentByte content = pdfStamper.getOverContent(1);
					  BaseFont bfont = BaseFont.createFont(font, BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
					  BaseFont helv = FontFactory.getFont(font,40,Font.BOLD).getBaseFont(); 
					  content.beginText();
					  content.setFontAndSize(bfont, 25);
					  if(stud_nm.length() == 2) {
						  content.showTextAligned(PdfContentByte.ALIGN_LEFT,stud_nm,205,663,0); 
					  } else if(stud_nm.length() == 3) {
						  content.showTextAligned(PdfContentByte.ALIGN_LEFT,stud_nm,180,663,0);
					  } else {
						  content.showTextAligned(PdfContentByte.ALIGN_LEFT,stud_nm,155,663,0);
					  }
					  
					  content.setFontAndSize(helv, 40); 
					  //content.set
					  content.showTextAligned(PdfContentByte.ALIGN_LEFT,weekvalue,123,730,0); 
					  
					  
					  content.endText(); 
		    	  }
		          //content.addImage(image);
		      }

		      pdfStamper.close();

		    } catch (IOException e) {
		    	throw e;
		    } catch (DocumentException e) {
		    	throw e;
		    }
	}
	
	
	
	
	/**
	 * @param temp_path //C:/pdf/save/temp/
	 * @param titlefile //C:/pdf/title/wrongtitle.pdf
	 * @param stud_cd
	 * @param stud_nm
	 * @param font D:/eclipse/work/kandedu/WebContent/font/GulimChe.ttf
	 * @throws Exception
	 */
	public void getWrongTitlepdf(String temp_path, String titlefile,String stud_cd, String stud_nm, String font) throws Exception {
		try {
		      PdfReader pdfReader = new PdfReader(titlefile);
		      PdfStamper pdfStamper = new PdfStamper(pdfReader,
		            new FileOutputStream(temp_path+"/"+stud_nm+stud_cd+".pdf"));

		      for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
		    	  if(i==1) {
			          PdfContentByte content = pdfStamper.getOverContent(1);
					  BaseFont bfont = BaseFont.createFont(font, BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
					  BaseFont helv = FontFactory.getFont(font,40,Font.BOLD).getBaseFont(); 
					  content.beginText();
					  content.setFontAndSize(bfont, 30);
					  if(stud_nm.length() == 2) {
						  content.showTextAligned(PdfContentByte.ALIGN_LEFT,stud_nm,380,410,0); 
					  } else if(stud_nm.length() == 3) {
						  content.showTextAligned(PdfContentByte.ALIGN_LEFT,stud_nm,350,410,0);
					  } else {
						  content.showTextAligned(PdfContentByte.ALIGN_LEFT,stud_nm,320,410,0);
					  }
					  content.endText(); 
		    	  }
		          //content.addImage(image);
		      }

		      pdfStamper.close();

		    } catch (IOException e) {
		    	throw e;
		    } catch (DocumentException e) {
		    	throw e;
		    }
	}
	
	
	public String[] getWeekFolderValue(String setweekval, String weekval) {
		int setweek = Integer.parseInt(setweekval);
		int intweekval = Integer.parseInt(weekval);
		
		int elseval = intweekval % setweek;
		int imsifirst = ( intweekval / setweek);
		if(elseval == 0) {
			imsifirst--;
		}
		imsifirst = imsifirst  * setweek + 1;
		
		String strimsifirst = "";
		
		int imsilast = imsifirst + setweek - 1;
		String strimsilast = "";
		
		if(imsifirst < 10) {
			strimsifirst = "0" + imsifirst;
		} else {
			strimsifirst = "" + imsifirst;
		}
		
		if(imsilast < 10) {
			strimsilast = "0" + imsilast;
		} else {
			strimsilast = "" + imsilast;
		}
		
		String returnval[] = {  strimsifirst, strimsilast};
		return returnval;
	}

}
