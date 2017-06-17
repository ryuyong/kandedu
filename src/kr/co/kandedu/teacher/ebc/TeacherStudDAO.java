package kr.co.kandedu.teacher.ebc;

import java.util.List;

import kr.co.kandedu.base.domain.AdminVo;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.TeacherVo;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface TeacherStudDAO {
	public TeacherVo getTeacher(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	public int getStudAnswerStatsCnt(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public List<AnswerVo> getStudAnswerStats(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public int getStudAnswerSubmitStatsCnt(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public List<AnswerVo> getStudAnswerSubmitStats(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public void deleteanswer(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
}
