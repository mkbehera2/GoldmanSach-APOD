package com.goldmansachs.assignment.view.favorite

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldmansachs.assignment.R
import com.goldmansachs.assignment.databinding.FragmentFavoriteBinding
import com.goldmansachs.assignment.view.MainActivity
import com.goldmansachs.assignment.view.adapter.MainAdapter
import com.goldmansachs.assignment.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAdapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = FragmentFavoriteBinding.bind(view)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        setupBinding()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.favList.observe(viewLifecycleOwner, {
            mainAdapter.submitList(it)
            if (it.isNotEmpty()){
                binding.emptyImg.visibility = View.GONE
                binding.textEmpty.visibility = View.GONE
                binding.favView.visibility = View.VISIBLE
            }else{
                binding.emptyImg.visibility = View.VISIBLE
                binding.textEmpty.visibility = View.VISIBLE
                binding.favView.visibility = View.GONE
            }

        })

        viewModel.allFavoritesItems()
    }

    private fun setupBinding() {
        mainAdapter = MainAdapter { apodEntity ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.action_favoriteFragment_to_nav_detail2,
                bundleOf("apod" to apodEntity)
            )
        }
        binding.favView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}