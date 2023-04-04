package com.rasyidin.moneyverse.ui.screen.target

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentTargetBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.utils.showBotNav

class TargetFragment : FragmentBinding<FragmentTargetBinding>(FragmentTargetBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        showBotNav()
    }
}