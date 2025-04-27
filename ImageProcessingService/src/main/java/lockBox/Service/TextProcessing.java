package lockBox.Service;

public interface TextProcessing {
    public String bitsToAscii(boolean[] bits);
    public boolean[] bitStringToBitArray(String bitString);
    public String binaryToString(String bitString);//принимает String=111000101 выдает String ASCI
}