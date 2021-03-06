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

package dev.arpan.bengali.quiz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import dev.arpan.bengali.quiz.R
import dev.arpan.bengali.quiz.databinding.FragmentHomeBinding
import dev.arpan.bengali.quiz.ui.NavigationDestinationFragment
import dev.arpan.bengali.quiz.ui.home.HomeFragmentDirections.Companion.toNavBookmarkedWords
import dev.arpan.bengali.quiz.ui.home.HomeFragmentDirections.Companion.toNavQuiz
import dev.arpan.bengali.quiz.ui.home.HomeFragmentDirections.Companion.toNavThemeDialog

class HomeFragment : NavigationDestinationFragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.toolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.home_menu_bookmarked_words -> {
                    findNavController().navigate(toNavBookmarkedWords())
                    true
                }
                R.id.action_theme -> {
                    findNavController().navigate(toNavThemeDialog())
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEnToBn.setOnClickListener { startWordQuiz(true) }
        binding.btnBnToEn.setOnClickListener { startWordQuiz(false) }
    }

    private fun startWordQuiz(englishToBengali: Boolean) {
        val numberOfWord = binding.chipWords
            .findViewById<Chip>(binding.chipWords.checkedChipId)
            .text.toString()
            .toIntOrNull()

        val numberOfOption = binding.chipOptions
            .findViewById<Chip>(binding.chipOptions.checkedChipId)
            .text.toString()
            .toIntOrNull()

        if (numberOfOption != null && numberOfWord != null) {
            findNavController().navigate(
                toNavQuiz(
                    englishToBengali,
                    numberOfWord,
                    numberOfOption
                )
            )
        }
    }
}
