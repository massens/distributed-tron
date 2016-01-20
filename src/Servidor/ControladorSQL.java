/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Utils.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcassens
 */
public class ControladorSQL implements Observer {

    protected Connection con;
    protected Model_Servidor model;

    public ControladorSQL(Model_Servidor model) {
        this.model = model;
        model.addObserver(this);
        try {
            con = DBUtilidades.createMySQLConnection("tron");
            System.out.println("[SQL Connection Established]");

            asegurarTaulaExisteix(con, "scores");

            int[] scores = getBestScore();
            model.setBestScore(scores[0], scores[1]);
        } catch (Exception ex) {
            System.out.println("[!] Recorda que com a mínim has de tenir creada una BD anomenada 'tron' \n"
                    + "Si no la tens, entra en el teu client de mysql i executa 'CREATE DATABASE tron'");
            Logger.getLogger(ControladorSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        //Si el que volem és actualitzar les posicions al joc
        if (arg instanceof int[]) {
            //no fem res
        } //Si el que volem és acabar la partida
        else if (arg instanceof Integer) {
            try {
                //Afegim a la base de dades la puntuació
                addScore(con, model.getScore());
                int[] scores = getBestScore();
                model.setBestScore(scores[0], scores[1]);

            } catch (SQLException ex) {
                Logger.getLogger(ControladorSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addScore(Connection con, int[] scores) {
        try {
            System.out.println("[SQL INSERT] Afegim les puntuacions - " + scores[0] + ", " + scores[1]);
            String update = "INSERT INTO SCORES VALUES ('" + scores[0] + "', '" + scores[1] + "');";
            con.setAutoCommit(false);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate(update);
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int[] getBestScore() throws SQLException {
            //OBTENCIÓ DE LES PUNTUACIONS MÀXIMES
        //Utilitzem la funció SQL MAX
        int[] scores = new int[2];

        String query1 = "SELECT MAX(Player1) FROM SCORES";
        Statement st1 = con.createStatement();
        ResultSet result1 = st1.executeQuery(query1);
        result1.next();
        scores[0] = (int) result1.getObject(1);

        String query2 = "SELECT MAX(Player2) FROM SCORES";
        Statement st2 = con.createStatement();
        ResultSet result2 = st2.executeQuery(query2);
        result2.next();
        scores[1] = (int) result2.getObject(1);

        System.out.println("[SQL SELECT] Obtenció de puntuacions màximes");
        return scores;
    }

    public void asegurarTaulaExisteix(Connection con, String table) {
        //Si quan executem la query obtenem una excepció, voldrà dir que 
        //la taula no existeix. En aquest cas, crearem una nova taula
        //i li afegirem valors nuls per defecte.

        try {
            String query = "SELECT * FROM " + table;
            Statement st = con.createStatement();
            ResultSet result;
            result = st.executeQuery(query);
        } catch (Exception ex) {
            crearTaula(con, table);
            addScore(con, new int[]{0, 0});

        }

    }

    public void crearTaula(Connection con, String table) {
        try {
            System.out.println("[SQL CREATE TABLE] Creació de una taula anomenada " + table);
            String update = "CREATE TABLE " + table + " (Player1 INTEGER, Player2 INTEGER)";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.executeUpdate(update);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
