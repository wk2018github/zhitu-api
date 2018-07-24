package zhitu.cfg;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class JqGridParams implements Serializable {


	private static final long serialVersionUID = 2296722197188158072L;

	/**
	 * 当前第几页
	 */
	private int page;

	/**
	 * 显示多少条数据
	 */
	private int rows;

	/**
	 * 排序列名
	 */
	private String sidx;

	/**
	 * 排序规则 asc or desc
	 */
	private String sord;

	/**    
	 * 得到firstresult hibernate使用
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (getPage() - 1) * getRows();
	}

	/**
	 * 计算总共有多少页
	 * 
	 * @param records
	 * @return
	 */
	public int getTotal(int records) {
		return records % getRows() == 0 ? records / getRows() : records / getRows() + 1;
	}

	/**
	 * 设置默认排序
	 * 
	 * @param sidx
	 * @param sord
	 */
	public void setDefaultOrder(String sidx, String sord) {
		if (StringUtils.isEmpty(this.sidx)) {
			this.sidx = sidx;
			this.sord = sord;
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	@Override
	public String toString() {
		return "JqGridParams[page=" +page + ",rows=" +rows + ",sidx="
				+ sidx + ",sord=" + sord + "]";
	}

}
