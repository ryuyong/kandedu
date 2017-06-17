package kr.co.kandedu.stud.pbc;

import java.util.List;

import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.StudentVo;

public interface StudStudService {
	public StudentVo getStudent(StudentVo StudentVo);
	public void insertAnswer(String path, String kwamok_folder_cd, String hakneon, AnswerVo answerVo) throws Exception ;
	public List<AnswerVo> getStudAnswer(AnswerVo answerVo);
}
