package com.example.tech_mart_application.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tech_mart_application.R
import com.example.tech_mart_application.database.ProductDatabase
import com.example.tech_mart_application.models.Banner
import com.example.tech_mart_application.models.Category
import com.example.tech_mart_application.models.Product
import java.util.Date

class ProductViewModel(private val productDatabase: ProductDatabase) : ViewModel() {

    private var bannerLiveData = MutableLiveData<MutableList<Banner>>()
    private var listBanner = mutableListOf<Banner>()

    private var categoryLiveData = MutableLiveData<MutableList<Category>>()
    private var listCategory = mutableListOf<Category>()

    private var recommendLiveData = MutableLiveData<MutableList<Product>>()
    private var listRecommend = mutableListOf<Product>()


    fun getDataBanner() {
        listBanner.add(Banner("1", R.drawable.banner1))
        listBanner.add(Banner("2", R.drawable.banner2))
        listBanner.add(Banner("3", R.drawable.banner3))
        listBanner.add(Banner("4", R.drawable.banner4))
        listBanner.add(Banner("4", R.drawable.banner5))
        bannerLiveData.postValue(listBanner)
    }

    fun observerBanner(): MutableLiveData<MutableList<Banner>> {
        return bannerLiveData
    }

    fun getDataCategory() {
        val listProduct = mutableListOf<Product>()
        listProduct.add(
            Product(
                "1", "1", listOf(), "1", 1, 1.1, 1.1, "Apple"
            )
        )
        listCategory.add(
            Category("Test", "1", R.drawable.avatar, listProduct)
        )
        listCategory.add(Category("Test", "1", R.drawable.avatar, listProduct))
        listCategory.add(Category("Test", "1", R.drawable.avatar, listProduct))
        listCategory.add(Category("Test", "1", R.drawable.avatar, listProduct))
        listCategory.add(Category("Test", "1", R.drawable.avatar, listProduct))
        listCategory.add(Category("Test", "1", R.drawable.avatar, listProduct))
        categoryLiveData.postValue(listCategory)
    }

    fun observerCategory(): MutableLiveData<MutableList<Category>> {
        return categoryLiveData
    }

    fun getDataRecommend() {
        listRecommend.add(
            Product(
                "1",
                "Product1",
                listOf(),
                "Description1",
                R.drawable.avatar,
                1.1,
                1.1,
                "",
                Date(),
                "Apple"
            ),
        )
        listRecommend.add(
            Product(
                "1",
                "Product1",
                listOf(),
                "Description1",
                R.drawable.avatar,
                1.1,
                1.1,
                "",
                Date(),
                "Apple"
            ),
        )
        listRecommend.add(
            Product(
                "1",
                "Product1",
                listOf(),
                "Description1",
                R.drawable.avatar,
                1.1,
                1.1,
                "",
                Date(),
                "Apple"
            ),
        )

        listRecommend.add(
            Product(
                "1",
                "Product1",
                listOf(),
                "Description1",
                R.drawable.avatar,
                1.1,
                1.1,
                "",
                Date(),
                "Apple"
            ),
        )

        recommendLiveData.postValue(listRecommend)
    }

    fun observerRecommendProduct(): MutableLiveData<MutableList<Product>> {
        return recommendLiveData
    }


}