package com.example.tech_mart_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.CartViewAdapter
import com.example.tech_mart_application.databinding.FragmentCartBinding
import com.example.tech_mart_application.models.Product
import com.example.tech_mart_application.viewModel.ProductViewModel


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var mCartViewBinding: CartViewAdapter
    private lateinit var productViewModel: ProductViewModel
    private var listProduct = mutableListOf<Product>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel = (activity as MainActivity).viewModel

        //Load Cart
        productViewModel.getDataCart()
        loadCart()

    }


    private fun loadCart() {
        binding.isLoading = true
        productViewModel.observerCart().observe(viewLifecycleOwner) {
            this.listProduct = it
            binding.isEmpty = this.listProduct.isEmpty()
            if (listProduct.isNotEmpty()) {
                mCartViewBinding = CartViewAdapter(listProduct)
                binding.rcvCart.apply {
                    adapter = mCartViewBinding
                    layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }
            binding.isLoading = false
        }

    }
}