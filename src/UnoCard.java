enum Color {
    RED, YELLOW, GREEN, BLUE
}

enum Type {
    NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR
}

class UnoCard {
    private final Color color;
    private final Type type;
    private final int number;  // Only used for NUMBER cards

    public UnoCard(Color color, Type type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        if (type == Type.NUMBER) {
            return color + " " + number;
        } else {
            return color + " " + type;
        }
    }

    public boolean isPowerUpCard() {//POWER UP CARDS = Draw Two, Draw For, Wild ,SKIP, REVERSE
        return switch (type) {
            case DRAW_TWO, WILD_DRAW_FOUR, WILD, SKIP, REVERSE -> true;
            default -> false;
        };
    }
}