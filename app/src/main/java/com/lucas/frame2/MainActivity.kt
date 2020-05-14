package com.lucas.frame2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.lucas.frame.aop.annotation.CheckLogin
import com.lucas.frame.aop.annotation.FastClick
import com.lucas.frame.aop.annotation.RequestPermission
import com.lucas.frame.arouter.ARouterPagePath
import com.lucas.frame2.data.db.AppDatabase
import com.lucas.frame2.module.register.RegisterActivity
import com.lucas.frame2.test.LazyFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

@Route(path = ARouterPagePath.APP_MAIN)
class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    val appDatabase by inject<AppDatabase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        launch(Dispatchers.IO) {
//            appDatabase.userDao().insertUser(TestBean().apply {
//                id = 1
//                username = "lucas"
//            })
//            val user = appDatabase.userDao().getUser()
//            user?.apply {
//                Log.d(BaseApp.TAG,"user:$user")
//            }
//        }

        RegisterActivity.launch(this)

//        GlobalScope.launch(Dispatchers.Main) {
//            "start".p()
//            val testRun = testRun()
//            "end:$testRun".p()
//        }
//        "main end".p()
//
//        launch {
//        }


//        testLazyFragment()
    }

    private fun testLazyFragment() {
        v_pager.adapter = TestPagerAdapter()
        val transaction = supportFragmentManager.beginTransaction()
        val arrayOf = arrayOf("2-1", "2-2", "2-3")
        fragments2.reverse()
        fragments2.forEach {
            val indexOf = fragments2.indexOf(it)
            v_tabs.addTab(v_tabs.newTab().setText(arrayOf[indexOf]))
            transaction.add(R.id.v_container,it,arrayOf[indexOf])
        }
        transaction.commitNowAllowingStateLoss()
        v_tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                val beginTransaction = supportFragmentManager.beginTransaction()
                supportFragmentManager.fragments.forEach {
                    if (it.tag.equals(tab.text.toString())){
                        beginTransaction.show(it)
                    }else{
                        beginTransaction.hide(it)
                    }
                }
               beginTransaction.commitNowAllowingStateLoss()
            }
        })

    }

    val fragments = arrayOf(LazyFragment.getIntance("1-1"),LazyFragment.getIntance("1-2"),LazyFragment.getIntance("1-3"))
    val fragments2 = arrayOf(LazyFragment.getIntance("2-1"),LazyFragment.getIntance("2-2"),LazyFragment.getIntance("2-3"))

    inner class TestPagerAdapter: FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment =fragments[position]

        override fun getCount(): Int =fragments.size

    }

    suspend fun testRun(): String {
        Thread.sleep(2000)
        "testRun".p()
        return "complete"
    }

    fun Any.p() {
        Log.d("ace", this.toString())
    }

    @FastClick
    fun test(view: View) {
        Log.d("ace", "test")
    }

    @CheckLogin
    fun test2() {
        Log.d("ace", "test2")
    }

    @RequestPermission(values = [android.Manifest.permission.READ_PHONE_STATE], isSetup = true)
    fun test3(vararg a: Int) {
        Log.d("ace", "test3")
    }
}
