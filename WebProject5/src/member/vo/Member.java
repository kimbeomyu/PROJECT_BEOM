package member.vo;

public class Member {
	private String member_ID;
	private String member_Pwd;
	private String member_Name;
	private String member_Address;
	private String member_Email;
	private String member_Phone;
	
	public Member() {}

	public String getMember_ID() {
		return member_ID;
	}

	public void setMember_ID(String member_ID) {
		this.member_ID = member_ID;
	}

	public String getMember_Pwd() {
		return member_Pwd;
	}

	public void setMember_Pwd(String member_Pwd) {
		this.member_Pwd = member_Pwd;
	}

	public String getMember_Name() {
		return member_Name;
	}

	public void setMember_Name(String member_Name) {
		this.member_Name = member_Name;
	}

	public String getMember_Address() {
		return member_Address;
	}

	public void setMember_Address(String member_Address) {
		this.member_Address = member_Address;
	}

	public String getMember_Email() {
		return member_Email;
	}

	public void setMember_Email(String member_Email) {
		this.member_Email = member_Email;
	}

	public String getMember_Phone() {
		return member_Phone;
	}

	public void setMember_Phone(String member_Phone) {
		this.member_Phone = member_Phone;
	}
	
}
