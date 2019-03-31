package me.nathanp.magiccreatures.model;

import android.util.Log;
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

public class CreatureAdapter extends RecyclerView.Adapter<CreatureAdapter.CreatureHolder> {
    private List<Creature> creatures = new ArrayList<>();

    @NonNull
    @Override
    public CreatureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.creature_item, parent, false);
        return new CreatureHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatureHolder holder, int position) {
        Creature currentCreature = creatures.get(position);
        Glide.with(holder.imageView.getContext())
            .load(currentCreature.getDrawableId())
            .into(holder.imageView);
        holder.textViewName.setText(currentCreature.getName());
        holder.textViewStats.setText(currentCreature.getStatSummary());
    }

    @Override
    public int getItemCount() {
        return creatures.size();
    }

    public void setCreatures(List<Creature> creatures) {
        Log.d("CreatureAdapter", "Setting creatures");
        this.creatures = creatures;
        notifyDataSetChanged();
    }

    public void setCreatures(Creature.Creatures creatures) {
        this.creatures.clear();
        this.creatures.add(creatures.c1);
        this.creatures.add(creatures.c2);
        this.creatures.add(creatures.c3);
        notifyDataSetChanged();
    }

    class CreatureHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewStats;

        public CreatureHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.creature_item_image);
            textViewName = view.findViewById(R.id.creature_item_name);
            textViewStats = view.findViewById(R.id.creature_item_stats);
        }
    }
}
