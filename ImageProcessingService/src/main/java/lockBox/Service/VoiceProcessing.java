package lockBox.Service;

public interface VoiceProcessing {
    byte[] getDataBytes();
    void loadFromBytes(byte[] data);
}
