package Utils;

public class Annuncio {
	
	String titolo = "";
	String prezzo = "";
	String time = "";
	String url ="";

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Titolo: "+ titolo + "\n" + "Prezzo: "+ prezzo + "\n"+ "Time: "+ time + "\n"+ url + "\n" +"\n";
	//	return url + "\n" +"\n";
}
	
	
	

}
