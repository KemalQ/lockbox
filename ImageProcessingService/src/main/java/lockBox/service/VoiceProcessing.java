package lockBox.service;

public interface VoiceProcessing {
    byte[] getDataBytes();
    void loadFromBytes(byte[] data);
}
