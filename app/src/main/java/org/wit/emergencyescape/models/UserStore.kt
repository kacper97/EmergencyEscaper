package org.wit.emergencyescape.models

interface UserStore {
    fun findAllUsers():List<UserModel>
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

    fun findbyId(user: UserModel, id: Long): LocationModel?
}