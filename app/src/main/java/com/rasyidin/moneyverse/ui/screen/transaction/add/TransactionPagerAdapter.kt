package com.rasyidin.moneyverse.ui.screen.transaction.add

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TransactionPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OutcomeFragment()
            1 -> IncomeFragment()
            2 -> TransferFragment()
            else -> throw IllegalStateException("Unknown fragment")
        }
    }
}