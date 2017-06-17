package kr.co.kandedu.admin.cmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.kandedu.admin.cmd.AdminStudController;

import kr.co.kandedu.admin.pbc.AdminStudService;
import kr.co.kandedu.base.domain.AdminVo;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.FileVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentKwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.TeacherHakwonVo;
import kr.co.kandedu.base.domain.TeacherVo;
import kr.co.kandedu.base.domain.WeekVo;
import kr.co.kandedu.base.util.ExcelWriteUtil;
import kr.co.kandedu.base.util.FileMngUtil;
import kr.co.kandedu.base.util.FileUtil;
import kr.co.kandedu.base.util.PageUtil;
import kr.co.kandedu.base.util.PdfMergeUtil;
import kr.co.kandedu.base.util.StringUtil;
import kr.co.kandedu.common.pbc.CommonService;
import kr.co.kandedu.stud.pbc.StudStudService;
import kr.co.kandedu.teacher.pbc.TeacherStudService;

@Controller
public class AdminStudController {
	public static Logger logger = Logger.getLogger(AdminStudController.class);
	
	@Autowired
	AdminStudService adminstudService;
	
	@Autowired
	TeacherStudService teacherstudService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	StudStudService studstudService;
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
	
	@Resource(name="PageUtil")
	private PageUtil pageutil;
	
