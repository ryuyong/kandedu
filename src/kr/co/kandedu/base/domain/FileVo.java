package kr.co.kandedu.base.domain;

import kr.co.kandedu.base.util.BaseObject;

public class FileVo extends BaseObject {
	private static final long serialVersionUID = -6788322434002387507L;
	private String filename = "";
	private String kwamok_folder_cd = "";
	private String weekvalue = "";
	private String dayvalue = "";
	
	/**
     * 첨부파일 아이디
     */
    public String file_id = "";
    /**
     * 생성일자
     */
    public String reg_date = "";
    /**
     * 삭제유무
     */
    public String del_yn = "";
    /**
     * 파일구분
     */
    public String file_sn = "";
    /**
     * 파일경로
     */
    public String file_path = "";
    /**
     * 파일명
     */
    public String file_nm= "";
    /**
     * 원본파일명
     */
    public String ori_file_nm= "";
    /**
     * 파일확장자
     */
    public String file_ext= "";
    /**
     * 파일사이즈
     */
    public String file_size= "";
    private String kwamok_cd = "";
    
    public String file_gubun;
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getWeekvalue() {
		return weekvalue;
	}

	public void setWeekvalue(String weekvalue) {
		this.weekvalue = weekvalue;
	}

	public String getDayvalue() {
		return dayvalue;
	}

	public void setDayvalue(String dayvalue) {
		this.dayvalue = dayvalue;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getDel_yn() {
		return del_yn;
	}

	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}

	public String getFile_sn() {
		return file_sn;
	}

	public void setFile_sn(String file_sn) {
		this.file_sn = file_sn;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_nm() {
		return file_nm;
	}

	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}

	public String getOri_file_nm() {
		return ori_file_nm;
	}

	public void setOri_file_nm(String ori_file_nm) {
		this.ori_file_nm = ori_file_nm;
	}

	public String getFile_ext() {
		return file_ext;
	}

	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getFile_gubun() {
		return file_gubun;
	}

	public void setFile_gubun(String file_gubun) {
		this.file_gubun = file_gubun;
	}

	public String getKwamok_folder_cd() {
		return kwamok_folder_cd;
	}

	public void setKwamok_folder_cd(String kwamok_folder_cd) {
		this.kwamok_folder_cd = kwamok_folder_cd;
	}

	public String getKwamok_cd() {
		return kwamok_cd;
	}

	public void setKwamok_cd(String kwamok_cd) {
		this.kwamok_cd = kwamok_cd;
	}
	
	
}