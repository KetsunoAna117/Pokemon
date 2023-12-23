package id.ac.binus.pokemon.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Pokemon;

public class NewRoutePokemonAdapter extends ArrayAdapter<Pokemon> {
    private Context context;
    private int resource;

    public NewRoutePokemonAdapter(@NonNull Context context, int resource, ArrayList<Pokemon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pokemon currentPokemon = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        String sprite = currentPokemon.getSprites().getFrontSprite();
        String name = currentPokemon.getName();
        String type = currentPokemon.getTypes().get(0).getTypeName().getName();

        ImageView new_route_pokemon_image_view = (ImageView) convertView.findViewById(R.id.new_route_pokemon_image_view);
        TextView new_route_pokemon_name = (TextView) convertView.findViewById(R.id.new_route_pokemon_name);
        TextView new_route_pokemon_type = (TextView) convertView.findViewById(R.id.new_route_pokemon_type);

        Picasso.get().load(sprite).into(new_route_pokemon_image_view);
        new_route_pokemon_name.setText(name.toUpperCase());
        new_route_pokemon_type.setText(type);

        return convertView;
    }
}