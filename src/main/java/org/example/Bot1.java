package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Bot1 extends TelegramLongPollingBot {
	Long a = 0l;
	Long b = 0l;

	@Override
	public void onUpdateReceived(Update update) {

		Message message = update.getMessage();
		Long chatId = message.getChatId();
		System.out.println(chatId);
		System.out.println(a);
		String text = message.getText();
		SendMessage send = new SendMessage();
		if (a == 0L) {
			System.out.println("kirdim");
			a = chatId;
		} else if (a.equals(chatId)) {
			if (b != 0L) {
				send.setChatId(b);
				send.setText(text);
			}
		} else if (b.equals(chatId)) {
				send.setChatId(a);
				send.setText(text);
		} else {
			b = chatId;
			send.setText(text);
			send.setChatId(a);}
		try {
			if(send.getChatId()!=null)
			execute(send);
		} catch (TelegramApiException e) {
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
