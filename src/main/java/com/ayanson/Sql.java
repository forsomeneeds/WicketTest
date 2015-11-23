package com.ayanson;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class Sql {

    private Connection getConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:hsqldb:file:target\\testdb", "SA", "");
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  c;
    }

    public List<RecordItem> getItems() {
        List<RecordItem> result = new ArrayList<RecordItem>();

        Connection conn = null;
        Statement statement = null;

        try {
            conn = getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select Item_Id,Group_Id from Queue");

            while(rs.next())
                result.add(new RecordItem(rs.getInt("Item_Id"), rs.getInt("Group_Id")));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return result;
    }

    public void addItem(int itemId, int groupId) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement("insert into Queue(Item_ID,Group_ID) values (?,?)");
            statement.setInt(1, itemId);
            statement.setInt(2, groupId);
            statement.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}

