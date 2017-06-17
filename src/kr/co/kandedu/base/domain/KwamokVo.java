package kr.co.kandedu.base.domain;

public class KwamokVo extends SearchDefaultVo {
	private static final long serialVersionUID = -7131177246067445721L;
	private String rnum="";
	private String kwamok_cd="";
	private String kwamok_folder_cd="";
	private String kwamok_nm = "";
	private String use_yn = "";
	private String kwamok_cds = "";
	
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getKwamok_cd() {
		return kwamok_cd;
	}
	public void setKwamok_cd(String kwamok_cd) {
		this.kwamok_cd = kwamok_cd;
	}
	public String getKwamok_folder_cd() {
		return kwamok_folder_cd;
	}
	public void setKwamok_folder_cd(String kwamok_folder_cd) {
		this.kwamok_folder_cd = kwamok_folder_cd;
	}
	public String getKwamok_nm() {
		return kwamok_nm;
	}
	public void setKwamok_nm(String kwamok_nm) {
		this.kwamok_nm = kwamok_nm;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getKwamok_cds() {
		return kwamok_cds;
	}
	public void setKwamok_cds(String kwamok_cds) {
		this.kwamok_cds = kwamok_cds;
	}
	
}
