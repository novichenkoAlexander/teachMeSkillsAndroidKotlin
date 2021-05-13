package io.techmeskills.an02onl_plannerapp.screen.login


import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentLoginBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import android.widget.Toast


class LoginScreenFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewBinding: FragmentLoginBinding by viewBinding()

    private val viewModel: LoginScreenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.apply {
            alpha = 0f
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorLiveData.observe(this.viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        viewModel.autoCompleteUserNameLiveData.observe(this.viewLifecycleOwner) { userNames ->
            val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(), R.layout.select_dialog_item, userNames
            )
            viewBinding.etUserName.setAdapter(arrayAdapter)
        }

        viewModel.loggedIn.observe(this.viewLifecycleOwner) { loggedIn ->
            if (loggedIn) {
                findNavController().navigateSafe(
                    LoginScreenFragmentDirections.toMainFragment()
                )
            } else {
                viewBinding.root.alpha = 1f
            }
        }

        viewBinding.btnLogIn.setOnClickListener {
            viewModel.login(viewBinding.etUserName.text.toString(), viewBinding.etPassword.text.toString())
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.root.setPadding(0, top, 0, 0)
        viewBinding.root.setPadding(0, top, 0, 0)
    }

}
