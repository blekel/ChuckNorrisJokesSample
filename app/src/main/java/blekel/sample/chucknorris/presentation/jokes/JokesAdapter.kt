package blekel.sample.chucknorris.presentation.jokes

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import blekel.sample.chucknorris.R
import blekel.sample.chucknorris.databinding.ItemJokeBinding
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.ViewHolder>() {

    private var items = emptyList<JokeViewModel>()

    fun setItems(items: List<JokeViewModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemJokeBinding>(
            inflater, R.layout.item_joke, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.model = item
    }

    class ViewHolder(val binding: ItemJokeBinding) : RecyclerView.ViewHolder(binding.root)
}