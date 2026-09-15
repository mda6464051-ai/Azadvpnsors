package com.freeconfig.vpn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object ConfigFetcher {
    private val SOURCES = mapOf(
        VpnType.V2RAY to listOf(
            "https://raw.githubusercontent.com/barry-far/V2ray-Configs/main/Sub1.txt",
            "https://raw.githubusercontent.com/mahdibland/V2RayAggregator/main/sub/sub_merge.txt"
        ),
        VpnType.SHADOWSOCKS to listOf(
            "https://raw.githubusercontent.com/Epodonios/v2ray-configs/main/Splitted-By-Protocol/shadowsocks.txt"
        ),
        VpnType.TROJAN to listOf(
            "https://raw.githubusercontent.com/Epodonios/v2ray-configs/main/Splitted-By-Protocol/trojan.txt"
        )
    )

    suspend fun fetchAll(): List<VpnConfig> = withContext(Dispatchers.IO) {
        val all = mutableListOf<VpnConfig>()
        SOURCES.forEach { (type, urls) ->
            urls.forEach { url ->
                try {
                    URL(url).readText().lines().forEach { line ->
                        if(line.startsWith("vless://") || line.startsWith("vmess://"))
                            all.add(VpnConfig(line, VpnType.V2RAY))
                        else if(line.startsWith("ss://"))
                            all.add(VpnConfig(line, VpnType.SHADOWSOCKS))
                        else if(line.startsWith("trojan://"))
                            all.add(VpnConfig(line, VpnType.TROJAN))
                    }
                } catch(e: Exception) {}
            }
        }
        all.shuffled().take(150)
    }
}
