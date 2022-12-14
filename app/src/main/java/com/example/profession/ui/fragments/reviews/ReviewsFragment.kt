package com.example.profession.ui.fragments.reviews

import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentReviewsBinding
import com.example.profession.ui.base.BaseFragment


class ReviewsFragment : BaseFragment<FragmentReviewsBinding>() {
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
    }

}