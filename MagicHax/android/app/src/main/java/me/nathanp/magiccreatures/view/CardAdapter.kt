package me.nathanp.magiccreatures.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.nathanp.magiccreatures.databinding.CardItemBinding
import me.nathanp.magiccreatures.view.CardAdapter.CardInfo
import java.util.*

abstract class CardAdapter<T : CardInfo> : RecyclerView.Adapter<CardAdapter<T>.CardHolder>() {
    abstract fun onSelected(pos: Int, item: T)
    interface CardInfo {
        val drawableId: Int
        val name: String
        val description: String
        val isCheckable: Boolean
        var checked: Boolean
    }

    var things: List<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(inflater, parent, false)
        return CardHolder(binding)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(things[position])
    }

    override fun getItemCount() = things.size

    inner class CardHolder(var binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var thing: T

        init {
            binding.root.setOnClickListener {
                binding.checkBox.isChecked = !binding.checkBox.isChecked
                onSelected(adapterPosition, thing)
            }
        }

        fun bind(thing: T) {
            this.thing = thing
            binding.cardinfo = thing
            binding.executePendingBindings()
        }
    }
}