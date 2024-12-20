package org.example;

import com.google.gson.Gson;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static org.glassfish.grizzly.ProcessorExecutor.execute;

public class BotService {
	static List < Users > list = Bot1.list;
	static SendMessage sendMessage = Bot1.sendMessage;
	static Post[] post=null;


	public static SendMessage callBack(Update update) {
		String data = update.getCallbackQuery().getData();
		Long chatId = update.getCallbackQuery().getMessage().getChatId();
		Users user = UserService.findUser(chatId, list);
		user.setCurrency(data);
		user.setState(States.NORMAL);
		sendMessage.setText(data + " -- hisoblash uchun miqdorni kiriting : ");
		sendMessage.setChatId(chatId);
		return sendMessage;
	}

	public static SendMessage startCondition(Update update, Long chatId) {
		sendMessage.setChatId(chatId);
		sendMessage.setText("Assalomu Aleykum\uD83D\uDE0A.Valyuta hisoblovchi botimizga hush kelibsiz.Sizni qanday chaqirsak bo'ladi? ");
		System.out.println(chatId);

		Bot1.list.add(new Users(chatId, States.NAME));
		return sendMessage;
	}
	public  static  SendMessage  changeCurrency(long chatId){
		InlineKeyboardButton button1 = new InlineKeyboardButton();
		button1.setText("USD \uD83C\uDDFA\uD83C\uDDF8"); // Tugma matni
		button1.setCallbackData("USD"); // Callback ma'lumot
		InlineKeyboardButton button2 = new InlineKeyboardButton();
		button2.setText("RUB \uD83C\uDDF7\uD83C\uDDFA");
		button2.setCallbackData("RUB");
		InlineKeyboardButton button3 = new InlineKeyboardButton();
		button3.setText("EUR \uD83C\uDDEA\uD83C\uDDFA");
		button3.setCallbackData("EUR");
		List < InlineKeyboardButton > row1 = new ArrayList <>();
		row1.add(button1);
		row1.add(button2);
		row1.add(button3);
		List < List < InlineKeyboardButton > > rows = new ArrayList <>();
		rows.add(row1);
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		markup.setKeyboard(rows);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText("Valyutani kiriting \uD83D\uDD3B");
		sendMessage.setReplyMarkup(markup);
		return sendMessage;

	}
	public static SendMessage nameCondition(Update update, Long chatId, Users user) {
		String name = update.getMessage().getText();
		user.setName(name);
		user.setState(States.NORMAL);
		InlineKeyboardButton button1 = new InlineKeyboardButton();
		button1.setText("USD \uD83C\uDDFA\uD83C\uDDF8"); // Tugma matni
		button1.setCallbackData("USD"); // Callback ma'lumot
		InlineKeyboardButton button2 = new InlineKeyboardButton();
		button2.setText("RUB \uD83C\uDDF7\uD83C\uDDFA");
		button2.setCallbackData("RUB");
		InlineKeyboardButton button3 = new InlineKeyboardButton();
		button3.setText("EUR \uD83C\uDDEA\uD83C\uDDFA");
		button3.setCallbackData("EUR");
		List < InlineKeyboardButton > row1 = new ArrayList <>();
		row1.add(button1);
		row1.add(button2);
		row1.add(button3);
		List < List < InlineKeyboardButton > > rows = new ArrayList <>();
		rows.add(row1);
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		markup.setKeyboard(rows);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText("Valyutani kiriting \uD83D\uDD3B");
		sendMessage.setReplyMarkup(markup);
		return sendMessage;
	}
public  static Post[] posts(){
	String s = InternetSetting.setCurrency();
	Gson gson = new Gson();
	post = gson.fromJson(s, Post[].class);
return post;

}
	public static SendMessage normalCondition(Update update, Long chatId, Users user) {
		Message message = update.getMessage();
		double amount = 0;
		try {
			amount = Integer.parseInt(message.getText());
		} catch (NumberFormatException e) {
			sendMessage.setText("Faqatgina raqamlar qabul qilinadi! Raqam yuboring:");
			sendMessage.setChatId(chatId);
			return sendMessage;
		}
posts();
		double result = 0;
		for (int i = 0; i < post.length; i++) {
			if (post[ i ].getCcy().equals(user.getCurrency())) {
				result = amount * Double.parseDouble(post[ i ].getRate());
			}

		}
		String text = "";
		if (result < 1000000000) {
			text = "\n" + toText((long) result);
		}
		sendMessage.setText(amount + "\t" + user.getCurrency() + " = " + String.valueOf(result) + "\tSUM" + text);
		sendMessage.setChatId(chatId);

		return sendMessage;
	}
public static SendMessage allCurrency(){
posts();
String s="";
	for (Post post1 : post) {
s=s+"100 "+post1.getCcy()+" = "+(((int)(1000*Double.parseDouble(post1.getRate()))/10.0)+" SUM")+"\n\n";
	}
	sendMessage.setText(s);
return sendMessage;}

	public static String toText(long amount) {
		String text = "";
		long y = 0;
		//325645
		if (amount % 1000 != 0) {
			y = amount % 1000;
			text = "" + y + "sum";

		}
		if (amount % 1000000 != 0 && amount / 1000 >= 1) {
			y = amount / 1000 % 1000;
			text = y + "ming " +
					text;
		}
		if (amount % 1000000000 != 0 && amount / 1000000 >= 1) {
			y = amount / 1000000 % 1000000;
			text = y + "mln " + text;
		}
		if (amount / 1000000000 >= 1) {

			text = (amount / 1000000) + "mlrd " + text;
		}

		return text;
	}


	public static ReplyKeyboardMarkup reply(){
		ReplyKeyboardMarkup markup=new ReplyKeyboardMarkup();
		List< KeyboardRow > rows=new ArrayList<>();
		KeyboardButton button=new KeyboardButton("\uD83D\uDD04 Valyutani almashtirish");
		KeyboardButton button1=new KeyboardButton("\uD83D\uDCB3 Valyuta kurslari");
		KeyboardButton button2=new KeyboardButton("☎\uFE0F Bog'lanish");
		KeyboardRow row = new KeyboardRow();
		KeyboardRow row1 = new KeyboardRow();
		KeyboardRow row2 = new KeyboardRow();
		row.add(button);
		row1.add(button1);
		row2.add(button2);
rows.add(row);
rows.add(row1);
rows.add(row2);
markup.setKeyboard(rows);
return  markup;
	}

}
