package com.example.aroundmeapplication.activities

import android.text.TextUtils.replace
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aroundmeapplication.R

open class BaseActivity : AppCompatActivity() {

    fun showToast(message:String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    protected fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,fragment)
            addToBackStack(null)
            commit()
        }

    }
}