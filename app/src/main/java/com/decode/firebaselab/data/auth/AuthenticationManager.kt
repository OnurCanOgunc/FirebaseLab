package com.decode.firebaselab.data.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.decode.firebaselab.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID

class AuthenticationManager(private val context: Context) {
    val auth = Firebase.auth
    private val webClientId = BuildConfig.WEB_CLIENT_ID

    fun createAccountWithEmailAndPassword(fullName: String, email: String, password: String): Flow<AuthResponse> =
        callbackFlow {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()
                    auth.currentUser?.updateProfile(profileUpdate)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Log.d("SignUp", "User profile updated successfully.")
                            } else {
                                Log.e(
                                    "SignUp",
                                    "User profile update failed: ${updateTask.exception?.message}"
                                )
                            }
                        }
                    Log.d("SignUp", "User created successfully.")
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Failure(it.exception?.message ?: "Unknown Error"))
                }
            }
            awaitClose()
        }

    fun loginWithEmailAndPassword(email: String, password: String): Flow<AuthResponse> =
        callbackFlow {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Failure(it.exception?.message ?: "Unknown Error"))
                }
            }
            awaitClose()
        }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    fun signInWithGoogle(): Flow<AuthResponse> = callbackFlow {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        try {
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(context = context, request = request)

            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
                    val firebaseCredential = GoogleAuthProvider.getCredential(
                        googleIdToken.idToken,
                        null
                    )
                    auth.signInWithCredential(firebaseCredential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            trySend(AuthResponse.Success)
                        } else {
                            trySend(AuthResponse.Failure(it.exception?.message ?: "Unknown Error"))
                        }
                    }
                } catch (e: GoogleIdTokenParsingException) {
                    trySend(AuthResponse.Failure(e.message ?: "Unknown Error"))
                }
            }

        } catch (e: Exception) {
            trySend(AuthResponse.Failure(e.message ?: "Unknown Error"))
        }
        awaitClose()
    }
}

sealed interface AuthResponse {
    data object Success : AuthResponse
    data class Failure(val message: String) : AuthResponse
}