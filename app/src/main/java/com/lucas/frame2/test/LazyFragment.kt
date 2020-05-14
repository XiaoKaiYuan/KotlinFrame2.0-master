package com.lucas.frame2.test

import android.os.Bundle
import android.view.View
import com.lucas.frame.base.view.fragment.BaseFragment
import com.lucas.frame2.R
import kotlinx.android.synthetic.main.fragment_lazy.*

class LazyFragment : BaseFragment() {

    companion object {
        fun getIntance(tag: String): LazyFragment {
            val fragment = LazyFragment()
            val bundle = Bundle()
            bundle.putString("tag", tag)
            fragment.arguments = bundle
            return fragment
        }
    }

    val name by lazy { arguments?.getString("tag") ?: "null" }
    override val layoutId: Int = R.layout.fragment_lazy

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        v_text.text = name
    }

    override fun initData() {
    }
}