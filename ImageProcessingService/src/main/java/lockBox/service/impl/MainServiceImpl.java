package lockBox.service.impl;

import lockBox.dao.AppUserDAO;
import lockBox.dao.RawDataDAO;
import lockBox.entity.AppUser;
import lockBox.entity.RawData;
import lockBox.service.MainService;
import lockBox.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static lockBox.entity.enums.WorkFlowState.*;
import static lockBox.entity.enums.UserState.BASIC_STATE;
import static lockBox.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;
import static lockBox.entity.enums.WorkFlowStep.*;
import static lockBox.service.enums.ServiceCommands.*;

//ImageProcessingService
@Slf4j
@Service
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService, AppUserDAO appUserDAO){
        this.producerService = producerService;
        this.rawDataDAO = rawDataDAO;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void processTextMessage(Update update){
        saveRawData(update);//handle and save any incoming update
        var appUser = findOrSaveAppUser(update);//checking and saving user in db if he isn't there

        var userState = appUser.getUserState();//is he in BASIC_STATE or WAIT_FOR_EMAIL_STATE?
        var text = update.getMessage().getText();
        var output = "";
        var chatId = update.getMessage().getChatId();
        var botState = appUser.getWorkFlowState();//TODO check this

        if (CANCEL.equals(text)){
            output = cancelProcess(appUser);

        } else if (BASIC_STATE.equals(userState)){
            output = processServiceCommand(appUser, text);
        } else if (WAIT_FOR_EMAIL_STATE.equals(userState)){
            //TODO 30.06.2025 add after account confirmation via email
        }
        
        else {
            log.error("Unknown user state: " + userState);
            output = "Unknown error! Enter /help and try again later...";
        }
        sendAnswer(output, chatId);
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)){
            return;
        }

        //TODO Add document saving
        var answer = "Document succesfully uploaded! Link to download: http://finteh.com/getDoc/123";
        sendAnswer(answer, chatId);

    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)){
            return;
        }

        //TODO Add document saving
        var answer = "Photo succesfully uploaded! Link to download: http://finteh.com/getDoc/123";
        sendAnswer(answer, chatId);
    }

    private boolean isNotAllowToSendContent(Long chatId, AppUser appUser) {
        var userState = appUser.getUserState();
        if (appUser.getIsActive() != true){
            var error = "Register or activate an account to download content";
            return true;
        } else if (BASIC_STATE.equals(userState) != true){
            var error = "Cancel the current command with /cancel to send a files";
            return true;
        }
        return false;
    }

    private void sendAnswer(String output, Long chatId) {
        //TODO 25.06.2025 переводу текст в бинарный вид и возв в чат
//        TextProcessingImpl textProcessing = new TextProcessingImpl();
//        output = textProcessing.toBinary(output);
//        String binaryToText = textProcessing.fromBinary(output);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("This is MainServiceImpl, ImageProcessingService: "
                + output+ "\n ");

        producerService.produceTextAnswer(sendMessage);
    }

    private String cancelProcess(AppUser appUser) {
        appUser.setUserState(BASIC_STATE);
        appUserDAO.save(appUser);
        return "Command canceled!";
    }

    private String processServiceCommand(AppUser appUser, String cmd) {
        if (REGISTRATION.equals(cmd)){
            //TODO
            return "Temporary unavaible";
        } else if (HELP.equals(cmd)){
            return help();
        } else if (START.equals(cmd)){
            return "Welcome! Enter /help to see avaible commands";
        } else {
            return "Unknown commnand! Enter /help to see avaible commands";
        }
    }

    private String processUserBotStates(AppUser appUser){
        if (INITIAL_STATE.equals(appUser.getWorkFlowState())){//

        } else if (WAITING_FOR_ACTION.equals(appUser.getWorkFlowState())) {//waiting for embedding or extracting
            return "Choose one of the this commands: \n/embedding\n/extracting";
        } else if (WAITING_FOR_IMAGE.equals(appUser.getWorkFlowState())){//have to download image
            return "Download your photo";
        } else if (WAITING_FOR_TEXT.equals(appUser.getWorkFlowState())){
            return "Enter the text you want to hide in photo: ";
        } else if (WAITING_FOR_ALGORITHM.equals(appUser.getWorkFlowState())){
            return "Enter one of the keyboard number: ";
        } else if (PROCESSING.equals(appUser.getWorkFlowState())){
            return "";
        }
        else return "check processUserBotStates. It doesn't works properly";

        return "check processUserBotStates. It doesn't works properly";
    }

    private String help() {//TODO 30.06.2025 ЗУПИНИВСЯ ТУТ
        return "Avaible command list:\n" +
                "/cancel - cancel of current command\n" +
                "/registration - user registration";
    }

    private AppUser findOrSaveAppUser(Update update){
        User telegramUser = update.getMessage().getFrom();
        AppUser persistentAppUser = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());

        if (persistentAppUser == null){
            AppUser transientAppUser = AppUser.builder().
                    telegramUserId(telegramUser.getId()).
                    firstName(telegramUser.getFirstName()).
                    lastName(telegramUser.getLastName()).
                    userName(telegramUser.getUserName()).
                    //TODO change default values after adding registration
                    isActive(true).
                    userState(BASIC_STATE).         // устанавливаю по умолчанию
                    workFlowState(INITIAL_STATE).   // устанавливаю по умолчанию, но нужно отредактировать
                    build();
            return appUserDAO.save(transientAppUser); // сохраняю нового пользователя в бд
        }
        return persistentAppUser;
    }

    private void saveRawData(Update update){//to handle any incoming message
        RawData rawData = RawData.builder().
                event(update).build();
        rawDataDAO.save(rawData);
    }
}
