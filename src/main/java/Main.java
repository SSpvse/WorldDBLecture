import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, FileNotFoundException {

        Menu menu = new Menu();
        menu.runMenu();
    }
}