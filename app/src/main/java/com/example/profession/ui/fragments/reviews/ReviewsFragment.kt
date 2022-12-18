package com.example.profession.ui.fragments.reviews

import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentReviewsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment


class ReviewsFragment : BaseFragment<FragmentReviewsBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        binding.btnDone.setOnClickListener {
            findNavController().navigate(R.id.chooseTimeFragment)
        }
        binding.item1.root.setOnClickListener {
            findNavController().navigate(R.id.chooseTimeFragment)
        }
        binding.item2.root.setOnClickListener {
            findNavController().navigate(R.id.chooseTimeFragment)
        }
        binding.item3.root.setOnClickListener {
            findNavController().navigate(R.id.chooseTimeFragment)
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.rating.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                //   Toast.makeText(requireContext(), rating.toString(), Toast.LENGTH_SHORT).show()
            }
        setupUi()

    }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)

    }

}