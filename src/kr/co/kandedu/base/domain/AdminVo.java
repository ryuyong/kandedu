package kr.co.kandedu.base.domain;

public class AdminVo extends SearchDefaultVo  {
	private static final long serialVersionUID = 7399461240962015545L;
	
	private String admin_cd="";
	private String admin_id="";
	private String admin_pw="";
	private String admin_nm="";
	private String use_yn="";
	public String getAdmin_cd() {
		return admin_cd;
	}
	public void setAdmin_cd(String admin_cd) {
		this.admin_cd = admin_cd;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_pw() {
		return admin_pw;
	}
	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}
	public String getAdmin_nm() {
		return admin_nm;
	}
	public void setAdmin_nm(String admin_nm) {
		this.admin_nm = admin_nm;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	
}
