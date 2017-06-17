package kr.co.kandedu.base.util;

import org.springframework.stereotype.Component;

@Component("PageUtil")
public class PageUtil {
	//현재 페이지
    private int currentPageNo;
    //한페이지에 표시되는 수건수
    private int recordCountPerPage;
    //한페이지에 표시되는 페이지 사이즈
    private int pageSize;
    //전체 건수
    private int totalRecordCount;
    //전체 페이지수
    private int totalPageCount;
    private int firstPageNoOnPageList;
    private int lastPageNoOnPageList;
    //SQL처음 건수
    private int firstRecordIndex;
    //SQL마지막건수
    private int lastRecordIndex;
    
	public int getRecordCountPerPage()
    {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage(int recordCountPerPage)
    {
        this.recordCountPerPage = recordCountPerPage;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getCurrentPageNo()
    {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo)
    {
        this.currentPageNo = currentPageNo;
    }

    public void setTotalRecordCount(int totalRecordCount)
    {
        this.totalRecordCount = totalRecordCount;
    }

    public int getTotalRecordCount()
    {
        return totalRecordCount;
    }

    public int getTotalPageCount()
    {
        totalPageCount = (getTotalRecordCount() - 1) / getRecordCountPerPage() + 1;
        return totalPageCount;
    }

    public int getFirstPageNo()
    {
        return 1;
    }

    public int getLastPageNo()
    {
        return getTotalPageCount();
    }
    //현재 그룹에서의 첫번째 페이지
    public int getFirstPageNoOnPageList()
    {
        firstPageNoOnPageList = ((getCurrentPageNo() - 1) / getPageSize()) * getPageSize() + 1;
        return firstPageNoOnPageList;
    }
    //현재 그룹에서의 마지막 페이지
    public int getLastPageNoOnPageList()
    {
        lastPageNoOnPageList = (getFirstPageNoOnPageList() + getPageSize()) - 1;
        if(lastPageNoOnPageList > getTotalPageCount())
            lastPageNoOnPageList = getTotalPageCount();
        return lastPageNoOnPageList;
    }
    //첫번째 레코드 INDEX 취득
    public int getFirstRecordIndex()
    {
        firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
        return firstRecordIndex;
    }
    
    //마지막레코드 INDEX취득
    public int getLastRecordIndex()
    {
        lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
        return lastRecordIndex;
    }
    
	//이전그룹 존재여부 확인
	public boolean isPrePage(){
		boolean check = false;
		if(currentPageNo > pageSize){
			check = true;
		}
		return check;
	}
	//이전페이지 (-10)
	public int getPrePage(){
		int prePage = 0;
		int pageList = (new Float((currentPageNo - 1) / pageSize).intValue() * pageSize);
		if(this.isPrePage()){
			prePage = pageList - pageSize + 1;
		}
		return prePage;
	}
	
	//다음페이지 존재여부 확인
	public boolean isNextPage(){
		boolean check = false;
		int pageList = (new Float((currentPageNo - 1) / pageSize).intValue() * pageSize);
		if((pageList + pageSize + 1) <= getTotalPageCount()){
			check = true;
		}
		return check;
	}
	
	//다음페이지(+10)
	public int getNextPage(){
		int nextPage = 0;
		int pageList = (new Float((currentPageNo - 1) / pageSize).intValue() * pageSize);
		if(this.isNextPage() ){ 
			nextPage = pageList + pageSize + 1;
		}
		return nextPage;
	}
	
	//페이징 구현
	public String getPaging(){
		StringBuffer sb = new StringBuffer();
		int pageList = (new Float((currentPageNo - 1) / pageSize).intValue() * pageSize);
		int viewList = pageList;
		sb.append("<p>");
		if(isPrePage()){
			//sb.append("<a href='javascript:Search(\""+getFirstPageNo()+"\")' class='pgup'><span>처음</span></a>");
			sb.append("<a class='btn btn-default' href='javascript:Search(\""+getPrePage()+"\")' ><span>이전</span></a>");
		}
		while(viewList<(pageList + pageSize) && viewList < getLastPageNo()){
			int displayCount = viewList + 1;
			if((viewList + 1) == currentPageNo) {
				//sb.append("<a href='javascript:Search("+currentPageNo+")' class='sel'><strong>"+displayCount+"</strong></a>");
				sb.append("<a class='btn btn-info' ");
				sb.append("<strong>"+displayCount+"</strong>");
				sb.append("</a>");
			}else{
				sb.append("<a class='btn btn-default' href='javascript:Search(\""+displayCount+"\")'>");
				sb.append(""+displayCount+"");
				sb.append("</a>");
			}
			sb.append("&nbsp;");
			viewList++;
		}
		if(isNextPage()){
			sb.append("<a class='btn btn-default' href='javascript:Search(\""+getNextPage()+"\")' ><span>다음</span></a>");
			//sb.append("<a href='javascript:Search(\""+getLastPageNo()+"\")' class='pgdn'><span>마지막</span></a>");
		}
		sb.append("</p>");
		return sb.toString();
	}
	
	public String getStatPage() {
		StringBuffer sb = new StringBuffer();
		sb.append("페이지 : <strong>"+getCurrentPageNo()+"</strong>/"+getTotalPageCount()+"(전체목록 : "+getTotalRecordCount()+"건)");
		return sb.toString();
	}
}
