package com.chan.moviesearcher.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.chan.moviesearcher.R
import com.chan.moviesearcher.databinding.ActivityMainBinding
import com.chan.moviesearcher.ui.common.adapter.ViewPagerAdapter
import com.chan.moviesearcher.ui.main.data.ClickEventMessage.*
import com.chan.moviesearcher.ui.main.fragment.SaveListFragment
import com.chan.moviesearcher.ui.main.fragment.SearchListFragment
import com.chan.ui.BaseActivity
import com.chan.ui.livedata.observeEvent
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var pagerAdapter: ViewPagerAdapter
    private val viewModel by viewModels<MovieSearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        initViewModelObserve()
    }

    private fun initViewPager() {
        pagerAdapter = ViewPagerAdapter(this)
        pagerAdapter.addFragment(SearchListFragment())
        pagerAdapter.addFragment(SaveListFragment())
        binding.viewPager.adapter = pagerAdapter
        val tabLayout = binding.tabs
        val tabTitleList =
            listOf(
                getString(R.string.tab_search_list),
                getString(R.string.tab_save_list)
            )
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }

    private fun initViewModelObserve() {
        viewModel.message.observeEvent(lifecycleOwner = this, observer = {
            it?.let {
                val resource = when (it) {
                    SAVE_SUCCESS -> R.string.save_success
                    DELETE_SUCCESS -> R.string.delete_success
                    ALREADY_EXIST -> R.string.already_exist
                }
                Snackbar.make(binding.root, resource, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

}