package com.lucas.frame2

import io.reactivex.Flowable
import io.reactivex.Scheduler
import org.junit.Test

import org.junit.Assert.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    val arrayOf = arrayOf("lucas", "ace", "alenx", "stevn")
    @Test
    fun addition_isCorrect() {
        var count = 60
//        Observable.interval(0, 1, TimeUnit.SECONDS)
//            .take(count + 1)
//            .map {
//                count - it
//            }
//            .observeOn(Schedulers.io())
//            .doOnSubscribe {
//                "do subscribe".p()
//            }
//            .subscribe({ it.p() }, { "error".p() }, { "complete".p() })

        //交叉无序遍历
        Observable.from(arrayOf)
            .flatMap { Observable.just(it + "add") }
            .subscribe {
                it.p()
            }
        "-----------------".p()
        //队列循环遍历
        Observable.from(arrayOf)
            .concatMap { Observable.just(it + "add") }
            .subscribe {
                it.p()
            }

        "----------------".p()
        Observable.from(arrayOf)
            .flatMapIterable {
                val arrayList = ArrayList<String>()
                arrayList.add(it)
                arrayList
            }.subscribe {
                it.p()
            }
        "----------------".p()
        //分批遍历
        Observable.from(arrayOf)
            .buffer(2)
            .subscribe {
                Observable.from(it).subscribe { it.p() }
                "//////////".p()
            }
        "----------------".p()
        //筛选分组
        Observable.from(arrayOf)
            .groupBy { it.contains("a") }
            .subscribe {
                "key:${it.key}".p()//groupBy的返回类型
                it.asObservable().subscribe { it.p() }
                "///////".p()
            }
    }

    @Test
    fun test1() {
        //过滤
        Observable.from(arrayOf)
            .filter { it.contains("a") }
            .subscribe {
                it.p()
            }
        "----------------".p()
        //获取一个指定索引位置的数据
        Observable.range(0, 10)
            .elementAt(4)
            .subscribe {
                it.p()
            }
        "----------------".p()
        //去重
        Observable.just(1, 2, 1, 4, 6, 6)
            .distinct()
            .subscribe {
                it.p()
            }
        "----------------".p()
        //截取,向后取值
        Observable.range(0, 10)
            .skip(5)
            .subscribe {
                it.p()
            }
        "----------------".p()
        //截取，向前保留取值
        Observable.range(0, 10)
            .take(5)
            .subscribe {
                it.p()
            }
    }

    @Test
    fun test2() {
        //头部插入数据
        Observable.range(0, 10)
            .startWith(100, 200)
            .subscribe {
                it.p()
            }
        "----------------".p()
        //无序合并数集
        Observable.merge(Observable.range(0, 5), Observable.range(4, 6))
            .subscribe {
                it.p()
            }

        "----------------".p()
        //有序合并数集
        Observable.concat(Observable.range(0, 5), Observable.range(4, 6))
            .subscribe {
                it.p()
            }

        "----------------".p()
        //对两个数集的数据源进行合并
        Observable.zip(Observable.range(0, 5), Observable.range(4, 6)) { i1, i2 ->
            i1 + i2
        }.subscribe {
            it.p()
        }

        "----------------".p()
        //发射之前预处理，类似于do while
        Observable.range(0,3)
            .doOnNext {
                "do $it".p()
            }
            .subscribe{
                it.p()
            }
        "----------------".p()
        //如果没有收到任何数据，则发射一个默认数据
        Observable.create(Observable.OnSubscribe<Int> {
            it.onCompleted()
        }).defaultIfEmpty(404).subscribe{it.p()}

        "----------------".p()
        Flowable.interval(0,1,TimeUnit.SECONDS)
            .take(3)
            .map { 3-it }
            .subscribe({it.p()},{"error".p()},{"complete".p()})
    }

    @Test
    fun test3(){
        Flowable.interval(0,1,TimeUnit.SECONDS)
            .take(3)
            .map { 3-it }
            .subscribe({it.p()},{"error".p()},{"complete".p()})
    }

    @Test
    fun test4(){
        repeat(10){
        }
    }

    fun Any.p() {
        println(this)
    }
}
