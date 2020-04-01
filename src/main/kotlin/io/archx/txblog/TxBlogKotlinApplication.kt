package io.archx.txblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TxBlogKotlinApplication

fun main(args: Array<String>) {
    runApplication<TxBlogKotlinApplication>(*args)
}
