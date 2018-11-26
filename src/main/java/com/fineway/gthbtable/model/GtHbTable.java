package com.fineway.gthbtable.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class GtHbTable implements Serializable {
	DecimalFormat    df   = new DecimalFormat("######0.00");
/*钢铁实体类*/
 private String gthcid;//ID
 private String civilregionalismid;//区划ID
 private String qymc;//企业名称
 private String xydm;//统一信用代码
 private String lttczbjsl;//炼铁已完成退出装备型号及数量
 private double  ltywcyjcn;//炼铁已完成压减产能
 private String ltsjtcsj;// 炼铁实际退出时间
 private String ltsfys;//炼铁是否验收
 private String lttcfs;//炼铁退出方式
 private String lgtczbjsl;//炼钢已完成退出装备型号及数量
 private double lgywcyjcn;//炼钢已完成压减产能
 private String lgsjtcsj;// 炼钢实际退出时间
 private String lgsfys;// 炼钢是否验收
 private String lgtcfs;//炼钢退出方式
 private String qysfzttc;//企业是否整体退出
 private String tchsfgs;//退出后是否公示
 private String sfsbbjlxh;//企业是否上报部级联席会
 private String sfsc;//是否存在复产
 private String scxkzh;//建筑钢材生产许可证号
 private String sfzxxkz;//是否注销建筑钢材生产许可证
 private Date scxkzzxsj;//生产许可证注销时间
 private String parentid;//所属省份ID
 private String zzzxsfgg; //证照注销是否公告
 private String hcstate;// 核查状态 0:未核查 1:已核查
 private String hcisnew;// 0：历史 1：最新
 private String reporttime;//报表时间
 private Date createtime;//创建时间
 
public GtHbTable() {
	super();
	// TODO Auto-generated constructor stub
}
public GtHbTable(String gthcid, String civilregionalismid, String qymc,
		String xydm, String lttczbjsl, double ltywcyjcn, String ltsjtcsj,
		String ltsfys, String lttcfs, String lgtczbjsl, double lgywcyjcn,
		String lgsjtcsj, String lgsfys, String lgtcfs, String qysfzttc,
		String tchsfgs, String sfsbbjlxh, String sfsc, String scxkzh,
		String sfzxxkz, Date scxkzzxsj, String parentid, String zzzxsfgg,
		String hcstate, String hcisnew, String reporttime, Date createtime) {
	super();
	this.gthcid = gthcid;
	this.civilregionalismid = civilregionalismid;
	this.qymc = qymc;
	this.xydm = xydm;
	this.lttczbjsl = lttczbjsl;
	this.ltywcyjcn = ltywcyjcn;
	this.ltsjtcsj = ltsjtcsj;
	this.ltsfys = ltsfys;
	this.lttcfs = lttcfs;
	this.lgtczbjsl = lgtczbjsl;
	this.lgywcyjcn = lgywcyjcn;
	this.lgsjtcsj = lgsjtcsj;
	this.lgsfys = lgsfys;
	this.lgtcfs = lgtcfs;
	this.qysfzttc = qysfzttc;
	this.tchsfgs = tchsfgs;
	this.sfsbbjlxh = sfsbbjlxh;
	this.sfsc = sfsc;
	this.scxkzh = scxkzh;
	this.sfzxxkz = sfzxxkz;
	this.scxkzzxsj = scxkzzxsj;
	this.parentid = parentid;
	this.zzzxsfgg = zzzxsfgg;
	this.hcstate = hcstate;
	this.hcisnew = hcisnew;
	this.reporttime = reporttime;
	this.createtime = createtime;
}
public String getGthcid() {
	return gthcid;
}
public void setGthcid(String gthcid) {
	this.gthcid = gthcid; 
}
public String getCivilregionalismid() {
	return civilregionalismid;
}
public void setCivilregionalismid(String civilregionalismid) {
	this.civilregionalismid = civilregionalismid;
}
public String getQymc() {
	return qymc;
}
public void setQymc(String qymc) {
	this.qymc = qymc;
}
public String getXydm() {
	return xydm;
}
public void setXydm(String xydm) {
	this.xydm = xydm;
}
public String getLttczbjsl() {
	return lttczbjsl;
}
public void setLttczbjsl(String lttczbjsl) {
	this.lttczbjsl = lttczbjsl;
}
public double getLtywcyjcn() {
	return ltywcyjcn;
}
public void setLtywcyjcn(double ltywcyjcn) {
   	String lg =   df.format(ltywcyjcn);
   	double lgg = Double.parseDouble(lg);
	this.ltywcyjcn = lgg;
}
public String getLtsjtcsj() {
	return ltsjtcsj;
}
public void setLtsjtcsj(String ltsjtcsj) {
	this.ltsjtcsj = ltsjtcsj;
}
public String getLtsfys() {
	return ltsfys;
}
public void setLtsfys(String ltsfys) {
	this.ltsfys = ltsfys;
}
public String getLttcfs() {
	return lttcfs;
}
public void setLttcfs(String lttcfs) {
	this.lttcfs = lttcfs;
}
public String getLgtczbjsl() {
	return lgtczbjsl;
}
public void setLgtczbjsl(String lgtczbjsl) {
	this.lgtczbjsl = lgtczbjsl;
}
public double getLgywcyjcn() {
	return lgywcyjcn;
}
public void setLgywcyjcn(double lgywcyjcn) {
   	String lg =   df.format(lgywcyjcn);
   	double lgg = Double.parseDouble(lg);
	this.lgywcyjcn = lgg;
}
public String getLgsjtcsj() {
	return lgsjtcsj;
}
public void setLgsjtcsj(String lgsjtcsj) {
	this.lgsjtcsj = lgsjtcsj;
}
public String getLgsfys() {
	return lgsfys;
}
public void setLgsfys(String lgsfys) {
	this.lgsfys = lgsfys;
}
public String getLgtcfs() {
	return lgtcfs;
}
public void setLgtcfs(String lgtcfs) {
	this.lgtcfs = lgtcfs;
}
public String getQysfzttc() {
	return qysfzttc;
}
public void setQysfzttc(String qysfzttc) {
	this.qysfzttc = qysfzttc;
}
public String getTchsfgs() {
	return tchsfgs;
}
public void setTchsfgs(String tchsfgs) {
	this.tchsfgs = tchsfgs;
}
public String getSfsbbjlxh() {
	return sfsbbjlxh;
}
public void setSfsbbjlxh(String sfsbbjlxh) {
	this.sfsbbjlxh = sfsbbjlxh;
}
public String getSfsc() {
	return sfsc;
}
public void setSfsc(String sfsc) {
	this.sfsc = sfsc;
}
public String getScxkzh() {
	return scxkzh;
}
public void setScxkzh(String scxkzh) {
	this.scxkzh = scxkzh;
}
public String getSfzxxkz() {
	return sfzxxkz;
}
public void setSfzxxkz(String sfzxxkz) {
	this.sfzxxkz = sfzxxkz;
}
public Date getScxkzzxsj() {
	return scxkzzxsj;
}
public void setScxkzzxsj(Date scxkzzxsj) {
	this.scxkzzxsj = scxkzzxsj;
}
public String getParentid() {
	return parentid;
}
public void setParentid(String parentid) {
	this.parentid = parentid;
}
public String getZzzxsfgg() {
	return zzzxsfgg;
}
public void setZzzxsfgg(String zzzxsfgg) {
	this.zzzxsfgg = zzzxsfgg;
}
public String getHcstate() {
	return hcstate;
}
public void setHcstate(String hcstate) {
	this.hcstate = hcstate;
}
public String getHcisnew() {
	return hcisnew;
}
public void setHcisnew(String hcisnew) {
	this.hcisnew = hcisnew;
}
public String getReporttime() {
	return reporttime;
}
public void setReporttime(String reporttime) {
	this.reporttime = reporttime;
}
public Date getCreatetime() {
	return createtime;
}
public void setCreatetime(Date createtime) {
	this.createtime = createtime;
}
 
}
