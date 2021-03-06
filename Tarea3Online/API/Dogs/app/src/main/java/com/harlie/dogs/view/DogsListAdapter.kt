package com.harlie.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.ajalt.timberkt.Timber
import com.harlie.dogs.R
import com.harlie.dogs.databinding.ItemDogsBinding
import com.harlie.dogs.model.DogBreed
import com.harlie.dogs.util.DogClickedEvent
import com.harlie.dogs.util.NavigationErrorEvent
import kotlinx.android.synthetic.main.item_dogs.view.*

class DogsListAdapter(private val dogsList: ArrayList<DogBreed>)
    : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(),
      DogClickListener
{
    private val _tag = "LEE: <" + DogsListAdapter::class.java.simpleName + ">"

    fun updateDogList(newDogList: List<DogBreed>) {
        Timber.tag(_tag).d("updateDogList")
        dogsList.clear()
        dogsList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        //Timber.tag(_tag).d("onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogsBinding>(inflater, R.layout.item_dogs, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        //Timber.tag(_tag).d("onBindViewHolder")
        holder.view.dog = dogsList[position]
        holder.view.listener = this
    }

    class DogViewHolder(var view: ItemDogsBinding): RecyclerView.ViewHolder(view.root)

    override fun onDogClicked(view: View) {
        val breedId = view.dogId.text.toString()
        val recyclerView = view.parent as RecyclerView
        val position = recyclerView.getChildAdapterPosition(view)
        Timber.tag(_tag).d("-CLICK- breedId=${breedId} position=${position}")
        try {
            val dogClickedEvent = DogClickedEvent(view.dogName.text.toString(), position)
            dogClickedEvent.post()
            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
            action.dogBreedId = breedId
            view.findNavController().navigate(action)
        }
        catch (e: IllegalStateException) {
            Timber.tag(_tag).e("PROBLEM WITH NAVIGATION e=${e}")
            val navigationErrorEvent = NavigationErrorEvent("Navigation failed: $e")
            navigationErrorEvent.post()
        }
    }

}
