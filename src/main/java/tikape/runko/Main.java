package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.PizzaDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Pizza;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
                  

        Database database = new Database("jdbc:sqlite:pizzat.db");
        database.init();
        

        PizzaDao pizzaDao = new PizzaDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/pizzat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("pizzat", pizzaDao.findAll());

            return new ModelAndView(map, "pizzat");
        }, new ThymeleafTemplateEngine());
        
        post("/pizzat", (req, res) -> {
            String nimi = req.queryParams("nimi");
            System.out.println("Vastaanotettiin " + nimi);
            pizzaDao.save(new Pizza(1, nimi));

            return nimi + " lisätty";
        });
        
        get("/taytteet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("taytteet", raakaAineDao.findAll());

            return new ModelAndView(map, "taytteet");
        }, new ThymeleafTemplateEngine());
        
        post("/taytteet", (req, res) -> {
            String nimi = req.queryParams("nimi");
            System.out.println("Vastaanotettiin " + nimi);
            raakaAineDao.save(new RaakaAine(1, nimi));
            res.redirect("/taytteet");
            return "";
        });

        get("/pizzat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("pizza", pizzaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "pizza");
        }, new ThymeleafTemplateEngine());
    }
}
