package com.example.satset.view.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satset.databinding.FragmentHomeBinding
import com.example.satset.view.adapter.AdsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
@ExperimentalPagingApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private var token: String = "null"
    private lateinit var adsAdapter: AdsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loading.visibility = View.VISIBLE

        setUpRecyclerView()


        homeViewModel.checkIfTokenAvailable().observe(viewLifecycleOwner) { token ->
            this.token = token ?: "null"

            // Fetch the profile data once the token is available
            if (token != "null") {
                loadHomeData()
                binding.loading.visibility = View.GONE
            }
        }

        binding.btnInputCode.setOnClickListener {
            val code = binding.codeEditText.text.toString()
            if (code.isNotEmpty()) {
                joinQueue(code)
            } else {
                // Show a message to the user indicating that the code is empty
                Toast.makeText(requireContext(), "Please enter a code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when the fragment resumes
        if (token != "null") {
            loadHomeData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.rvPromo
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun loadHomeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.getProfile(token!!).observe(viewLifecycleOwner) { profile ->
                if (profile != null) {
                    // Update UI with profile data
                    binding.tvname.text = "Halo " + profile.name
                } else {
                    // Handle null profile
                    Toast.makeText(requireContext(), "Profile not available", Toast.LENGTH_SHORT).show()
                }
            }

            homeViewModel.getQueue(token!!).observe(viewLifecycleOwner) { queue ->
                if (queue != null && queue.data != null) {
                    // Update UI with profile data
                    binding.tvNamaEvent.text = queue.data.events?.eventName ?: "Event Name Not Available"
                    binding.tvNomorAntrian.text = queue.data.queueNumber ?: "Queue Number Not Available"
                } else {
                    Toast.makeText(requireContext(), "Queue not available", Toast.LENGTH_SHORT).show()
                }
            }

            homeViewModel.getAds(token!!).observe(viewLifecycleOwner) { adsResponse ->
                adsResponse?.data?.let { adsList ->
                    // Set up RecyclerView with ads data
                    adsAdapter = AdsAdapter(adsList)
                    binding.rvPromo.adapter = adsAdapter
                }
            }
        }
    }

    private fun joinQueue(code: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.joinQueue(token!!, code).observe(viewLifecycleOwner) { result ->
                if (result.isSuccess) {
                    Toast.makeText(requireContext(), "Joined queue successfully", Toast.LENGTH_SHORT).show()
                    // Reload home data after successfully joining the queue
                    loadHomeData()
                } else {
                    Toast.makeText(requireContext(), "Failed to join queue: Please enter a valid code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


