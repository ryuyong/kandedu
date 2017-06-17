package kr.co.kandedu.base.domain;

public class HakwonVo extends SearchDefaultVo {
	private static final long serialVersionUID = 6046190433081992582L;
	private String rnum="";
	private String hakwon_cd="";
	private String hakwon_nm="";
	private String use_yn = "";
	private String hakwon_cds = "";
	
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
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
	
}
