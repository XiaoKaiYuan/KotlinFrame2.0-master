package com.lucas.frame2.module.project

import com.lucas.frame.config.FrameConstants
import com.lucas.frame.data.net.RequestCall
import com.lucas.frame.ext.injectExt
import com.lucas.frame2.base.presenter.BaseUserPresenter
import com.lucas.frame2.data.remote.ApiServer
import java.lang.ref.SoftReference

class ProjectListPresenter(val activity: ProjectListActivity) :
    BaseUserPresenter<ProjectListActivity>(SoftReference(activity)) {

    private val apiServer by injectExt<ApiServer>()

    fun loadData(page: Int = FrameConstants.Page.START_INDEX) {
        request(activity.rootSwitchView) {
            call = apiServer.projectList(1, page)
            loadStyle = if (page == 1) RequestCall.LoadStyle.DIALOG else RequestCall.LoadStyle.VIEW
            requestListModel =
                if (page == 1) RequestCall.RequestListModel.REFRESH else RequestCall.RequestListModel.LOAD_MODE
        }
    }
}