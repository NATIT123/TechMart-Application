package com.example.tech_mart_application.viewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tech_mart_application.R
import com.example.tech_mart_application.database.ProductDatabase
import com.example.tech_mart_application.models.Banner
import com.example.tech_mart_application.models.Category
import com.example.tech_mart_application.models.Notification
import com.example.tech_mart_application.models.Order
import com.example.tech_mart_application.models.Product
import com.example.tech_mart_application.utils.Constants.Companion.LIST_PRODUCT
import com.example.tech_mart_application.utils.PreferenceManager
import java.util.Date

class ProductViewModel @SuppressLint("StaticFieldLeak") constructor(
    private val productDatabase: ProductDatabase,
    @SuppressLint("StaticFieldLeak") private val context: Context
) : ViewModel() {

    private var bannerLiveData = MutableLiveData<MutableList<Banner>>()
    private var listBanner = mutableListOf<Banner>()

    private var categoryLiveData = MutableLiveData<MutableList<Category>>()
    private var listCategory = mutableListOf<Category>()

    private var recommendLiveData = MutableLiveData<MutableList<Product>>()
    private var listRecommend = mutableListOf<Product>()


    private var orderLiveData = MutableLiveData<MutableList<Order>>()
    private var listOrder = mutableListOf<Order>()

    private var cartLiveData = MutableLiveData<MutableList<Product>>()
    private var listProduct = listOf<Product>()


    private lateinit var preferenceManager: PreferenceManager

    private var notificationLiveData = MutableLiveData<MutableList<Notification>>()
    private var listNotification = mutableListOf<Notification>()

    fun getDataBanner() {
        listBanner.clear()
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
        listCategory.clear()
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
        listRecommend.clear()
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


    //Order
    fun getDataOrder() {
        listOrder.clear()
        listOrder.add(Order(category = Category("Test", "1", 1, null)))
        listOrder.add(Order(category = Category("Test", "1", 1, null)))
        listOrder.add(Order(category = Category("Test", "1", 1, null)))
        listOrder.add(Order(category = Category("Test", "1", 1, null)))
        orderLiveData.postValue(listOrder)
    }

    fun observerOrder(): MutableLiveData<MutableList<Order>> {
        return orderLiveData
    }

    //Cart
    fun getDataCart() {
        listProduct = listOf()
        preferenceManager = PreferenceManager(context)
        preferenceManager.instance()
        listProduct = preferenceManager.getList(LIST_PRODUCT)
        cartLiveData.postValue(listProduct.toMutableList())
    }

    fun observerCart(): MutableLiveData<MutableList<Product>> {
        return cartLiveData
    }





}