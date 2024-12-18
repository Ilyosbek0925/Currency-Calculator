package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Bot1 extends TelegramLongPollingBot {
	Long a = 0l;
	Long b = 0l;

	@Override
	public void onUpdateReceived(Update update) {
		try {
			SendMessage message = new SendMessage();
			Long chatId = update.getMessage().getChatId();
			message.setChatId(chatId);
			message.setText("click button");
			ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
			List < KeyboardRow > list = new ArrayList <>();
			KeyboardRow row = new KeyboardRow();
			KeyboardButton button = new KeyboardButton();
			KeyboardButton button1 = new KeyboardButton();
			button.setText("test");
			button1.setText("javob");
			row.add(button);
			row.add(button1);


			list.add(row);

			markup.setKeyboard(list);
            message.setReplyMarkup(markup);
			execute(message);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}


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
