import java.sql.*;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlWriter {
    private java.sql.Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private String url = "jdbc:mysql://localhost:3306/store";
    private String user = "root";
    private String password = "6VBMYf123";

    public void saveToDatabase(Map<String, String> items){

        try {
            con = DriverManager.getConnection(url, user, password);
            Statement st = (Statement) con.createStatement();

            for (Map.Entry<String, String> entry : items.entrySet()) {
                String key = entry.getKey();
                if (key.indexOf("'")>0){
                    key = key.replace("'", "\\'");
                }
                if (key.indexOf("\"")>0){
                    key = key.replace("\"", "\\\"");
                }
                st.executeUpdate("INSERT INTO products  (name, price)" + String.format("VALUES (\"%s\", \"%s\")", key, entry.getValue()  ));
            }
            con.close();
        }

        catch (SQLException ex) {
          System.out.println(ex);

        }
    }
}
