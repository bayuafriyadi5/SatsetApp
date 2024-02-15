package com.example.satset.view.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.satset.databinding.FragmentProfileBinding
import com.example.satset.view.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val profileViewModel: ProfileViewModel by viewModels()
    private var token: String = "null"
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.checkIfTokenAvailable().observe(viewLifecycleOwner) { token ->
            this.token = token ?: "null"

            // Fetch the profile data once the token is available
            if (token != "null") {
                viewLifecycleOwner.lifecycleScope.launch {
                    profileViewModel.getProfile(token!!).observe(viewLifecycleOwner) { profile ->
                        if (profile != null) {
                            // Update UI with profile data
                            binding.tvProfileName.text = profile.name
                        } else {
                            // Handle null profile
                            Toast.makeText(
                                requireContext(),
                                "Profile not available",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.cardPin.setOnClickListener{
            Toast.makeText(requireContext(), "Belum tersedia sekarang", Toast.LENGTH_SHORT).show()
        }
        binding.cardsyarat.setOnClickListener{
            Toast.makeText(requireContext(), "Belum tersedia sekarang", Toast.LENGTH_SHORT).show()
        }
        binding.cardFaq.setOnClickListener{
            Toast.makeText(requireContext(), "Belum tersedia sekarang", Toast.LENGTH_SHORT).show()
        }
        binding.cardKeluar.setOnClickListener{
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Logout!")
                setMessage("Are you sure want to logout?")
                setPositiveButton("Yes") { _, _ ->
                    profileViewModel.logout()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                setNegativeButton("no"){ dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }

            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}