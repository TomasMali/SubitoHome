package tomas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import Utils.Annuncio;
import Utils.Consts;
import Utils.InlineKeyboardsBuilder;
import Utils.User;
import Utils.Utils;

public class MyAmazingBot extends TelegramLongPollingBot {

	public List<User> list = new ArrayList<User>();
	User current_user = null;

	public String getBotUsername() {
		return "SubitoHome_bot";
		// tomasmalibot
		// 502596920:AAGXj1omTxPldCElns1Wiw965LqslMSKBHw
		// TomasSubitoBot
		// 657809545:AAEA4xHiTKLndDuRJc9G5XYrwt-ul2WFqH0
		// SubitoHome_bot
		// 698046082:AAGjc4i0hqlOGFES6CrJ4vRg2vhTs5ExStw

	}

	@Override
	public String getBotToken() {
		return "698046082:AAGjc4i0hqlOGFES6CrJ4vRg2vhTs5ExStw";
	}

	public void onUpdateReceived(Update update) {

		current_user = new User(this, update);
		if (!Utils.checkUser(current_user, list))
			list.add(current_user);

		if (update.getMessage() != null)
			current_user = Utils.getUser(update.getMessage().getChat().getId(), list);
		else if (update.hasCallbackQuery() && update.getCallbackQuery().getMessage() != null)
			current_user = Utils.getUser(update.getCallbackQuery().getMessage().getChatId(), list);

		if (update.hasMessage())
			execHasMessage(update);
		else if (update.hasCallbackQuery()) {
			current_user.setInlineKeyboardData(update.getCallbackQuery().getData());
			execHasCallbackQuery(update);
		}
	}

	public void execHasMessage(Update update) {
		if (update.getMessage().hasText()) {
			switch (update.getMessage().getText().toUpperCase()) {
			case Consts.SUBITO:
				getCitta(update);
				break;
			case Consts.TIMER:
			case Consts.TIMEROFF:
				giveMeNotification(update);
				break;
			default:
				current_user.sms = update.getMessage().getText();
				checkIfSearch(update);
				break;
			}

		}
	}

	public void execHasCallbackQuery(Update update) {
		switch (current_user.getInlineKeyboardData().toLowerCase()) {
		case Consts.TUTTAPROVINCIA:
			current_user.citta = "";
			break;
		case Consts.BELLUNO:
			current_user.citta = Consts.BELLUNO;
			break;
		case Consts.PADOVA:
			current_user.citta = Consts.PADOVA;

			break;
		case Consts.ROVIGO:
			current_user.citta = Consts.ROVIGO;
			break;
		case Consts.TREVISO:
			current_user.citta = Consts.TREVISO;
			break;
		case Consts.VENEZIA:
			current_user.citta = Consts.VENEZIA;
			break;
		case Consts.VERONA:
			current_user.citta = Consts.VERONA;
			break;
		case Consts.VICENZA:
			current_user.citta = Consts.VICENZA;
			break;

		default:

			break;
		}

		EditMessageText new_message = current_user.composeTextToSendBack(
				"Scrivi '#' poi subito l'articolo che desideri cercare");

		try {
			// execute(new_message);
			execute(current_user.composeMessage("Scrivi '#' poi subito l'articolo che desideri cercare"));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	public void getCitta(Update update) {
		current_user = Utils.getUser(update.getMessage().getChat().getId(), list);
		SendMessage message = current_user.composeMessage("Scegli Città");

		message.setReplyMarkup(new InlineKeyboardsBuilder().composeInlineKeyboard_Citta());
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public void checkIfSearch(Update update) {
		current_user = Utils.getUser(update.getMessage().getChat().getId(), list);
		String aux = update.getMessage().getText();
		if (aux.contains("#")) {
			current_user.cerca = aux.substring(1);
			System.out.println(current_user.cerca);
			current_user.parola = current_user.cerca;
			current_user.path = "https://www.subito.it/annunci-veneto/vendita/informatica/" + current_user.citta
					+ "?q=";
			try {
				execute(current_user.composeMessage("Scrivi il prezzo massimo da cercare"));
				return;
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		List<String> list = Arrays.asList(aux.split(","));

		if (Utils.isNumeric(list)) {

			if (list.size() != Arrays.asList(current_user.parola.split(",")).size()) {
				try {
					execute(current_user.composeMessage(
							"Attenzione, il nuomero degli annunci che vuoi cercare non corrisponde con il numero dei prezzi separati con la virgola!"));
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				return;
			}

			for (String s : list) {
				System.out.println("prezzo: " + s);
				current_user.price.add(Float.parseFloat(s.trim()));

			}

			System.out.println("Sto cercando in: " + current_user.path + " Con prezzi: " + current_user.price);
			List<Annuncio> list_an = new ArrayList<>();

			try {

				System.out.println("i:  :" + current_user.parola);
				list_an = current_user.Jsoup_HTML.getOggi(current_user.parola, current_user.path, current_user.price,
						current_user.cache, true);
				current_user.price_Timer = new ArrayList<Float>(current_user.price);

				current_user.price.clear();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			for (Annuncio an : list_an) {

				try {
					System.out.println(an.toString());
					SendMessage message = current_user.composeMessage(an.toString());

					execute(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (list_an.isEmpty()) {
				System.out.println("Nessun annuncio trovato !");
				SendMessage message = current_user.composeMessage("Nessun annuncio trovato !");

				try {
					execute(message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public void executeOnTime() {
		List<Annuncio> list_an = null;

		try {

			list_an = current_user.Jsoup_HTML.getOggi(current_user.parola, current_user.path, current_user.price_Timer,
					current_user.cache, false);
			if (!current_user.statoTimer)
				current_user.price_Timer.clear();
			if (!list_an.isEmpty()) {

				for (Annuncio an : list_an) {
					SendMessage message = current_user.composeMessage(an.toString());
					execute(message);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void giveMeNotification(Update update) {
		current_user = Utils.getUser(update.getMessage().getChat().getId(), list);
		String sms_ = update.getMessage().getText().toUpperCase();
		if (current_user.statoTimer && sms_.equals(Consts.TIMER)) {
			try {
				execute(current_user.composeMessage("Attenzione hai gia attivato le notifiche per: "
						+ current_user.cerca + "\n" + "Devi scrivere 'Timer off' prima di attivarlo di nuovo!"));

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
			return;
		}

		if (sms_.equals(Consts.TIMER) && !current_user.cerca.isEmpty() && !current_user.statoTimer) {
			try {
				current_user.statoTimer = true;
				System.out.println("Il sistema di notificazione per '" + current_user.cerca + "' è stato attivato");
				execute(current_user.composeMessage("Il sistema di notificazione per '" + current_user.cerca
						+ "' è stato attivato"));
				current_user.executor.scheduleAtFixedRate(current_user.helloRunnable, 0, 20, TimeUnit.MINUTES);

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} else if (sms_.equals(Consts.TIMEROFF) && !current_user.cerca.isEmpty() && current_user.statoTimer) {

			current_user.statoTimer = false;

			try {
				System.out.println("Il sistema di notificazione per '" + current_user.cerca + "' è stato disattivato");
				execute(current_user.composeMessage("Il sistema di notificazione per '" + current_user.cerca
						+ "' è stato  disattivato"));
				current_user.executor.shutdownNow();

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

		}

	}

}
