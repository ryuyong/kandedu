package kr.co.kandedu.stud.ebc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.StudentVo;

public interface StudStudDAO {
	public StudentVo getStudent(@ModelAttribute("StudentVo") StudentVo StudentVo) throws DataAccessException;
	public void insertAnswer(@ModelAttribute("AnswerVo") AnswerVo answerVo) throws DataAccessException;
	public List<AnswerVo> getStudAnswer(@ModelAttribute("AnswerVo") AnswerVo answerVo) throws DataAccessException;
}
