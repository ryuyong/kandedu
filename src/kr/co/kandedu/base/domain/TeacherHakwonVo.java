package kr.co.kandedu.base.domain;

public class TeacherHakwonVo extends SearchDefaultVo {
	private static final long serialVersionUID = 8583204656261701611L;
	
	private String teacher_cd="";
	private String hakwon_cd="";
	private String hakwon_nm="";
	public String getTeacher_cd() {
		return teacher_cd;
	}
	public void setTeacher_cd(String teacher_cd) {
		this.teacher_cd = teacher_cd;
	}
	public String getHakwon_cd() {
		return hakwon_cd;
	}
	public void setHakwon_cd(String hakwon_cd) {
		this.hakwon_cd = hakwon_cd;
	}
	public String getHakwon_nm() {
		return hakwon_nm;
	}
	public void setHakwon_nm(String hakwon_nm) {
		this.hakwon_nm = hakwon_nm;
	}
	
	
}
