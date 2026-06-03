package com.example.proyectofinal.data.repository

import com.example.proyectofinal.data.firebase.FirebaseAuthService
import com.example.proyectofinal.data.model.UserModel
import com.google.firebase.auth.FirebaseUser

class AuthRepository(
    private val firebaseAuthService: com.example.proyectofinal.data.firebase.FirebaseAuthService = _root_ide_package_.com.example.proyectofinal.data.firebase.FirebaseAuthService()
) {

    val isUserLoggedIn: Boolean get() = firebaseAuthService.isUserLoggedIn()

    val currentUser: com.example.proyectofinal.data.model.UserModel?
        get() = firebaseAuthService.currentUser?.toUserModel()

    suspend fun login(email: String, password: String): Result<com.example.proyectofinal.data.model.UserModel> {
        return firebaseAuthService.login(email, password).map { it.toUserModel() }
    }

    suspend fun register(email: String, password: String): Result<com.example.proyectofinal.data.model.UserModel> {
        return firebaseAuthService.register(email, password).map { it.toUserModel() }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        return firebaseAuthService.resetPassword(email)
    }

    fun logout() {
        firebaseAuthService.logout()
    }

    private fun FirebaseUser.toUserModel() =
        _root_ide_package_.com.example.proyectofinal.data.model.UserModel(
            uid = uid,
            email = email ?: "",
            displayName = displayName ?: "",
            photoUrl = photoUrl?.toString() ?: ""
        )
}