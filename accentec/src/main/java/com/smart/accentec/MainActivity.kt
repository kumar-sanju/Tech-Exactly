package com.smart.accentec

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val imageUris = ArrayList<Uri>()
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var myText: TextView

    companion object {
        private const val REQUEST_PERMISSION_CODE = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectImagesButton: Button = findViewById(R.id.selectImagesButton)
        val convertToPdfButton: Button = findViewById(R.id.convertToPdfButton)
        val imageRecyclerView: RecyclerView = findViewById(R.id.imageRecyclerView)
        myText = findViewById(R.id.textView)
//        val textView: TextView = findViewById(R.id.myText) as TextView


        selectImagesButton.setOnClickListener {
            if (checkPermissions()) {
                pickImages()
            } else {
                requestPermissions()
            }
        }

        convertToPdfButton.setOnClickListener {
            convertImagesToPdf()
        }

        imageAdapter = ImageAdapter(imageUris)
        imageRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = imageAdapter
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionRead = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val permissionWrite = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissionRead == PackageManager.PERMISSION_GRANTED &&
                permissionWrite == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_PERMISSION_CODE
        )
    }

    private fun pickImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun convertImagesToPdf() {
        if (imageUris.isNotEmpty()) {
//            val timeStamp: String = java.lang.String.valueOf(
//                TimeUnit.MILLISECONDS.toSeconds(
//                    System.currentTimeMillis()
//                )
//            )
            val timeStampLong = System.currentTimeMillis() / 1000
            val timeStamp = timeStampLong.toString()

//            val pdfPath = "${externalCacheDir?.absolutePath}/converted_images.pdf"
            val pdfPath = "${externalCacheDir?.absolutePath}/$timeStamp.pdf"

            try {
                val document = Document(PageSize.A4)
                PdfWriter.getInstance(document, FileOutputStream(pdfPath))
                document.open()

                for (uri in imageUris) {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val image = Image.getInstance(stream.toByteArray())
                    image.scaleToFit(document.pageSize.width, document.pageSize.height)
                    document.add(image)
                }

                document.close()

//                Log.d("PDF", "PDF created at: $pdfPath")
                Toast.makeText(this, "PDF created successfully", Toast.LENGTH_SHORT).show()
                myText.text = "PDF file path is at: $pdfPath"

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            data?.let {
                val clipData = it.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val imageUri = clipData.getItemAt(i).uri
                        imageUris.add(imageUri)
                    }
                } else {
                    val imageUri = data.data
                    imageUri?.let { uri ->
                        imageUris.add(uri)
                    }
                }
                imageAdapter.notifyDataSetChanged()
                findViewById<Button>(R.id.convertToPdfButton).isEnabled = true
            }
        }
    }
}
