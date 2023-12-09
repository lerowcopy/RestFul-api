package API.Operation;

import API.MySql.MySqlConnection;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Objects;

import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiOperation extends MySqlConnection  {

    Connection connection = null;



    public ApiOperation(Connection connect){
        super();
        connection = connect;
    }
     @Override
    public void request() {
        System.out.print("Введите запрос: \n");
        Scanner in = new Scanner(System.in);
        RequestHandler(in.next());
    }

    @Override
    public void RequestHandler(String request){
        if (Objects.equals(request, "GET")){
            GET();
            System.out.print("GET запрос выполнен");
        }
    }
    @Override
    public void PUT() throws SQLException {
        if (connection == null){
            System.err.print("Сервер ещё не запущен");
        }else {
            Statement s = connection.createStatement();
            s.executeUpdate("insert into test1 (name) values ('Sergey')");

        }

    }}

/*    @Override
    public String GET() {
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
                System.out.print(jsonObject.toString(4));
                jsonObject.toString(4);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}*/
