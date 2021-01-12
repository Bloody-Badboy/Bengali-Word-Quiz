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

package dev.arpan.bengali.quiz.ui.theme

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.arpan.bengali.quiz.prefs.Theme

@AndroidEntryPoint
class ThemeSettingDialogFragment : DialogFragment() {

    private val viewModel: ThemeSettingDialogViewModel by viewModels()

    private lateinit var listAdapter: ArrayAdapter<ThemeHolder>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        listAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_single_choice
        )

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose Theme")
            .setSingleChoiceItems(listAdapter, 0) { dialog, position ->
                dialog.dismiss()
                listAdapter.getItem(position)?.theme?.let {
                    viewModel.setTheme(it)
                }
            }
            .create()
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listAdapter.clear()
        listAdapter.addAll(
            Theme.availableThemes.map { theme ->
                ThemeHolder(
                    theme,
                    getTitleForTheme(theme)
                )
            }
        )

        updateSelectedItem(viewModel.theme.value)

        // using viewLifecycleOwner throws java.lang.IllegalStateException: Can't access the
        // Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or
        // after onDestroyView()
        viewModel.theme.observe(this, Observer(::updateSelectedItem))
    }

    private fun updateSelectedItem(selected: Theme?) {
        val selectedPosition = (0 until listAdapter.count).indexOfFirst { index ->
            listAdapter.getItem(index)?.theme == selected
        }
        (dialog as AlertDialog).listView.setItemChecked(selectedPosition, true)
    }

    private fun getTitleForTheme(theme: Theme) = when (theme) {
        Theme.LIGHT -> "Light"
        Theme.DARK -> "Dark"
        Theme.SYSTEM -> "System default"
        Theme.BATTERY_SAVER -> "Set by Battery Saver"
    }

    private data class ThemeHolder(val theme: Theme, val title: String) {
        override fun toString(): String = title
    }
}
