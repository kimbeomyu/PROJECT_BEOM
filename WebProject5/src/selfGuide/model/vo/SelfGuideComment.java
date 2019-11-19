package selfGuide.model.vo;

import java.sql.Date;
import java.util.ArrayList;

public class SelfGuideComment {
	private String memberId;
	private String scommentText;
	private Date scommentDate;
	private int selfNo;
	private int scommentNo;
	private ArrayList<SelfGuideComment> comment;
	
	public int getScommentNo() {
		return scommentNo;
	}
	public void setScommentNo(int scommentNo) {
		this.scommentNo = scommentNo;
	}
	
	public ArrayList<SelfGuideComment> getComment() {
		return comment;
	}
	public void setComment(ArrayList<SelfGuideComment> comment) {
		this.comment = comment;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getScommentText() {
		return scommentText;
	}
	public void setScommentText(String scommentText) {
		this.scommentText = scommentText;
	}
	public Date getScommentDate() {
		return scommentDate;
	}
	public void setScommentDate(Date scommentDate) {
		this.scommentDate = scommentDate;
	}
	public int getSelfNo() {
		return selfNo;
	}
	public void setSelfNo(int selfNo) {
		this.selfNo = selfNo;
	}
}
