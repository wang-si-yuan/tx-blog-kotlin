package io.archx.txblog.data.service

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.data.domain.User
import io.archx.txblog.data.mapper.UserMapper
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import java.security.SecureRandom
import java.util.*

interface UserService : IService<User> {

    /**
     * 初始化管理员
     */
    fun init(): User?

    /**
     * 根据用户查找用户
     */
    fun findByUsername(username: String): User?

    /**
     * 修改密码
     */
    fun changepwd(id: Int, checkpwd: String, password: String)
}

@Service
class UserServiceImpl : ServiceImpl<UserMapper, User>(), UserService {

    override fun init(): User? {
        val admin = getById(1)
        if (admin != null) {
            return admin
        }

        // 初始化 密码123456
        val salt = createSalt()
        val password = DigestUtils.md5DigestAsHex(("123456\n$salt").byteInputStream())
        val u = User(1, "admin", password, salt, "admin@example.com", 0, DateUtils.timestamp())

        return if (save(u)) u else null
    }

    override fun findByUsername(username: String): User? {
        return baseMapper.findByUsername(username)
    }

    override fun changepwd(id: Int, checkpwd: String, password: String) {
        val u = getById(id) ?: throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)

        if (u.password == DigestUtils.md5DigestAsHex((checkpwd + "\n" + u.salt).byteInputStream())) {
            val salt = createSalt()
            val npwd = DigestUtils.md5DigestAsHex((password + "\n" + salt).byteInputStream())

            val update = KtUpdateWrapper(User::class.java)
            update.eq(User::id, id).set(User::password, npwd).set(User::salt, salt).set(User::updatedAt, DateUtils.timestamp())

            update(update)
        } else {
            throw MessageCodeException(CodeDef.INVALID_CERTIFICATE)
        }

    }

    private fun createSalt(): String {
        val salt = ByteArray(16)
        val r = SecureRandom()
        r.nextBytes(salt)

        return Base64.getEncoder().encodeToString(salt)
    }
}