package lockBox.Service.impl;

import lockBox.Service.VoiceProcessing;

import java.io.File;

public class VoiceProcessingImpl implements VoiceProcessing {
    private File oggFile;
    private byte[] rawAudioBytes; // уже в виде 8-бит PCM

    public VoiceProcessingImpl(File oggFile) {
        this.oggFile = oggFile;
    }

    @Override
    public byte[] getDataBytes() {
        if (rawAudioBytes == null) {
            convertOggToPcmBytes();
        }
        return rawAudioBytes;
    }

    @Override
    public void loadFromBytes(byte[] data) {
        this.rawAudioBytes = data;
    }

    public void saveToWav(File outputWav) {
        //TODO сохраняем rawAudioBytes обратно в WAV-файл
    }

    private void convertOggToPcmBytes() {
        // TODO вызывает ffmpeg и читает WAV в 8-бит PCM
        // сохраняет результат в rawAudioBytes
    }
}
