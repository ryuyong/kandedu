package kr.co.kandedu.base.domain;

public class TeacherVo extends SearchDefaultVo {
	private static final long serialVersionUID = 247342178716813737L;
	
	private String rnum = "";
	private String teacher_cd="";
	private String teacher_id="";
	private String teacher_pass="";
	private String teacher_nm="";
	private String tel_no="";
	private String ptel_no="";
	private String bigo="";
	private String use_yn="";
	private String hakwon_cds="";
	private String teacher_cds="";
	private String hakwon_nms="";
	private String old_teacher_id="";
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getTeacher_cd() {
		return teacher_cd;
	}
	public void setTeacher_cd(String teacher_cd) {
		this.teacher_cd = teacher_cd;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getTeacher_pass() {
		return teacher_pass;
	}
	public void setTeacher_pass(String teacher_pass) {
		this.teacher_pass = teacher_pass;
	}
	public String getTeacher_nm() {
		return teacher_nm;
	}
	public void setTeacher_nm(String teacher_nm) {
		this.teacher_nm = teacher_nm;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getPtel_no() {
		return ptel_no;
	}
	public void setPtel_no(String ptel_no) {
		this.ptel_no = ptel_no;
	}
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getHakwon_cds() {
		return hakwon_cds;
	}
	public void setHakwon_cds(String hakwon_cds) {
		this.hakwon_cds = hakwon_cds;
	}
	public String getTeacher_cds() {
		return teacher_cds;
	}
	public void setTeacher_cds(String teacher_cds) {
		this.teacher_cds = teacher_cds;
	}
	public String getHakwon_nms() {
		return hakwon_nms;
	}
	public void setHakwon_nms(String hakwon_nms) {
		this.hakwon_nms = hakwon_nms;
	}
	public String getOld_teacher_id() {
		return old_teacher_id;
	}
	public void setOld_teacher_id(String old_teacher_id) {
		this.old_teacher_id = old_teacher_id;
	}
	
	
}
