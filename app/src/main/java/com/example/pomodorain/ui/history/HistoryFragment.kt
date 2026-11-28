package com.example.pomodorain.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pomodorain.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() = _binding ?: throw IllegalStateException("FragmentHistoryBinding for HistoryFragment must not be null")

    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var adapter: SessionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTestButton()
        observeState()
    }

    private fun setupRecyclerView() {
        adapter = SessionAdapter()

        binding.rvSessions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryFragment.adapter
        }
    }

    // TODO: убрать после добавления сохранения сессий
    private fun setupTestButton() {
        binding.btnTestButton.setOnClickListener {
            viewModel.addTestSession()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    render(it)
                }
            }
        }
    }

    private fun render(state: HistoryUiState) {
        when (state) {
            is HistoryUiState.Loading,
            is HistoryUiState.Empty,
            is HistoryUiState.Error -> {
                binding.llEmptyHistory.visibility = View.VISIBLE
            }
            is HistoryUiState.Success -> {
                binding.llEmptyHistory.visibility = View.GONE
                adapter.submitList(state.sessions)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}