package id.ac.binus.pokemon.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.listener.OnPokemonItemClickedListener;
import id.ac.binus.pokemon.model.items.Item;

public class ItemAdapter extends ArrayAdapter<Item>{
    private OnPokemonItemClickedListener buttonClickListener;
    private Context context;
    private int resource;

    public ItemAdapter(@NonNull Context context, int resource, Vector<Item> objects, OnPokemonItemClickedListener buttonClickListener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.buttonClickListener = buttonClickListener;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item selectedItem = getItem(position);

        Integer icon = selectedItem.getIcon();
        String name =selectedItem.getName();
        String desc =selectedItem.getDesc();
        Integer quantity = selectedItem.getQuantity();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        ImageView item_sprite = (ImageView) convertView.findViewById(R.id.item_sprite);
        TextView item_name = (TextView) convertView.findViewById(R.id.item_name);
        TextView item_quantity = (TextView) convertView.findViewById(R.id.item_quantity);
        TextView item_description = (TextView) convertView.findViewById(R.id.item_description);
        Button item_use_button = (Button) convertView.findViewById(R.id.item_use_button);

        item_sprite.setImageResource(icon);
        item_name.setText(name.toUpperCase());
        item_quantity.setText("Stock: " + quantity.toString());
        item_description.setText(desc);

        if(quantity <= 0){
            item_use_button.setVisibility(View.GONE);
        }

        item_use_button.setOnClickListener(event -> {
            buttonClickListener.onItemClickedEvent(getItem(position));
        });

        return convertView;
    }
}