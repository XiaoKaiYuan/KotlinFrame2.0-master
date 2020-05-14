package com.lucas.frame2.koin

import com.lucas.frame2.module.project.ProjectListActivity
import com.lucas.frame2.module.project.ProjectListPresenter
import com.lucas.frame2.module.register.RegisterActivity
import com.lucas.frame2.module.register.RegisterPresenter
import org.koin.dsl.module
import java.lang.ref.SoftReference

val presenterModule = module {
    factory { (activity: RegisterActivity) -> RegisterPresenter(activity) }
    factory { (activity: ProjectListActivity) -> ProjectListPresenter(activity) }
}