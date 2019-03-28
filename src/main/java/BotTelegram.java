import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.ArrayList;

/**
 * Created by Наталья on 04.05.2017.
 */
public class BotTelegram {

public ArrayList<String> list = new ArrayList<>();

   public void sendMail(String url) {
       if (!list.contains(url)) {
           list.add(url);
           TelegramBot bot = TelegramBotAdapter.build("312666857:AAGSJhNa-uLdCouub1j3tIbHhxDj66KjMwA");
           SendMessage request = new SendMessage("326762610", "http://www.myscore.ru/match/" + url.substring(4, url.length()) + "/#match-summary");
           SendResponse sendResponse = bot.execute(request);
           boolean ok = sendResponse.isOk();
           Message message = sendResponse.message();
       }
   }
}
