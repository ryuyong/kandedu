package kr.co.kandedu.stud.pbc.impl;

import java.io.File;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.stud.ebc.StudStudDAO;
import kr.co.kandedu.stud.pbc.StudStudService;

@Service
public class StudStudServiceImpl implements StudStudService {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@Override
	public StudentVo getStudent(StudentVo StudentVo) {
		StudStudDAO studStudDAO = sqlSession.getMapper(StudStudDAO.class);
		return studStudDAO.getStudent(StudentVo);
	}

	@Override
	public void insertAnswer(String path ,String kwamok_cd, String hakneon, AnswerVo answerVo) throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			StudStudDAO studStudDAO = sqlSession.getMapper(StudStudDAO.class);
			String answers = answerVo.getAnswers();
			String[] answer = answers.split(":::::");
			
			String questions = answerVo.getQuestions();
			String[] question = questions.split(":::::");
			
			answerVo.setDayvalue(answerVo.getDayval());
			
			for(int i=0;i<answer.length;i++) {
				//해당 폴더의 파일 이름을 구함
				String filaname = path + "/" + "0"+hakneon+ "_" + answerVo.getWeekvalue() + "_" + answerVo.getDayvalue() + "_" + question[i] + "_" + answer[i]+".pdf";
				
				File file = new File(filaname);
				String right = "N";
				//파일이 존재할 경우 정답 아니면 오답
				if(file.exists()) {
					right = "Y";
				}
				answerVo.setKwamok_cd(kwamok_cd);
				answerVo.setStudanswer(answer[i]);
				answerVo.setQuestionvalue(question[i]);
				answerVo.setRightyn(right);
				studStudDAO.insertAnswer(answerVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
		
	}

	@Override
	public List<AnswerVo> getStudAnswer(AnswerVo answerVo) {
		StudStudDAO studStudDAO = sqlSession.getMapper(StudStudDAO.class);
		return studStudDAO.getStudAnswer(answerVo);
	}

}
