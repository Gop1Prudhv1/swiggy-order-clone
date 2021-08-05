package com.gopi.mytest.myapplication

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gopi.mytest.myapplication.databinding.ItemFragmentBinding
import com.gopi.mytest.myapplication.models.AddOn
import com.gopi.mytest.myapplication.models.MainItem
import java.lang.reflect.Type
import kotlin.math.absoluteValue

class ItemFragment : Fragment() {

    private var _viewBinding: ItemFragmentBinding? = null
    private val binding: ItemFragmentBinding get() = _viewBinding!!
    private var addOnCount: Int = 0
    private var price: Int = 0
    private var mainItem: MainItem? = null
    private var selectedItemsList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainItem = arguments?.getParcelable(MAIN_ITEM)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = ItemFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        price += mainItem?.price!!
        populateTheView()
    }

    private fun populateTheView() {
        binding.toolbar.text = mainItem?.name

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = mainItem?.addlnItems?.let {
            ItemAdapter(
                requireContext(),
                it
            ) { addOn: AddOn, currentBtnPrice: Int, linearLayout: LinearLayout, btn: RadioButton ->
                setSelectionLogic(addOn, currentBtnPrice, linearLayout, btn)
            }
        }

        setSideBarView()
        setFinalAmount()
    }

    private fun setSideBarView() {
        var prevTxtV = VerticalTextView(requireContext())
        mainItem?.addlnItems?.forEachIndexed { index, addOn ->
            val txtV = VerticalTextView(requireContext()).apply {
                text = "   · ${addOn.name}  "
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    setTextAppearance(R.style.sideBarText)
                }
            }
            txtV.setOnClickListener {
                prevTxtV.typeface = Typeface.DEFAULT
                binding.recyclerView.smoothScrollToPosition(index)
                txtV.setTypeface(txtV.typeface, Typeface.BOLD)
                prevTxtV = txtV
            }
            binding.sideBar.addView(txtV)
        }
    }

    private fun setSelectionLogic(
        addOn: AddOn,
        currentBtnPrice: Int,
        linearLayout: LinearLayout,
        btn: RadioButton
    ) {
        when (addOn.maxAllowed) {
            1 -> {
                linearLayout.children.forEach {
                    val radioBtn = it.findViewById<RadioButton>(R.id.radio_btn)
                    (radioBtn).isChecked = false
                }
                btn.isChecked = !btn.isChecked
            }
            3 -> {
                var mAcnt = 0

                linearLayout.children.forEach {
                    val radioButton = it.findViewById<RadioButton>(R.id.radio_btn)
                    if (radioButton.isChecked) {
                        mAcnt++;
                    }
                }

                // proud of finally being able to write this two lines logic, where things seemed
                // to be pretty interlocked, bit tough situation..

                btn.isChecked = mAcnt <= 3 && btn.tag.toString().toInt() % 2 != 0
                btn.tag = if (btn.isChecked) btn.tag.toString().toInt().plus(1) else 1

            }
            else -> {
                //if the user clicks on the btn that is already checked that means he wants to remove it
                if (btn.tag.toString().toInt() % 2 == 0) {
                    price -= currentBtnPrice
                    btn.isChecked = false
                    selectedItemsList.remove(btn.text.toString())
                    addOnCount--
                } else {
                    price += currentBtnPrice
                    btn.isChecked = true
                    addOnCount++
                    selectedItemsList.add(btn.text.toString())
                }
                btn.tag = btn.tag.toString().toInt().plus(1)
                setFinalAmount()
            }
        }
    }

    private fun setFinalAmount() {
        binding.amount.text = "Item total ${price.toString().addRupeeSymbol()}"
        binding.addOnCount.text = "+$addOnCount Add On"
        var s = selectedItemsList.firstOrNull() ?: ""
        if (selectedItemsList.size > 1)
        for (i in 1 until selectedItemsList.size) {
            s += ", " + selectedItemsList.getOrNull(i)
        }
        binding.itemsName.text = s
    }

}



