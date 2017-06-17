package kr.co.kandedu.teacher.pbc.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import kr.co.kandedu.admin.ebc.AdminStudDAO;
import kr.co.kandedu.base.domain.AdminVo;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.TeacherVo;
import kr.co.kandedu.teacher.ebc.TeacherStudDAO;
import kr.co.kandedu.teacher.pbc.TeacherStudService;

@Service
public class TeacherStudServiceImpl implements TeacherStudService {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@Override
	public TeacherVo getTeacher(TeacherVo teacherVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		return teacherStudDAO.getTeacher(teacherVo);
	}
	
	@Override
	public int getStudAnswerStatsCnt(AnswerVo AnswerVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		return teacherStudDAO.getStudAnswerStatsCnt(AnswerVo);
	}
	
	@Override
	public List<AnswerVo> getStudAnswerStats(AnswerVo AnswerVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		return teacherStudDAO.getStudAnswerStats(AnswerVo);
	}

	@Override
	public List<AnswerVo> getStudAnswerSubmitStats(AnswerVo AnswerVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		return teacherStudDAO.getStudAnswerSubmitStats(AnswerVo);
	}
	
	@Override
	public int getStudAnswerSubmitStatsCnt(AnswerVo AnswerVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		return teacherStudDAO.getStudAnswerSubmitStatsCnt(AnswerVo);
	}
	
	@Override
	public void deleteanswer(AnswerVo AnswerVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		teacherStudDAO.deleteanswer(AnswerVo);
	}
}
