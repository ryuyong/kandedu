package kr.co.kandedu.base.domain;

public class StudentKwamokVo extends SearchDefaultVo {
	private static final long serialVersionUID = -3750165976326471436L;
	
	private String stud_cd="";
	private String kwamok_cd="";
	private String kwamok_nm="";
	private String kwamok_folder_cd="";
	public String getStud_cd() {
		return stud_cd;
	}
	public void setStud_cd(String stud_cd) {
		this.stud_cd = stud_cd;
	}
	public String getKwamok_cd() {
		return kwamok_cd;
	}
	public void setKwamok_cd(String kwamok_cd) {
		this.kwamok_cd = kwamok_cd;
	}
	public String getKwamok_nm() {
		return kwamok_nm;
	}
	public void setKwamok_nm(String kwamok_nm) {
		this.kwamok_nm = kwamok_nm;
	}
	public String getKwamok_folder_cd() {
		return kwamok_folder_cd;
	}
	public void setKwamok_folder_cd(String kwamok_folder_cd) {
		this.kwamok_folder_cd = kwamok_folder_cd;
	}
}
