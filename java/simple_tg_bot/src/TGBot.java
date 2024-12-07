import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class TGBot implements LongPollingSingleThreadUpdateConsumer {
    private static final String botUsername = "Daniel Telegram Bot";
    private static final String botToken = "7365624172:AAFstBRpGve2uMOVeyHzePZ3nHgZJXLsj14";
    private TelegramClient telegramClient = new OkHttpTelegramClient(botToken);


    public String getBotUsername() {
        return botUsername;
    }
    public static String getBotToken() { return botToken; }


    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String message = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        String response;

        switch (message.toLowerCase()) {
            case "/start":
                response = "Welcome to Daniel's Telegram Bot! How can i assist you today?";
                break;
            case "hello":
                response = "Hello there! How can i help you?";
                break;

            case "/help":
                response = "Here are some commands you can use:\n" +
                        "/start - Start interacting with the bot\n" +
                        "/help - Get the list of commands\n" +
                        "Say 'hello' or 'hi' to greet the bot!";
                break;

            default:
                response = "I cannot answer that question. Try /help for a list of commands.";
        }

        SendMessage sendMessage = new SendMessage(chatId, response);

        try {
            telegramClient.execute(sendMessage);
        }
        catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
