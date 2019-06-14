package me.nathanp.magiccreatures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.nathanp.magiccreatures.R;

public class CardAdapter<T extends CardAdapter.CardInfo> extends RecyclerView.Adapter<CardAdapter<T>.CardHolder> {

    public interface onCardSelectedListener<O> {
        void onSelected(O thing);
    }

    public interface CardInfo {
        int getDrawableId();
        String getName();
        String getDescription();
    }

    private List<T> things = new ArrayList<>();
    private onCardSelectedListener<T> listener;

    public CardAdapter(onCardSelectedListener<T> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new CardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        T thing = things.get(position);
        holder.bind(thing);
    }

    @Override
    public int getItemCount() {
        return things.size();
    }

    public void setThings(List<T> things) {
        this.things = things;
        notifyDataSetChanged();
    }

    class CardHolder extends RecyclerView.ViewHolder {
        T myThing;
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewStats;

        CardHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(myThing);
                }
            });
            imageView = view.findViewById(R.id.card_item_image);
            textViewName = view.findViewById(R.id.card_item_name);
            textViewStats = view.findViewById(R.id.creature_item_stats);
        }

        void bind(T thing) {
            myThing = thing;
            itemView.setTag(thing);
            Glide.with(imageView.getContext())
                    .load(thing.getDrawableId())
                    .into(imageView);
            textViewName.setText(thing.getName());
            textViewStats.setText(thing.getDescription());
        }
    }
}
