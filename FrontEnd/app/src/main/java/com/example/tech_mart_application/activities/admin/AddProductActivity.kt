package com.example.tech_mart_application.activities.admin

import android.R.attr.data
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.adapters.ImageViewAdapter
import com.example.tech_mart_application.databinding.ActivityAddProductBinding
import org.w3c.dom.ls.LSInput
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class AddProductActivity : AppCompatActivity(), ImageViewAdapter.OnClickItemListener {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var encodeImage: String
    private val imagesEncodedList = mutableListOf<Bitmap>();
    private val listImage = mutableListOf<String>()
    private lateinit var mImageViewAdapter: ImageViewAdapter
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data?.clipData != null) {
                    val count: Int = result.data!!.clipData!!.itemCount;
                    imagesEncodedList.clear();
                    listImage.clear();
                    for (i in 0 until count) {
                        val imageUri: Uri = result!!.data!!.clipData!!.getItemAt(i).uri;
                        val inputStream = imageUri.let { contentResolver.openInputStream(it) }
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        encodeImage = encodeImage(bitmap)
                        listImage.add(encodeImage);
                        imagesEncodedList.add(bitmap);
                    }
                } else if (result.data != null) {
                    val imageUri = result.data!!.data
                    try {
                        val inputStream = imageUri?.let { contentResolver.openInputStream(it) }
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        encodeImage = encodeImage(bitmap)
                        listImage.add(encodeImage);
                        imagesEncodedList.add(bitmap);
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
                loadImage(imagesEncodedList)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Button Back
        binding.btnBack.setOnClickListener { finish() }

        binding.btnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            pickImage.launch(intent)
        }

    }

    private fun encodeImage(bitmap: Bitmap): String {
        val previewWidth = 150
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun loadImage(listImage: MutableList<Bitmap>) {
        mImageViewAdapter = ImageViewAdapter(listImage, this)
        binding.rcvImage.apply {
            adapter = mImageViewAdapter
            layoutManager =
                LinearLayoutManager(this@AddProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onRemoveItem(position: Int) {

    }
}