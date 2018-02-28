/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Dao;
import tikape.runko.database.Database;
import tikape.runko.domain.RaakaAine;

/**
 *
 * @author kelokari
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        RaakaAine a = new RaakaAine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }
    
    public RaakaAine findOneByName(String name) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
        stmt.setObject(1, name);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        RaakaAine a = new RaakaAine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }
    
    @Override
    public List<RaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> aineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            aineet.add(new RaakaAine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return aineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    @Override
    public RaakaAine save(RaakaAine aine) throws SQLException {

        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine"
                + " (nimi)"
                + " VALUES (?)");

        stmt.setString(1, aine.getNimi());

        stmt.executeUpdate();

        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM RaakaAine"
                + " WHERE nimi = ?");

        stmt.setString(1, aine.getNimi());


        ResultSet rs = stmt.executeQuery();

        rs.next();

        RaakaAine a = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();

        rs.close();

        conn.close();

        return a;

    }

}
