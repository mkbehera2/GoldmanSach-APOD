package com.goldmansachs.assignment.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldmansachs.assignment.R
import com.goldmansachs.assignment.databinding.MainFragmentBinding
import com.goldmansachs.assignment.model.util.*
import com.goldmansachs.assignment.model.util.Event
import com.goldmansachs.assignment.model.util.Resource
import com.goldmansachs.assignment.model.util.showCalender
import com.goldmansachs.assignment.model.util.showSnackBarError
import com.goldmansachs.assignment.view.MainActivity
import com.goldmansachs.assignment.view.adapter.MainAdapter
import com.goldmansachs.assignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAdapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = MainFragmentBinding.bind(view)
        setupBinding()
        setupObserver()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val settingsItem = menu.findItem(R.id.menu_theme)
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            settingsItem.title = resources.getString(R.string.menuThemeDay)
        }else{
            settingsItem.title = resources.getString(R.string.menuThemeNight)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_refresh -> {
                viewModel.refreshData()
                true
            }
            R.id.menu_theme -> {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    item.title = resources.getString(R.string.menuThemeDay)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    item.title = resources.getString(R.string.menuThemeNight)
                }
                true
            }

            R.id.menu_favorite -> {
                (requireActivity() as MainActivity).navController.navigate(
                        R.id.action_nav_main_to_favoriteFragment,
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

    private fun setupBinding() {
        mainAdapter = MainAdapter { apodEntity ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.action_main_to_detail,
                bundleOf("apod" to apodEntity)
            )
        }
        binding.mfSrl.setOnRefreshListener { viewModel.refreshData() }
        binding.mfRc.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && binding.mfFab.isShown)
                        binding.mfFab.hide()
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        binding.mfFab.show()
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
        binding.mfFab.setOnClickListener {
            childFragmentManager.showCalender {
                viewModel.updateDate(it)
            }
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.also {
            it.launchWhenStarted {
                viewModel.errorEvents.collect { event ->
                    when (event) {
//                        is Event.ShowErrorMessage -> showSnackBarError(
//                            getString(
//                                R.string.mainErrorEvent,
//                                event.error.localizedMessage ?: getString(R.string.mainErrorUnknown)
//                            )
//                        )
                    }
                }
            }
            it.launchWhenStarted {
                viewModel.lists.collect { resource ->
                    val result = resource ?: return@collect
                    binding.mfSrl.isRefreshing = resource is Resource.Loading
                    binding.mfRc.isVisible = !result.data.isNullOrEmpty()
                    binding.mfGroup.isVisible = !binding.mfRc.isVisible
                    mainAdapter.submitList(result.data)
                }
            }
        }
    }
}