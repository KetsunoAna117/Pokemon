package id.ac.binus.pokemon.view.newRouteActivityFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.RouteController;
import id.ac.binus.pokemon.controller.SQLiteHelper;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Route;
import id.ac.binus.pokemon.utils.Helper;
import id.ac.binus.pokemon.view.MainActivity;
import id.ac.binus.pokemon.view.adapter.NewRoutePokemonAdapter;


public class NewRouteInformationFragment extends Fragment {
    private ArrayList<Pokemon> pokemonFound;

    public NewRouteInformationFragment(ArrayList<Pokemon> pokemonFound) {
        this.pokemonFound = pokemonFound;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_route_information, container, false);

        TextView new_route_level = view.findViewById(R.id.new_route_level);
        EditText new_route_input_route_name = view.findViewById(R.id.new_route_input_route_name);
        MaterialButton new_route_submit_button = view.findViewById(R.id.new_route_submit_button);
        TextView new_route_status_msg = view.findViewById(R.id.new_route_status_msg);
        ListView new_route_pokemon_list = view.findViewById(R.id.new_route_pokemon_list);

        int minLevel = Helper.getRandomNumber(0, 99);
        int maxLevel = Helper.getRandomNumber(minLevel, 100);
        new_route_level.setText(String.format("Lv. %d - %d", minLevel, maxLevel));

        new_route_pokemon_list.setAdapter(new NewRoutePokemonAdapter(getActivity(), R.layout.activity_new_route_pokemon_adapter, pokemonFound));

        new_route_submit_button.setOnClickListener(event -> {
            String routeName = new_route_input_route_name.getText().toString();
            String insertFailed = RouteController.insertNewRoute(getActivity(), routeName, minLevel, maxLevel, pokemonFound);

            if(insertFailed.equals("")){
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
            else{
                new_route_status_msg.setText(insertFailed);
            }


        });

        return view;
    }
}