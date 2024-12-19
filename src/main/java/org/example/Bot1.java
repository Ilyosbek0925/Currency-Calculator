package org.example;

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
	List < Users > list = new ArrayList <>();
	SendMessage sendMessage = new SendMessage();


	@Override
	public void onUpdateReceived(Update update) {
		executor.execute(() -> {
			try {
				Message message = update.getMessage();
				if(message==null){
					String data = update.getCallbackQuery().getData();
					Long chatId = update.getCallbackQuery().getMessage().getChatId();
					Users user = UserService.findUser(chatId, list);
					user.setState(States.EMAIL);
sendMessage.setText(data +" --> hisoblash uchun miqdorni kiriting : ");
sendMessage.setChatId(chatId);
execute(sendMessage);
return;
				}
				Long chatId = message.getChatId();
				if (message.getText().equals("/start")) {
					sendMessage.setChatId(chatId);
					sendMessage.setText("Assalomu Aleykum\uD83D\uDE0A.Valyuta hisoblovchi botimizga hush kelibsiz\uD83E\uDD29.Sizni qanday chaqirsak bo'ladi? ");
					execute(sendMessage);
					System.out.println(chatId);
					list.add(new Users(chatId, States.NAME));
					return;
				}
				Users user = UserService.findUser(chatId,list);

				if (user.getState().equals(States.NAME)) {
					String name = message.getText();
					user.setName(name);
					user.setState(States.EMAIL);

// Tugma yaratish
					InlineKeyboardButton button1 = new InlineKeyboardButton();
					button1.setText("USD \uD83C\uDDFA\uD83C\uDDF8"); // Tugma matni
					button1.setCallbackData("USD"); // Callback ma'lumot

					InlineKeyboardButton button2 = new InlineKeyboardButton();
					button2.setText("RUB \uD83C\uDDF7\uD83C\uDDFA");
					button2.setCallbackData("RUB");
					InlineKeyboardButton button3 = new InlineKeyboardButton();
					button3.setText("EUR \uD83C\uDDEA\uD83C\uDDFA");
					button3.setCallbackData("EUR");

// Tugmalarni qatorga qo'shish
					List < InlineKeyboardButton > row1 = new ArrayList <>();
					row1.add(button1);
					row1.add(button2);
					row1.add(button3);

// Qatorlarni tugma paneliga qo'shish
					List < List < InlineKeyboardButton > > rows = new ArrayList <>();
					rows.add(row1);
					InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
					markup.setKeyboard(rows);
// Panelni xabarga biriktirish
					SendMessage Sendmessage = new SendMessage();
					Sendmessage.setChatId(chatId);
					Sendmessage.setText("Valyutani kiriting \uD83D\uDD3B");
					Sendmessage.setReplyMarkup(markup);
					try {
						execute(Sendmessage);
						System.out.println("ketti");
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;
				} else if (user.getState().equals(States.EMAIL)) {
					String amount = message.getText();


					sendMessage.setChatId(chatId);
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
				execute(sendMessage);
			} catch (Exception e) {
				throw new RuntimeException(e);
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
