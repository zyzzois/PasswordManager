package com.example.detail.presentation

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.detail.R
import com.example.detail.databinding.FragmentDetailBinding
import com.example.detail.di.DaggerDetailsComponent
import com.example.detail.di.DetailsDepsProvider
import com.example.detail.utils.BiometricAuthListener
import com.example.detail.utils.BiometricUtil

class DetailFragment : Fragment(), BiometricAuthListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding ?: throw RuntimeException("error")

    private val component = DaggerDetailsComponent
        .builder()
        .dependencies(DetailsDepsProvider.dependencies)
        .build()

    private val viewModel by lazy {
        component.getDetailsViewModel()
    }

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragmentMode()
        setupUi()
    }

    private fun setupFragmentMode() {
        if (args.url == null) {
            viewModel.isEditMode.value = false
        } else {
            viewModel.isEditMode.value = true
            viewModel.parseArgFromStringToModel(args.url!!)
        }
    }

    // туду: сделать нормальное ветвление
    private fun setupUi() {
        viewModel.password.observe(viewLifecycleOwner) {
            binding.tilUrl.editText?.setText(it.websiteUrl)
            binding.tilPassword.editText?.setText(it.password)
            binding.tilTitle.editText?.setText(it.title)
        }
        viewModel.isEditMode.observe(viewLifecycleOwner) { edit ->
            if (edit) {
                binding.editUrl.isFocusable = false
                binding.editTitle.isFocusable = false
                binding.editPassword.isFocusable = false
                binding.buttonSavePassword.visibility = View.GONE
                binding.tilPassword.isPasswordVisibilityToggleEnabled = true
                binding.tilPassword.setEndIconOnClickListener {
                    showBiometricPermission()
                }
            } else {
                addTextChangeListeners()
                observeViewModel()
                binding.buttonSavePassword.setOnClickListener {
                    viewModel.addPassword(
                        inputPassword =  binding.editPassword.text?.toString(),
                        inputUrl = binding.editUrl.text?.toString(),
                        inputTitle = binding.editTitle.text?.toString()
                    )
                }
            }
        }

    }

    private fun showBiometricPermission() {
        BiometricUtil.showBiometricPrompt(
            activity = requireActivity(),
            listener = this,
            title = requireContext().getString(R.string.biometrics_title),
            description = requireContext().getString(R.string.biometrics_description)
        )
    }

    override fun onBiometricAuthSuccess(result: BiometricPrompt.AuthenticationResult) {
        Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show()
        binding.editPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    }

    override fun onBiometricAuthError(errorCode: Int, errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    // туду: убрать хардкод строки
    private fun observeViewModel() = with(viewModel) {
        errorInputUrl.observe(viewLifecycleOwner) { error ->
            val message = if (error) "Некорректная ссылка" else null
            binding.tilUrl.error = message
        }
        errorInputPassword.observe(viewLifecycleOwner) { error ->
            val message = if (error) "Некорректный пароль" else null
            binding.tilPassword.error = message
        }
        errorInputTitle.observe(viewLifecycleOwner) { error ->
            val message = if (error) "Тайтл не может быть пустым" else null
            binding.tilTitle.error = message
        }
        shouldCloseScreen.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun addTextChangeListeners() {
        binding.editPassword.doOnTextChanged { _, _, _, _ -> viewModel.resetErrorInputPassword() }
        binding.editUrl.doOnTextChanged { _, _, _, _ -> viewModel.resetErrorInputUrl() }
        binding.editTitle.doOnTextChanged { _, _, _, _ -> viewModel.resetErrorInputTitle() }
    }
}