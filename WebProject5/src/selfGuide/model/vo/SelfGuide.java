package selfGuide.model.vo;

import java.sql.Date;

public class SelfGuide {
	private Date selfDate;
	private String writerId;
	private String selfContent;
	private int selfViews;
	private int selfLike;
	private int selfNo;
	private String selfTitle;
	private String photoOriginalFilename;
	private String photoRenameFilename;
	
	public SelfGuide() {}
	
	public Date getSelfDate() {
		return selfDate;
	}
	public void setSelfDate(Date selfDate) {
		this.selfDate = selfDate;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public String getSelfContent() {
		return selfContent;
	}
	public void setSelfContent(String selfText) {
		this.selfContent = selfText;
	}
	public int getSelfViews() {
		return selfViews;
	}
	public void setSelfViews(int selfViews) {
		this.selfViews = selfViews;
	}
	public int getSelfLike() {
		return selfLike;
	}
	public void setSelfLike(int selfLike) {
		this.selfLike = selfLike;
	}
	public int getSelfNo() {
		return selfNo;
	}
	public void setSelfNo(int selfNo) {
		this.selfNo = selfNo;
	}
	public String getSelfTitle() {
		return selfTitle;
	}
	public void setSelfTitle(String selfTitle) {
		this.selfTitle = selfTitle;
	}
	public String getPhotoOriginalFilename() {
		return photoOriginalFilename;
	}
	public void setPhotoOriginalFilename(String photoOriginalFilename) {
		this.photoOriginalFilename = photoOriginalFilename;
	}
	public String getPhotoRenameFilename() {
		return photoRenameFilename;
	}
	public void setPhotoRenameFilename(String photoRenameFilename) {
		this.photoRenameFilename = photoRenameFilename;
	}
	
}