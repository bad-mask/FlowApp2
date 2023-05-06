package com.zly.flowapp2.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withStarted
import com.zly.flowapp2.R
import com.zly.flowapp2.account.AccountManager
import com.zly.flowapp2.databinding.FragmentMainBinding
import com.zly.flowapp2.main.ui.viewmodel.MainVM
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val model: MainVM by viewModels()
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_main, container, false)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initData() {
        model.createTestAData()
    }

    private fun initObserver() {

//        lifecycleScope.launchWhenStarted {
//            model.testAFlow.collect {
//                binding.mainTv.text = "$it"
//            }
//        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.testAFlow.collect {
                    binding.mainTv.text = "$it"
                }
            }
        }

        lifecycleScope.launch {
            AccountManager.testFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                binding.main1Tv.text = "${it?.lastUseTime}"
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    AccountManager.testStateFlow.collect {

                    }
                }
                launch {
                    AccountManager.testSharedFlow.collect {

                    }
                }
            }
        }
    }

    companion object {
        fun tag() = "MainFragment"
        fun newInstance() = MainFragment()
    }
}

