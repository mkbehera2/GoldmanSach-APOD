package com.goldmansachs.assignment.view.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.goldmansachs.assignment.R
import com.goldmansachs.assignment.databinding.DetailFragmentBinding
import com.goldmansachs.assignment.model.apiservice.ApodEntity
import com.goldmansachs.assignment.model.apiservice.ApodFavoriteEntity
import com.goldmansachs.assignment.model.db.AppDatabase
import com.goldmansachs.assignment.view.MainActivity
import com.goldmansachs.assignment.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private lateinit var viewModel: FavoriteViewModel
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var favEntity: ApodFavoriteEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = DetailFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        setupBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val favoriteMenu = menu.findItem(R.id.menu_favorite)
        val status = viewModel.existsInFavorite(favEntity)
        if(status){
            favoriteMenu.title = resources.getString(R.string.menuRemoveFavorite)
        }else{
            favoriteMenu.title = resources.getString(R.string.menuAddFavorite)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_favorite -> {
                if (item.title.equals(resources.getString(R.string.menuRemoveFavorite))) {
                    item.title = resources.getString(R.string.menuAddFavorite)
                    viewModel.removeFromFavorite(favEntity)
                    Toast.makeText(binding.root.context,"removed from fav list!", Toast.LENGTH_LONG).show()
                } else {
                    item.title = resources.getString(R.string.menuRemoveFavorite)
                    viewModel.addToFavorite(favEntity)
                    Toast.makeText(binding.root.context,"added to fav list!", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setupBinding() {
        arguments?.getParcelable<ApodEntity>("apod")?.let {
            binding.dfTitle.text = it.title ?: getString(R.string.mainErrorUnknown)
            binding.dfDate.text = it.date ?: getString(R.string.mainErrorUnknown)
            binding.dfExplanation.text = it.explanation ?: getString(R.string.mainErrorUnknown)
            Glide.with(binding.root)
                .load(it.hdurl)
                .into(binding.dfImage)
            favEntity = ApodFavoriteEntity(
                title = it.title,
                copyright = it.copyright,
                date = it.date,
                explanation = it.explanation,
                hdurl = it.hdurl,
                mediaType = it.mediaType,
                serviceVersion = it.serviceVersion,
                url = it.url,
                id = it.id
            )
        }
    }

}