	@Resource(name="FileUtil")
	private FileUtil fileUtil;
	
	
	@Resource(name="FileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name="PdfMergeUtil")
	private PdfMergeUtil pdfMergeUtil;
	
	
	@Resource(name="ExcelWriteUtil")
	private ExcelWriteUtil excelWriteUtil;
	/**
	 * 학원리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="/admin/hakwon_list.do")
	public ModelAndView hakwon_list(@ModelAttribute("HakwonVo") HakwonVo hakwonVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			pageutil.setCurrentPageNo(hakwonVo.getPageIndex());
			pageutil.setPageSize(hakwonVo.getPageSize());
			pageutil.setRecordCountPerPage(hakwonVo.getPageUnit());
			hakwonVo.setFirstIndex(pageutil.getFirstRecordIndex());
			hakwonVo.setLastIndex(pageutil.getLastRecordIndex());
			
			
//			hakwonVo.setHakwon_cd("1");
//			hakwonVo.setHakwon_nm("메가스터디");
//			adminstudService.insertHakwon(hakwonVo);
			int totalcnt = adminstudService.getHakwonListCnt(hakwonVo);
			pageutil.setTotalRecordCount(totalcnt);
			
			List<HakwonVo> hakwonList = adminstudService.getHakwonList(hakwonVo);
			mav.setViewName("admin");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/admin/hakwon_list.jsp");
			mav.addObject("hakwonList", hakwonList);
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="/admin/hakwon_insert.do")
	public ModelAndView hakwon_insert(@ModelAttribute("HakwonVo") HakwonVo hakwonVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			mav.addObject("pageUrl", "/WEB-INF/view/admin/hakwon_insert.jsp");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/hakwon_insertafter.do")
	public ModelAndView hakwon_insertafter(@ModelAttribute("HakwonVo") HakwonVo hakwonVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			
			hakwonVo.setReg_cd(memberVo.getAdmin_cd());
			hakwonVo.setUpd_cd(memberVo.getAdmin_cd());
			
			String hakwon_cd = adminstudService.getMaxHakwoncd();
			hakwonVo.setHakwon_cd(hakwon_cd);
			adminstudService.insertHakwon(hakwonVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/hakwon_list.do");
			mav.addObject("message","학원을 등록하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/hakwon_update.do")
	public ModelAndView hakwon_update(@ModelAttribute("HakwonVo") HakwonVo hakwonVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			HakwonVo result = adminstudService.getHakwonDetail(hakwonVo);
			mav.addObject("result",result );
			mav.addObject("pageUrl", "/WEB-INF/view/admin/hakwon_update.jsp");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/hakwon_updateafter.do")
	public ModelAndView hakwon_updateafter(@ModelAttribute("HakwonVo") HakwonVo hakwonVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			hakwonVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.updateHakwon(hakwonVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/hakwon_list.do");
			mav.addObject("message","학원을 수정하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/hakwon_deleteafter.do")
	public ModelAndView hakwon_deleteafter(@ModelAttribute("HakwonVo") HakwonVo hakwonVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			hakwonVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.deleteHakwon(hakwonVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/hakwon_list.do");
			mav.addObject("message","학원을 삭제하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	
	
	
	
	
	/**
	 * 학원리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="/admin/student_list.do")
	public ModelAndView student_list(@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			pageutil.setCurrentPageNo(studentVo.getPageIndex());
			pageutil.setPageSize(studentVo.getPageSize());
			pageutil.setRecordCountPerPage(studentVo.getPageUnit());
			studentVo.setFirstIndex(pageutil.getFirstRecordIndex());
			studentVo.setLastIndex(pageutil.getLastRecordIndex());
			
			int totalcnt = adminstudService.getStudentListCnt(studentVo);
			pageutil.setTotalRecordCount(totalcnt);
			
			List<StudentVo> studentList = adminstudService.getStudentList(studentVo);
			mav.setViewName("admin");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/admin/student_list.jsp");
			mav.addObject("studentList", studentList);
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			mav.addObject("hakwonList", hakwonList);
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="/admin/student_insert.do")
	public ModelAndView student_insert(@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			mav.addObject("pageUrl", "/WEB-INF/view/admin/student_insert.jsp");
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("hakwonList", hakwonList);
			mav.addObject("kwamokList", kwamokList);
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/student_insertafter.do")
	public ModelAndView hakwon_insertafter(@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			int cnt = adminstudService.getStudentStudidCnt(studentVo);
			if(cnt > 0) {
				String contextPath = request.getContextPath();
				mav.addObject("redirecturl", contextPath+"/admin/student_list.do");
				mav.addObject("message","동일한 ID가 존재합니다.");
				mav.setViewName("/common/alert_message");
				return mav;
			}
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			
			studentVo.setReg_cd(memberVo.getAdmin_cd());
			studentVo.setUpd_cd(memberVo.getAdmin_cd());
			
			String stud_cd = adminstudService.getMaxStudcd();
			studentVo.setStud_cd(stud_cd);
			adminstudService.insertStudent(studentVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/student_list.do");
			mav.addObject("message","학생을 등록하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/student_update.do")
	public ModelAndView student_update(@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			StudentVo result = adminstudService.getStudentDetail(studentVo);
			mav.addObject("result",result );
			mav.addObject("pageUrl", "/WEB-INF/view/admin/student_update.jsp");
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			
			StudentKwamokVo studentKwamokVo = new StudentKwamokVo();
			studentKwamokVo.setStud_cd(studentVo.getStud_cd());
			
			List<StudentKwamokVo> studKwamokList = commonService.getStudKwamokList(studentKwamokVo);
			mav.addObject("hakwonList", hakwonList);
			mav.addObject("kwamokList", kwamokList);
			mav.addObject("studKwamokList", studKwamokList);
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/student_updateafter.do")
	public ModelAndView hakwon_updateafter(@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			String old_stud_id = studentVo.getOld_stud_id();
			String stud_id = studentVo.getStud_id();
			if(!old_stud_id.equals(stud_id)) {
				int cnt = adminstudService.getStudentStudidCnt(studentVo);
				if(cnt > 0) {
					String contextPath = request.getContextPath();
					mav.addObject("redirecturl", contextPath+"/admin/student_list.do");
					mav.addObject("message","동일한 ID가 존재합니다.");
					mav.setViewName("/common/alert_message");
					return mav;
				}
			}
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			studentVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.updateStudent(studentVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/student_list.do");
			mav.addObject("message","학생을 수정하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/student_clinicupdate.do")
	public ModelAndView student_clinicupdate(@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			studentVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.updateStudentClinic(studentVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/student_list.do");
			mav.addObject("message","클리닉 유형을 일괄 수정하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	@RequestMapping(value="/admin/student_deleteafter.do")
	public ModelAndView student_deleteafter(@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			studentVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.deleteStudent(studentVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/student_list.do");
			if(studentVo.getUse_yn() !=null && studentVo.getUse_yn().equals("Y")) {
				mav.addObject("message","학생을 탈퇴취소처리하였습니다.");
			} else {
				mav.addObject("message","학생을 탈퇴처리하였습니다.");
			}
			
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	
	/**
	 * 어드민 로그인 화면 표시
	 * @param AdminVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login/alogin.do")
	public ModelAndView alogin(@ModelAttribute("AdminVo") AdminVo AdminVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/login/alogin");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	

	
	
	/**
	 * 어드민 로그인 처리
	 * @param adminVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login/adminlogin.do")
	public ModelAndView adminlogin(@ModelAttribute("AdminVo") AdminVo adminVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			String loginId = adminVo.getAdmin_id();
			String loginpass = adminVo.getAdmin_pw();
			//로그인시 세션값 초기화
			request.getSession().invalidate();
			adminVo = adminstudService.getAdmin(adminVo);
			if (adminVo == null)
			{
				TeacherVo pateacherVo = new TeacherVo();
				pateacherVo.setTeacher_id(loginId);
				pateacherVo.setTeacher_pass(loginpass);
				TeacherVo teacherVo = teacherstudService.getTeacher(pateacherVo);
				
				if(teacherVo == null) {
					mav.addObject("message", "로그인 정보가 올바르지 않습니다.");
					mav.setViewName("/common/alert_message");
					return mav;
				} else {
					/* 로그인정보 및 메뉴 저장 */
					HttpSession session = request.getSession();
					String contextPath = request.getContextPath();
					session.setAttribute("teacher", teacherVo);
					mav.addObject("redirecturl", contextPath+"/teacher/answer_list.do");
					mav.setViewName("/common/redirect_location");
				}
				

			} else {
				/* 로그인정보 및 메뉴 저장 */
				HttpSession session = request.getSession();
				String contextPath = request.getContextPath();
				session.setAttribute("admin", adminVo);
				mav.addObject("redirecturl", contextPath+"/admin/hakwon_list.do");
				mav.setViewName("/common/redirect_location");
			}

		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	/**
	 * 어드민 로그아웃
	 * @param AdminVo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/alogout.do")
	public void alogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//로그인시 세션값 초기화
			request.getSession().invalidate();
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath+"/login/alogin.do");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	
	/**
	 * 문제별 정답리스트 표시 
	 * @param answerVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/answer_list.do")
	public ModelAndView answer_list(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			pageutil.setCurrentPageNo(answerVo.getPageIndex());
			pageutil.setPageSize(answerVo.getPageSize());
			pageutil.setRecordCountPerPage(answerVo.getPageUnit());
			answerVo.setFirstIndex(pageutil.getFirstRecordIndex());
			answerVo.setLastIndex(pageutil.getLastRecordIndex());
			
			List<String> weekvalList = new ArrayList<String>();
			for(int i=1;i<=52;i++) {
				if(i < 10) {
					weekvalList.add("0"+i);
				} else {
					weekvalList.add(""+i);
				}
			}
			mav.addObject("weekvalList", weekvalList);
			
			List<String> dayvalList = new ArrayList<String>();
			for(int i=1;i<=31;i++) {
				if(i < 10) {
					dayvalList.add("0"+i);
				} else {
					dayvalList.add(""+i);
				}
			}
			mav.addObject("dayvalList", dayvalList);
			
			int totalcnt = 0;
			if(!StringUtil.isEmpty(answerVo.getFirstsearch())) {
				totalcnt = adminstudService.getStudAnswerStatsCnt(answerVo);
				pageutil.setTotalRecordCount(totalcnt);
			} else {
				pageutil.setTotalRecordCount(totalcnt);
			}

			if(!StringUtil.isEmpty(answerVo.getFirstsearch())) {
				List<AnswerVo> answerststsList = adminstudService.getStudAnswerStats(answerVo);
				mav.addObject("answerststsList", answerststsList);
			}
			
			//학원리스트
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			mav.addObject("hakwonList", hakwonList);
			
			//과목리스트
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("kwamokList", kwamokList);
			
			mav.setViewName("admin");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/admin/answer_list.jsp");
			
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	/**
	 * 학생별 제출 리스트
	 * @param answerVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/answersubmit_list.do")
	public ModelAndView answersubmit_list(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			pageutil.setCurrentPageNo(answerVo.getPageIndex());
			pageutil.setPageSize(answerVo.getPageSize());
			pageutil.setRecordCountPerPage(answerVo.getPageUnit());
			answerVo.setFirstIndex(pageutil.getFirstRecordIndex());
			answerVo.setLastIndex(pageutil.getLastRecordIndex());
			
			List<String> weekvalList = new ArrayList<String>();
			for(int i=1;i<=52;i++) {
				if(i < 10) {
					weekvalList.add("0"+i);
				} else {
					weekvalList.add(""+i);
				}
			}
			mav.addObject("weekvalList", weekvalList);
			
			List<String> dayvalList = new ArrayList<String>();
			for(int i=1;i<=31;i++) {
				if(i < 10) {
					dayvalList.add("0"+i);
				} else {
					dayvalList.add(""+i);
				}
			}
			mav.addObject("dayvalList", dayvalList);
			
			int totalcnt = 0;
			if(!StringUtil.isEmpty(answerVo.getFirstsearch())) {
				totalcnt = adminstudService.getStudAnswerSubmitStatsCnt(answerVo);
				pageutil.setTotalRecordCount(totalcnt);
			} else {
				pageutil.setTotalRecordCount(totalcnt);
			}

			if(!StringUtil.isEmpty(answerVo.getFirstsearch())) {
				List<AnswerVo> answersubmitststsList = adminstudService.getStudAnswerSubmitStats(answerVo);
				mav.addObject("answersubmitststsList", answersubmitststsList);
			}
			
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			mav.addObject("hakwonList", hakwonList);
			
			//과목리스트
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("kwamokList", kwamokList);
			
			mav.setViewName("admin");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/admin/answersubmit_list.jsp");
			
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	/**
	 * 오답노트생성
	 * @param answerVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/wrongnote_make.do")
	public ModelAndView wrongnote_make(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			pageutil.setCurrentPageNo(answerVo.getPageIndex());
			pageutil.setPageSize(answerVo.getPageSize());
			pageutil.setRecordCountPerPage(answerVo.getPageUnit());
			answerVo.setFirstIndex(pageutil.getFirstRecordIndex());
			answerVo.setLastIndex(pageutil.getLastRecordIndex());
			
			int totalcnt = 0;
			if(!StringUtil.isEmpty(answerVo.getFirstsearch())) {
				totalcnt = adminstudService.getStudAnswerWrongStatsCnt(answerVo);
				pageutil.setTotalRecordCount(totalcnt);
			} else {
				pageutil.setTotalRecordCount(totalcnt);
			}
			
			if(!StringUtil.isEmpty(answerVo.getFirstsearch())) {
				List<AnswerVo> answerwrongststsList = adminstudService.getStudAnswerWrongStats(answerVo);
				mav.addObject("answerwrongststsList", answerwrongststsList);
			}
			

			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			mav.addObject("hakwonList", hakwonList);

			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("kwamokList", kwamokList);
			
			String titlepdfPath = messageSourceAccessor.getMessage("pdf.title.home.dir");
			List<String> titlelist = fileUtil.getClinicTitleList(titlepdfPath);
			mav.addObject("titlelist", titlelist);
			
			List<String> weekvalList = new ArrayList<String>();
			for(int i=1;i<=52;i++) {
				if(i < 10) {
					weekvalList.add("0"+i);
				} else {
					weekvalList.add(""+i);
				}
			}
			mav.addObject("weekvalList", weekvalList);
			
			mav.setViewName("admin");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/admin/wrongnote_make.jsp");
			
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/wrongnote_aftermake.do")
	public ModelAndView wrongnote_aftermake(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			String selhakwon_cd = answerVo.getHakwon_cd();
			String selhakneon = answerVo.getHakneon();
			String selkwamok_cd = answerVo.getKwamok_cd();
			//String selsetweekval = answerVo.getSetweekval();
			String selweekval = answerVo.getWeekval();
			String selweekvalto  = answerVo.getWeekvalto();
			String now = StringUtil.getNow();
			
			//C:/pdf/question/
			String pdfPath = messageSourceAccessor.getMessage("pdf.sys.home.dir");
			//C:/pdf/save/question/
			String pdfsavePath = messageSourceAccessor.getMessage("pdf.save.home.dir");
			//C:/pdf/save/question/
			String pdfsavePathView = messageSourceAccessor.getMessage("pdf.save.home.dir");
			//C:/pdf/save/temp/
			String pdftempPath = messageSourceAccessor.getMessage("pdf.save.temp.dir");
			String fontfile = messageSourceAccessor.getMessage("pdf.korean.font.file");
			//C:/pdf/title/wrongtitle.pdf
			//String pdftitlefile = messageSourceAccessor.getMessage("pdf.wrong.title.file");
			String pdftitlefile = messageSourceAccessor.getMessage("pdf.title.home.dir")+answerVo.getTitlevalue();
			//C:/pdf/title/clinicback.pdf
			//String pdfendfile = messageSourceAccessor.getMessage("pdf.clinic.back.file");
			String pdfendfile = messageSourceAccessor.getMessage("pdf.title.home.dir")+answerVo.getTitlevalueend();
			//C:/pdf/title/blankpdf.pdf
			String pdfblankfile = messageSourceAccessor.getMessage("pdf.clinic.blank.file");
			//String setweekval = answerVo.getSetweekval();
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			mav.addObject("hakwonList", hakwonList);
			
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("kwamokList", kwamokList);
			
			List<AnswerVo> studAnswerWrongList = adminstudService.getStudAnswerWrongList(answerVo);
			
			String kwamok_cd = answerVo.getKwamok_cd();
			String kwamok_folder_cd = "";
			if(!StringUtil.isEmpty(kwamok_cd)) {
				KwamokVo kwamokVo = commonService.getKwamokDetailPk(kwamok_cd);
				kwamok_folder_cd = kwamokVo.getKwamok_folder_cd();
			}
			 answerVo.getKwamok_folder_cd();
			String motopdfsavePath = "";
			String motopdfPath = "";
			if(!StringUtil.isEmpty(kwamok_folder_cd)) {
				//C:/pdf/save/question/kuk/
				pdfsavePath = pdfsavePath + kwamok_folder_cd + "/";
				//C:/pdf/question/kuk/
				pdfPath = pdfPath + kwamok_folder_cd + "/";
			} else {
				motopdfsavePath = pdfsavePath;
				motopdfPath = pdfPath;
			}
			
			String prekwamok_folder_cd = "";
			String prestud_cd = "";
			String prestud_nm = "";
			String prehakneon = "";
			String prehakwon_nm = "";
			String savepath = "";
			String presavepath = "";
			String prepdfsavePath = "";
			String prepdfPath = "";
			
			String contextPath = request.getContextPath();
			List<String> filenames = new ArrayList<String>();
			//boolean weekselectflg = true;
			boolean kwamokselectflg = true;
			for(int i=0;i<studAnswerWrongList.size();i++) {

				AnswerVo detail = studAnswerWrongList.get(i);
//				if(StringUtil.isEmpty(answerVo.getWeekval()) || StringUtil.isEmpty(answerVo.getWeekvalto())) {
//					weekselectflg = false;
//				}
//				//주차From 주차 To중 하나라도 선택 안했을경우
//				if(!weekselectflg) {
//					if(StringUtil.isEmpty(answerVo.getWeekval()) || StringUtil.isEmpty(answerVo.getWeekvalto())) {
//						String[] setweekvals = fileUtil.getWeekFolderValue(setweekval, detail.getWeekvalue());
//						answerVo.setWeekval(setweekvals[0]);
//						answerVo.setWeekvalto(setweekvals[1]);
//					} else if (StringUtil.isEmpty(answerVo.getWeekval())) {
//						String[] setweekvals = fileUtil.getWeekFolderValue(setweekval, detail.getWeekvalue());
//						answerVo.setWeekval(setweekvals[0]);
//					} else {
//						String[] setweekvals = fileUtil.getWeekFolderValue(setweekval, answerVo.getWeekval());
//						answerVo.setWeekvalto(setweekvals[1]);
//					}
//				}
				
				String hakwon_nm = detail.getHakwon_nm();
				
				if(StringUtil.isEmpty(kwamok_folder_cd)) {
					kwamokselectflg = false;
				}
				//과목을 선택 안했을경우
				if(!kwamokselectflg) {
					kwamok_folder_cd = detail.getKwamok_folder_cd();
					//C:/pdf/save/question/kuk/
					pdfsavePath = motopdfsavePath + kwamok_folder_cd + "/";
					//C:/pdf/question/kuk/
					pdfPath = motopdfPath + kwamok_folder_cd + "/";
					prepdfsavePath = motopdfsavePath + prekwamok_folder_cd + "/";
					prepdfPath = motopdfPath + prekwamok_folder_cd + "/";
				}
				//C:/pdf/save/question/kuk/폴더 없으면 생성
				File folderkwa = new File(pdfsavePath);
				if(!folderkwa.exists()) {
					folderkwa.mkdir();
				}
				//C:/pdf/save/question/kuk/학원명/폴더 없으면 생성
				File folder = new File(pdfsavePath+hakwon_nm);
				if(!folder.exists()) {
					folder.mkdir();
				}
				//C:/pdf/save/question/kuk/학원명/01_04/폴더 없으면 생성
				File folder2 = new File(pdfsavePath+hakwon_nm+"/"+answerVo.getWeekval()+"_"+answerVo.getWeekvalto());
				if(!folder2.exists()) {
					folder2.mkdir();
				}
				
				String stud_cd = detail.getStud_cd();
				String stud_nm = detail.getStud_nm();
				String hakneon = detail.getHakneon();
				
				
				savepath = pdfsavePath + prehakwon_nm + "/"+answerVo.getWeekval()+"_"+answerVo.getWeekvalto()+"/";
				presavepath = prepdfsavePath  + prehakwon_nm + "/"+answerVo.getWeekval()+"_"+answerVo.getWeekvalto()+"/";

				if(i == 0) {
					try {
						fileUtil.getWrongTitlepdf(pdftempPath, pdftitlefile, detail.getStud_cd(),detail.getStud_nm(), fontfile);
					} catch (Exception e) {
						mav.addObject("redirecturl", contextPath+"/admin/wrongnote_make.do");
						mav.addObject("message",pdftitlefile+"파일이 존재하지 않습니다.");
						mav.setViewName("/common/redirect_location");
						return mav;
					}
					//pdftempPath=C:/pdf/save/temp/
					filenames.add(pdftempPath+stud_nm+stud_cd+".pdf");
					//filenames.add(pdftempPath+"blank.pdf");
				} else if(!prekwamok_folder_cd.equals(kwamok_folder_cd) || !prestud_cd.equals(stud_cd)) {
					int pagesizes = pdfMergeUtil.getPdfPages(filenames);
					int endfilesize = pdfMergeUtil.getPdfPage(pdfendfile);
					int addblanksize = 4 - ((pagesizes + endfilesize) % 4);
					for(int tt=0;tt<addblanksize;tt++) {
						filenames.add(pdfblankfile);
					}
					//C:/pdf/title/clinicback.pdf
					filenames.add(pdfendfile);
					//과목을 선택 안했을경우
					if(!kwamokselectflg) {
						pdfMergeUtil.makepdfmerge(filenames, presavepath+prehakneon+"학년_"+prestud_nm+prestud_cd+"의오답노트_"+now+".pdf");
					} else {
						pdfMergeUtil.makepdfmerge(filenames, savepath+prehakneon+"학년_"+prestud_nm+prestud_cd+"의오답노트_"+now+".pdf");
					}
					filenames = new ArrayList<String>();
					try {
						fileUtil.getWrongTitlepdf(pdftempPath, pdftitlefile, detail.getStud_cd(),detail.getStud_nm(), fontfile);
					} catch (Exception e) {
						mav.addObject("redirecturl", contextPath+"/admin/wrongnote_make.do");
						mav.addObject("message",pdftitlefile+"파일이 존재하지 않습니다.");
						mav.setViewName("/common/redirect_location");
						return mav;
					}
					filenames.add(pdftempPath+stud_nm+stud_cd+".pdf");
				}
				String folderrightanswer = fileUtil.getRightAnswer(pdfPath,detail.getHakneon(), detail.getWeekvalue()+"_"+detail.getDayvalue()+"_"+detail.getQuestionvalue());
				//System.out.println(pdfPath+":::::"+detail.getWeekvalue()+":::::"+detail.getDayvalue()+":::::"+detail.getQuestionvalue());
				String[] answers = folderrightanswer.split(":::::");
				String setfoldername = answers[0];
				String rightanswer = answers[1];
				filenames.add(pdfPath+setfoldername+"/"+"0" + detail.getHakneon() +"_"+ detail.getWeekvalue()+"_"+detail.getDayvalue()+"_"+detail.getQuestionvalue()+"_"+rightanswer+".pdf");
				if(i == studAnswerWrongList.size()-1) {
					int pagesizes = pdfMergeUtil.getPdfPages(filenames);
					int endfilesize = pdfMergeUtil.getPdfPage(pdfendfile);
					int addblanksize = 4 - ((pagesizes + endfilesize) % 4);
					for(int tt=0;tt<addblanksize;tt++) {
						filenames.add(pdfblankfile);
					}
					filenames.add(pdfendfile);
					pdfMergeUtil.makepdfmerge(filenames, savepath+hakneon+"학년_"+stud_nm+stud_cd+"의오답노트_"+now+".pdf");
					filenames = new ArrayList<String>();
				}
				
				prestud_cd = stud_cd;
				prestud_nm = stud_nm;
				prehakneon = hakneon;
				prekwamok_folder_cd = kwamok_folder_cd;
				prehakwon_nm = hakwon_nm;
			}

			mav.addObject("redirecturl", contextPath+"/admin/wrongnote_make.do?hakwon_cd="+selhakwon_cd+"&hakneon="+selhakneon+"&kwamok_cd="+selkwamok_cd+"&weekval="+selweekval+"&weekvalto="+selweekvalto+"&firstsearch=1");
			mav.addObject("message",pdfsavePathView+"폴더에 오답노트를 생성하였습니다.");
			
			mav.setViewName("/common/redirect_location");
			//mav.setViewName("admin");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	
	/**
	 * 오답노트생성
	 * @param answerVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/clinic_make.do")
	public ModelAndView clinic_make(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			mav.addObject("hakwonList", hakwonList);
			String titlepdfPath = messageSourceAccessor.getMessage("pdf.title.home.dir");
			
			List<String> titlelist = fileUtil.getClinicTitleList(titlepdfPath);
			
			mav.addObject("titlelist", titlelist);
			//과목리스트
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("kwamokList", kwamokList);
			
			List<String> weekvalList = new ArrayList<String>();
			for(int i=1;i<=52;i++) {
				if(i < 10) {
					weekvalList.add("0"+i);
				} else {
					weekvalList.add(""+i);
				}
			}
			mav.addObject("weekvalList", weekvalList);
			
			mav.setViewName("admin");
			mav.addObject("pageUrl", "/WEB-INF/view/admin/clinic_make.jsp");
			
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	/**
	 * 학원에 대한 학생리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/stud_selectlist.do")
	public ModelAndView stud_selectlist(@ModelAttribute("StudentVo") StudentVo studentVo, HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/common/resultMessage");
			List<StudentVo> studlist = commonService.getHakwonStudList(studentVo);
			String responseMsg = "";
			String stud_cd = studentVo.getStud_cd();
			if(studlist != null && studlist.size() > 0) {
				responseMsg = "<option value=''>선택</option>";
				for(int i=0;i<studlist.size();i++) {
					StudentVo detail = studlist.get(i);
					//String selected = "";
					
					if(stud_cd != null && stud_cd.equals(detail.getStud_cd())) {
						responseMsg += "<option value='"+detail.getStud_cd()+"' selected>"+detail.getStud_id()+":"+detail.getStud_nm()+"</option>";
					} else {
						responseMsg += "<option value='"+detail.getStud_cd()+"'>"+detail.getStud_id()+":"+detail.getStud_nm()+"</option>";
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
	
	
	
	@RequestMapping(value="/admin/clinic_aftermake.do")
	public ModelAndView clinic_aftermake(@ModelAttribute("AnswerVo") AnswerVo answerVo,@ModelAttribute("StudentVo") StudentVo studentVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			String pdfclinictitle = messageSourceAccessor.getMessage("pdf.title.home.dir") + answerVo.getTitlevalue();
			//String pdfclinicback = messageSourceAccessor.getMessage("pdf.clinic.back.file");
			String pdfclinicback = messageSourceAccessor.getMessage("pdf.title.home.dir") + answerVo.getTitlevalueend();
			String pdfclinicPath = messageSourceAccessor.getMessage("pdf.clinic.home.dir");
			String pdfclinicsavePath = messageSourceAccessor.getMessage("pdf.clinicsave.home.dir");
			String pdftempsavePath = messageSourceAccessor.getMessage("pdf.save.temp.dir");
			String font = messageSourceAccessor.getMessage("pdf.korean.font.file");
			String pdfblankfile = messageSourceAccessor.getMessage("pdf.clinic.blank.file");
			
			String contextPath = request.getContextPath();
			String hakwon_nm = commonService.getHakwonNm(answerVo.getHakwon_cd());
			String stud_cd = answerVo.getStud_cd();
			String kwamok_cd = answerVo.getKwamok_cd();
			KwamokVo kwamokVo = commonService.getKwamokDetailPk(kwamok_cd);
			String kwamok_folder_cd = kwamokVo.getKwamok_folder_cd();
			
			pdfclinicPath = pdfclinicPath + kwamok_folder_cd + "/";
			pdfclinicsavePath = pdfclinicsavePath + kwamok_folder_cd + "/";
			
			String weekvalue = answerVo.getWeekvalue();
			List<StudentVo> studentlist;
			if(stud_cd == null || stud_cd.length() == 0) {
				studentVo.setFirstIndex(0);
				studentVo.setLastIndex(100000);
				studentlist = adminstudService.getStudentList(studentVo);
			} else {
				studentlist = new ArrayList<StudentVo>();
				StudentVo param = adminstudService.getStudentDetail(studentVo);
				studentlist.add(param);
			}

			//String stud_nm = commonService.getStudNm(answerVo.getStud_cd());
			//String redirect = answerVo.getRedirect();
			File folderkwamok = new File(pdfclinicsavePath);
			if(!folderkwamok.exists()) {
				folderkwamok.mkdir();
			}
			
			String path = pdfclinicsavePath+hakwon_nm;
			
			File folder = new File(path);
			if(!folder.exists()) {
				folder.mkdir();
			}
			String resultvalue = "";
			String today = StringUtil.getToday();
			if(studentlist != null && studentlist.size() > 0) {
				for(int i=0;i<studentlist.size();i++) {
					StudentVo detail = studentlist.get(i);
					List<String> filenames = new ArrayList<String>();
					try {
						fileUtil.getClinicTitlepdf(pdftempsavePath, pdfclinictitle,weekvalue, detail.getStud_cd(),detail.getStud_nm(), font);
					} catch (Exception e) {
						mav.addObject("redirecturl", contextPath+"/admin/clinic_make.do");
						mav.addObject("message",pdfclinictitle+"파일이 존재하지 않습니다.");
						mav.setViewName("/common/redirect_location");
						return mav;
					}
					
					filenames.add(pdftempsavePath+"/"+detail.getStud_nm()+detail.getStud_cd()+".pdf");
					String clinictype = answerVo.getClinictype();
					if(clinictype == null || clinictype.length() == 0) {
						clinictype = detail.getClinictype();
					}
					if(clinictype != null && clinictype.length() > 0) {
						for(int j=0;j<clinictype.length();j++) {
							String fileindex = "0"+detail.getHakneon() + "_" + clinictype.substring(j,j+1)+"_"+weekvalue+"_";
							if(j < 9) {
								fileindex = fileindex+"0"+(j+1);
							} else {
								fileindex = fileindex + (j+1);
							}
							File tempfile = new File(pdfclinicPath+fileindex+".pdf");
							if(!tempfile.exists()) {
								mav.addObject("redirecturl", contextPath+"/admin/clinic_make.do");
								mav.addObject("message",pdfclinicPath+fileindex+".pdf파일이 존재하지 않습니다.");
								mav.setViewName("/common/redirect_location");
								return mav;
							}
							filenames.add(pdfclinicPath+fileindex+".pdf");
						}
						int pagesizes = pdfMergeUtil.getPdfPages(filenames);
						int endfilesize = pdfMergeUtil.getPdfPage(pdfclinicback);
						int addblanksize = 4 - ((pagesizes + endfilesize) % 4);
						for(int tt=0;tt<addblanksize;tt++) {
							filenames.add(pdfblankfile);
						}
						filenames.add(pdfclinicback);
						pdfMergeUtil.makepdfmerge(filenames, path+"/"+weekvalue+"주차_"+detail.getStud_nm()+clinictype+"_"+today+"_"+detail.getStud_cd()+".pdf");
					} else {
						resultvalue += detail.getStud_nm()+":";
					}
				}
				
			}
			/*
			if(redirect == null || !redirect.equals("Y")) {
				String day = fileUtil.getExistClinicFile(path,clinictype);
				if(day.length() > 0) {
					mav.setViewName("/common/redirect_confirmlocation");
					mav.addObject("redirecturlok", contextPath+"/admin/clinic_make.do?hakwon_cd="+answerVo.getHakwon_cd()+"&stud_cd="+answerVo.getStud_cd()+"&clinictype="+clinictype+"&redirect=Y");
					mav.addObject("redirecturlno", contextPath+"/admin/clinic_make.do?hakwon_cd="+answerVo.getHakwon_cd()+"&stud_cd="+answerVo.getStud_cd()+"&clinictype="+clinictype);
					mav.addObject("message",day+"날에 생성한 파일이 존재합니다.그래도 파일을 생성 하시겠습니까? ");
					return mav;
				}
			}*/
			/*
			File folder2 = new File(pdfclinicsavePath+hakwon_nm+"/"+stud_nm+answerVo.getStud_cd());
			if(!folder2.exists()) {
				folder2.mkdir();
			}*/
			//mav.addObject("redirecturl", contextPath+"/admin/clinic_make.do?hakwon_cd="+answerVo.getHakwon_cd()+"&weekvalue="+answerVo.getWeekvalue()+"&stud_cd="+answerVo.getStud_cd());
			mav.addObject("redirecturl", contextPath+"/admin/clinic_make.do");
			if(resultvalue.length() > 0) {
				mav.addObject("message",path+" 폴더에 클리닉을 생성하였습니다. "+resultvalue+" 학생은 클리닉 유형이 없어서 파일 생성 안함.");
			} else {
				mav.addObject("message",path+" 폴더에 클리닉을 생성하였습니다.");
			}
			
			
			mav.setViewName("/common/redirect_location");
			//mav.setViewName("admin");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	@RequestMapping(value="/admin/deleteanswer.do")
	public ModelAndView deleteanswer(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			adminstudService.deleteanswer(answerVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/answersubmit_list.do");
			mav.addObject("message","제출한 답안를 삭제하였습니다.");
			mav.setViewName("/common/redirect_location");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	@RequestMapping(value="/admin/deleteanswernodayvalue.do")
	public ModelAndView deleteanswernodayvalue(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			adminstudService.deleteanswernodayvalue(answerVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/wrongnote_make.do");
			mav.addObject("message","오답노트생성 오류 처리를 완료하였습니다.");
			mav.setViewName("/common/redirect_location");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 선생님리스트 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="/admin/teacher_list.do")
	public ModelAndView hakwon_list(@ModelAttribute("TeacherVo") TeacherVo teacherVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			pageutil.setCurrentPageNo(teacherVo.getPageIndex());
			pageutil.setPageSize(teacherVo.getPageSize());
			pageutil.setRecordCountPerPage(teacherVo.getPageUnit());
			teacherVo.setFirstIndex(pageutil.getFirstRecordIndex());
			teacherVo.setLastIndex(pageutil.getLastRecordIndex());
			
			int totalcnt = adminstudService.getTeacherListCnt(teacherVo);
			pageutil.setTotalRecordCount(totalcnt);
			
			List<TeacherVo> teacherList = adminstudService.getTeacherList(teacherVo);
			mav.setViewName("admin");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/admin/teacher_list.jsp");
			mav.addObject("teacherList", teacherList);
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="/admin/teacher_insert.do")
	public ModelAndView teacher_insert(@ModelAttribute("TeacherVo") TeacherVo teacherVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			mav.addObject("pageUrl", "/WEB-INF/view/admin/teacher_insert.jsp");
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			mav.addObject("hakwonList", hakwonList);
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/teacher_insertafter.do")
	public ModelAndView teacher_insertafter(@ModelAttribute("TeacherVo") TeacherVo teacherVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			int cnt = adminstudService.getTeacherTeacheridCnt(teacherVo);
			if(cnt > 0) {
				String contextPath = request.getContextPath();
				mav.addObject("redirecturl", contextPath+"/admin/teacher_list.do");
				mav.addObject("message","동일한 ID가 존재합니다.");
				mav.setViewName("/common/alert_message");
				return mav;
			}
			
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			
			teacherVo.setReg_cd(memberVo.getAdmin_cd());
			teacherVo.setUpd_cd(memberVo.getAdmin_cd());
			
			String teacher_cd = adminstudService.getMaxTeachercd();
			teacherVo.setTeacher_cd(teacher_cd);
			adminstudService.insertTeacher(teacherVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/teacher_list.do");
			mav.addObject("message","선생님을 등록하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/teacher_update.do")
	public ModelAndView teacher_update(@ModelAttribute("TeacherVo") TeacherVo teacherVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			TeacherVo result = adminstudService.getTeacherDetail(teacherVo);
			mav.addObject("result",result );
			mav.addObject("pageUrl", "/WEB-INF/view/admin/teacher_update.jsp");
			
			List<HakwonVo> hakwonList = commonService.getHakwonList(new HakwonVo());
			
			TeacherHakwonVo teacherHakwonVo = new TeacherHakwonVo();
			teacherHakwonVo.setTeacher_cd(teacherVo.getTeacher_cd());
			
			List<TeacherHakwonVo> teacherHakwonList = commonService.getTeacherHakwonList(teacherHakwonVo);
			
			mav.addObject("hakwonList", hakwonList);
			mav.addObject("teacherHakwonList", teacherHakwonList);
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/teacher_updateafter.do")
	public ModelAndView teacher_updateafter(@ModelAttribute("TeacherVo") TeacherVo teacherVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			String old_teacher_id = teacherVo.getOld_teacher_id();
			String teacher_id = teacherVo.getTeacher_id();
			if(!old_teacher_id.equals(teacher_id)) {
				int cnt = adminstudService.getTeacherTeacheridCnt(teacherVo);
				if(cnt > 0) {
					String contextPath = request.getContextPath();
					mav.addObject("redirecturl", contextPath+"/admin/teacher_list.do");
					mav.addObject("message","동일한 ID가 존재합니다.");
					mav.setViewName("/common/alert_message");
					return mav;
				}
			}
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			teacherVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.updateTeacher(teacherVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/teacher_list.do");
			mav.addObject("message","선생님을 수정하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/teacher_deleteafter.do")
	public ModelAndView teacher_deleteafter(@ModelAttribute("TeacherVo") TeacherVo teacherVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			teacherVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.deleteTeacher(teacherVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/teacher_list.do");
			mav.addObject("message","선생님을 삭제하였습니다.");
			mav.setViewName("/common/redirect_location");
			
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
	@RequestMapping(value="/admin/clinicweek_list.do")
	public ModelAndView week_list(@ModelAttribute("AnswerVo") AnswerVo answerVo, HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			String clinicpdfPath = messageSourceAccessor.getMessage("pdf.clinic.home.dir");
			String kwamok_folder_cd = answerVo.getKwamok_folder_cd();
			clinicpdfPath = clinicpdfPath + kwamok_folder_cd + "/";
			List<String> weeklist = fileUtil.getClinicWeekList(clinicpdfPath);
			
			mav.setViewName("/common/resultMessage");

			String responseMsg = "";
			if(weeklist != null && weeklist.size() > 0) {
				responseMsg = "<option value=''>선택</option>";
				for(int i=0;i<weeklist.size();i++) {
					String result = weeklist.get(i);
					
					String weekvalue = answerVo.getWeekvalue();
					if(weekvalue != null && weekvalue.equals(result)) {
						responseMsg += "<option value='"+result+"' selected>"+result+"</option>";
					} else {
						responseMsg += "<option value='"+result+"'>"+result+"</option>";
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
	
	
	
	
	
	@RequestMapping(value="/admin/kwamok_list.do")
	public ModelAndView kwamok_list(@ModelAttribute("KwamokVo") KwamokVo kwamokVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			pageutil.setCurrentPageNo(kwamokVo.getPageIndex());
			pageutil.setPageSize(kwamokVo.getPageSize());
			pageutil.setRecordCountPerPage(kwamokVo.getPageUnit());
			kwamokVo.setFirstIndex(pageutil.getFirstRecordIndex());
			kwamokVo.setLastIndex(pageutil.getLastRecordIndex());
			
			int totalcnt = adminstudService.getKwamokListCnt(kwamokVo);
			pageutil.setTotalRecordCount(totalcnt);
			
			List<KwamokVo> kwamokList = adminstudService.getKwamokList(kwamokVo);
			mav.setViewName("admin");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/admin/kwamok_list.jsp");
			mav.addObject("kwamokList", kwamokList);
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="/admin/kwamok_insert.do")
	public ModelAndView kwamok_insert(@ModelAttribute("KwamokVo") KwamokVo kwamokVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			mav.addObject("pageUrl", "/WEB-INF/view/admin/kwamok_insert.jsp");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/kwamok_insertafter.do")
	public ModelAndView kwamok_insertafter(@ModelAttribute("KwamokVo") KwamokVo kwamokVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			
			kwamokVo.setReg_cd(memberVo.getAdmin_cd());
			kwamokVo.setUpd_cd(memberVo.getAdmin_cd());
			int cnt = adminstudService.getKwamokKwamokFolderCnt(kwamokVo);
			if(cnt > 0) {
				String contextPath = request.getContextPath();
				mav.addObject("redirecturl", contextPath+"/admin/kwamok_list.do");
				mav.addObject("message","동일한폴더명이 존재합니다.");
				mav.setViewName("/common/alert_message");
				return mav;
			}
			
			String kwamok_cd = adminstudService.getMaxKwamokcd();
			kwamokVo.setKwamok_cd(kwamok_cd);
			adminstudService.insertKwamok(kwamokVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/kwamok_list.do");
			mav.addObject("message","과목을 등록하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/kwamok_update.do")
	public ModelAndView kwamok_update(@ModelAttribute("KwamokVo") KwamokVo kwamokVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("admin");
			KwamokVo result = adminstudService.getKwamokDetail(kwamokVo);
			mav.addObject("result",result );
			mav.addObject("pageUrl", "/WEB-INF/view/admin/kwamok_update.jsp");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/kwamok_updateafter.do")
	public ModelAndView kwamok_updateafter(@ModelAttribute("KwamokVo") KwamokVo kwamokVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			kwamokVo.setUpd_cd(memberVo.getAdmin_cd());
			
			int cnt = adminstudService.getKwamokKwamokFolderCnt(kwamokVo);
			if(cnt > 0) {
				String contextPath = request.getContextPath();
				mav.addObject("redirecturl", contextPath+"/admin/kwamok_list.do");
				mav.addObject("message","동일한폴더명이 존재합니다.");
				mav.setViewName("/common/alert_message");
				return mav;
			}
			
			adminstudService.updateKwamok(kwamokVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/kwamok_list.do");
			mav.addObject("message","과목을 수정하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	
	@RequestMapping(value="/admin/kwamok_deleteafter.do")
	public ModelAndView kwamok_deleteafter(@ModelAttribute("KwamokVo") KwamokVo kwamokVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			kwamokVo.setUpd_cd(memberVo.getAdmin_cd());
			
			adminstudService.deleteKwamok(kwamokVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/admin/kwamok_list.do");
			mav.addObject("message","과목을 삭제하였습니다.");
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	@RequestMapping(value="/admin/answersubmit_aftermake.do")
	public ModelAndView answersubmit_aftermake(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		String selhakwon_cd = answerVo.getHakwon_cd();
		String selhakneon = answerVo.getHakneon();
		String selkwamok_folder_cd = answerVo.getKwamok_folder_cd();
		String selweekvalue = answerVo.getWeekvalue();
		String seldayvalue  = answerVo.getDayvalue();
		
		//C:/pdf/question/
		String pdfPath = messageSourceAccessor.getMessage("pdf.sys.home.dir");
		String excelPath = messageSourceAccessor.getMessage("excel.down.home.dir");
		String now = StringUtil.getNow();
		try {
			HttpSession session = request.getSession();
			AdminVo memberVo = (AdminVo)session.getAttribute("admin");
			
			List<AnswerVo> studAnswerList = adminstudService.getStudAnswerList(answerVo);
			
			String rightFolderPath = "";
			for(int i=0;i<studAnswerList.size();i++) {
				AnswerVo detail =  studAnswerList.get(i);
				rightFolderPath = pdfPath+detail.getKwamok_folder_cd() + "/";
				String folderrightanswer = fileUtil.getRightAnswer(rightFolderPath, detail.getHakneon(), detail.getWeekvalue()+"_"+detail.getDayvalue()+"_"+detail.getQuestionvalue());
				String[] answers = folderrightanswer.split(":::::");
				//String setfoldername = answers[0];
				String rightanswer = answers[1];
				
				detail.setRightanswer(rightanswer);
			}
			
			excelWriteUtil.write(excelPath+now+".xls", studAnswerList);
			String contextPath = request.getContextPath();
			
			mav.addObject("redirecturl", contextPath+"/admin/answersubmit_list.do?hakwon_cd="+selhakwon_cd+"&hakneon="+selhakneon+"&kwamok_folder_cd="+selkwamok_folder_cd+"&weekvalue="+selweekvalue+"&dayvalue="+seldayvalue+"&firstsearch=1");
			mav.addObject("message",excelPath+"폴더에 엑셀파일을 생성하였습니다.");
			
			mav.setViewName("/common/redirect_location");
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
}
