public enum Colour {

    YELLOW,
    GREEN,
    BLUE,
    BROWN,
    RED,
    WHITE,
    ORANGE,
    PINK,
    GRAY,
    BLACK;

    @Override
    public String toString() {
        switch(this) {
            case RED: return "RED";
            case GREEN: return "GREEN";
            case YELLOW: return "YELLOW";
            case BLUE: return "BLUE";
            case BROWN: return "BROWN";
            case WHITE: return "WHITE";
            case ORANGE: return "ORANGE";
            case PINK: return "PINK";
            case GRAY: return "GRAY";
            case BLACK: return "BLACK";
            default: throw new IllegalArgumentException();
        }
    }
}
