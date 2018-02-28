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
import tikape.runko.domain.Pizza;
import tikape.runko.domain.RaakaAine;

/**
 *
 * @author huyingying
 */
public class PizzaDao implements Dao<Pizza, Integer> {
    private Database db;
    
    public PizzaDao(Database db) {
        this.db = db;
    }

    @Override
    public Pizza findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Pizza WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Pizza a = new Pizza(key, rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();
        return a;
    }
    
    public List<RaakaAine> listAllIngredients(Pizza p) throws SQLException {
        return p.getIngredients();
    }

    @Override
    public List<Pizza> findAll() throws SQLException {
        List<Pizza> pizzat = new ArrayList<>();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Pizza");
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Pizza pizza = new Pizza(rs.getInt("id"), rs.getString("nimi"));
            pizzat.add(pizza);
        }
        rs.close();
        stmt.close();
        conn.close();
        
        return pizzat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Pizza WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public Pizza save(Pizza object) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Pizza (nimi) VALUES (?)");
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM Pizza WHERE nimi = ?");
        stmt.setString(1, object.getNimi());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        Pizza p = new Pizza(rs.getInt("id"), rs.getString("nimi"));
        stmt.close();
        rs.close();
        conn.close();
        
        return p;
    }
    
}
