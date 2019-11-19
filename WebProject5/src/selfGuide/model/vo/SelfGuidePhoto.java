package selfGuide.model.vo;

public class SelfGuidePhoto {
	private int self_No;
	private String photo_Name;
	
	public SelfGuidePhoto() {}
	
	public SelfGuidePhoto(int self_No, String photo_Name) {
		super();
		this.self_No = self_No;
		this.photo_Name = photo_Name;
	}

	public int getSelf_No() {
		return self_No;
	}
	public void setSelf_No(int self_No) {
		this.self_No = self_No;
	}
	public String getPhoto_Name() {
		return photo_Name;
	}
	public void setPhoto_Name(String photo_Name) {
		this.photo_Name = photo_Name;
	}
}
