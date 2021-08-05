package com.gopi.mytest.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.gson.Gson
import com.gopi.mytest.myapplication.models.JsonModel
import com.gopi.mytest.myapplication.models.MainItem

const val MAIN_ITEM = "Main_Item"

class MainActivity : AppCompatActivity() {

    private lateinit var item: Button
    lateinit var itemData: MainItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = findViewById(R.id.non_veg_sub_combo)
        getItemData()

        setListener()
    }

    private fun getItemData() {
       itemData = Gson().fromJson(JsonModel.MENU_ITEMS, MainItem::class.java)
    }

    private fun setListener() {

        item.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(MAIN_ITEM, itemData)
            }
            supportFragmentManager.commit {
                replace<ItemFragment>(
                    containerViewId = R.id.container_layout,
                    args = bundle
                )
                addToBackStack(ItemFragment::class.java.simpleName)
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            item.visibility = if (supportFragmentManager.backStackEntryCount == 0 ) View.VISIBLE else View.GONE
        }
    }

}