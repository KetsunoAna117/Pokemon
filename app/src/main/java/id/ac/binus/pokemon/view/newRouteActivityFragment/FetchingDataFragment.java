package id.ac.binus.pokemon.view.newRouteActivityFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import id.ac.binus.pokemon.R;

public class FetchingDataFragment extends Fragment {
    private int counter;

    public FetchingDataFragment(int counter) {
        this.counter = counter;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fetching_data, container, false);
        ProgressBar progressBar = view.findViewById(R.id.fragment_fetching_data_progress);
        progressBar.setMax(5);
        progressBar.setProgress(counter, true);

        return view;
    }
}