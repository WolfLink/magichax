package me.nathanp.magiccreatures.model;

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

    public interface OnCreatureSelected {
        void onSelected(Creature creature);
    }

    private List<Creature> creatures = new ArrayList<>();
    private OnCreatureSelected listener;

    public CreatureAdapter(OnCreatureSelected listener) {
        this.listener = listener;
    }

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
        holder.bind(currentCreature);
    }

    @Override
    public int getItemCount() {
        return creatures.size();
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
        notifyDataSetChanged();
    }

    class CreatureHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewStats;

        CreatureHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Creature creature = (Creature)v.getTag();
                    listener.onSelected(creature);
                }
            });
            imageView = view.findViewById(R.id.creature_item_image);
            textViewName = view.findViewById(R.id.creature_item_name);
            textViewStats = view.findViewById(R.id.creature_item_stats);
        }

        void bind(Creature creature) {
            itemView.setTag(creature);
            Glide.with(imageView.getContext())
                    .load(creature.getDrawableId())
                    .into(imageView);
            textViewName.setText(creature.getName());
            textViewStats.setText(creature.getStatSummary());
        }
    }
}
