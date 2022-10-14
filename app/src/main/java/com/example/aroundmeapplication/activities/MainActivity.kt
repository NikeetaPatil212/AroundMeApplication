package com.example.aroundmeapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.aroundmeapplication.R
import com.example.aroundmeapplication.fragments.NearByFragment
import com.example.aroundmeapplication.fragments.WishListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.imgLocation -> loadFragment(NearByFragment())
                R.id.imgWhistList ->loadFragment(WishListFragment())
                else -> {
                    showToast("No Fragment Found!")
                }
            }
        }
        true
    }
}