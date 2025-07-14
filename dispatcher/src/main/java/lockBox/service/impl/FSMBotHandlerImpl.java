package lockBox.service.impl;

import lockBox.dao.AppUserDAO;
import lockBox.dao.RawDataDAO;
import lockBox.entity.AppUser;
import lockBox.entity.RawData;
import lockBox.service.FSMBotHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static lockBox.entity.enums.UserState.BASIC_STATE;
import static lockBox.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;
import static lockBox.entity.enums.WorkFlowState.INITIAL_STATE;
import static lockBox.service.enums.ServiceCommands.*;
import static lockBox.service.enums.ServiceCommands.START;

@Service
public class FSMBotHandlerImpl implements FSMBotHandler {
    private final RawDataDAO rawDataDAO;
    private final AppUserDAO appUserDAO;

    public FSMBotHandlerImpl(RawDataDAO rawDataDAO, AppUserDAO appUserDAO) {
        this.rawDataDAO = rawDataDAO;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void check(Update update) {
        saveRawData(update);
        AppUser appUser = findOrSaveAppUser(update);//checking and saving user in db if he isn't there

        var userState = appUser.getUserState();//is he in BASIC_STATE or WAIT_FOR_EMAIL_STATE?
        var workFlowState = appUser.getWorkFlowState();//
        var text = update.getMessage().getText();
        var output = "";
        var chatId = update.getMessage().getChatId();

        if (CANCEL.equals(text)){
            output = cancelProcess(appUser);

        } else if (BASIC_STATE.equals(userState)){
            output = processServiceCommand(appUser, text);
        } else if (WAIT_FOR_EMAIL_STATE.equals(userState)){
            //TODO 30.06.2025 add after account confirmation via email
        }

    }

    private void saveRawData(Update update){//to handle any incoming message
        RawData rawData = RawData.builder().
                event(update).build();
        rawDataDAO.save(rawData);
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

    private String help() {
        return "Avaible command list:\n" +
                "/cancel - cancel of current command\n" +
                "/registration - user registration";
    }
}
