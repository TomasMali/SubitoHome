package Utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Jsoup_HTML {

	public static List<String> getCitta() {
		List<String> list = new ArrayList<>();
		try {

			Document doc = Jsoup.connect("https://www.subito.it/annunci-veneto/vendita/usato/").userAgent(
					"Mozilla/17.0").get();

			int i = 0;
			Elements temp = doc.select("select#searcharea option");
			list.add("Tutta la provincia");
			for (Element uno : temp) {
				if (i > 3) {
					System.out.println(uno.text());
					list.add(uno.text());
				}
				i++;
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return list;

	}

	public List<Annuncio> getOggi(String titolo, String path_original, List<Float> price, List<Annuncio> cache,
			boolean ricerca) throws IOException {
		String query = "";
		String path = path_original;

		path = path.replace("informatica", "usato");

		int i = 0;

		List<String> aux = Arrays.asList(titolo.split(","));
		List<String> list = new ArrayList<String>();
		for (String a : aux)
			list.add(a);

		System.out.println("ii:  :" + list);
		List<Annuncio> cache_Local = new ArrayList<Annuncio>();

		for (String tit : list) {

			// int u = i;
			// String t = tit;

			System.out.println("ii:  :" + path + URLEncoder.encode(tit, "UTF-8") + "Numero elementi :" + list.size());
			path = path + URLEncoder.encode(tit.trim(), "UTF-8");
			Document doc = Jsoup.connect(path).userAgent("Mozilla/17.0").get();
			Elements temp = doc.select("ul.items_listing li");

			for (Element el : temp.select("div.item_list_section.item_description")) {
				String html_price = el.select("span.item_price").text();

				String original_price = html_price;
				html_price = html_price.replace(".", "");
				html_price = html_price.isEmpty() ? "000" : html_price;

				String time = el.select("span.item_info").select("time").text();

				if (!ricerca) {

					int prezzo_item_corrente = (int) Float.parseFloat(html_price.substring(0, html_price.length() - 2));

					if (prezzo_item_corrente <= price.get(i) && !original_price.isEmpty() && (time.contains("Oggi")
							|| time.contains("Ieri"))) {

						Annuncio an = new Annuncio();

						an.setTitolo(el.select(" h2 a").text());
						an.setPrezzo(el.select("span.item_price").text());
						an.setTime(time);
						an.setUrl(el.select(" h2 a").attr("href"));

						if (!Utils.isInCache(cache, an)) {
							if (!cache_Local.contains(an))
								cache_Local.add(an);
							if (!cache.contains(an))
								cache.add(an);
						}

					}
				} else {

					int prezzo_item_corrente = (int) Float.parseFloat(html_price.substring(0, html_price.length() - 2));

					if (prezzo_item_corrente <= price.get(i) && !original_price.isEmpty()) { // &&
																								// (titolo.contains("macbook")||
																								// titolo.contains("iphone")
																								// ? true :
																								// (time.contains("Oggi")
																								// ||
																								// time.contains("Ieri")))

						Annuncio an = new Annuncio();

						an.setTitolo(el.select(" h2 a").text());
						an.setPrezzo(el.select("span.item_price").text());
						an.setTime(time);
						an.setUrl(el.select(" h2 a").attr("href"));
						if (!cache_Local.contains(an))
							cache_Local.add(an);

						if (!Utils.isInCache(cache, an)) {
							if (!cache_Local.contains(an))
								cache_Local.add(an);
							if (!cache.contains(an))
								cache.add(an);
						}
					}
				}

			}
			path = path_original;
			i++;
		}

		return cache_Local;

	}

}
