package io.techmeskills.an02onl_plannerapp.screen.main


import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.*
import androidx.navigation.fragment.findNavController
import io.techmeskills.an02onl_plannerapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentLoginBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel
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

        viewModel.loggedIn.observe(this.viewLifecycleOwner) { loggedIn ->
            if (loggedIn) {
                findNavController().navigateSafe(
                    LoginScreenFragmentDirections.toMainFragment(
                        viewBinding.etUserName.text.toString()
                    )
                )
            } else {
                viewBinding.root.alpha = 1f
            }
        }

        viewBinding.btnPasswordVisibility.setOnClickListener {

            if (viewBinding.btnPasswordVisibility.text.equals("show")) {
                viewBinding.etUserPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                viewBinding.btnPasswordVisibility.text = HIDE
            } else {
                viewBinding.etUserPass.transformationMethod = PasswordTransformationMethod.getInstance()
                viewBinding.btnPasswordVisibility.text = SHOW
            }
        }

        viewBinding.btnLogIn.setOnClickListener {
            viewModel.login(viewBinding.etUserName.text.toString(), viewBinding.etUserPass.text.toString())
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.root.setPadding(0, top, 0, 0)
        viewBinding.root.setPadding(0, top, 0, 0)
    }

    companion object {
        private const val HIDE = "hide"
        private const val SHOW = "show"
    }
}
