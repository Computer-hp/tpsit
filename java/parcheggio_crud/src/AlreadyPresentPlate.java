public class AlreadyPresentPlate extends Exception {
    public AlreadyPresentPlate(String targa) {
        super("L'auto con targa " + targa + " è già presente nel parcheggio.");
    }
}