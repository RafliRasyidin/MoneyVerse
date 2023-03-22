package com.rasyidin.moneyverse.ui.screen.transaction.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentTransferBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.utils.hideBotNav
import com.rasyidin.moneyverse.utils.showLongToast

class TransferFragment : FragmentBinding<FragmentTransferBinding>(FragmentTransferBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        requireActivity().hideBotNav()
    }

}