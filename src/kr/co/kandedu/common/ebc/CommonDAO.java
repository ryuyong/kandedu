package kr.co.kandedu.common.ebc;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.kandedu.base.domain.FileVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentKwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.TeacherHakwonVo;

public interface CommonDAO {
	public List<HakwonVo> getHakwonList(@ModelAttribute("HakwonVo") HakwonVo hakwonVo) throws DataAccessException;
	
	public List<StudentVo> getHakwonStudList(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public String getHakwonNm(@Param("hakwon_cd") String hakwon_cd) throws DataAccessException;
	public String getStudNm(@Param("stud_cd") String stud_cd) throws DataAccessException;
	
	public void insertFileMaster(@ModelAttribute("FileVo") FileVo fileVo) throws DataAccessException;
	public void insertFileDetail(@ModelAttribute("FileVo") FileVo fileVo) throws DataAccessException;
	public FileVo selectFileInf(FileVo fileVo) throws DataAccessException ;
	
	public String getMaxFileId() throws DataAccessException;
	public String getMaxBbsId() throws DataAccessException;
	
	public String getHakwonCd(@ModelAttribute("StudentVo") StudentVo studentVo) throws DataAccessException;
	public String getMaxQnaCd() throws DataAccessException;
	
	
	public List<KwamokVo> getKwamokList(@ModelAttribute("KwamokVo") KwamokVo kwamokVo) throws DataAccessException;
	
	public List<StudentKwamokVo> getStudKwamokList(@ModelAttribute("StudentKwamokVo") StudentKwamokVo studentKwamokVo) throws DataAccessException;
	public List<TeacherHakwonVo> getTeacherHakwonList(@ModelAttribute("TeacherHakwonVo") TeacherHakwonVo teacherHakwonVo) throws DataAccessException;
	
	public List<HakwonVo> getTeacherHakwonAllList(@ModelAttribute("TeacherHakwonVo") TeacherHakwonVo teacherHakwonVo) throws DataAccessException;
	
	public KwamokVo getKwamokDetailPk(@ModelAttribute("kwamok_cd") String kwamok_cd) throws DataAccessException;
}
