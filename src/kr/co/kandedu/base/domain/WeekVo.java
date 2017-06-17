package kr.co.kandedu.base.domain;

import kr.co.kandedu.base.util.BaseObject;

public class WeekVo extends BaseObject {
	private static final long serialVersionUID = -573095195199357258L;
	
	private String weekvalue="";
	private String weekview="";
	public String getWeekvalue() {
		return weekvalue;
	}
	public void setWeekvalue(String weekvalue) {
		this.weekvalue = weekvalue;
	}
	public String getWeekview() {
		return weekview;
	}
	public void setWeekview(String weekview) {
		this.weekview = weekview;
	}
	
	
}
