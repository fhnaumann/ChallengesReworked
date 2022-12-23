package wand555.github.io.challengesreworkedgui;

public class DeserializationNotImplementedException extends NotImplementedException {
    public DeserializationNotImplementedException() {
        super("Deserialization from yml files into gui objects is not supported.");
    }
}
