package kr.co.kandedu.stud.cmd;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.kandedu.stud.cmd.StudStudController;

import kr.co.kandedu.admin.pbc.AdminStudService;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentKwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.WeekVo;
import kr.co.kandedu.base.util.FileUtil;
import kr.co.kandedu.base.util.PageUtil;
import kr.co.kandedu.common.pbc.CommonService;
import kr.co.kandedu.stud.pbc.StudStudService;

@Controller
public class StudStudController {
	public static Logger logger = Logger.getLogger(StudStudController.class);
	
	@Autowired
	StudStudService studstudService;
	
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	AdminStudService adminstudService;
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
	
	@Resource(name="PageUtil")
	private PageUtil pageutil;
	
	@Resource(name="FileUtil")
	private FileUtil fileUtil;
	
	/**
	 * 학생용 로그인 화면 표시
	 * @param hakwonVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login/slogin.do")
	public ModelAndView slogin(@ModelAttribute("HakwonVo") HakwonVo hakwonVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/login/slogin");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	/**
	 * 학생용 로그인 처리
	 * @param studentVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login/studlogin.do")
	public ModelAndView studlogin(@ModelAttribute("StudentVo") StudentVo studentVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			//로그인시 세션값 초기화
			request.getSession().invalidate();
			String hakwon_cd = commonService.getHakwonCd(studentVo);
			if(hakwon_cd != null && hakwon_cd.length() > 0) {
				studentVo.setHakwon_cd(hakwon_cd);
			} else {
				hakwon_cd = "";
			}
			studentVo = studstudService.getStudent(studentVo);
			if (studentVo == null)
			{
				mav.addObject("message", "로그인 정보가 올바르지 않습니다.");
				mav.setViewName("/common/alert_message");
				return mav;
			} else {
				/* 로그인정보 및 메뉴 저장 */
				HttpSession session = request.getSession();
				String contextPath = request.getContextPath();
				session.setAttribute("stud", studentVo);
				mav.addObject("redirecturl", contextPath+"/stud/question_list.do");
				mav.setViewName("/common/redirect_location");
			}

		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	/**
	 * 학생용 로그아웃 처리
	 * @param AdminVo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/slogout.do")
	public void alogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//로그인시 세션값 초기화
			request.getSession().invalidate();
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath+"/login/slogin.do");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	/**
	 * 주차에 대한 일차 리스트  Ajax
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stud/day_list.do")
	public ModelAndView day_list(@ModelAttribute("AnswerVo") AnswerVo answerVo, HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession(false);
			StudentVo memberVo = (StudentVo)session.getAttribute("stud");
			
			String pdfPath = messageSourceAccessor.getMessage("pdf.sys.home.dir");
			
			String kwamok_cd = answerVo.getKwamok_cd();
			KwamokVo kwamokVo = commonService.getKwamokDetailPk(kwamok_cd);
			String kwamok_folder_cd = kwamokVo.getKwamok_folder_cd();
			
			pdfPath = pdfPath + kwamok_folder_cd + "/";
			
			mav.setViewName("/common/resultMessage");
			String weekvalue = answerVo.getWeekvalue();
			String[] weekvalues = weekvalue.split(":::::");
			String foldername = weekvalues[0];//폴더명 01_04
			String week = weekvalues[1];//주차 01주차 02주차
			String hakneon = "";
			if(memberVo != null) {
				hakneon = memberVo.getHakneon();
			} else {
				//admin의 경우
				hakneon = answerVo.getHakneon();
			}
			 
			
			List<String> daylist = fileUtil.getDaylist(pdfPath+"/"+foldername, hakneon, week);
			Collections.sort(daylist);
			String responseMsg = "";
			if(daylist != null && daylist.size() > 0) {
				responseMsg = "<option value=''>선택</option>";
				for(int i=0;i<daylist.size();i++) {
					//String selected = "";
					String dayvalue = answerVo.getDayvalue();
					if(dayvalue != null && dayvalue.equals(daylist.get(i))) {
						responseMsg += "<option value='"+daylist.get(i)+"' selected>"+daylist.get(i)+"</option>";
					} else {
						responseMsg += "<option value='"+daylist.get(i)+"'>"+daylist.get(i)+"</option>";
					}
					
					
				}
//				responseMsg += "</select>";
			}
			mav.addObject("responseMsg",responseMsg);
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	/**
	 * 과목  Ajax
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stud/kwamok_list.do")
	public ModelAndView kwamok_list(@ModelAttribute("AnswerVo") AnswerVo answerVo, HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/common/resultMessage");
			
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			String responseMsg = "";
			if(kwamokList != null && kwamokList.size() > 0) {
				responseMsg = "<option value=''>선택</option>";
				for(int i=0;i<kwamokList.size();i++) {
					//String selected = "";
					KwamokVo result = kwamokList.get(i);
					
					String kwamok_cd = answerVo.getKwamok_cd();
					if(kwamok_cd != null && kwamok_cd.equals(result.getKwamok_cd())) {
						responseMsg += "<option value='"+result.getKwamok_cd()+"' selected>"+result.getKwamok_nm()+"</option>";
					} else {
						responseMsg += "<option value='"+result.getKwamok_cd()+"'>"+result.getKwamok_nm()+"</option>";
					}
				}
			}
			mav.addObject("responseMsg",responseMsg);
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * 과목에  대한 주차 리스트  Ajax
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stud/week_list.do")
	public ModelAndView week_list(@ModelAttribute("AnswerVo") AnswerVo answerVo, HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			String pdfPath = messageSourceAccessor.getMessage("pdf.sys.home.dir");
			
			mav.setViewName("/common/resultMessage");
			
			String kwamok_cd = answerVo.getKwamok_cd();
			KwamokVo kwamokVo = commonService.getKwamokDetailPk(kwamok_cd);
			String kwamok_folder_cd = kwamokVo.getKwamok_folder_cd();
			
			pdfPath = pdfPath + kwamok_folder_cd + "/";
			List<WeekVo> weeklist = fileUtil.getWeekList(pdfPath);
			
			String responseMsg = "";
			if(weeklist != null && weeklist.size() > 0) {
				responseMsg = "<option value=''>선택</option>";
				for(int i=0;i<weeklist.size();i++) {
					//String selected = "";
					WeekVo result = weeklist.get(i);
					
					String weekvalue = answerVo.getWeekvalue();
					if(weekvalue != null && weekvalue.equals(result.getWeekvalue())) {
						responseMsg += "<option value='"+result.getWeekvalue()+"' selected>"+result.getWeekview()+"</option>";
					} else {
						responseMsg += "<option value='"+result.getWeekvalue()+"'>"+result.getWeekview()+"</option>";
					}
				}
			}
			mav.addObject("responseMsg",responseMsg);
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	/**
	 * 학생용 답변화면 보여주기
	 * @param answerVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stud/question_list.do")
	public ModelAndView question_list(@ModelAttribute("AnswerVo") AnswerVo answerVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		StudentVo memberVo = (StudentVo)session.getAttribute("stud");
		
		try {
			String pdfPath = messageSourceAccessor.getMessage("pdf.sys.home.dir");
			
			StudentKwamokVo studentKwamokVo = new StudentKwamokVo();
			studentKwamokVo.setStud_cd(memberVo.getStud_cd());
			
			List<StudentKwamokVo> studKwamokList = commonService.getStudKwamokList(studentKwamokVo);
			mav.addObject("studKwamokList", studKwamokList);
			
			//List<WeekVo> weeklist = fileUtil.getWeekList(pdfPath);
			//mav.addObject("weeklist", weeklist);
			String kwamok_cd = answerVo.getKwamok_cd();
			String weekvalue = answerVo.getWeekvalue();
			String dayvalue = answerVo.getDayvalue();
			
			if(kwamok_cd != null && kwamok_cd.length() > 0 && 
					weekvalue != null && weekvalue.length() > 0) {
				KwamokVo kwamokVo = commonService.getKwamokDetailPk(kwamok_cd);
				String kwamok_folder_cd = kwamokVo.getKwamok_folder_cd();
				
				String[] weekvalues = weekvalue.split(":::::");
				String foldername = weekvalues[0];
				String week = weekvalues[1];
				
				answerVo.setWeekval(week);
				answerVo.setStud_cd(memberVo.getStud_cd());
				
				//질문리스트
				String hakneon = memberVo.getHakneon();
				pdfPath = pdfPath + kwamok_folder_cd + "/";
				answerVo.setKwamok_folder_cd(kwamok_folder_cd);
				
				//학생의 답변리스트
				List<AnswerVo> answerlist = studstudService.getStudAnswer(answerVo);
				if(answerlist != null && answerlist.size() > 0) {
					for(int i=0;i<answerlist.size();i++) {
						AnswerVo deatil = answerlist.get(i);
						//String setweekvalue = deatil.getWeekvalue();
						//String setdayvalue = deatil.getDayvalue();
						//String setquestionvalue = deatil.getQuestionvalue();
						
						String folderrightanswer = fileUtil.getRightAnswer(pdfPath, hakneon, deatil.getWeekvalue()+"_"+deatil.getDayvalue()+"_"+deatil.getQuestionvalue());
						String[] answers = folderrightanswer.split(":::::");
						String setfoldername = answers[0];
						String rightanswer = answers[1];
						deatil.setRightanswer(rightanswer);
					}
				}
				mav.addObject("answerlist", answerlist);
				

				List<String> questionlist = fileUtil.getQuestionlist(pdfPath+"/"+foldername,hakneon,week,dayvalue);
				mav.addObject("questionlist", questionlist);
			}
			mav.setViewName("stud");
			mav.addObject("pageUrl", "/WEB-INF/view/stud/question_list.jsp");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	/**
	 * 학생 답안 제출
	 * @param answerVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/stud/answer_insert.do")
	public ModelAndView answer_insert(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			StudentVo memberVo = (StudentVo)session.getAttribute("stud");
			answerVo.setStud_cd(memberVo.getStud_cd());
			
			answerVo.setReg_cd(memberVo.getStud_cd());
			answerVo.setUpd_cd(memberVo.getStud_cd());
			
			String pdfPath = messageSourceAccessor.getMessage("pdf.sys.home.dir");
			
			String kwamok_cd = answerVo.getKwamok_cd();
			KwamokVo kwamokVo = commonService.getKwamokDetailPk(kwamok_cd);
			String kwamok_folder_cd = kwamokVo.getKwamok_folder_cd();
			
			String weekval = answerVo.getWeekval();
			if(kwamok_folder_cd != null && kwamok_folder_cd.length() > 0 &&
					weekval != null && weekval.length() > 0) {
				String[] weekvals = weekval.split(":::::");
				String foldername = weekvals[0];
				String week = weekvals[1];
				answerVo.setWeekvalue(week);
				String hakneon = memberVo.getHakneon();
				pdfPath = pdfPath + kwamok_folder_cd + "/";
				studstudService.insertAnswer(pdfPath+foldername, kwamok_cd, hakneon, answerVo);
			}
			
			mav.addObject("message","답변을 등록하였습니다.");
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/stud/question_list.do?kwamok_cd="+kwamok_cd+"&weekvalue="+weekval+"&dayvalue="+answerVo.getDayval());
			mav.setViewName("/common/redirect_location");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
}
