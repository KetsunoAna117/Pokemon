package id.ac.binus.pokemon.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.Locale;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.OnPokemonButtonSwitchListener;
import id.ac.binus.pokemon.controller.TrainerController;
import id.ac.binus.pokemon.model.Pokemon;

public class MyPokemonAdapter extends ArrayAdapter<Pokemon> {
    private OnPokemonButtonSwitchListener buttonClickListener;

    private Context context;
    private int resource;

    public MyPokemonAdapter(@NonNull Context context, int resource, LinkedList<Pokemon> objects, OnPokemonButtonSwitchListener buttonClickListener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sprite = getItem(position).getSprites().getFrontSprite();
        Integer level = getItem(position).getLevel();
        String name = getItem(position).getName();
        String type = getItem(position).getTypes().get(0).getTypeName().getName();
        Integer hp = getItem(position).getHp();
        Integer maxHp = getItem(position).getMaxHp();
        Integer atkStats = getItem(position).getAttackStats();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        ImageView pokemonSprite = (ImageView) convertView.findViewById(R.id.mypokemon_sprite);
        TextView pokemonLvl = (TextView) convertView.findViewById(R.id.mypokemon_level);
        TextView pokemonName = (TextView) convertView.findViewById(R.id.mypokemon_name);
        TextView pokemonType = (TextView) convertView.findViewById(R.id.mypokemon_type);
        TextView pokemonHp = (TextView) convertView.findViewById(R.id.mypokemon_hp);
        ProgressBar pokemonHpBar = (ProgressBar) convertView.findViewById(R.id.mypokemon_hp_hpbar);
        pokemonHpBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        TextView pokemonAttackStats = (TextView) convertView.findViewById(R.id.mypokemon_attack);
        pokemonAttackStats.setTextColor(Color.RED);
        Button makeActivePokemonBtn = (Button) convertView.findViewById(R.id.mypokemon_switchToActive_btn);
        Button releasePokemonBtn = (Button) convertView.findViewById(R.id.mypokemon_release_btn);

        Picasso.get().load(sprite).into(pokemonSprite);
        pokemonLvl.setText("Lv." + level);
        pokemonName.setText(name.toUpperCase(Locale.ROOT));
        pokemonType.setText(type.toUpperCase(Locale.ROOT));
        pokemonHp.setText("HP: " + hp + " / " + maxHp);
        pokemonHpBar.setMax(maxHp);
        pokemonHpBar.setProgress(hp);
        pokemonAttackStats.setText(atkStats + " ATK");

        makeActivePokemonBtn.setOnClickListener(view -> {
            Pokemon selectedPokemon = getItem(position);

            if(buttonClickListener != null){
                buttonClickListener.onPokemonSwitchButtonClick(selectedPokemon);
            }
        });

        releasePokemonBtn.setOnClickListener(event -> {
            Pokemon selectedPokemon = getItem(position);

            if(buttonClickListener != null){
                buttonClickListener.onPokemonReleaseButtonClick(selectedPokemon);
            }
        });

        return convertView;
    }
}