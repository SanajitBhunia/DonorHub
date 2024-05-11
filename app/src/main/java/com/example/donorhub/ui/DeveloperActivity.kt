package com.example.donorhub.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.ActivityDeveloperBinding

class DeveloperActivity : AppCompatActivity() {

    lateinit var binding: ActivityDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.RLCompanyInfo2.setOnClickListener {
            val phone = "+91 9749304693"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }

        binding.RLCompanyInfo1.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setAction(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("sanajitbhuia13@gmail.com"))
            intent.putExtra("subject", "Report or Suggestion")
            startActivity(intent)
        }

    }

}