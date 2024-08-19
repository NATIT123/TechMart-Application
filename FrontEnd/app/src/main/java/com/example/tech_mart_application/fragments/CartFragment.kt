package com.example.tech_mart_application.fragments

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.adapters.CartViewAdapter
import com.example.tech_mart_application.databinding.FragmentCartBinding
import com.example.tech_mart_application.models.Product
import com.example.tech_mart_application.viewModel.ProductViewModel
import org.json.JSONException
import org.json.JSONObject
import vn.momo.momo_partner.AppMoMoLib


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var mCartViewBinding: CartViewAdapter
    private lateinit var productViewModel: ProductViewModel
    private var listProduct = mutableListOf<Product>()

    private var amount = "10000"
    private val fee = "0"
    var environment = 0 //developer default

    private val merchantName = "Demo SDK"
    private val merchantCode = "SCB01"
    private val merchantNameLabel = "Nhà cung cấp"
    private val description = "Thanh toán dịch vụ ABC"


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

        AppMoMoLib.getInstance()
            .setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION

        //Load Cart
        productViewModel.getDataCart()
        loadCart()

        //Purchase
        binding.buttonMoMo.setOnClickListener {
            handleMoMo()
        }

    }

    private fun handleMoMo() {

    }

    //Get token through MoMo app
    private fun requestPayment(idOrder: String) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT)
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN)
        val eventValue: MutableMap<String, Any> = HashMap()
        //client Required
        eventValue["merchantname"] =
            merchantName //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue["merchantcode"] =
            merchantCode //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue["amount"] = 10000 //Kiểu integer
        eventValue["orderId"] =
            idOrder //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue["orderLabel"] = idOrder //gán nhãn

        //client Optional - bill info
        eventValue["merchantnamelabel"] = "Dịch vụ" //gán nhãn
        eventValue["fee"] = 0 //Kiểu integer
        eventValue["description"] = description //mô tả đơn hàng - short description

        //client extra data
        eventValue["requestId"] = merchantCode + "merchant_billId_" + System.currentTimeMillis()
        eventValue["partnerCode"] = merchantCode
        //Example extra data
        val objExtraData = JSONObject()
        try {
            objExtraData.put("site_code", "008")
            objExtraData.put("site_name", "CGV Cresent Mall")
            objExtraData.put("screen_code", 0)
            objExtraData.put("screen_name", "Special")
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3")
            objExtraData.put("movie_format", "2D")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        eventValue["extraData"] = objExtraData.toString()
        eventValue["extra"] = ""
        AppMoMoLib.getInstance().requestMoMoCallBack(requireActivity(), eventValue)
    }

    //Get token callback from MoMo app an submit to server side
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("MyApp", "thanh cong")
                    val token = data.getStringExtra("data") //Token response
                    //Update Token User:Using Retrofit Update
                    val phoneNumber = data.getStringExtra("phonenumber")
                    var env = data.getStringExtra("env")
                    if (env == null) {
                        env = "app"
                    }
                    if (token != null && token != "") {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("MyApp", "ko thanh cong")
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    val message =
                        if (data.getStringExtra("message") != null) data.getStringExtra("message") else "Thất bại"
                    Log.d("MyApp", "ko thanh cong")
                } else if (data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("MyApp", "ko thanh cong")
                } else {
                    //TOKEN FAIL
                    Log.d("MyApp", "ko thanh cong")
                }
            } else {
                Log.d("MyApp", "ko thanh cong")
            }
        } else {
            Log.d("MyApp", "ko thanh cong")
        }
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