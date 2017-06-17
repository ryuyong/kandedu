package kr.co.kandedu.admin.ebc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.kandedu.base.domain.AdminVo;
import kr.co.kandedu.base.domain.AnswerVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentKwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.TeacherHakwonVo;
import kr.co.kandedu.base.domain.TeacherVo;

public interface AdminStudDAO {
	
	public AdminVo getAdmin(@ModelAttribute("AdminVo") AdminVo AdminVo) throws DataAccessException;
	
	public int getStudAnswerStatsCnt(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public List<AnswerVo> getStudAnswerStats(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public int getStudAnswerSubmitStatsCnt(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public List<AnswerVo> getStudAnswerSubmitStats(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public List<AnswerVo> getStudAnswerWrongStats(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	public int getStudAnswerWrongStatsCnt(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	
	public List<AnswerVo> getStudAnswerWrongList(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	 
	
	public List<HakwonVo> getHakwonList(@ModelAttribute("HakwonVo") HakwonVo hakwonVo) throws DataAccessException;
	public int getHakwonListCnt(@ModelAttribute("HakwonVo") HakwonVo hakwonVo) throws DataAccessException;
	public void insertHakwon(@ModelAttribute("HakwonVo") HakwonVo HakwonVo) throws DataAccessException;
	public void updateHakwon(@ModelAttribute("HakwonVo") HakwonVo hakwonVo) throws DataAccessException;
	public void deleteHakwon(@ModelAttribute("HakwonVo") HakwonVo hakwonVo) throws DataAccessException;
	public String getMaxHakwoncd() throws DataAccessException;
	public HakwonVo getHakwonDetail(@ModelAttribute("HakwonVo") HakwonVo HakwonVo) throws DataAccessException;
	

	public List<StudentVo> getStudentList(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public int getStudentListCnt(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public void insertStudent(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public void updateStudent(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	
	public void updateStudentClinic(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public void deleteStudent(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public String getMaxStudcd() throws DataAccessException;
	public StudentVo getStudentDetail(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public int getStudentStudidCnt(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	
	public void deleteanswer(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	
	public void deleteanswernodayvalue(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	
	public void insertStudentKwamok(@ModelAttribute("StudentKwamokVo") StudentKwamokVo studentKwamokVo) throws DataAccessException;
	
	public void deleteStudentKwamok(@ModelAttribute("StudentKwamokVo") StudentKwamokVo studentKwamokVo) throws DataAccessException;

	
	public List<TeacherVo> getTeacherList(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	public int getTeacherListCnt(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	public void insertTeacher(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	public void updateTeacher(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	public void deleteTeacher(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	public String getMaxTeachercd() throws DataAccessException;
	public TeacherVo getTeacherDetail(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	
	public int getTeacherTeacheridCnt(@ModelAttribute("TeacherVo") TeacherVo teacherVo) throws DataAccessException;
	
	public void insertTeacherHakwon(@ModelAttribute("TeacherHakwonVo") TeacherHakwonVo teacherHakwonVo) throws DataAccessException;
	
	public void deleteTeacherHakwon(@ModelAttribute("TeacherHakwonVo") TeacherHakwonVo teacherHakwonVo) throws DataAccessException;

	
	
	public List<KwamokVo> getKwamokList(@ModelAttribute("KwamokVo") KwamokVo kwamokVo) throws DataAccessException;
	public int getKwamokListCnt(@ModelAttribute("KwamokVo") KwamokVo kwamokVo) throws DataAccessException;
	public void insertKwamok(@ModelAttribute("KwamokVo") KwamokVo KwamokVo) throws DataAccessException;
	public void updateKwamok(@ModelAttribute("KwamokVo") KwamokVo kwamokVo) throws DataAccessException;
	public void deleteKwamok(@ModelAttribute("KwamokVo") KwamokVo kwamokVo) throws DataAccessException;
	public String getMaxKwamokcd() throws DataAccessException;
	public KwamokVo getKwamokDetail(@ModelAttribute("KwamokVo") KwamokVo KwamokVo) throws DataAccessException;
	
	public List<AnswerVo> getStudAnswerList(@ModelAttribute("AnswerVo") AnswerVo AnswerVo) throws DataAccessException;
	
	public int getKwamokKwamokFolderCnt(@ModelAttribute("KwamokVo") KwamokVo KwamokVo) throws DataAccessException;
}
