package com.example.tech_mart_application.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.activities.NotificationActivity
import com.example.tech_mart_application.adapters.BannerViewAdapter
import com.example.tech_mart_application.adapters.CategoryViewAdapter
import com.example.tech_mart_application.adapters.RecommendationViewAdapter
import com.example.tech_mart_application.databinding.FragmentHomeBinding
import com.example.tech_mart_application.models.Banner
import com.example.tech_mart_application.models.Category
import com.example.tech_mart_application.models.Product
import com.example.tech_mart_application.viewModel.ProductViewModel


class HomeFragment : Fragment(), CategoryViewAdapter.mClickCategoryListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mBannerViewAdapter: BannerViewAdapter
    private var listBanner = mutableListOf<Banner>()
    private var listCategory = mutableListOf<Category>()
    private var listRecommend = mutableListOf<Product>()
    private lateinit var mRecommendViewAdapter: RecommendationViewAdapter
    private lateinit var mCategoryViewAdapter: CategoryViewAdapter
    private lateinit var productViewModel: ProductViewModel
    private var titleCategory = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = (activity as MainActivity).viewModel


        //Banner
        productViewModel.getDataBanner()
        loadBanner()

        //Category
        productViewModel.getDataCategory()
        loadCategory()

        //Recommend
        productViewModel.getDataRecommend()
        loadRecommend()


        //Notification
        binding.imgNotification.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }


    }

    private fun loadBanner() {
        binding.isLoadingBanner = true
        productViewModel.observerBanner().observe(viewLifecycleOwner) { it ->
            listBanner = it
            mBannerViewAdapter = BannerViewAdapter(listBanner)
            binding.containerBanner.apply {
                adapter = mBannerViewAdapter
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
            binding.circleIndicator.setViewPager(binding.containerBanner)
            mBannerViewAdapter.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)
            binding.isLoadingBanner = false
        }
    }

    private fun loadCategory() {
        binding.isLoadingOfficial = true
        productViewModel.observerCategory().observe(viewLifecycleOwner) {
            listCategory = it
            mCategoryViewAdapter = CategoryViewAdapter(listCategory,this)
            binding.rcvOfficialCategory.apply {
                adapter = mCategoryViewAdapter
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                binding.isLoadingOfficial = false
            }

        }
    }

    private fun loadRecommend() {
        binding.isLoadingRecommendation = true
        productViewModel.observerRecommendProduct().observe(viewLifecycleOwner) {
            listRecommend = it
            mRecommendViewAdapter = RecommendationViewAdapter(listRecommend)
            binding.rcvRecommendation.apply {
                adapter = mRecommendViewAdapter
                layoutManager =
                    GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
                binding.isLoadingRecommendation = false
            }
        }
    }

    override fun onClick(category: Category) {
        Log.d("MyApp", category.title)
    }
}

