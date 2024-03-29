package com.youhajun.ui.models.states

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import com.youhajun.domain.models.enums.GptType
import com.youhajun.domain.models.vo.gpt.GptAssistantVo
import com.youhajun.domain.models.vo.gpt.GptChannelVo
import com.youhajun.domain.models.vo.gpt.GptMessageVo

data class GptState(
    val onLoading: Boolean = false,
    val onError: Boolean = false,
    val selectedGptType: GptType = GptType.CHAT_GPT_3_5_TURBO,
    val selectedRole: String = "",
    val drawerState:DrawerState = DrawerState(initialValue = DrawerValue.Closed),
    val gptChannelList: List<GptChannelVo> = emptyList(),
    val gptTypeList: List<GptType> = GptType.values().filterNot { it.type == GptType.NONE.type },
    val currentGptChannel: GptChannelVo? = null,
    val currentGptAssistants: List<GptAssistantVo> = emptyList(),
    val currentGptMessages: List<GptMessageVo> = emptyList(),
    val isRoleExpanded: Boolean = false,
    val roleList: List<String> = emptyList(),
) {
    val wasSentMessage = currentGptChannel?.let { it.gptType == GptType.NONE } ?: false
}