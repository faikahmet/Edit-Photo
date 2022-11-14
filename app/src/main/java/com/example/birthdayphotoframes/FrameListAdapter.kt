package com.example.birthdayphotoframes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.birthdayphotoframes.databinding.ListFrameItemBinding
import kotlinx.android.synthetic.main.fragment_frame.view.*

class FrameListAdapter(val list:List<Int>,private val onItemClicked: (Int) -> Unit) :
    ListAdapter<Int, FrameListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListFrameItemBinding.inflate(
            LayoutInflater.from(parent.context)
        ))    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val photoPath = list[position]
        holder.bind(photoPath)
        holder.itemView.setOnClickListener{
            onItemClicked(position)
        }
    }


    class ItemViewHolder(private var binding: ListFrameItemBinding):
        RecyclerView.ViewHolder(binding.root) {


        fun bind(itemPath:Int){
            binding.listFrameImage.load(itemPath)
        }

    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }
    }





