package com.example.kotlin_test1

import com.google.android.gms.maps.model.LatLng

data class SupplyStation(
    val spotId: String,
    val position: LatLng,
    var nextClaimTime: Long? = null
) {
    private val cooldownMillis = 15 * 60 * 1000L // 15 分鐘 (要換算成毫秒)

    fun isAvailable(): Boolean {
        val now = System.currentTimeMillis()
        return nextClaimTime?.let { it + cooldownMillis < now } ?: true
    }

    fun remainingCooldownMillis(): Long {
        val now = System.currentTimeMillis()
        return nextClaimTime?.let { (it + cooldownMillis) - now } ?: 0L
    }

    fun formattedRemainingCooldown(): String {
        val totalSeconds = remainingCooldownMillis() / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "${minutes}分${seconds}秒"
    }
}
