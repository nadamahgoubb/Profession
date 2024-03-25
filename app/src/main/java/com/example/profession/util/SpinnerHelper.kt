package com.example.profession.util

import android.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.ViewCompat
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.NationalitiesItem
import org.json.JSONObject

class SpinnerHelper(private val context: Context, list: List<NationalitiesItem>, hasHint: Boolean) {
    private var adapter: ArrayAdapter<String?>? = null
    private var hasHint = false
    private val list: List<NationalitiesItem>
    private var position = 0
    fun setAdapter(spinner: Spinner) {
        adapter = object : ArrayAdapter<String?>(context, R.layout.simple_spinner_item) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    (v.findViewById<View>(R.id.text1) as TextView).text = ""
                    (v.findViewById<View>(R.id.text1) as TextView).hint =
                        getItem(
                            count
                        )
                    (v.findViewById<View>(R.id.text1) as TextView).setTextColor(ViewCompat.MEASURED_STATE_MASK)
                    (v.findViewById<View>(R.id.text1) as TextView).setHintTextColor(
                        context.resources.getInteger(R.color.holo_green_light)
                    )
                    (v.findViewById<View>(R.id.text1) as TextView).highlightColor =
                        ViewCompat.MEASURED_STATE_MASK
                }
                (v.findViewById<View>(R.id.text1) as TextView).setTextColor(ViewCompat.MEASURED_STATE_MASK)
                (v.findViewById<View>(R.id.text1) as TextView).setHintTextColor(ViewCompat.MEASURED_STATE_MASK)
                (v.findViewById<View>(R.id.text1) as TextView).highlightColor =
                    ViewCompat.MEASURED_STATE_MASK
                return v
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val v = super.getDropDownView(position, convertView, parent)
                (v.findViewById<View>(R.id.text1) as TextView).setTextColor(ViewCompat.MEASURED_STATE_MASK)
                (v.findViewById<View>(R.id.text1) as TextView).setHintTextColor(ViewCompat.MEASURED_STATE_MASK)
                (v.findViewById<View>(R.id.text1) as TextView).highlightColor =
                    ViewCompat.MEASURED_STATE_MASK
                return v
            }

            override fun getCount(): Int {
                return super.getCount() - 1
            }
        }
        (adapter as ArrayAdapter<String?>).setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        for (i in list.indices) {
            var s: NationalitiesItem = list[i]
           (adapter as ArrayAdapter<String?>).add(s.name)
         }
        spinner.adapter = adapter
        adapter?.count?.let { spinner.setSelection(it) }
        spinner.onItemSelectedListener = onItemSelected()
    }

    inner class onItemSelected : AdapterView.OnItemSelectedListener {
        constructor()

        constructor(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {

            var id = JSONObject(list[i].toString()).getInt("id")

            position = id
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }


     fun getIndexOf( value: Int): Int {
        var index = 0
        val count: Int? = adapter?.count
        if(count!= null ){
        while (index < count) {
            var s: NationalitiesItem = list[index]
            if (s.id ?. equals(value) == true) {
                return index
            }
            ++index
        }
        }
        return -1
    }

    fun setPosition(position: Int) {}
    fun notifyDataChange() {
        adapter!!.notifyDataSetChanged()
    }

    fun removeItem(item: String?) {
        adapter!!.remove(item)
    }

    fun getPosition(): Int {
        return position
    }

    companion object {
        private const val TAG = "SpinnerAdapter"
    }

    init {
        this.list = list
        this.hasHint = hasHint
    }
}

