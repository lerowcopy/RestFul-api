package API;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class API {

    private final String url;
    private final String username;
    private final String password;
    protected Connection connection = null;
    public Boolean connected = false;

    public API(){
        url = "jdbc:mysql://db4free.net:3306/testproject_lyc";
        username = "testproject_lyc";
        password = "feHTsGVF8d";
    }

    public void POST(JsonNode jsonNode) {
        String sqlQuery = "INSERT INTO myDataBase (name) VALUES (?)";

        try (PreparedStatement s = connection.prepareStatement(sqlQuery)){
            s.setString(1, jsonNode.get("name").get("title").asText());
            s.executeUpdate();
            System.out.println("Data inserted into the database.");
        }catch (SQLException e){
            sqlQuery = "ALTER TABLE myDataBase DROP `id`;";
            String sql = "ALTER TABLE myDataBase ADD `id` INT(11) NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY (`id`);";
            try (PreparedStatement st = connection.prepareStatement(sqlQuery); PreparedStatement sta = connection.prepareStatement(sql)){
                st.executeUpdate();
                sta.executeUpdate();
            }catch (SQLException r){
                r.printStackTrace();
            }
            e.printStackTrace();

        }
    }

    public void DELETE(JsonNode jsonNode) {
        String sqlQuery = "DELETE FROM myDataBase WHERE id = (?)";

        try (PreparedStatement s = connection.prepareStatement(sqlQuery)){
            int deletedId = jsonNode.get("id").asInt();
            s.setInt(1, deletedId);
            s.executeUpdate();
            System.out.println("Data deleted into the database.");
            sqlQuery = "ALTER TABLE myDataBase DROP `id`;";
            String sql = "ALTER TABLE myDataBase ADD `id` INT(11) NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY (`id`);";
            try (PreparedStatement st = connection.prepareStatement(sqlQuery); PreparedStatement sta = connection.prepareStatement(sql)){
                st.executeUpdate();
                sta.executeUpdate();
            }catch (SQLException r){
                r.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void PUT(JsonNode jsonNode) {
        String sqlQuery = "UPDATE myDataBase SET name = (?) WHERE id = (?)";

        try (PreparedStatement s = connection.prepareStatement(sqlQuery)){
            s.setString(1, jsonNode.get("name").asText());
            s.setInt(2, jsonNode.get("id").asInt());
            s.executeUpdate();
            System.out.println("Data updated in the database.");
        }catch (SQLException e){
            sqlQuery = "ALTER TABLE myDataBase DROP `id`;";
            String sql = "ALTER TABLE myDataBase ADD `id` INT(11) NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY (`id`);";
            try (PreparedStatement st = connection.prepareStatement(sqlQuery); PreparedStatement sta = connection.prepareStatement(sql)){
                st.executeUpdate();
                sta.executeUpdate();
            }catch (SQLException r){
                r.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    public String GET(){
        String sqlQuery = "SELECT * FROM myDataBase";
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
                System.out.println("Disconnect from MySQL is successful!");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
