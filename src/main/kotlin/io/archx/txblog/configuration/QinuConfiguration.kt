package io.archx.txblog.configuration

import io.archx.txblog.common.qiniu.Kodo
import io.archx.txblog.configuration.bean.QiniuOption
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QinuConfiguration/*(@Value("\${tiny.png.api-key}") var tinyPNGApiKey: String)*/ {

    @Bean
    @ConfigurationProperties(prefix = "qns")
    fun qiniuOption() = QiniuOption()

    @Bean
    fun kodo(option: QiniuOption) = Kodo(option)
}