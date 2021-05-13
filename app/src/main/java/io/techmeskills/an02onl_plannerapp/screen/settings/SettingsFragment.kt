package io.techmeskills.an02onl_plannerapp.screen.settings

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentSettingsBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : NavigationFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setVerticalMargin(marginTop = top)
    }

    override val viewBinding: FragmentSettingsBinding by viewBinding()

    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tvLogOut.setOnClickListener {
            viewModel.logOut()
            findNavController().navigateSafe(SettingsFragmentDirections.toLoginScreenFragment())
        }

        viewBinding.btnDeleteUser.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_request_title)
            .setMessage(R.string.delete_request_message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.deleteUser()
                dialog.cancel()
                findNavController().navigateSafe(SettingsFragmentDirections.toLoginScreenFragment())
            }.setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }.show()
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
}