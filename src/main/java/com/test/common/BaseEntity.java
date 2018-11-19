package com.test.common;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;
/*实体公共类*/
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/*创建时间*/
	@Column(name = "GMT_CREATE")
    private Timestamp    gmtCreate;
	/*更新时间*/
    @Column(name = "GMT_MODIFY")
    @JSONField(serialize = false)
    private Timestamp    gmtModify;
    /*是否删除*/
    @Column(name = "IS_DELETED")
    @Enumerated(EnumType.STRING)
    @JSONField(serialize = false)
    private DeletedState isDeleted;
    /*版本号*/
    @Column(name = "VERSION")
    @JSONField(serialize = false)
    @Version
    private long         version;
    public Timestamp getGmtCreate() {
		return gmtCreate;
	}
	public BaseEntity() {
		super();
		
	}
	public BaseEntity(Timestamp gmtCreate, Timestamp gmtModify,
			DeletedState isDeleted, long version) {
		super();
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.isDeleted = isDeleted;
		this.version = version;
	}
	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Timestamp getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Timestamp gmtModify) {
		this.gmtModify = gmtModify;
	}
	public DeletedState getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(DeletedState isDeleted) {
		this.isDeleted = isDeleted;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
}
