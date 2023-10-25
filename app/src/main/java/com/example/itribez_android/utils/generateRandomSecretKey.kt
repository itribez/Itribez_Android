package com.example.itribez_android.utils


import android.os.Build
import androidx.annotation.RequiresApi
import java.security.SecureRandom
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
fun generateRandomSecretKey(length: Int): String {
    val random = SecureRandom()
    val keyBytes = ByteArray(length)
    random.nextBytes(keyBytes)
    return Base64.getEncoder().encodeToString(keyBytes)
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val secretKey = generateRandomSecretKey(32) // Generate a 32-character key
    println("Generated Secret Key: $secretKey")
}
