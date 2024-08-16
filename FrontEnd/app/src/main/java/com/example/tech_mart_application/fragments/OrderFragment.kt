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
import com.example.tech_mart_application.adapters.OrderViewAdapter
import com.example.tech_mart_application.databinding.FragmentOrderBinding
import com.example.tech_mart_application.models.Order
import com.example.tech_mart_application.viewModel.ProductViewModel

class OrderFragment : Fragment() {


    private lateinit var binding: FragmentOrderBinding
    private lateinit var productViewModel: ProductViewModel

    private lateinit var mOrderViewAdapter: OrderViewAdapter
    private var listOrder = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = (activity as MainActivity).viewModel


        //Order
        productViewModel.getDataOrder()
        loadOrder()



    }

    private fun loadOrder() {
        binding.isLoading = true
        productViewModel.observerOrder().observe(viewLifecycleOwner) {
            listOrder = it
            binding.isEmpty = listOrder.isEmpty()
            if(listOrder.isNotEmpty()){
                mOrderViewAdapter = OrderViewAdapter(listOrder)
                binding.rcvOrder.apply {
                    adapter = mOrderViewAdapter
                    layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(
                        DividerItemDecoration(
                            requireActivity(),
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }
            binding.isLoading = false
        }
    }

}