package com.horizons.profession.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.horizons.profession.R
import com.horizons.profession.data.dataSource.response.CitesItemsResponse
import com.horizons.profession.databinding.DialogCitiesBinding
import com.horizons.profession.ui.adapter.CitesListener
import com.horizons.profession.ui.adapter.CitesAdapter
import com.horizons.profession.util.ext.init

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoriesDialog(
    private val onClick: CitesListener,
    var data: ArrayList<CitesItemsResponse>,
) : DialogFragment(R.layout.item_filter_multi_choice), CitesListener {

    private lateinit var binding: DialogCitiesBinding
    private lateinit var adapter: CitesAdapter
    var cityItem: CitesItemsResponse? = null


    companion object {
        fun newInstance(
            onClick: CitesListener,
            data: ArrayList<CitesItemsResponse>,
        ): CategoriesDialog {
            val args = Bundle()
            val f = CategoriesDialog(
                onClick, data)
            f.arguments = args
            return f
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (dialog?.isShowing == false) {
            binding = DialogCitiesBinding.inflate(inflater)
            return binding.root
        } else return null
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


    private fun initAdapters() {

        adapter = CitesAdapter(requireContext(), this)
        binding.rvCat.init(requireContext(), adapter, 2)
        adapter.list = (data)
        adapter.notifyDataSetChanged()
    }

    override fun onOrderClicked(item: CitesItemsResponse?) {
        cityItem = item
        onClick.onOrderClicked(item)
        dialog?.dismiss()
    }
}
