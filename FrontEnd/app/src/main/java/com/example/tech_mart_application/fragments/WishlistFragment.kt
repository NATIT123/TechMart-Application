package com.example.tech_mart_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.AllProductViewAdapter
import com.example.tech_mart_application.adapters.WishlistViewAdapter
import com.example.tech_mart_application.databinding.FragmentWishlistBinding
import com.example.tech_mart_application.models.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WishlistFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding
    private lateinit var mWishlistViewAdapter: WishlistViewAdapter
    private val listProduct = mutableListOf<Product>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadData()
        loadRecyclerView()


    }

    private fun loadData() {
        listProduct.add(Product())
        listProduct.add(Product())
        listProduct.add(Product())
        listProduct.add(Product())
    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.isEmpty = listProduct.isEmpty()
        if (listProduct.isNotEmpty()) {
            mWishlistViewAdapter = WishlistViewAdapter(listProduct)
            binding.rcvProduct.apply {
                adapter = mWishlistViewAdapter
                layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
        binding.isLoading = false
    }


}