package com.example.tech_mart_application.activities.admin

import android.R.attr.data
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tech_mart_application.databinding.ActivityAddProductBinding
import java.io.ByteArrayOutputStream


class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var encodeImage: String
    var imagesEncodedList: List<String>? = null
    var imageEncoded: String? = null
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data?.clipData != null) {
                    val count: Int = result.data!!.clipData!!.itemCount;
                    for (i in 0 until count) {
                        val imageUri: Uri = result!!.data!!.clipData!!.getItemAt(i).uri;
                        Log.d("MyApp",imageUri.toString());
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    }
                } else if (result.data != null) {
                    val imagePath: String = result!!.data!!.data!!.path.toString();
                    Log.d("MyApp",imagePath);
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
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
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
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
}