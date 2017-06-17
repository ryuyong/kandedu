package kr.co.kandedu.admin.pbc.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.kandedu.admin.ebc.AdminStudDAO;
import kr.co.kandedu.admin.pbc.AdminStudService;
import kr.co.kandedu.base.domain.AdminVo;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentKwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.TeacherHakwonVo;
import kr.co.kandedu.base.domain.TeacherVo;
import kr.co.kandedu.common.ebc.CommonDAO;
import kr.co.kandedu.teacher.ebc.TeacherStudDAO;

@Service
public class AdminStudServiceImpl implements AdminStudService {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@Override
	public AdminVo getAdmin(AdminVo AdminVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getAdmin(AdminVo);
	}

	@Override
	public int getStudAnswerStatsCnt(AnswerVo AnswerVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		return teacherStudDAO.getStudAnswerStatsCnt(AnswerVo);
	}
	
	@Override
	public List<AnswerVo> getStudAnswerStats(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudAnswerStats(AnswerVo);
	}

	@Override
	public List<AnswerVo> getStudAnswerSubmitStats(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudAnswerSubmitStats(AnswerVo);
	}

	@Override
	public int getStudAnswerSubmitStatsCnt(AnswerVo AnswerVo) {
		TeacherStudDAO teacherStudDAO = sqlSession.getMapper(TeacherStudDAO.class);
		return teacherStudDAO.getStudAnswerSubmitStatsCnt(AnswerVo);
	}
	
	@Override
	public List<AnswerVo> getStudAnswerWrongStats(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudAnswerWrongStats(AnswerVo);
	}
	
	@Override
	public int getStudAnswerWrongStatsCnt(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudAnswerWrongStatsCnt(AnswerVo);
	}

	@Override
	public List<AnswerVo> getStudAnswerWrongList(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudAnswerWrongList(AnswerVo);
	}

	@Override
	public List<HakwonVo> getHakwonList(HakwonVo hakwonVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getHakwonList(hakwonVo);
	}

	@Override
	public int getHakwonListCnt(HakwonVo hakwonVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getHakwonListCnt(hakwonVo);
	}

	@Override
	public void insertHakwon(HakwonVo HakwonVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		AdminStudDAO.insertHakwon(HakwonVo);
	}

	@Override
	public void updateHakwon(HakwonVo hakwonVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		AdminStudDAO.updateHakwon(hakwonVo);
	}

