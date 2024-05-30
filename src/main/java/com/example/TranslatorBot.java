package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import static com.google.api.client.googleapis.json.GoogleJsonResponseException.execute;

@SpringBootApplication
@RestController
public class TranslatorBot {

    private String botUsername = "trans_bimba_bot";
    private String botToken = "7200984784:AAGryzumUnQHuIeKFNjYEokmYqvQEFzd_8g";

    public static void main(String[] args) {
        SpringApplication.run(TranslatorBot.class, args);
    }

    @PostMapping("/")
    public void onUpdateReceived(@RequestBody Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String translatedText = translateText(messageText, "en", "uk"); // Переклад з англійської на українську, змініть за потребою
            SendMessage message = new SendMessage();
                    message.setChatId(String.valueOf(chatId));
                    message.setText(translatedText);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String execute(String message) {
        return message;
    }

    private String translateText(String text, String sourceLanguage, String targetLanguage) {
        // Ініціалізація Google Translate API
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        // Переклад тексту
        Translation translation = translate.translate(text, Translate.TranslateOption.sourceLanguage(sourceLanguage),
                Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }


    public String getBotUsername() {
        return botUsername;
    }


    public String getBotToken() {
        return botToken;
    }


}
