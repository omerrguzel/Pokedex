package com.omerguzel.pokedex.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.FragmentSearchBinding
import com.omerguzel.pokedex.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import tr.com.allianz.digitall.extensions.viewbinding.viewBinding

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
