public class GameDriver {
    public static void main(String[] args) {
        Game myUnoGame = MyUnoGame.getInstance();
        myUnoGame.play();
    }
}