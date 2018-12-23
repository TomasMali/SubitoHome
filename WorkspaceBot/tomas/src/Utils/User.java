package Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;

import tomas.MyAmazingBot;

public class User extends MyAmazingBot {
	Update update = null;
	MyAmazingBot myAmazingBot = null;

	private String firstName = "";
	private String lastName = "";
	private long chatId = 0;
	private long userId = 0;
	private String messageText = "";
	long message_id = 0;
	long chat_id = 0;
	private String inlineKeyboardData = "";

	public Jsoup_HTML Jsoup_HTML = new Jsoup_HTML();
	public String parola = "";
	public String citta = "";
	public String cerca = "";
	public String path = "https://www.subito.it/annunci-veneto/vendita/usato/" + citta + cerca;
	public List<Float> price = new ArrayList<>();
	public List<Float> price_Timer = new ArrayList<>();
	public String sms = "";
	public List<Annuncio> cache = new ArrayList<Annuncio>();

	public boolean statoTimer = false;

	public Runnable helloRunnable = new Runnable() {
		public void run() {
			System.out.println("Timer attivo alle: " + LocalDateTime.now());
			myAmazingBot.executeOnTime();
		}
	};

	public ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	public User(MyAmazingBot myAmazingBot, Update update) {
		this.update = update;
		this.myAmazingBot = myAmazingBot;
		if (update.hasMessage()) {
			firstName = update.getMessage().getChat().getFirstName();
			lastName = update.getMessage().getChat().getLastName();
			chatId = update.getMessage().getChatId();
			userId = update.getMessage().getChat().getId();
			messageText = update.getMessage().getText();
		} else if (update.hasCallbackQuery()) {
			firstName = update.getCallbackQuery().getMessage().getChat().getFirstName();
			lastName = update.getCallbackQuery().getMessage().getChat().getLastName();
			chatId = update.getCallbackQuery().getMessage().getChatId();
			userId = update.getCallbackQuery().getMessage().getChat().getId();
			messageText = update.getCallbackQuery().getMessage().getText();

			message_id = update.getCallbackQuery().getMessage().getMessageId();
			chat_id = update.getCallbackQuery().getMessage().getChatId();
			inlineKeyboardData = update.getCallbackQuery().getData();
		}

	}

	public void doTimer() {

	}

	public long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(long message_id) {
		this.message_id = message_id;
	}

	public long getChat_id() {
		return chat_id;
	}

	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}

	public SendMessage composeMessage(String text) {
		return new SendMessage().setChatId(this.getChatId()).setText(text);
	}

	public EditMessageText composeTextToSendBack(String text) {
		return new EditMessageText().setChatId(this.getChatId()).setMessageId((int) (this.getMessage_id())).setText(
				text);
	}

	public String getInlineKeyboardData() {
		return inlineKeyboardData;
	}

	public void setInlineKeyboardData(String inlineKeyboardData) {
		this.inlineKeyboardData = inlineKeyboardData;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

}
