package kr.co.kandedu.common.pbc.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kandedu.base.domain.FileVo;
import kr.co.kandedu.base.domain.HakwonVo;
import kr.co.kandedu.base.domain.KwamokVo;
import kr.co.kandedu.base.domain.StudentKwamokVo;
import kr.co.kandedu.base.domain.StudentVo;
import kr.co.kandedu.base.domain.TeacherHakwonVo;
import kr.co.kandedu.common.ebc.CommonDAO;
import kr.co.kandedu.common.pbc.CommonService;

@Service
public class CommonServiceImpl implements CommonService {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<HakwonVo> getHakwonList(HakwonVo hakwonVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getHakwonList(hakwonVo);
	}

	@Override
	public List<StudentVo> getHakwonStudList(StudentVo studentVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getHakwonStudList(studentVo);
	}

	@Override
	public String getHakwonNm(String hakwon_cd) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getHakwonNm(hakwon_cd);
	}

	@Override
	public String getStudNm(String stud_cd) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getStudNm(stud_cd);
	}

	
	@Override
	public void insertFileMaster(FileVo fileVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		commonDAO.insertFileMaster(fileVo);
	}

	@Override
	public void insertFileDetail(FileVo fileVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		commonDAO.insertFileDetail(fileVo);
	}

	@Override
	public FileVo selectFileInf(FileVo fileVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.selectFileInf(fileVo);
	}
	
	@Override
	public String insertFileInf(FileVo fvo) throws Exception {
		CommonDAO commonDao = sqlSession.getMapper(CommonDAO.class);
		
		String atchFileId = fvo.getFile_id();
		
		commonDao.insertFileMaster(fvo);
		commonDao.insertFileDetail(fvo);
		
		return atchFileId;
	}

	@Override
	public String getMaxFileId() {
		CommonDAO commonDao = sqlSession.getMapper(CommonDAO.class);
		return commonDao.getMaxFileId();
	}

	@Override
	public String getMaxBbsId() {
		CommonDAO commonDao = sqlSession.getMapper(CommonDAO.class);
		return commonDao.getMaxBbsId();
	}

	@Override
	public String getHakwonCd(StudentVo studentVo) {
		CommonDAO commonDao = sqlSession.getMapper(CommonDAO.class);
		return commonDao.getHakwonCd(studentVo);
	}

	@Override
	public String getMaxQnaCd() {
		CommonDAO commonDao = sqlSession.getMapper(CommonDAO.class);
		return commonDao.getMaxQnaCd();
	}
	
	@Override
	public List<KwamokVo> getKwamokList(KwamokVo kwamokVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getKwamokList(kwamokVo);
	}
	
	@Override
	public List<StudentKwamokVo> getStudKwamokList(StudentKwamokVo studentKwamokVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getStudKwamokList(studentKwamokVo);
	}
	
	@Override
	public List<TeacherHakwonVo> getTeacherHakwonList(TeacherHakwonVo teacherHakwonVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getTeacherHakwonList(teacherHakwonVo);
	}
	
	@Override
	public List<HakwonVo> getTeacherHakwonAllList(TeacherHakwonVo teacherHakwonVo) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getTeacherHakwonAllList(teacherHakwonVo);
	}
	
	public KwamokVo getKwamokDetailPk(String kwamok_cd) {
		CommonDAO commonDAO = sqlSession.getMapper(CommonDAO.class);
		return commonDAO.getKwamokDetailPk(kwamok_cd);
	}
}
