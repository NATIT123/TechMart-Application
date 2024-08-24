package com.example.tech_mart_application.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tech_mart_application.R
import com.example.tech_mart_application.activities.HistoryProductActivity
import com.example.tech_mart_application.activities.OffersActivity
import com.example.tech_mart_application.activities.ProfileActivity
import com.example.tech_mart_application.databinding.FragmentProfileBinding
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_FULL_NAME
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_IMAGE
import com.example.tech_mart_application.utils.PreferenceManager


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var preferenceManager: PreferenceManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManager = PreferenceManager(requireContext())
        preferenceManager.instance()

        binding.layoutProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {

            val dialog = AlertDialog.Builder(requireActivity())
            dialog.apply {
                setTitle("Confirm Logout")
                setMessage("Are you sure you want to logout?")
                setCancelable(false)
                setPositiveButton("Yes") { _, _ ->
                    preferenceManager.clear()
                    requireActivity().finishAffinity()
                }
                setNegativeButton("No") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                create()
                show()
            }
        }


        //History Purchase
        binding.btnHistory.setOnClickListener {
            val intent = Intent(requireActivity(), HistoryProductActivity::class.java)
            startActivity(intent)
        }

        //Offers
        binding.btnOffers.setOnClickListener {
            val intent = Intent(requireActivity(), OffersActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun loadData() {
        binding.tvEmail.text = preferenceManager.getString(KEY_USER_FULL_NAME)
        val bytes = Base64.decode(preferenceManager.getString(KEY_USER_IMAGE), Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        Glide.with(requireContext()).load(bitmap)
            .into(binding.avatar)
    }


}