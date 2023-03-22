package com.rasyidin.moneyverse.ui.screen.transaction.add.transfer

import android.os.Bundle
import android.view.View
import com.rasyidin.moneyverse.databinding.FragmentTransferBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.utils.hideBotNav

class TransferFragment : FragmentBinding<FragmentTransferBinding>(FragmentTransferBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        requireActivity().hideBotNav()
    }

}