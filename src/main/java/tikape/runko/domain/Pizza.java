/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nielasse
 */
public class Pizza {
    
    private Integer id;
    private String nimi;
    private List<RaakaAine> ainekset;
    

    public Pizza(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.ainekset = new ArrayList<>();
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
<<<<<<< HEAD
    public void lisaaTayte(RaakaAine aine) {
        this.ainekset.add(aine);
=======
    public void lisaaAine(RaakaAine r) {
        this.ainekset.add(r);
    }
    
    public List<RaakaAine> getIngredients () {
        return this.ainekset;
>>>>>>> 361b269bc819f75f9d623c562ba1e0eafeb260b7
    }
    
    
}
