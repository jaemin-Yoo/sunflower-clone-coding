package com.jaemin.sunflower_clone.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jaemin.sunflower_clone.GardenFragment
import com.jaemin.sunflower_clone.PlantListFragment
import java.lang.IndexOutOfBoundsException

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

class SunflowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     *  각각의 Fragment에 ViewPager index 매핑
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() },
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}