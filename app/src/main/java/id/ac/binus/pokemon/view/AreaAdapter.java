package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.AdventureController;
import id.ac.binus.pokemon.controller.OnRouteSwitchListener;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Route;

public class AreaAdapter extends ArrayAdapter<Route> {
    private OnRouteSwitchListener onRouteSwitchListener;
    private Context context;
    private int resource;

    public AreaAdapter(@NonNull Context context, int resource, Vector<Route> objects, OnRouteSwitchListener onRouteSwitchListener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.onRouteSwitchListener = onRouteSwitchListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getRouteName();
        Vector<String> pokemonList = getItem(position).getAreaPokemonList();
        Integer minLevel = getItem(position).getMinLevel();
        Integer maxLevel = getItem(position).getMaxLevel();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView routeNameTv = (TextView) convertView.findViewById(R.id.area_adapter_route_name);
        TextView routePokemonTv = (TextView) convertView.findViewById(R.id.area_adapter_route_pokemon_list);
        TextView routePokemonLevel = (TextView) convertView.findViewById(R.id.area_adapter_route_pokemon_level);

        routeNameTv.setText(name);
        for(int i=0; i<pokemonList.size(); i++){
            if(i < pokemonList.size() - 1){
                routePokemonTv.append(pokemonList.get(i) + ", ");
            }
            else{
                routePokemonTv.append(pokemonList.get(i));
            }
        }
        routePokemonLevel.setText("Lv. " + minLevel + " - " + maxLevel);

        Button areaSwitchBtn = (Button) convertView.findViewById(R.id.area_adapter_select_route_button);
        areaSwitchBtn.setOnClickListener(view -> {
            Route selectedRoute = getItem(position);
            AdventureController.setActiveRoute(selectedRoute);

            if(onRouteSwitchListener != null){
                onRouteSwitchListener.onRouteSwitched(selectedRoute);
            }
        });

        return convertView;
    }
}