/*
 * Copyright 2021 Arpan Sarkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.arpan.bengali.quiz.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.arpan.bengali.quiz.R
import dev.arpan.bengali.quiz.data.model.QuizWordItem
import dev.arpan.bengali.quiz.databinding.ItemAnswerBinding

class QuestionOptionsAdapter(private val onOptionClick: ((QuizWordItem.Option) -> Unit)? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var correctAnswerPosition = -1
    private var selectedPosition = -1
    private var showAnswer = false

    var data: List<QuizWordItem.Option> = emptyList()
        set(value) {
            field = value
            selectedPosition = -1
            showAnswer = false
            value.forEachIndexed { index, option ->
                if (option.correctOption) {
                    correctAnswerPosition = index
                }
            }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SingleChoiceItemViewHolder(
            ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position].let {
            with(holder as SingleChoiceItemViewHolder) {
                bind(it, onOptionClick)
            }
        }
    }

    inner class SingleChoiceItemViewHolder(private val binding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(option: QuizWordItem.Option, onOptionClick: ((QuizWordItem.Option) -> Unit)?) {
            binding.option = option
            if (showAnswer && (selectedPosition == absoluteAdapterPosition || correctAnswerPosition == absoluteAdapterPosition)) {
                if (option.correctOption) {
                    binding.textView2.setBackgroundResource(R.drawable.option_bg_correct)
                } else {
                    binding.textView2.setBackgroundResource(R.drawable.option_bg_wrong)
                }
            } else {
                binding.textView2.setBackgroundResource(R.drawable.option_bg_normal)
            }
            itemView.setOnClickListener {
                onOptionClick?.invoke(option)
                selectedPosition = absoluteAdapterPosition
                showAnswer = true
                data.forEach {
                    it.clickable = false
                }
                notifyDataSetChanged()
            }
        }
    }
}
