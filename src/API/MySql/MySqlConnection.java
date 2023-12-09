package API.MySql;

import API.Operation.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Objects;

public class MySqlConnection {

    private String url;
    private String username;
    private String password;
    protected Connection connection = null;
    public ApiOperation api = null;
    public Boolean connected = false;
    public void request(){}

    public MySqlConnection(){
        url = "jdbc:mysql://sql11.freesqldatabase.com/sql11668720";
        username = "sql11668720";
        password = "feHTsGVF8d";
    }

    public void RequestHandler(String request){
        if (Objects.equals(request, "GET")){
            GET();
            System.out.print("GET запрос выполнен");
        }
    }
    public void PUT() throws SQLException {}
    public String GET(){
        System.out.print("work");
        String sqlQuery = "SELECT * FROM test1";
        try (PreparedStatement s = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = s.executeQuery()){
                JSONArray jsonArray = new JSONArray();
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", name);
                    jsonObject.put("id", id);
                    jsonArray.put(jsonObject);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("table", jsonArray);
                JSONObject json = new JSONObject();
                json.put("tableList", jsonObject);
                try (PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\JSON\\test.json"))){
                    out.write(jsonObject.toString(4));
                }catch (Exception e){
                    e.printStackTrace();
                }
                return jsonObject.toString(4);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Nothing";
    }

    public void connect (){
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

    public void disconect (){
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
