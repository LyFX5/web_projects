public class FileCannotReadException extends Exception {
    String message = "File cannot read! Please grant read permission or choose another file.";
    public FileCannotReadException(){}

    @Override
    public String getMessage() {
        return message;
    }
}
