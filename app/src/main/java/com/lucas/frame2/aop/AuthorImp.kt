package com.lucas.frame2.aop

import com.lucas.frame.aop.port.IAuthor

class AuthorImp:IAuthor {
    override fun isLogin(): Boolean {
        return true
    }
}