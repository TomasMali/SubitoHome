package Schedulers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import CallMatches.Commands;
import CallMatches.CommandsMatches;
import ConnectionDB.Queries;
import DAO.Abilitazione;
import DAO.Links;
import DAO.UserAdmin;

/**
 * This is a sample class to execute scheduler on specific date based on <code>java.util.Calendar</code>. Over here,
 * <code>executor</code> is a runnable which run on everyday basis from 8:00 AM to 5:00 PM.
 * 
 * @author Tomas Mali
 */
public class Scheduler {

	public static Commands mySianConnect = null;
	public static Update update = null;
	public static String query = "";
	public static boolean firstTime = true;
	public static ScheduledExecutorService mipaafTecnicheWebServiceMVV = Executors.newScheduledThreadPool(1);
	public static ScheduledExecutorService codificheRegistriVitivinicoli = Executors.newScheduledThreadPool(1);
	public static ScheduledExecutorService modalita_registro_vitivinicolo_v4 = Executors.newScheduledThreadPool(1);
	public static ScheduledExecutorService guida_chiusura_campagna_v2 = Executors.newScheduledThreadPool(1);
	public static ScheduledExecutorService mipaafTecnicheWebService = Executors.newScheduledThreadPool(1);
	public static ScheduledExecutorService matrice_schede_operazioni_controlli = Executors.newScheduledThreadPool(1);

	public static Long mio = (long) 145645559;

	public Scheduler(Commands instance) {
		mySianConnect = instance;

	}

	public static void setQuery(String s) {
		query = s;
	}

