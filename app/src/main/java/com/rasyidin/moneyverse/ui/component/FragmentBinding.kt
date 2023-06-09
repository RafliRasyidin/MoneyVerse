package com.rasyidin.moneyverse.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class FragmentBinding<VB : ViewBinding>(private val inflate: InflateFragment<VB>) : Fragment(){

    protected var _binding: VB? = null
    protected val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}