package DAO;

public class Links {
	private int id;
	private String descrizione;
	private String link;
	private String ultimamodifica;
	private String progetto;

	public Links(int id, String descrizione, String link, String ultimamodifica, String progetto) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.link = link;
		this.ultimamodifica = ultimamodifica;
		this.progetto = progetto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUltimamodifica() {
		return ultimamodifica;
	}

	public void setUltimamodifica(String ultimamodifica) {
		this.ultimamodifica = ultimamodifica;
	}

	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	@Override
	public String toString() {
		return "Links [id=" + id + ", descrizione=" + descrizione + ", link=" + link + ", ultimamodifica="
				+ ultimamodifica + ", progetto=" + progetto + "]";
	}

}
