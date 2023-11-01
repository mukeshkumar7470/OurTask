package com.example.ourtask

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ourtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private var prevMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        val selectedItemId = mBinding?.bottomNav?.selectedItemId
        prevMenuItem = mBinding?.bottomNav?.menu?.findItem(selectedItemId!!)

        when (selectedItemId) {
            R.id.menuHome -> {
                // Perform actions for the Home tab
            }

            R.id.menuFinished -> {
                // Perform actions for the Dashboard tab
            }

            R.id.menuSearch -> {
                // Perform actions for the Notifications tab
            }

            R.id.menuSetting -> {
                // Perform actions for the Notifications tab
            }
        }
        prevMenuItem?.setIcon(getSelectedIcon(prevMenuItem!!.itemId))
        mBinding?.bottomNav?.setOnItemSelectedListener { item ->
            prevMenuItem?.setIcon(getDefaultIcon(prevMenuItem!!.itemId)) // Set the default icon for the previously selected item
            item.setIcon(getSelectedIcon(item.itemId)) // Set the selected icon for the current item
            // Update the previously selected item
            prevMenuItem = item
            // Add your logic here based on the selected item
            when (item.itemId) {
                R.id.menuHome -> {
                    // Perform actions for the Home tab
                    true
                }

                R.id.menuFinished -> {
                    // Perform actions for the Dashboard tab
                    true
                }

                R.id.menuSearch -> {
                    // Perform actions for the Notifications tab
                    true
                }

                R.id.menuSetting -> {
                    // Perform actions for the Notifications tab
                    true
                }

                else -> false
            }
        }
    }

    private fun getDefaultIcon(itemId: Int): Int {
        return when (itemId) {
            R.id.menuHome -> R.drawable.ic_outline_menu_home
            R.id.menuFinished -> R.drawable.ic_outline_menu_finished
            R.id.menuSearch -> R.drawable.ic_outline_menu_search
            R.id.menuSetting -> R.drawable.ic_outline_menu_setting
            else -> throw IllegalArgumentException("Invalid menu item")
        }
    }

    private fun getSelectedIcon(itemId: Int): Int {
        return when (itemId) {
            R.id.menuHome -> R.drawable.ic_solid_menu_home
            R.id.menuFinished -> R.drawable.ic_solid_menu_finished
            R.id.menuSearch -> R.drawable.ic_solid_menu_search
            R.id.menuSetting -> R.drawable.ic_solid_menu_setting
            else -> throw IllegalArgumentException("Invalid menu item")
        }
    }
}