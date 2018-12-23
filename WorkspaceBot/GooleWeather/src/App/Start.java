package App;

import java.io.IOException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import CallMatches.Commands;
import Schedulers.Scheduler;

public class Start {

	public static void main(String[] args) {

		while (true) {
			ApiContextInitializer.init();

			// Instantiate Telegram Bots API
			TelegramBotsApi botsApi = new TelegramBotsApi();
			System.out.println("Application V1 has been started successfully!");

			// Register our bot
			try {
				Commands command = new Commands();
				botsApi.registerBot(command);

				Scheduler demo = new Scheduler(command);
				// Thread per Mipaaf Codifiche Registri Vitivinicoli
				try {
					// da qui si fanno partire tutti i thread
					demo.runThreadWithTask();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

		}
	}

}
