package kr.co.kandedu.teacher.pbc;

import java.util.List;

import kr.co.kandedu.base.domain.AdminVo;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.TeacherVo;

public interface TeacherStudService {
	public TeacherVo getTeacher(TeacherVo teacherVo);
	public int getStudAnswerStatsCnt(AnswerVo AnswerVo);
	public List<AnswerVo> getStudAnswerStats(AnswerVo AnswerVo);
	public int getStudAnswerSubmitStatsCnt(AnswerVo AnswerVo);
	public List<AnswerVo> getStudAnswerSubmitStats(AnswerVo AnswerVo);
	public void deleteanswer(AnswerVo AnswerVo);
}
