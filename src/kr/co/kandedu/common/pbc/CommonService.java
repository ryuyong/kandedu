package kr.co.kandedu.common.pbc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.kandedu.base.domain.FileVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentKwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.TeacherHakwonVo;

public interface CommonService {
	public List<HakwonVo> getHakwonList(HakwonVo hakwonVo);
	public List<StudentVo> getHakwonStudList(StudentVo studentVo);
	public String getHakwonNm(String hakwon_cd);
	public String getStudNm(String stud_cd);
	public String insertFileInf(FileVo fvo) throws Exception;
	public void insertFileMaster(FileVo fileVo);
	public void insertFileDetail(FileVo fileVo);
	public FileVo selectFileInf(FileVo fileVo);
	
	public String getMaxFileId();
	public String getMaxBbsId();
	
	public String getHakwonCd(StudentVo studentVo);
	public String getMaxQnaCd();
	
	public List<KwamokVo> getKwamokList(KwamokVo kwamokVo);
	public List<StudentKwamokVo> getStudKwamokList(StudentKwamokVo studentKwamokVo);
	
	public List<TeacherHakwonVo> getTeacherHakwonList(TeacherHakwonVo teacherHakwonVo);
	
	public List<HakwonVo> getTeacherHakwonAllList(TeacherHakwonVo teacherHakwonVo);

	public KwamokVo getKwamokDetailPk(String kwamok_cd);
}
