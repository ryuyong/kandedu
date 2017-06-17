package kr.co.kandedu.admin.pbc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.kandedu.base.domain.AdminVo;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.TeacherVo;

public interface AdminStudService {
	public AdminVo getAdmin(AdminVo AdminVo);
	public int getStudAnswerStatsCnt(AnswerVo AnswerVo);
	public List<AnswerVo> getStudAnswerStats(AnswerVo AnswerVo);
	public int getStudAnswerSubmitStatsCnt(AnswerVo AnswerVo);
	public List<AnswerVo> getStudAnswerSubmitStats(AnswerVo AnswerVo);
	public List<AnswerVo> getStudAnswerWrongStats(AnswerVo AnswerVo);
	public int getStudAnswerWrongStatsCnt(AnswerVo AnswerVo);
	public List<AnswerVo> getStudAnswerWrongList(AnswerVo AnswerVo);
	
	public List<HakwonVo> getHakwonList(HakwonVo hakwonVo);
	public int getHakwonListCnt(HakwonVo hakwonVo);
	public void insertHakwon(HakwonVo HakwonVo);
	public void updateHakwon(HakwonVo hakwonVo);
	
	public void deleteHakwon(HakwonVo hakwonVo) throws Exception;
	public String getMaxHakwoncd();
	public HakwonVo getHakwonDetail(HakwonVo HakwonVo);
	
	public List<StudentVo> getStudentList(StudentVo studentVo);
	public int getStudentListCnt(StudentVo studentVo);
	public void insertStudent(StudentVo studentVo);
	public void updateStudent(StudentVo studentVo);
	public void deleteStudent(StudentVo studentVo) throws Exception;
	public String getMaxStudcd();
	public StudentVo getStudentDetail(StudentVo studentVo);
	public int getStudentStudidCnt(StudentVo studentVo);
	
	public void updateStudentClinic(StudentVo studentVo) throws Exception;
	
	public void deleteanswer(AnswerVo AnswerVo);
	
	public void deleteanswernodayvalue(AnswerVo AnswerVo);
	
	
	
	
	public List<TeacherVo> getTeacherList(TeacherVo teacherVo);
	public int getTeacherListCnt(TeacherVo teacherVo);
	public void insertTeacher(TeacherVo teacherVo);
	public void updateTeacher(TeacherVo teacherVo);
	
	public void deleteTeacher(TeacherVo teacherVo) throws Exception;
	public String getMaxTeachercd();
	public TeacherVo getTeacherDetail(TeacherVo teacherVo);
	
	public int getTeacherTeacheridCnt(TeacherVo teacherVo);
	
	
	public List<KwamokVo> getKwamokList(KwamokVo kwamokVo);
	public int getKwamokListCnt(KwamokVo kwamokVo);
	public void insertKwamok(KwamokVo KwamokVo);
	public void updateKwamok(KwamokVo kwamokVo);
	public void deleteKwamok(KwamokVo kwamokVo) throws Exception;
	public String getMaxKwamokcd();
	public KwamokVo getKwamokDetail(KwamokVo KwamokVo);
	
	public List<AnswerVo> getStudAnswerList(AnswerVo AnswerVo);
	
	public int getKwamokKwamokFolderCnt(KwamokVo KwamokVo);
}
