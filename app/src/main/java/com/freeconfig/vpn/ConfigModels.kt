package com.freeconfig.vpn
enum class VpnType { V2RAY, SHADOWSOCKS, TROJAN }
data class VpnConfig(
    val link: String,
    val type: VpnType,
    var ping: Int = 9999,
    var isWorking: Boolean = true
)
