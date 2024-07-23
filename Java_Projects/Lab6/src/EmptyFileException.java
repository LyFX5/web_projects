public class EmptyFileException extends Exception {
    String message = "File is empty! Please add data or choose another file.";
    public EmptyFileException(){}

    @Override
    public String getMessage() {
        return message;
    }
}
