package com.fineway.common.vo;

import java.util.List;

//分页查询的类
public class PaginationVO<T> {
private List<T> list;
private int total;
public List<T> getList() {
	return list;
}
public void setList(List<T> list) {
	this.list = list;
}
public int geTtotal() {
	return total;
}
public void setCountPage(int total) {
	this.total = total;
}
}
