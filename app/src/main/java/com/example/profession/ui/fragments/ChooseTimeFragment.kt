package com.example.profession.ui.fragments


 import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentChooseTimeBinding
import com.example.profession.ui.adapter.ChooseTimeAdapter
import com.example.profession.ui.base.BaseFragment
import com.example.profession.ui.listener.ChooseTimeOnClickListener
import com.example.profession.util.ext.init

class ChooseTimeFragment : BaseFragment<FragmentChooseTimeBinding>(), ChooseTimeOnClickListener {
    lateinit var adapter :ChooseTimeAdapter
    override fun onFragmentReady() {
        initAdapters()
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }
    private fun initAdapters() {
        adapter = ChooseTimeAdapter(this)
        binding.rvAvailableTime.init(requireContext(), adapter, 3)
    }

    override fun onChooseTimeClickListener() {
findNavController().navigate(R.id.checkOutFragment)    }
}