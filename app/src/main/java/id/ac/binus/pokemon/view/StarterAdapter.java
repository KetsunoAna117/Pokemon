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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.OnRouteSwitchListener;
import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Route;

public class StarterAdapter extends ArrayAdapter<Pokemon> {
    private Context context;
    private int resource;
    public StarterAdapter(@NonNull Context context, int resource, ArrayList<Pokemon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sprite = getItem(position).getSprites().getFrontSprite();
        Integer level = getItem(position).getLevel();
        String name = getItem(position).getName();
        String type = getItem(position).getTypes().get(0).getTypeName().getName();
        Integer atkStats = getItem(position).getAttackStats();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        ImageView pokemonSprite = (ImageView) convertView.findViewById(R.id.starter_sprite);
        TextView pokemonLvl = (TextView) convertView.findViewById(R.id.starter_level);
        TextView pokemonName = (TextView) convertView.findViewById(R.id.starter_name);
        TextView pokemonType = (TextView) convertView.findViewById(R.id.starter_type);
        TextView pokemonAttackStats = (TextView) convertView.findViewById(R.id.starter_attack);

        Picasso.get().load(sprite).into(pokemonSprite);
        pokemonLvl.setText("Lv." + level);
        pokemonName.setText(name.toUpperCase(Locale.ROOT));
        pokemonType.setText(type.toUpperCase(Locale.ROOT));
        pokemonAttackStats.setText(atkStats + " ATK");

        return convertView;

    }
}