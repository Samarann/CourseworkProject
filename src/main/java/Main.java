import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main{

    public static Connection db = null;

    public static void main(String[] args){
        openDatabase("Users.db"); //Opens the access to the database to be read to.
        readDatabase();
        closeDatabase(); //Closes the access to the database to be read to, so that it doesn't take up resources when it isn't being used.
    }

    private static void openDatabase(String dbFile){ //Function to open the database.
        try{
            Class.forName("org.sqlite.JDBC"); //Loads the database driver.
            SQLiteConfig config = new SQLiteConfig(); //Loads the database settings.
            config.enforceForeignKeys(true); //Loads the database settings.
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties()); //Open the database files.
            System.out.println("Database connection successfully established.");
        }catch (Exception exception){
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    private static void readDatabase(){
        try{
            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, Password FROM Users");
            ResultSet results = ps.executeQuery();
            System.out.println("UserID | Username | Password");
            while (results.next()){
                int UserID = results.getInt(1);
                String Username = results.getString(2);
                String Password = results.getString(3);
                System.out.println(UserID + " | " + Username + " | " + Password);
            }
        }catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    private static void writeDatabase(){
        try{

            PreparedStatement ps = db.prepareStatement("INSERT INTO Users (UserID, Username, Password) VALUES (?, ?, ?)");

            Scanner sc = new Scanner(System.in);

            System.out.println("Please input UserID");
            int UserIDAdd = sc.nextInt();

            System.out.println("Please input Username");
            String UsernameAdd = sc.nextString();

            System.out.println("Please input Password");
            String PasswordAdd = sc.nextString();

            ps.setInt(1, UserIDAdd);
            ps.setString(2, UsernameAdd);
            ps.setString(3, PasswordAdd);

            ps.executeUpdate();

        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
        }
    }



    private static void closeDatabase(){ //Function to close the database.
        try{
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception){
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }
}

