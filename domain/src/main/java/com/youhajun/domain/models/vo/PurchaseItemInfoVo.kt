package com.youhajun.domain.models.vo

import com.youhajun.data.models.dto.store.PurchaseItemInfo
import com.youhajun.domain.models.Mapper
import com.youhajun.domain.models.enums.SubsGradeType

data class PurchaseItemInfoVo(
    val currentItemCount: Int,
    val currentGrade: SubsGradeType,
    val inAppItems: List<PurchaseInAppItemVo> = emptyList(),
    val subsItems: List<PurchaseSubsItemVo> = emptyList()
) {
    companion object : Mapper.ResponseMapper<PurchaseItemInfo, PurchaseItemInfoVo> {
        override fun mapDtoToModel(type: PurchaseItemInfo): PurchaseItemInfoVo {
            return type.run {
                PurchaseItemInfoVo(
                    currentItemCount = currentItemCount,
                    currentGrade = SubsGradeType.typeOf(currentGrade),
                    inAppItems = inAppItems.map {
                        PurchaseInAppItemVo.mapDtoToModel(it)
                    },
                    subsItems = subsItems.map {
                        PurchaseSubsItemVo.mapDtoToModel(it)
                    }
                )
            }
        }
    }
}