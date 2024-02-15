package com.example.satset.view.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satset.databinding.FragmentHistoryBinding
import com.example.satset.view.adapter.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryViewModel by viewModels()
    private var token: String = "null"

    private lateinit var historyAdapter: HistoryAdapter

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

        binding.loading.visibility = View.VISIBLE

        val recyclerView = binding.rvHistory
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        historyViewModel.checkIfTokenAvailable().observe(viewLifecycleOwner) { token ->
            this.token = token ?: "null"

            if (token != "null") {
                viewLifecycleOwner.lifecycleScope.launch {
                    historyViewModel.getHistory(token!!).observe(viewLifecycleOwner) { it ->
                        it!!.data.let {
                            historyAdapter = HistoryAdapter(it!!)
                            recyclerView.adapter = historyAdapter
                            binding.loading.visibility = View.GONE
                        }
                    }

                }
            }
        }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}