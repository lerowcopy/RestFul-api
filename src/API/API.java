package API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

public class API {

    private String url;
    private String username;
    private String password;
    protected Connection connection = null;
    public Boolean connected = false;
    public void request(){}

    public API(){
        url = "jdbc:mysql://sql11.freesqldatabase.com/sql11668720";
        username = "sql11668720";
        password = "feHTsGVF8d";
    }

    public File PUT() {

        return null;
    }
    public String GET(){
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
            System.out.println("Connection to MySQL is successful!");
            connected = true;

        } catch (ClassNotFoundException e){

            System.err.println("JDBC driver not found");
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
