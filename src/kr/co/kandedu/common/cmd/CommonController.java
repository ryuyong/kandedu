package kr.co.kandedu.common.cmd;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.kandedu.base.domain.FileVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.util.FileMngUtil;
import kr.co.kandedu.common.pbc.CommonService;

@Controller
public class CommonController {
	public static Logger logger = Logger.getLogger(CommonController.class);
	@Autowired
	CommonService commonService;
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
	
	
	@Resource(name="FileMngUtil")
	private FileMngUtil fileUtil;
	
	
	
	@RequestMapping("/common/selectFileInfs.do")
    public String selectFileInfs(FileVo fileVo, ModelMap model) throws Exception {
		try {
			FileVo result = commonService.selectFileInf(fileVo);
			//logger.debug(result.toString());
			model.addAttribute("result", result);
			model.addAttribute("updateFlag", "N");
			model.addAttribute("fileListCnt", 1);
			model.addAttribute("atchFileId", fileVo.getFile_id());
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return "/common/fileList";
    }
	
	/**
	 * 파일 다운로드()
	 * @param fileVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/fildDownload.do")
	public void fildDownload(@ModelAttribute("FileVo") FileVo fileVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		StudentVo memberVo = (StudentVo)session.getAttribute("stud");
		String hakneon = memberVo.getHakneon();
		
		try {
			String kwamok_cd = fileVO.getKwamok_cd();
			KwamokVo kwamokVo = commonService.getKwamokDetailPk(kwamok_cd);
			String kwamok_folder_cd = kwamokVo.getKwamok_folder_cd();
			
			String weekvalue = fileVO.getWeekvalue();
			String[] weekvalues = weekvalue.split(":::::");
			String week = weekvalues[1];
			
			String file_nm = week+"_"+fileVO.getDayvalue()+".pdf";
			String savefile_nm = week+"주차"+fileVO.getDayvalue()+"일차해설지.pdf";
			String answerPath = messageSourceAccessor.getMessage("pdf.answer.home.dir");
			answerPath = answerPath + kwamok_folder_cd + "/";
			
			File uFile = new File(answerPath+file_nm);
			//해설지도도 학년별로 구분
			if(!uFile.exists()) {
				file_nm = "0" + hakneon + "_" + week+"_"+fileVO.getDayvalue()+".pdf";
				uFile = new File(answerPath+file_nm);
			}
			int fSize = (int) uFile.length();
			
			if (fSize > 0) {
				 String browser = request.getHeader("User-Agent");
		         //파일 인코딩
		         if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){             
		        	 savefile_nm = URLEncoder.encode(savefile_nm,"UTF-8").replaceAll("\\+", "%20");
		         } else {               
		        	 savefile_nm = new String(savefile_nm.getBytes("UTF-8"), "ISO-8859-1");
		         }

		         
				String mimetype = "application/x-msdownload;charset=utf-8";
				response.setContentType(mimetype);
				setDisposition(savefile_nm, request, response);
				response.setContentLength(fSize);
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
	
				try {
					in = new BufferedInputStream(new FileInputStream(uFile));
					out = new BufferedOutputStream(response.getOutputStream());
	
					FileCopyUtils.copy(in, out);
					out.flush();
				} catch (Exception ex) {
					logger.debug("IGNORED: " + ex.getMessage());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception ignore) {
							logger.debug("IGNORED: " + ignore.getMessage());
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (Exception ignore) {
							logger.debug("IGNORED: " + ignore.getMessage());
						}
					}
				}
	
			} else {
				response.setContentType("application/x-msdownload");
				PrintWriter printwriter = response.getWriter();
				printwriter.flush();
				printwriter.close();
			}
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	
	/**
	 * 파일 다운로드
	 * @param fileVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/fildDownloadup.do")
	public void fildDownloadup(FileVo fileVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			FileVo fvo = commonService.selectFileInf(fileVO);
	
			File uFile = new File(fvo.getFile_path(), fvo.getFile_nm());
			int fSize = (int) uFile.length();
	
			if (fSize > 0) {
				String mimetype = "application/x-msdownload";
				response.setContentType(mimetype);
				setDisposition(fvo.getOri_file_nm(), request, response);
				response.setContentLength(fSize);
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
	
				try {
					in = new BufferedInputStream(new FileInputStream(uFile));
					out = new BufferedOutputStream(response.getOutputStream());
	
					FileCopyUtils.copy(in, out);
					out.flush();
				} catch (Exception ex) {
					logger.debug("IGNORED: " + ex.getMessage());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception ignore) {
							logger.debug("IGNORED: " + ignore.getMessage());
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (Exception ignore) {
							logger.debug("IGNORED: " + ignore.getMessage());
						}
					}
				}
	
			} else {
				response.setContentType("application/x-msdownload");
				PrintWriter printwriter = response.getWriter();
				printwriter.flush();
				printwriter.close();
			}
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	
	@RequestMapping(value="/common/attachFile.do")
	public ModelAndView attachFile(MultipartHttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			final Map<String, MultipartFile> files = request.getFileMap();
			
			String storedPath = messageSourceAccessor.getMessage("upload.sys.home.dir");
			
	    	// 첨부파일 관련 첨부파일ID 생성
			FileVo _result = null;
			if(!files.isEmpty()){
				SortedMap<String,MultipartFile> files1 = new TreeMap<String,MultipartFile>();
				files1.putAll(files);
				_result = fileUtil.parseFileInf(files1, "STUD_", 1, storedPath); 
				_result.setFile_id(commonService.getMaxFileId());
				commonService.insertFileInf(_result);
			}
			
			String file_nm = _result.getFile_nm();
			String file_id = _result.getFile_id();
			mav.addObject("file_id",file_id);
			mav.addObject("file_nm",file_nm);
			mav.setViewName("/common/fileUpload");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
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
			logger.error(e);
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
	
	
	@RequestMapping(value="/common/hwpview.do")
	public ModelAndView hwpview(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			String hwpfile = messageSourceAccessor.getMessage("hwpfile.web.file");
			mav.addObject("hwpfile",hwpfile);
			mav.setViewName("/common/hwpview");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	
	/**
	 * 파일 다운로드
	 * @param fileVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/fildDownhwp.do")
	public void fildDownhwp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String hwpfile = messageSourceAccessor.getMessage("hwpfile.file");
			File uFile = new File(hwpfile);
			int fSize = (int) uFile.length();
	
			if (fSize > 0) {
				String mimetype = "application/x-msdownload";
				response.setContentType(mimetype);
				setDisposition("hwpfile.hwp", request, response);
				response.setContentLength(fSize);
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
	
				try {
					in = new BufferedInputStream(new FileInputStream(uFile));
					out = new BufferedOutputStream(response.getOutputStream());
	
					FileCopyUtils.copy(in, out);
					out.flush();
				} catch (Exception ex) {
					logger.debug("IGNORED: " + ex.getMessage());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception ignore) {
							logger.debug("IGNORED: " + ignore.getMessage());
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (Exception ignore) {
							logger.debug("IGNORED: " + ignore.getMessage());
						}
					}
				}
	
			} else {
				response.setContentType("application/x-msdownload");
				PrintWriter printwriter = response.getWriter();
				printwriter.flush();
				printwriter.close();
			}
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	
	
	
	@RequestMapping(value="/common/bannerview.do")
	public ModelAndView bannerview(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
//			default.image.src = D:/eclipse/work/student/WebContent/images/banner/hakwon.png
//			hakwon.image.src = D:/eclipse/work/student/WebContent/images/banner/hakwon.jpg
//			curriculum.image.src = D:/eclipse/work/student/WebContent/images/banner/curriculum.jpg
//			dailytest.image.src = D:/eclipse/work/student/WebContent/images/banner/dailytest.jpg
//			clinic.image.src = D:/eclipse/work/student/WebContent/images/banner/clinic.jpg
//			system.image.src = D:/eclipse/work/student/WebContent/images/banner/system.jpg
//			default.image.src.url = /images/banner/default.png
//			hakwon.image.src.url = /images/banner/hakwon.jpg
//			curriculum.image.src.url = /images/banner/curriculum.jpg
//			dailytest.image.src.url = /images/banner/dailytest.jpg
//			clinic.image.src.url = /images/banner/clinic.jpg
//			system.image.src.url = /images/banner/system.jpg
			String gubun = request.getParameter("gubun");
			String imagepath = messageSourceAccessor.getMessage("default.image.src");
			String defaultimagepath = messageSourceAccessor.getMessage("default.image.src");
			String imagepathurl = messageSourceAccessor.getMessage("default.image.src.url");
			String defaultimagepathurl = messageSourceAccessor.getMessage("default.image.src.url");
			if(gubun != null && gubun.equals("1") ) {
				imagepath = messageSourceAccessor.getMessage("hakwon.image.src");
				imagepathurl = messageSourceAccessor.getMessage("hakwon.image.src.url");
			}
			if(gubun != null && gubun.equals("2") ) {
				imagepath = messageSourceAccessor.getMessage("curriculum.image.src");
				imagepathurl = messageSourceAccessor.getMessage("curriculum.image.src.url");
			}
			if(gubun != null && gubun.equals("3") ) {
				imagepath = messageSourceAccessor.getMessage("dailytest.image.src");
				imagepathurl = messageSourceAccessor.getMessage("dailytest.image.src.url");
			}
			if(gubun != null && gubun.equals("4") ) {
				imagepath = messageSourceAccessor.getMessage("clinic.image.src");
				imagepathurl = messageSourceAccessor.getMessage("clinic.image.src.url");
			}
			if(gubun != null && gubun.equals("5") ) {
				imagepath = messageSourceAccessor.getMessage("system.image.src");
				imagepathurl = messageSourceAccessor.getMessage("system.image.src.url");
			}
			//String hwpfile = messageSourceAccessor.getMessage("hwpfile.web.file");
			//mav.addObject("hwpfile",hwpfile);
			File imagefile = new File(imagepath);
			if(!imagefile.exists()) {
				imagefile = new File(defaultimagepath);
				imagepathurl = defaultimagepathurl;
			}
			BufferedImage bi = ImageIO.read(imagefile);
			int width=bi.getWidth();
			int height=bi.getHeight();
			mav.addObject("path",imagepathurl);
			mav.addObject("width",width);
			mav.addObject("height",height);
			mav.setViewName("/common/bannerview");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}

}
