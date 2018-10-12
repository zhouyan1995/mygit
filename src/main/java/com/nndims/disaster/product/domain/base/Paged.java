package com.nndims.disaster.product.domain.base;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午8:14:02
 * @param <T>
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Paged<T> extends ABBaseDto {

	private static final long serialVersionUID = 2179088654339866447L;

	// 查询数量
	private int queryQuantity;
	// 总数量
	private long totalQuantity;
	// 总页数
	private int totalPage;
	// 当前页
	private int currentPage;
	// 每页显示数量
	private int perPage;
	// 上页标记
	private Integer previousPage;
	// 下页标记
	private Integer nextPage;
	// 数据集合
	private List<T> dataList = new ArrayList<T>(0);

	private Paged() {
	}

	public <P> Paged(P page, IPagedObjectHandler<P, T> handler) {
		this();
		handler.handler(page, this);
	}

	/**
	 * 设置页信息
	 * @param totalPage
	 *            总页数
	 * @param currentPage
	 *            当前页
	 * @param perPage
	 *            每页显示数量
	 * @param queryQuantity
	 *            查询数量
	 * @param totalQuantity
	 *            总数量
	 * @param previousPage
	 *            上页标记
	 * @param nextPage
	 *            下页标记
	 */
	public void setPageInfo(int totalPage, int currentPage, int perPage, int queryQuantity, long totalQuantity,
			Integer previousPage, Integer nextPage) {
		this.queryQuantity = queryQuantity;
		this.totalQuantity = totalQuantity;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.perPage = perPage;
		this.previousPage = previousPage;
		this.nextPage = nextPage;
	}

	abstract public interface IPagedObjectHandler<P, T> {

		abstract public void handler(P page, Paged<T> pagedObject);
	}

	public int getQueryQuantity() {
		return queryQuantity;
	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPerPage() {
		return perPage;
	}

	public Integer getPreviousPage() {
		return previousPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public List<T> getDataList() {
		return dataList;
	}
}