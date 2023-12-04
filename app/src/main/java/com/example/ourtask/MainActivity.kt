package com.example.ourtask

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ourtask.databinding.ActivityMainBinding
import com.example.ourtask.ui.finish.FinishNotesFragment
import com.example.ourtask.ui.home.HomeFragment
import com.example.ourtask.ui.search.SearchNoteFragment
import com.example.ourtask.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private var prevMenuItem: MenuItem? = null
    var activityFlag: String? = this.javaClass.simpleName
    private lateinit var fragmentSavedState: HashMap<String, Fragment.SavedState?>

    companion object {
        private const val HOME = "home"
        private const val FINISH = "finish"
        private const val SEARCH = "search"
        private const val SETTING = "setting"
        private const val FRAGMENT_STATE = "fragmentState"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            fragmentSavedState = HashMap()
            replaceFragment(HOME)

        } else {
            fragmentSavedState =
                savedInstanceState.getSerializable(FRAGMENT_STATE) as HashMap<String, Fragment.SavedState?>
        }
        /*onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                println("Back button pressed")
                setBottomNavigationView()
                *//*if (supportFragmentManager.backStackEntryCount <= 1) {
                    setBottomNavigationView()
                } else {
                    finishAffinity()
                }*//*
            }
        })*/
        val selectedItemId = mBinding?.bottomNav?.selectedItemId
        prevMenuItem = mBinding?.bottomNav?.menu?.findItem(selectedItemId!!)
        when (selectedItemId) {
            R.id.menuHome -> {
                if (activityFlag != HomeFragment::class.java.simpleName)
                    replaceFragment(HOME)
            }

            R.id.menuFinished -> {
                if (activityFlag != FinishNotesFragment::class.java.simpleName)
                    replaceFragment(FINISH)
            }

            R.id.menuSearch -> {
                if (activityFlag != SearchNoteFragment::class.java.simpleName)
                    replaceFragment(SEARCH)
            }

            R.id.menuSetting -> {
                if (activityFlag != SettingFragment::class.java.simpleName)
                    replaceFragment(SEARCH)
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
                R.id.menuHome -> replaceFragment(HOME)

                R.id.menuFinished -> replaceFragment(FINISH)

                R.id.menuSearch -> replaceFragment(SEARCH)

                R.id.menuSetting -> replaceFragment(SETTING)
            }
            true

        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(FRAGMENT_STATE, fragmentSavedState)
        super.onSaveInstanceState(outState)
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

    private fun loadFragment(fragment: Fragment, args: Bundle?, shouldAdd: Boolean) {
        try {
            val manager = supportFragmentManager
            val fragmentPopped = manager.popBackStackImmediate(fragment.javaClass.simpleName, 0)
            if (!fragmentPopped) {  // fragment not in back stack, create it.
                val ft = manager.beginTransaction()
                if (args != null) {
                    fragment.arguments = args
                }
                if (shouldAdd) ft.addToBackStack(fragment.javaClass.simpleName)
                ft.replace(R.id.container, fragment)
                ft.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun replaceFragment(fragmentTag: String) {
        val newFrag: Fragment? = when (fragmentTag) {
            HOME -> HomeFragment()
            FINISH -> FinishNotesFragment()
            SEARCH -> SearchNoteFragment()
            SETTING -> SettingFragment()
            else -> null
        }

        val currFragment = supportFragmentManager
            .findFragmentById(R.id.container)


        //check if fragment is already there, if not then only proceed
        if (!currFragment?.tag.equals(fragmentTag)) {
            currFragment?.let { frag ->
                //save state of current fragment
                fragmentSavedState.put(
                    frag.tag!!,
                    supportFragmentManager.saveFragmentInstanceState(frag)
                )
            }

            //set initial state of fragment if there is any
            newFrag?.setInitialSavedState(fragmentSavedState[fragmentTag])
            newFrag?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it, fragmentTag)
                    .commit()
            }
        }


    }

}