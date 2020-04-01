package io.archx.txblog.data.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.data.domain.Navigation
import io.archx.txblog.data.dto.NavigationDto
import io.archx.txblog.data.mapper.NavigationMapper
import org.springframework.stereotype.Service

interface NavigationService : IService<Navigation> {

    fun create(dto: NavigationDto): Navigation?

    fun update(dto: NavigationDto): Navigation?

    fun findAll(): List<Navigation>
}

@Service
class NavigationServiceImpl : ServiceImpl<NavigationMapper, Navigation>(), NavigationService {

    override fun create(dto: NavigationDto): Navigation? {
        if (count(asCountQuery(dto)) > 0) {
            throw MessageCodeException(CodeDef.DUPLICATE_RECORD)
        }
        val nav = dto.asNewNavigation()
        return if (save(nav)) nav else null
    }

    override fun update(dto: NavigationDto): Navigation? {
        if (count(asCountQuery(dto)) > 0) {
            throw MessageCodeException(CodeDef.DUPLICATE_RECORD)
        }
        return if (update(dto.asUpdateNavigation())) getById(dto.id) else null
    }

    override fun findAll(): List<Navigation> {
        val query = KtQueryWrapper(Navigation::class.java)
        query.orderByAsc(Navigation::priority)
        return list(query)
    }

    private fun asCountQuery(dto: NavigationDto): KtQueryWrapper<Navigation> {
        val query = KtQueryWrapper(Navigation::class.java)
        query.ne(Navigation::id, dto.id)
            .eq(Navigation::name, dto.name)

        return query
    }
}