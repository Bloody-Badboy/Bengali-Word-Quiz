package dev.arpan.bengali.quiz.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.arpan.bengali.quiz.data.model.Word
import dev.arpan.bengali.quiz.databinding.ItemBookmarkedWordBinding

class BookmarkedWordsAdapter : PagingDataAdapter<Word, RecyclerView.ViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as WordViewHolder).bind(it)
        }
    }

    fun getWordAt(position: Int): Word? {
        return getItem(position)
    }

    class WordViewHolder(val binding: ItemBookmarkedWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            binding.tvEnWord.text = word.english
            binding.tvBnWord.text = word.bengali
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val binding = ItemBookmarkedWordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WordViewHolder(binding)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean =
                oldItem == newItem
        }
    }
}