package io.archx.txblog.data.dto

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.data.domain.Navigation

data class NavigationDto(var id: Int, var name: String, var link: String, var icon: String, var priority: Int) {

    fun asNewNavigation(): Navigation {
        return Navigation(0, name, link, icon, priority, 0, DateUtils.timestamp())
    }

    fun asUpdateNavigation(): KtUpdateWrapper<Navigation> {
        val up = KtUpdateWrapper(Navigation::class.java)
        up.eq(Navigation::id, id)
            .set(Navigation::name, name)
            .set(Navigation::icon, icon)
            .set(Navigation::link, link)
            .set(Navigation::priority, priority)
            .set(Navigation::updatedAt, DateUtils.timestamp())

        return up
    }
}