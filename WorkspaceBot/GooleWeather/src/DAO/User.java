package DAO;

public class User {

	private Long userID;
	private String nome;
	private String cognome;
	private String inserimento;

	public User(Long userID, String nome, String cognome, String inserimento) {
		super();
		this.userID = userID;
		this.nome = nome;
		this.cognome = cognome;
		this.inserimento = inserimento;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getInserimento() {
		return inserimento;
	}

	public void setInserimento(String inserimento) {
		this.inserimento = inserimento;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", nome=" + nome + ", cognome=" + cognome + ", inserimento=" + inserimento
				+ "]";
	}

}
