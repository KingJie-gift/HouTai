package entity;

import java.util.List;

public class Page {
	protected int pageSize;
	protected int allPage;
	protected int allCount;
	protected int pageIndex;
	protected List list;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		if (allCount > 0) {
			this.allCount = allCount;
			this.allPage = this.allCount % this.pageSize == 0 ? this.allCount
					/ this.pageSize : (this.allCount / this.pageSize + 1);
		}
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex <= 1) {
			this.pageIndex = 1;
		} else if (pageIndex > this.allPage) {
			this.pageIndex = this.allPage;
		} else {
			this.pageIndex = pageIndex;
		}
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
