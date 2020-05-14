package com.lucas.frame2.koin

import com.lucas.frame2.module.project.ProjectAdapter
import org.koin.dsl.module

val adapterModules = module {
    factory { ProjectAdapter() }
}