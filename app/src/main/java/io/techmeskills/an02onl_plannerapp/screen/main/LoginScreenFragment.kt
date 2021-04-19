package io.techmeskills.an02onl_plannerapp.screen.main


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import io.techmeskills.an02onl_plannerapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentLoginBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginScreenFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.btnLogIn.setVerticalMargin(marginBottom = bottom)
    }

    override val viewBinding: FragmentLoginBinding by viewBinding()

    private val viewModel: LoginScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnLogIn.setOnClickListener {
            findNavController().navigateSafe(LoginScreenFragmentDirections.toMainFragment())
        }
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
}
