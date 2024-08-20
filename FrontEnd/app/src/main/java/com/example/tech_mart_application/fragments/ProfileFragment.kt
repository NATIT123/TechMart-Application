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
import com.bumptech.glide.Glide
import com.example.tech_mart_application.R
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
        loadData()

        binding.layoutProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        }
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
        Log.d("MyApp", preferenceManager.getString(KEY_USER_IMAGE)!!)
        Glide.with(requireContext()).load(bitmap)
            .into(binding.avatar)
    }


}