package com.decode.firebaselab.domain

import com.decode.firebaselab.data.Repository

class CanAccessToApp {

    val repository = Repository()

    suspend operator fun invoke(): Boolean {
        val currentVersion = repository.getCurrentVersion()
        val minAllowedVersion = repository.getMinAllowedVersion()
        for((currentPart, minVersionPart) in currentVersion.zip(minAllowedVersion)){
            if(currentPart!=minVersionPart){
                return currentPart > minVersionPart
            }
        }
        return true
    }
}