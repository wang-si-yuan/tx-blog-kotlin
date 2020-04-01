package io.archx.txblog.web.controller

import io.archx.txblog.data.service.PostService
import io.archx.txblog.web.bind.annotation.ViewResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ViewController(val postService: PostService) {

    @GetMapping(value = ["/", "index"])
    @ViewResponse
    fun index(@RequestParam("keywords", required = false) searchText: String?, model: Model): String {
        model["title"] = "TxBlog 首页, 一个简单的博客程序"
        model["keywords"] = searchText ?: ""
        model["active"] = 0
        return "index"
    }

    @GetMapping("/s/{link}")
    @ViewResponse
    fun s(@PathVariable("link") link: String, model: Model): String {
        val post = postService.findByShortLink(link) ?: return "404"
        model["title"] = post.title + " TxBlog"
        model["link"] = post.shortLink
        model["id"] = post.id
        return "detail"
    }

    @GetMapping("/category/{id}")
    @ViewResponse
    fun category(@PathVariable("id") id: Int, @RequestParam("active", defaultValue = "-1") active: Int, model: Model): String {
        setup(model, active, id, "c")
        return "index"
    }

    @GetMapping("/tag/{id}")
    @ViewResponse
    fun tag(@PathVariable("id") id: Int, @RequestParam("active", defaultValue = "-1") active: Int, model: Model): String {
        setup(model, active, id, "t")
        return "index"
    }

    private fun setup(model: Model, active: Int, id: Int, type: String) {
        model["title"] = "TxBlog 首页, 一个简单的博客程序"
        model["keywords"] = ""
        model["active"] = active
        model["type"] = type
        model["id"] = id
    }
}