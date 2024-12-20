package org.example;

import com.google.gson.Gson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot1 extends TelegramLongPollingBot {
	ExecutorService executor = Executors.newFixedThreadPool(2);
	static List < Users > list = new ArrayList <>();
	static SendMessage sendMessage = new SendMessage();


	@Override
	public void onUpdateReceived(Update update) {
		executor.execute(() -> {
			try {
				Message message = update.getMessage();
				if (message == null) {
					execute(BotService.callBack(update));
					return;
				}
				Long chatId = message.getChatId();

				if (message.getText().equals("/start")) {
					execute(BotService.startCondition(update, chatId));
					return;
				}
				Users user = UserService.findUser(chatId, list);
				if (message.getText().equals("\uD83D\uDD04 Valyutani almashtirish")) {
					sendMessage = BotService.changeCurrency(chatId);
					execute(sendMessage);
					return;
				}
				if (message.getText().equals("\uD83D\uDCB3 Valyuta kurslari")) {

					sendMessage = BotService.allCurrency();
					sendMessage.setChatId(chatId);
					execute(sendMessage);

					return;
				}


				if (user.getState().equals(States.NAME)) {
					execute(BotService.nameCondition(update, chatId, user));
					return;
				} else if (user.getState().equals(States.NORMAL)) {
					sendMessage = BotService.normalCondition(update, chatId, user);
					sendMessage.setReplyMarkup(BotService.reply());
					execute(sendMessage);
					sendMessage.setText(user.currency+ " -- hisoblash uchun miqdorni kiriting : ");
					execute(sendMessage);
					return;
				} else {
					sendMessage.setChatId(chatId);
					sendMessage.setText("Kechirasiz nimadur xato ketti admin javobini kuting");
					execute(sendMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}


	@Override
	public String getBotUsername() {
		return "@CurrencyCalculator_toSUM_bot";
	}

	@Override
	public String getBotToken() {
		return "7107800742:AAE1CyXOpsoVAIF9Ygna6QZxhoH9KsA4sdU";
	}
}
