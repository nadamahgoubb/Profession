package com.example.profession.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.profession.R
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.databinding.DialogCitiesBinding
import com.example.profession.ui.adapter.CitesListener
import com.example.profession.ui.adapter.CitesPagingAdapter
import com.example.profession.util.Constants
import com.example.profession.util.ext.init

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoriesDialog(
  private val onClick: CitesListener,
  var data: PagingData<CitesItemsResponse>,
 ) :
    DialogFragment(R.layout.item_filter_multi_choice), CitesListener {
    private lateinit var adapter: CitesPagingAdapter

    private lateinit var binding: DialogCitiesBinding


    companion object {
        fun newInstance(
            onClick: CitesListener,
              data: PagingData<CitesItemsResponse>,
         ): CategoriesDialog {
            val args = Bundle()
            val f = CategoriesDialog(
            onClick, data)
            f.arguments = args
            return f
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog?.isShowing == false) {
            binding = DialogCitiesBinding.inflate(inflater)
            return binding.root
        } else
            return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT

        dialog!!.window?.setLayout(width, height)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window?.setGravity(Gravity.CENTER)
        setLayoutHorizintalBottom(binding.root, 100f)


     /*   (arguments?.getParcelableArrayList<CitesItemsResponse>(Constants.CITIES))?.let {
            categories= it
        }*/
        initAdapters()


    }

    fun setLayoutHorizintalBottom(view: View, bottom: Float) {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.rightMargin = bottom.toInt()
        layoutParams.leftMargin = bottom.toInt()
        view.layoutParams = layoutParams
    }
var cityId :CitesItemsResponse? = null

    private fun initAdapters() {

        adapter= CitesPagingAdapter(
            requireContext(),this,)
        binding.rvCat.init(requireContext(), adapter, 2)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.preProg.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
        adapter.addLoadStateListener { loadState ->

            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
            //    binding.lytEmptyState.visibility = View.GONE
           //     binding.lytData.visibility = View.VISIBLE
            }
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
           //     binding.lytData.visibility = View.GONE

             //   binding.lytEmptyState.visibility = View.VISIBLE
            } else {
               // binding.lytEmptyState.visibility = View.GONE
               // binding.lytData.visibility = View.VISIBLE
                // If we have an error, show a toast*/
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    /* if (it.error.message.equals(Constants.UNAUTHURAIZED_ACCESS)) {
                         showEmptyState(true)
                     } else*/
                    Toast.makeText(activity, it.error.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

            }
        }
        lifecycleScope.launch {
            adapter.submitData(data)
        }

    }


    override fun onOrderClicked(item: CitesItemsResponse?) {
cityId=item
    }
}
