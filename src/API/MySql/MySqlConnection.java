package API.MySql;

import API.Operation.ApiOperation;

import javax.swing.text.StyledEditorKit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
    private String url;
    private String username;
    private String password;
    protected Connection connection = null;
    protected Boolean connected = false;
    public void request() {};
    public void PUT() throws SQLException {}
    public void GET(){}
    public MySqlConnection(){
        url = "jdbc:mysql://sql11.freesqldatabase.com/sql11668720";
        username = "sql11668720";
        password = "feHTsGVF8d";
    }

    public void Connect (){
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Подключение к MySQL успешно!");
            connected = true;

        } catch (ClassNotFoundException e){

            System.err.println("JDBC драйвер не найден");
            e.printStackTrace();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }

    public void Disconect (){
        if (connection != null){
            try{
                connection.close();
                System.out.println("Отключение от MySQL успешно!");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
