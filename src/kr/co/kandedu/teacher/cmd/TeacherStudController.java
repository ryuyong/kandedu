package kr.co.kandedu.teacher.cmd;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.TeacherHakwonVo;
import kr.co.kandedu.base.domain.TeacherVo;
import kr.co.kandedu.base.util.FileMngUtil;
import kr.co.kandedu.base.util.FileUtil;
import kr.co.kandedu.base.util.PageUtil;
import kr.co.kandedu.common.pbc.CommonService;
import kr.co.kandedu.stud.pbc.StudStudService;
import kr.co.kandedu.teacher.pbc.TeacherStudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TeacherStudController {
	public static Logger logger = Logger.getLogger(TeacherStudController.class);
	
	@Resource(name="PageUtil")
	private PageUtil pageutil;
	
	@Autowired
	TeacherStudService teacherstudService;
	
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	StudStudService studstudService;
	
	/**
	 * 문제별 정답리스트 표시 
	 * @param answerVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/teacher/answer_list.do")
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
			if(answerVo.getHakwon_cd() != null && answerVo.getHakwon_cd().length() > 0) {
				totalcnt = teacherstudService.getStudAnswerStatsCnt(answerVo);
				pageutil.setTotalRecordCount(totalcnt);
			} else {
				pageutil.setTotalRecordCount(totalcnt);
			}

			if(answerVo.getHakwon_cd() != null && answerVo.getHakwon_cd().length() > 0) {
				List<AnswerVo> answerststsList = teacherstudService.getStudAnswerStats(answerVo);
				mav.addObject("answerststsList", answerststsList);
			}
			HttpSession session = request.getSession(false);
			TeacherVo teacherVo = (TeacherVo)session.getAttribute("teacher");
			
			TeacherHakwonVo teacherHakwonVo = new TeacherHakwonVo();
			teacherHakwonVo.setTeacher_cd(teacherVo.getTeacher_cd());
			//학원리스트
			List<HakwonVo> hakwonList = commonService.getTeacherHakwonAllList(teacherHakwonVo);
			mav.addObject("hakwonList", hakwonList);
			//과목리스트
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("kwamokList", kwamokList);
			
			mav.setViewName("teacher");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/teacher/answer_list.jsp");
			
			
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
	@RequestMapping(value="/teacher/answersubmit_list.do")
	public ModelAndView answersubmit_list(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession(false);
			TeacherVo teacherVo = (TeacherVo)session.getAttribute("teacher");
			
			pageutil.setCurrentPageNo(answerVo.getPageIndex());
			pageutil.setPageSize(answerVo.getPageSize());
			pageutil.setRecordCountPerPage(answerVo.getPageUnit());
			answerVo.setFirstIndex(pageutil.getFirstRecordIndex());
			answerVo.setLastIndex(pageutil.getLastRecordIndex());
			
			int totalcnt = 0;
			if(answerVo.getHakwon_cd() != null && answerVo.getHakwon_cd().length() > 0) {
				totalcnt = teacherstudService.getStudAnswerSubmitStatsCnt(answerVo);
				pageutil.setTotalRecordCount(totalcnt);
			} else {
				pageutil.setTotalRecordCount(totalcnt);
			}
			
			String weekvalue = answerVo.getWeekvalue();
			if(weekvalue != null && weekvalue.length() > 0) {
				String[] weekvalues = weekvalue.split(":::::");
				String week = weekvalues[1];
				answerVo.setWeekval(week);
			}
			
			if(answerVo.getHakwon_cd() != null && answerVo.getHakwon_cd().length() > 0) {
				List<AnswerVo> answersubmitststsList = teacherstudService.getStudAnswerSubmitStats(answerVo);
				mav.addObject("answersubmitststsList", answersubmitststsList);
			}
			
			TeacherHakwonVo teacherHakwonVo = new TeacherHakwonVo();
			teacherHakwonVo.setTeacher_cd(teacherVo.getTeacher_cd());
			//학원리스트
			List<HakwonVo> hakwonList = commonService.getTeacherHakwonAllList(teacherHakwonVo);
			mav.addObject("hakwonList", hakwonList);
			//과목리스트
			List<KwamokVo> kwamokList = commonService.getKwamokList(new KwamokVo());
			mav.addObject("kwamokList", kwamokList);
			
			mav.setViewName("teacher");
			mav.addObject("pageUtil",pageutil.getPaging());
			mav.addObject("pageUrl", "/WEB-INF/view/teacher/answersubmit_list.jsp");
			
			
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
	
	@RequestMapping(value="/teacher/deleteanswer.do")
	public ModelAndView deleteanswer(@ModelAttribute("AnswerVo") AnswerVo answerVo,HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		try {
			teacherstudService.deleteanswer(answerVo);
			String contextPath = request.getContextPath();
			mav.addObject("redirecturl", contextPath+"/teacher/answersubmit_list.do");
			mav.addObject("message","제출한 답안를 삭제하였습니다.");
			mav.setViewName("/common/redirect_location");
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return mav;
	}
}
