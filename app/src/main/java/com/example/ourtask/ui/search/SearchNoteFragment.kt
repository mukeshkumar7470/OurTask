package com.example.ourtask.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ourtask.MainActivity
import com.example.ourtask.R
class SearchNoteFragment : Fragment() {

    private lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_note, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return SearchNoteFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        activity.activityFlag = this.javaClass.simpleName
    }
}