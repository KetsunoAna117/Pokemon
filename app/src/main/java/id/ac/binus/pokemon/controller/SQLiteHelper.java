package id.ac.binus.pokemon.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Vector;

import id.ac.binus.pokemon.model.Route;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(@Nullable Context context) {
        super(context, "PokemonGame", null, 69);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table route (" +
                "routeName String primary key," +
                "minLevel Integer," +
                "maxLevel Integer" + ")"
        );


        sqLiteDatabase.execSQL("CREATE TABLE pokemon (" +
                "routeName String," +
                "pokemonName String," +
                "FOREIGN KEY(routeName) REFERENCES route(routeName)" + ")"
        );

        sqLiteDatabase.execSQL("INSERT INTO route (routeName, minLevel, maxLevel) VALUES (?, ?, ?)", new Object[]{"Route 1", 1, 10});
        sqLiteDatabase.execSQL("INSERT INTO pokemon (routeName, pokemonName) VALUES (?, ?)", new Object[]{"Route 1", "zigzagoon"});
        sqLiteDatabase.execSQL("INSERT INTO pokemon (routeName, pokemonName) VALUES (?, ?)", new Object[]{"Route 1", "wurmple"});
        sqLiteDatabase.execSQL("INSERT INTO pokemon (routeName, pokemonName) VALUES (?, ?)", new Object[]{"Route 1", "poochyena"});
        sqLiteDatabase.execSQL("INSERT INTO pokemon (routeName, pokemonName) VALUES (?, ?)", new Object[]{"Route 1", "taillow"});
        sqLiteDatabase.execSQL("INSERT INTO pokemon (routeName, pokemonName) VALUES (?, ?)", new Object[]{"Route 1", "shroomish"});
        sqLiteDatabase.execSQL("INSERT INTO pokemon (routeName, pokemonName) VALUES (?, ?)", new Object[]{"Route 1", "wingull"});

    }

    //INSERT
    public boolean insertRoute(String routeName, int minLevel, int maxLevel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("routeName", routeName);
        values.put("minLevel", minLevel);
        values.put("maxLevel", maxLevel);

        return (db.insert("route", null, values) != -1) ?
                true : false;
    }

    // INSERT operation: Insert a new Pokemon for a specific route
    public boolean insertPokemonForRoute(String routeName, String pokemonName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("routeName", routeName);
        values.put("pokemonName", pokemonName);

        return (db.insert("pokemon", null, values) != -1) ?
                true : false;
    }

    //UPDATE
    public boolean updateRoute(String routeName, String newRouteName, Integer minLevel, Integer maxLevel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(!newRouteName.isEmpty())
            values.put("newRouteName", routeName);
        values.put("minLevel", minLevel);
        values.put("maxLevel", maxLevel);

        return (db.update("route", values,
                "routeName = ?",
                new String[]{routeName}) != 0) ?
                true : false;
    }

    public boolean updatePokemon(String routeName, String oldPokemonName, String newPokemonName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pokemonName", newPokemonName);

        return (db.update("pokemon", values,
                "routeName = ? AND pokemonName = ?",
                new String[]{routeName, oldPokemonName}
        ) != 0) ?
                true : false;
    }

    //DELETE
    public void deleteRoute(String routeName) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("route", "routeName = ?"
                , new String[]{routeName});
        db.delete("pokemon", "routeName = ?"
                , new String[]{routeName});
    }

    public void deletePokemon(String routeName, String pokemonName) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("pokemon",
                "routeName = ? AND pokemonName = ?",
                new String[]{routeName, pokemonName}
        );
    }

    //FETCH
    public Vector<String> getPokemon(String routeName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Vector<String> pokemonList = new Vector<>();

        Cursor cursor = db.query("pokemon",
                new String[]{"pokemonName"},
                "routeName = ?",
                new String[]{routeName},
                null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String pokemonName = cursor.getString(cursor.getColumnIndexOrThrow("pokemonName"));
                pokemonList.add(pokemonName);
            }
            cursor.close();
        }

        return pokemonList;
    }

    public Vector<Route> getAllRoutes() {
        Vector<Route> routeList = new Vector<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM route", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String routeName = cursor.getString(cursor.getColumnIndexOrThrow("routeName"));
                int minLevel = cursor.getInt(cursor.getColumnIndexOrThrow("minLevel"));
                int maxLevel = cursor.getInt(cursor.getColumnIndexOrThrow("maxLevel"));

                Route route = new Route(routeName, minLevel, maxLevel, this.getPokemon(routeName));
                routeList.add(route);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return routeList;
    }

    public Route getRouteByRouteName(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM route WHERE routeName = ?", new String[]{name});
        if(cursor != null){
            while(cursor.moveToNext()){
                String routeName = cursor.getString(cursor.getColumnIndexOrThrow("routeName"));
                int minLevel = cursor.getInt(cursor.getColumnIndexOrThrow("minLevel"));
                int maxLevel = cursor.getInt(cursor.getColumnIndexOrThrow("maxLevel"));

                Route route = new Route(routeName, minLevel, maxLevel, this.getPokemon(routeName));
                return route;
            }
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
