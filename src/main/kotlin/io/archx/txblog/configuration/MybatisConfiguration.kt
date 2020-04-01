package io.archx.txblog.configuration

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer
import com.baomidou.mybatisplus.core.MybatisConfiguration
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import org.apache.ibatis.type.EnumOrdinalTypeHandler
import org.apache.ibatis.type.JdbcType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MybatisConfiguration {

    // -------------------------------
    // | MyBatis Plus Configuration  |
    // -------------------------------

    // MyBatis XML 配置替代
    @Bean
    fun buildConfigurationCustomizer(): ConfigurationCustomizer? {
        return ConfigurationCustomizer { configuration: MybatisConfiguration ->
            // 开启驼峰命名转换字段
            configuration.isMapUnderscoreToCamelCase = true
            // 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。
            configuration.isAggressiveLazyLoading = true
            configuration.defaultStatementTimeout = 25
            configuration.defaultFetchSize = 100
            // 默认为OTHER,为了解决oracle插入null报错的问题要设置为NULL
            configuration.jdbcTypeForNull = JdbcType.NULL
            configuration.setDefaultEnumTypeHandler(EnumOrdinalTypeHandler::class.java)
        }
    }

    // 分页插件
    @Bean
    fun paginationInterceptor(): PaginationInterceptor? {
        return PaginationInterceptor()
    }

}