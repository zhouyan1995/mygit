package com.test.common;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;
/*实体公共类*/
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Column(name = "GMT_CREATE")
    private Timestamp    gmtCreate;
    @Column(name = "GMT_MODIFY")
    @JSONField(serialize = false)
    private Timestamp    gmtModify;
    @Column(name = "IS_DELETED")
    @Enumerated(EnumType.STRING)
    @JSONField(serialize = false)
    private DeletedState isDeleted;
    @Column(name = "VERSION")
    @JSONField(serialize = false)
    @Version
    private long         version;

    public BaseEntity() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        setGmtCreate(now);
        setIsDeleted(DeletedState.N);
    }

    @PrePersist
    public void preInsert() {
        if (gmtCreate == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            setGmtCreate(now);
            setIsDeleted(DeletedState.N);
        }
    }

    @PreUpdate
    public void updateTimestamp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        setGmtModify(now);
    }


    public Timestamp getGmtCreate() {
        return gmtCreate;
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

    private void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "gmtCreate=" + gmtCreate +
                //", gmtModify=" + gmtModify +
                ", isDeleted=" + isDeleted +
                ", version=" + version +
                '}';
    }

    public String getEtag() {
        return MD5Utils.encode(this.toString() + String.valueOf(version));
    }

	
}
