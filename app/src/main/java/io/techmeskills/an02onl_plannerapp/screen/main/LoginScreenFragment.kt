package io.techmeskills.an02onl_plannerapp.screen.main


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import io.techmeskills.an02onl_plannerapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentLoginBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import io.techmeskills.an02onl_plannerapp.support.setVerticalMargin
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

class LoginScreenFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewBinding: FragmentLoginBinding by viewBinding()

    private val viewModel: LoginScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorLiveData.observe(this.viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()

        }

        viewBinding.btnLogIn.setOnClickListener {
            viewModel.login(viewBinding.etUserName.text.toString(), viewBinding.etUserPass.text.toString())
            findNavController().navigateSafe(LoginScreenFragmentDirections.toMainFragment())
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.root.setPadding(0, top, 0, 0)
        viewBinding.root.setPadding(0, top, 0, 0)
    }
}
