package DAO;

public class UserAdmin {

	private Long userID;
	private String link;
	private boolean firstTime;

	public UserAdmin(Long userID, String link, boolean firstTime) {
		super();
		this.userID = userID;
		this.link = link;
		this.firstTime = firstTime;
	}

	public Long getUserID() {
		return userID;
	}

	public String getLink() {
		return link;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	@Override
	public String toString() {
		return "UserAdmin [userID=" + userID + ", link=" + link + ", firstTime=" + firstTime + "]";
	}

}
