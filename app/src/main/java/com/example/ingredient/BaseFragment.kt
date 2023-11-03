package com.example.ingredient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("BASEFRAGMENT","onviewcreated")
        super.onViewCreated(view, savedInstanceState)
    }
}