	@Override
	public void deleteHakwon(HakwonVo hakwonVo) throws Exception  {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{
			AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			String hakwon_cds = hakwonVo.getHakwon_cds();
			String[] hakwon_cd = hakwon_cds.split(":::::");
			for(int i=0;i<hakwon_cd.length;i++) {
				hakwonVo.setHakwon_cd(hakwon_cd[i]);
				AdminStudDAO.deleteHakwon(hakwonVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	@Override
	public String getMaxHakwoncd() {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getMaxHakwoncd();
	}

	@Override
	public HakwonVo getHakwonDetail(HakwonVo HakwonVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getHakwonDetail(HakwonVo);
	}

	@Override
	public List<StudentVo> getStudentList(StudentVo studentVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		CommonDAO CommonDAO = sqlSession.getMapper(CommonDAO.class);
		
		List<StudentVo> getStudentList = AdminStudDAO.getStudentList(studentVo);
		
		for(int i=0;i<getStudentList.size();i++) {
			StudentVo result = getStudentList.get(i);
			
			StudentKwamokVo studentKwamokVo = new StudentKwamokVo();
			studentKwamokVo.setStud_cd(result.getStud_cd());
			
			List<StudentKwamokVo> getStudKwamokNmList = CommonDAO.getStudKwamokList(studentKwamokVo);
			
			String kwamok_nms = "";
			for(int j=0;j<getStudKwamokNmList.size();j++) {
				StudentKwamokVo restudentKwamokVo = getStudKwamokNmList.get(j);
				
				kwamok_nms += restudentKwamokVo.getKwamok_nm() + " ";
				
			}
			result.setKwamok_nms(kwamok_nms);
		}
		return getStudentList;
	}

	@Override
	public int getStudentListCnt(StudentVo studentVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudentListCnt(studentVo);
	}

	@Override
	public void insertStudent(StudentVo studentVo) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{

			AdminStudDAO adminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			adminStudDAO.insertStudent(studentVo);
			
			String kwamoks = studentVo.getKwamoks();
			String[] kwamok = kwamoks.split(":::::");
			for(int i=0;i<kwamok.length;i++) {
				StudentKwamokVo studentKwamokVo = new StudentKwamokVo();
				studentKwamokVo.setStud_cd(studentVo.getStud_cd());
				studentKwamokVo.setKwamok_cd(kwamok[i]);
				studentKwamokVo.setReg_cd(studentVo.getReg_cd());
				studentKwamokVo.setUpd_cd(studentVo.getUpd_cd());
				adminStudDAO.insertStudentKwamok(studentKwamokVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
		transactionManager.commit(status);
		
	}

	@Override
	public void updateStudent(StudentVo studentVo) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{

			AdminStudDAO adminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			adminStudDAO.updateStudent(studentVo);
			
			StudentKwamokVo studentKwamokVo = new StudentKwamokVo();
			studentKwamokVo.setStud_cd(studentVo.getStud_cd());
			adminStudDAO.deleteStudentKwamok(studentKwamokVo);
			
			String kwamoks = studentVo.getKwamoks();
			String[] kwamok = kwamoks.split(":::::");
			for(int i=0;i<kwamok.length;i++) {
				studentKwamokVo.setKwamok_cd(kwamok[i]);
				studentKwamokVo.setReg_cd(studentVo.getReg_cd());
				studentKwamokVo.setUpd_cd(studentVo.getUpd_cd());
				adminStudDAO.insertStudentKwamok(studentKwamokVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
		transactionManager.commit(status);
	}

	@Override
	public void deleteStudent(StudentVo studentVo) throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{
			AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			String stud_cds = studentVo.getStud_cds();
			String[] stud_cd = stud_cds.split(":::::");
			for(int i=0;i<stud_cd.length;i++) {
				studentVo.setStud_cd(stud_cd[i]);
				AdminStudDAO.deleteStudent(studentVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	@Override
	public String getMaxStudcd() {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getMaxStudcd();
	}

	@Override
	public StudentVo getStudentDetail(StudentVo studentVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudentDetail(studentVo);
	}

	@Override
	public int getStudentStudidCnt(StudentVo studentVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudentStudidCnt(studentVo);
	}

	@Override
	public void updateStudentClinic(StudentVo studentVo) throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			String stud_cds = studentVo.getStud_cds();
			String clinictypes = studentVo.getClinictypes();
			
			String[] stud_cd = stud_cds.split(":::::");
			String[] clinictype = clinictypes.split(":::::");
			for(int i=0;i<stud_cd.length;i++) {
				studentVo.setStud_cd(stud_cd[i]);
				if(clinictype[i] != null && clinictype[i].equals("NULL")) {
					studentVo.setClinictype("");
				} else {
					studentVo.setClinictype(clinictype[i]);
				}
				AdminStudDAO.updateStudentClinic(studentVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}
	
	@Override
	public void deleteanswer(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		AdminStudDAO.deleteanswer(AnswerVo);
	}
	
	@Override
	public void deleteanswernodayvalue(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		AdminStudDAO.deleteanswernodayvalue(AnswerVo);
	}
	
	
	
	
	
	
	
	
	
	@Override
	public List<TeacherVo> getTeacherList(TeacherVo teacherVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		CommonDAO CommonDAO = sqlSession.getMapper(CommonDAO.class);
		List<TeacherVo> getTeacherList = AdminStudDAO.getTeacherList(teacherVo);
		
		for(int i=0;i<getTeacherList.size();i++) {
			TeacherVo result = getTeacherList.get(i);
			
			TeacherHakwonVo teacherHakwonVo = new TeacherHakwonVo();
			teacherHakwonVo.setTeacher_cd(result.getTeacher_cd());
			
			List<TeacherHakwonVo> getTeacherHakwonNmList = CommonDAO.getTeacherHakwonList(teacherHakwonVo);
			
			String hakwon_nms = "";
			for(int j=0;j<getTeacherHakwonNmList.size();j++) {
				TeacherHakwonVo reteacherHakwonVo = getTeacherHakwonNmList.get(j);
				
				hakwon_nms += reteacherHakwonVo.getHakwon_nm() + " ";
				
			}
			result.setHakwon_nms(hakwon_nms);
		}
		return getTeacherList;
	}

	@Override
	public int getTeacherListCnt(TeacherVo teacherVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getTeacherListCnt(teacherVo);
	}

	@Override
	public void insertTeacher(TeacherVo teacherVo) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{

			AdminStudDAO adminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			adminStudDAO.insertTeacher(teacherVo);
			
			String hakwon_cds = teacherVo.getHakwon_cds();
			String[] hakwon_cd = hakwon_cds.split(":::::");
			for(int i=0;i<hakwon_cd.length;i++) {
				TeacherHakwonVo teacherHakwonVo = new TeacherHakwonVo();
				teacherHakwonVo.setTeacher_cd(teacherVo.getTeacher_cd());
				teacherHakwonVo.setHakwon_cd(hakwon_cd[i]);
				teacherHakwonVo.setReg_cd(teacherVo.getReg_cd());
				teacherHakwonVo.setUpd_cd(teacherVo.getUpd_cd());
				adminStudDAO.insertTeacherHakwon(teacherHakwonVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
		transactionManager.commit(status);
	}

	@Override
	public void updateTeacher(TeacherVo teacherVo) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{

			AdminStudDAO adminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			adminStudDAO.updateTeacher(teacherVo);
			
			TeacherHakwonVo teacherHakwonVo = new TeacherHakwonVo();
			teacherHakwonVo.setTeacher_cd(teacherVo.getTeacher_cd());
			adminStudDAO.deleteTeacherHakwon(teacherHakwonVo);
			
			String hakwon_cds = teacherVo.getHakwon_cds();
			String[] hakwon_cd = hakwon_cds.split(":::::");
			for(int i=0;i<hakwon_cd.length;i++) {
				teacherHakwonVo.setHakwon_cd(hakwon_cd[i]);
				teacherHakwonVo.setReg_cd(teacherVo.getReg_cd());
				teacherHakwonVo.setUpd_cd(teacherVo.getUpd_cd());
				adminStudDAO.insertTeacherHakwon(teacherHakwonVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			e.printStackTrace();
		}
		transactionManager.commit(status);
	}

	@Override
	public void deleteTeacher(TeacherVo teacherVo) throws Exception  {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{
			AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			String teacher_cds = teacherVo.getTeacher_cds();
			String[] teacher_cd = teacher_cds.split(":::::");
			for(int i=0;i<teacher_cd.length;i++) {
				teacherVo.setTeacher_cd(teacher_cd[i]);
				AdminStudDAO.deleteTeacher(teacherVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	@Override
	public String getMaxTeachercd() {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getMaxTeachercd();
	}

	@Override
	public TeacherVo getTeacherDetail(TeacherVo teacherVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getTeacherDetail(teacherVo);
	}

	@Override
	public int getTeacherTeacheridCnt(TeacherVo teacherVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getTeacherTeacheridCnt(teacherVo);
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<KwamokVo> getKwamokList(KwamokVo kwamokVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getKwamokList(kwamokVo);
	}

	@Override
	public int getKwamokListCnt(KwamokVo kwamokVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getKwamokListCnt(kwamokVo);
	}

	@Override
	public void insertKwamok(KwamokVo KwamokVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		AdminStudDAO.insertKwamok(KwamokVo);
	}

	@Override
	public void updateKwamok(KwamokVo kwamokVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		AdminStudDAO.updateKwamok(kwamokVo);
	}

	@Override
	public void deleteKwamok(KwamokVo kwamokVo) throws Exception  {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try{
			AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
			String kwamok_cds = kwamokVo.getKwamok_cds();
			String[] kwamok_cd = kwamok_cds.split(":::::");
			for(int i=0;i<kwamok_cd.length;i++) {
				kwamokVo.setKwamok_cd(kwamok_cd[i]);
				AdminStudDAO.deleteKwamok(kwamokVo);
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		transactionManager.commit(status);
	}

	@Override
	public String getMaxKwamokcd() {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getMaxKwamokcd();
	}

	@Override
	public KwamokVo getKwamokDetail(KwamokVo KwamokVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getKwamokDetail(KwamokVo);
	}
	
	@Override
	public List<AnswerVo> getStudAnswerList(AnswerVo AnswerVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getStudAnswerList(AnswerVo);
	}
	
	@Override
	public int getKwamokKwamokFolderCnt(KwamokVo KwamokVo) {
		AdminStudDAO AdminStudDAO = sqlSession.getMapper(AdminStudDAO.class);
		return AdminStudDAO.getKwamokKwamokFolderCnt(KwamokVo);
	}
}
