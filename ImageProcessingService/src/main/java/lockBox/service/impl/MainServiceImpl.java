package lockBox.service.impl;

import lockBox.dao.RawDataDAO;
import lockBox.entity.RawData;
import lockBox.service.MainService;
import lockBox.service.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

//ImageProcessingService
@Service
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService){
        this.producerService = producerService;
        this.rawDataDAO = rawDataDAO;
    }

    @Override
    public void processTextMessage(Update update){
        saveRawData(update);

        //TODO 25.06.2025
        var textToBinary = update.getMessage().getText();
        TextProcessingImpl textProcessing = new TextProcessingImpl();
        textToBinary = textProcessing.toBinary(textToBinary);
        var binaryToText = textProcessing.fromBinary(textToBinary);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Hello from MainServiceImpl, ImageProcessingService. Your text in binary is: "
                + textToBinary+ "\n " + binaryToText);

        producerService.produceTextAnswer(sendMessage);
    }

    private void saveRawData(Update update){
        RawData rawData = RawData.builder().
                event(update).build();
        rawDataDAO.save(rawData);
    }
}
