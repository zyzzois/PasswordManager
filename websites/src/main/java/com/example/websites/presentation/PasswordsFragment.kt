package com.example.websites.presentation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.websites.databinding.FragmentPasswordsBinding
import com.example.websites.di.DaggerUrlsScreenComponent
import com.example.websites.di.UrlsScreenDepsProvider
import com.example.websites.presentation.recycler.WebsitesListAdapter

class PasswordsFragment : Fragment() {
    private var _binding: FragmentPasswordsBinding? = null
    private val binding: FragmentPasswordsBinding
        get() = _binding ?: throw RuntimeException("error")

    private val listAdapter by lazy {
        WebsitesListAdapter()
    }

    private val component = DaggerUrlsScreenComponent
        .builder()
        .dependencies(UrlsScreenDepsProvider.dependencies)
        .build()

    private val viewModel by lazy {
        component.getUrlsViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateList()
    }

    private fun observeViewModel() {
        viewModel.websites.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    private fun setupUi() {
        setupRecyclerView()
        setupClicks()
    }

    private fun setupClicks() {
        binding.floatingActionButton.setOnClickListener {
            val dummyArg: String? = null
            val uri = Uri.parse("passwordManager://detail?url=$dummyArg")
            findNavController().navigate(uri, navOptions, null)
        }
        listAdapter.onItemClickListener = {
            val uri = Uri.parse("passwordManager://detail?url=${it.websiteUrl}")
            findNavController().navigate(uri, navOptions, null)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = listAdapter
    }
}