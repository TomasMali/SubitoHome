package DAO;

public class Abilitazione {
	private Long userid;
	private Long link;
	private boolean started = false;

	public Abilitazione(Long userid, Long link, boolean started) {
		super();
		this.userid = userid;
		this.link = link;
		this.started = started;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getLink() {
		return link;
	}

	public void setLink(Long link) {
		this.link = link;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	@Override
	public String toString() {
		return "abilitazione [userid=" + userid + ", link=" + link + ", started=" + started + "]";
	}

}
