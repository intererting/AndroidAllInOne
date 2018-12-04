package com.lqd.commonimp.model

data class SectionModel<M>(val type: Int = SECTION_NORMAL
                           , val headerName: String? = null
                           , val data: M? = null
                           , var openMode: Boolean = false) {
}

const val SECTION_HEADER = 10000//二级列表头部
const val SECTION_NORMAL = 10001//二级列表普通