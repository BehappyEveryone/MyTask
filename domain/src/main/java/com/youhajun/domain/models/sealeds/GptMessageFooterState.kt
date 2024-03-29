package com.youhajun.domain.models.sealeds

import com.youhajun.domain.models.vo.gpt.GptRecommendUrlVo

sealed class GptMessageFooterState {
    object ShowCreateAt: GptMessageFooterState()
    data class ShowRecommendingUrl(val recommendUrlVoList: List<GptRecommendUrlVo>) : GptMessageFooterState()
}