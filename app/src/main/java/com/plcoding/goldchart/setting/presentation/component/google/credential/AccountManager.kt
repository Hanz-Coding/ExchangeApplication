package com.plcoding.goldchart.setting.presentation.component.google.credential

import android.app.Activity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException

class AccountManager(
    private val activity: Activity,
) {
    private val credentialManager = CredentialManager.create(activity)

    suspend fun signUp(userName: String, password: String): SignUpResult {
        return try {
            credentialManager.createCredential(
                context = activity,
                request = CreatePasswordRequest(
                    id = userName,
                    password = password
                )
            )
            SignUpResult.Success(userName)
        } catch (e: CreateCredentialCancellationException) {
            e.printStackTrace()
            SignUpResult.Cancelled
        } catch (e: Exception) {
            e.printStackTrace()
            SignUpResult.Failure
        }
    }

    suspend fun signIn(): SignInResult {
        return try {
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )
            val credential =
                credentialResponse.credential as? PasswordCredential ?: return SignInResult.Failure
            SignInResult.Success(credential.id)

        } catch (e: GetCredentialCancellationException) {
            e.printStackTrace()
            SignInResult.Cancelled
        } catch (e: NoCredentialException) {
            e.printStackTrace()
            SignInResult.NoCredential
        } catch (e: Exception) {
            e.printStackTrace()
            SignInResult.Failure
        }
    }

    suspend fun signOut() {
        return try {
            credentialManager.clearCredentialState(
                request = ClearCredentialStateRequest()
            )
        } catch (e: ClearCredentialException) {

        }catch (e: Exception) {

        }
    }

}