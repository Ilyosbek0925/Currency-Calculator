package org.example;

import jdk.jshell.Snippet;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.lang.model.element.NestingKind;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot1 extends TelegramLongPollingBot {
	ExecutorService executor = Executors.newFixedThreadPool(2);
	List < Users > list = new ArrayList <>();
	SendMessage sendMessage = new SendMessage();


	@Override
	public void onUpdateReceived(Update update) {
		executor.execute(() -> {
			try {
				Message message = update.getMessage();
				Long chatId = message.getChatId();
				if (message.getText().equals("/start")) {
					sendMessage.setChatId(chatId);
					sendMessage.setText("Isminginni kiriting : ");
					execute(sendMessage);
					System.out.println(chatId);
					list.add(new Users(chatId, States.NAME));
					return;
				}
				Users user = null;
				for (Users users : list) {
					if (users.getId() == chatId) {
						user = users;
					}
				}
				if (user.getState().equals(States.NAME)) {
					String name = message.getText();
					user.setName(name);
					user.setState(States.EMAIL);
					sendMessage.setChatId(chatId);
					sendMessage.setText("Emailingizni kiriting");
					execute(sendMessage);
					return;
				} else if (user.getState().equals(States.EMAIL)) {
					String email = message.getText();
					user.setEmail(email);
					user.setState(States.KOD);
					sendMessage.setChatId(chatId);
					sendMessage.setText("emailingizga kod bordi kodni kiriting");
					execute(sendMessage);
					return;
				} else if (user.getState().equals(States.KOD)) {
					String kod = message.getText();
					user.setState(States.NORMAL);
					sendMessage.setChatId(chatId);
					sendMessage.setText("Siz muvaffaqiyatli ro'yhatdan o'tdingiz");
					execute(sendMessage);
					return;
				}

//				sendMessage.setText("click button");
//				ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
//				List < KeyboardRow > list = new ArrayList <>();
//				KeyboardRow row = new KeyboardRow();
//				KeyboardButton button = new KeyboardButton();
//				KeyboardButton button1 = new KeyboardButton();
//				button.setText("test");
//				button1.setText("javob");
//				row.add(button);
//				row.add(button1);
//				list.add(row);
//				markup.setKeyboard(list);
//				sendMessage.setReplyMarkup(markup);
//				execute(sendMessage);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}


	@Override
	public String getBotUsername() {
		return "Bobo5052_bot";
	}

	@Override
	public String getBotToken() {
		return "7596247893:AAHUdB4V-yc89z-sNfoxI8XVG-_5deh-ojw";
	}
}
