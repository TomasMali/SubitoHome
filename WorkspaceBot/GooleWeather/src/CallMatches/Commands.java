package CallMatches;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ConnectionDB.Queries;
import Schedulers.Scheduler;

public class Commands extends TelegramLongPollingBot {

	public boolean partito = false;

	@Override
	public String getBotToken() {

		return "645382473:AAG1Vtkoky27VLINnIWRvaxQxqig-xsbKa4";
	}

	public String getBotUsername() {
		return "siandocstatus";
		// tomasmalibot
		// 502596920:AAGXj1omTxPldCElns1Wiw965LqslMSKBHw
		// TomasSubitoBot
		// 657809545:AAEA4xHiTKLndDuRJc9G5XYrwt-ul2WFqH0
		// tomasweather
		// 601333146:AAHJ4Fa1wDt5x5Tsm2bB7CQE1qhYAEXxyBM
		// siandocstatus
		// 645382473:AAG1Vtkoky27VLINnIWRvaxQxqig-xsbKa4
		// Test2TomasBot
		// 676793933:AAFSqroVLFsRsYU1nk12-gmVWrYprDN2q-I

	}

	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasMessage())
			hasMessage(update);
		else if (update.hasCallbackQuery())
			HasCallbackQuery(update);

	}

	/**
	 * Controlla tutti i messaggi con testo che arrivano e fa il parsing
	 * 
	 * @param update
	 */
	public void hasMessage(Update update) {

		if (update.getMessage().hasText()) {
			if (Queries.userIdExsist(update.getMessage().getChatId()) || update.getMessage().getText().toUpperCase()
					.equals("/REGISTRAMI")) {
				switch (update.getMessage().getText().toUpperCase()) {

				case "/REGISTRAMI":
					CommandsMatches.UserRegistration(this, update);
					break;
				case "/START":
					CommandsMatches.createInlineKeyboardLinks(this, update);
					break;

				default:
					CommandsMatches.AllOtherMessages(this, update);
					break;
				}
			} else {
				try {
					execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText(
							"Registratiti clickando '/Registrami' prima di usare il bot!"));
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void HasCallbackQuery(Update update) {

		if (update.getCallbackQuery().getData().contains("Attiva_"))
			Scheduler.broadcast(update.getCallbackQuery().getData().substring(7));
		else
			// keyboard per abilitarsi nei links
			CommandsMatches.createAbilitazione(this, update, Queries.getLinkId(update.getCallbackQuery().getData()));

		// switch (update.getCallbackQuery().getData()) {
		//
		// case "Attiva_Mipaaf Tecniche Web Service":
		// case "Attiva_Mipaaf Codifiche Registri Vitivinicoli":
		// Scheduler.broadcast(update.getCallbackQuery().getData().substring(7));
		// break;
		//
		// case "Mipaaf Tecniche Web Service":
		// case "Mipaaf Codifiche Registri Vitivinicoli":
		// CommandsMatches.createAbilitazione(this, update, Queries.getLinkId(update.getCallbackQuery().getData()));
		// break;
		//
		// default:
		// CommandsMatches.AllOtherMessages(this, update);
		// break;
		// }

	}

}