	/**
	 * Questo metodo fa partire il thread
	 * 
	 * @throws IOException
	 */
	public static void runThreadWithTask() throws IOException {

		// Riempio prima la tabella adminuser per registrare l'utente Admin
		Queries.deleteTableUserAdmin();
		Queries.registerAdmin(mio);

		/**
		 * Thread per far partire MipaafTecnicheWebService Mipaaf Codifiche Registri Vitivinicoli
		 */
		Runnable taskMipaafTecnicheWebServiceMVV = new Runnable() {
			int statusCode = 000;

			@Override
			public void run() {
				Links link = Queries.getSingleLink("Regole tecniche web-service MVV-E");
				String mainLink = "https://www.sian.it/portale-mipaaf/servizio?sid=2166";

				URL url_ = null;
				URL url_1 = null;
				String dataModifica = "";

				// Sito Down con codice 404
				try {

					url_ = new URL(mainLink);
					HttpURLConnection http = (HttpURLConnection) url_.openConnection();
					statusCode = http.getResponseCode();

					url_1 = new URL(link.getLink());
					HttpURLConnection http_1 = (HttpURLConnection) url_1.openConnection();
					statusCode = http_1.getResponseCode();
					dataModifica = new Date(url_1.openConnection().getLastModified()).toString();
					System.out.println("ULTIMA MODIFICA: " + dataModifica);
					if (statusCode == 404)
						throw new IOException();

				} catch (IOException ioEx) {
					SendMessage message = new SendMessage().setChatId(mio).setText("Il sito '" + link.getDescrizione()
							+ "' è andato giù con codice : " + statusCode);
					try {
						mySianConnect.execute(message);
						CommandsMatches.createInlineKeyboardForUserSendingMessage(mySianConnect, link.getDescrizione());
						mipaafTecnicheWebServiceMVV.shutdown();
						return;
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}

				}

				// Non ci sono aggiornamenti dal sito di MIPAAF
				if (link.getUltimamodifica().equals(dataModifica)) {
					// è la prima volta
					UserAdmin userAdmin = Queries.getUserAdminWithLink(link.getLink());

					boolean isFirstTime = userAdmin.isFirstTime();
					if (!isFirstTime) {
						SendMessage message = new SendMessage().setChatId(mio).setText(
								"Application Started !! \n Non ci sono Aggiornamenti dal sito di : " + link
										.getDescrizione());
						try {
							Queries.setFirstTimeUserAdminToTrue(userAdmin);
							mySianConnect.execute(message);

						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
					}

				}

				else
				// Attenzione ci sono modifiche dal sito di MIPAAF
				// Hanno fatto modifiche sullo stesso URL
				{
					if (isTimeBetween()) {
						Scheduler.broadcast(link.getDescrizione());
						// modifico prima il link con la la nuova data di modifica
						link.setUltimamodifica(dataModifica);
						Queries.updateLinkTimestamp(link);
						// mipaafTecnicheWebService.shutdown();

					}
				}

			}

		};

		/**
		 * Threa per far partire il task Guida_chiusura_campagna_v2
		 */
		Runnable taskGuida_chiusura_campagna_v2 = new Runnable() {
			int statusCode = 000;

			@Override
			public void run() {

				Links link = Queries.getSingleLink("Guida_chiusura_campagna_v2");
				String mainLink = "https://www.sian.it/portale-mipaaf/servizio?sid=2081";

				URL url_ = null;
				URL url_1 = null;
				String dataModifica = "";

				// Sito Down con codice 404
				try {

					url_ = new URL(mainLink);
					HttpURLConnection http = (HttpURLConnection) url_.openConnection();
					statusCode = http.getResponseCode();

					url_1 = new URL(link.getLink());
					HttpURLConnection http_1 = (HttpURLConnection) url_1.openConnection();
					statusCode = http_1.getResponseCode();
					dataModifica = new Date(url_1.openConnection().getLastModified()).toString();
					System.out.println("ULTIMA MODIFICA: " + dataModifica);
					if (statusCode == 404)
						throw new IOException();

				} catch (IOException ioEx) {
					SendMessage message = new SendMessage().setChatId(mio).setText("Il sito '" + link.getDescrizione()
							+ "' è andato giù con codice : " + statusCode);
					try {
						mySianConnect.execute(message);
						CommandsMatches.createInlineKeyboardForUserSendingMessage(mySianConnect, link.getDescrizione());
						mipaafTecnicheWebServiceMVV.shutdown();
						return;
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}

				}
				// dataModifica = "Boh " + dataModifica;
				// Non ci sono aggiornamenti dal sito di MIPAAF
				if (link.getUltimamodifica().equals(dataModifica)) {
					// è la prima volta
					UserAdmin userAdmin = Queries.getUserAdminWithLink(link.getLink());

					boolean isFirstTime = userAdmin.isFirstTime();
					if (!isFirstTime) {
						SendMessage message = new SendMessage().setChatId(mio).setText(
								"Application Started !! \n Non ci sono Aggiornamenti dal sito di : " + link
										.getDescrizione());
						try {
							Queries.setFirstTimeUserAdminToTrue(userAdmin);
							mySianConnect.execute(message);

						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
					}

				}

				else
				// Attenzione ci sono modifiche dal sito di MIPAAF
				// Hanno modificato solo il timestamp
				{
					if (isTimeBetween()) {
						Scheduler.broadcast(link.getDescrizione());
						// modifico prima il link con la la nuova data di modifica
						link.setUltimamodifica(dataModifica);
						Queries.updateLinkTimestamp(link);
						// mipaafTecnicheWebService.shutdown();
					}
				}

			}
		};

		/**
		 * Threa per far partire il task Guida_chiusura_campagna_v2
		 */
		Runnable taskMipaafTecnicheWebService = new Runnable() {
			int statusCode = 000;

			@Override
			public void run() {

				Links link = Queries.getSingleLink("Regole_tecniche_web_service");
				String mainLink = "https://www.sian.it/portale-mipaaf/servizio?sid=1848";

				URL url_ = null;
				URL url_1 = null;
				String dataModifica = "";

				// Sito Down con codice 404
				try {

					url_ = new URL(mainLink);
					HttpURLConnection http = (HttpURLConnection) url_.openConnection();
					statusCode = http.getResponseCode();

					url_1 = new URL(link.getLink());
					HttpURLConnection http_1 = (HttpURLConnection) url_1.openConnection();
					statusCode = http_1.getResponseCode();
					dataModifica = new Date(url_1.openConnection().getLastModified()).toString();
					System.out.println("ULTIMA MODIFICA: " + dataModifica);
					if (statusCode == 404)
						throw new IOException();

				} catch (IOException ioEx) {
					SendMessage message = new SendMessage().setChatId(mio).setText("Il sito '" + link.getDescrizione()
							+ "' è andato giù con codice : " + statusCode);
					try {
						mySianConnect.execute(message);
						CommandsMatches.createInlineKeyboardForUserSendingMessage(mySianConnect, link.getDescrizione());
						mipaafTecnicheWebServiceMVV.shutdown();
						return;
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}

				}
				// dataModifica = "Boh " + dataModifica;
				// Non ci sono aggiornamenti dal sito di MIPAAF
				if (link.getUltimamodifica().equals(dataModifica)) {
					// è la prima volta
					UserAdmin userAdmin = Queries.getUserAdminWithLink(link.getLink());

					boolean isFirstTime = userAdmin.isFirstTime();
					if (!isFirstTime) {
						SendMessage message = new SendMessage().setChatId(mio).setText(
								"Application Started !! \n Non ci sono Aggiornamenti dal sito di : " + link
										.getDescrizione());
						try {
							Queries.setFirstTimeUserAdminToTrue(userAdmin);
							mySianConnect.execute(message);

						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
					}

				}

				else
				// Attenzione ci sono modifiche dal sito di MIPAAF
				// Hanno modificato solo il timestamp
				{
					if (isTimeBetween()) {
						Scheduler.broadcast(link.getDescrizione());
						// modifico prima il link con la la nuova data di modifica
						link.setUltimamodifica(dataModifica);
						Queries.updateLinkTimestamp(link);
						// mipaafTecnicheWebService.shutdown();
					}
				}

			}
		};

		/**
		 * Threa per far partire il task Guida_chiusura_campagna_v2
		 */
		Runnable taskMatrice_schede_operazioni_controlli = new Runnable() {
			int statusCode = 000;

			@Override
			public void run() {

				Links link = Queries.getSingleLink("Matrice_schede_operazioni_controlli");
				String mainLink = "https://www.sian.it/portale-mipaaf/servizio?sid=1848";

				URL url_ = null;
				URL url_1 = null;
				String dataModifica = "";

				// Sito Down con codice 404
				try {

					url_ = new URL(mainLink);
					HttpURLConnection http = (HttpURLConnection) url_.openConnection();
					statusCode = http.getResponseCode();

					url_1 = new URL(link.getLink());
					HttpURLConnection http_1 = (HttpURLConnection) url_1.openConnection();
					statusCode = http_1.getResponseCode();
					dataModifica = new Date(url_1.openConnection().getLastModified()).toString();
					System.out.println("ULTIMA MODIFICA: " + dataModifica);
					if (statusCode == 404)
						throw new IOException();

				} catch (IOException ioEx) {
					SendMessage message = new SendMessage().setChatId(mio).setText("Il sito '" + link.getDescrizione()
							+ "' è andato giù con codice : " + statusCode);
					try {
						mySianConnect.execute(message);
						CommandsMatches.createInlineKeyboardForUserSendingMessage(mySianConnect, link.getDescrizione());
						mipaafTecnicheWebServiceMVV.shutdown();
						return;
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}

				}
				// dataModifica = "Boh " + dataModifica;
				// Non ci sono aggiornamenti dal sito di MIPAAF
				if (link.getUltimamodifica().equals(dataModifica)) {
					// è la prima volta
					UserAdmin userAdmin = Queries.getUserAdminWithLink(link.getLink());

					boolean isFirstTime = userAdmin.isFirstTime();
					if (!isFirstTime) {
						SendMessage message = new SendMessage().setChatId(mio).setText(
								"Application Started !! \n Non ci sono Aggiornamenti dal sito di : " + link
										.getDescrizione());
						try {
							Queries.setFirstTimeUserAdminToTrue(userAdmin);
							mySianConnect.execute(message);

						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
					}

				}

				else
				// Attenzione ci sono modifiche dal sito di MIPAAF
				// Hanno modificato solo il timestamp
				{
					if (isTimeBetween()) {
						Scheduler.broadcast(link.getDescrizione());
						// modifico prima il link con la la nuova data di modifica
						link.setUltimamodifica(dataModifica);
						Queries.updateLinkTimestamp(link);
						// mipaafTecnicheWebService.shutdown();
					}
				}

			}
		};

		mipaafTecnicheWebServiceMVV.scheduleWithFixedDelay(taskMipaafTecnicheWebServiceMVV, 0, 20, TimeUnit.MINUTES);
		guida_chiusura_campagna_v2.scheduleWithFixedDelay(taskGuida_chiusura_campagna_v2, 0, 20, TimeUnit.MINUTES);
		mipaafTecnicheWebService.scheduleWithFixedDelay(taskMipaafTecnicheWebService, 0, 20, TimeUnit.MINUTES);
		matrice_schede_operazioni_controlli.scheduleWithFixedDelay(taskMatrice_schede_operazioni_controlli, 0, 20,
				TimeUnit.MINUTES);
	}

	/**
	 * Invia messaggio a tutti gli utenti registrati per il Link: descriozioneLink
	 * 
	 * @param descrizioneLink
	 */
	public static void broadcast(String descrizioneLink) {

		List<Abilitazione> listAbilitations = Queries.GetUserIdWithProjectAndDescription("Sian", descrizioneLink);

		try {
			for (Abilitazione ab : listAbilitations) {
				SendMessage message = new SendMessage().setChatId(ab.getUserid()).setText(
						"Attenzione ci sono aggiornamenti dal sito di: " + descrizioneLink + "\n"
								+ " Ultima modifica rilevata : " + new java.util.Date().toGMTString());
				mySianConnect.execute(message);
			}
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ritorna true se il tempo si trova tra i 2 specificati
	 * 
	 * @return
	 */
	public static boolean isTimeBetween() {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

			String string1 = "09:40:00";
			Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(time1);

			String string2 = "20:00:00";
			Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(time2);
			calendar2.add(Calendar.DATE, 1);

			String someRandomTime = sdf.format(cal.getTime());
			Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(d);
			calendar3.add(Calendar.DATE, 1);

			Date x = calendar3.getTime();
			if (x.after(calendar1.getTime()) && x.before(calendar2.getTime()))
				return true;
			else
				return false;

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

}