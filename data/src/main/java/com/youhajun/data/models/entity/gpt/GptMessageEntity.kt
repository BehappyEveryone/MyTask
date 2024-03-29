package com.youhajun.data.models.entity.gpt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.youhajun.data.models.entity.EntityTable

@Entity(
    tableName = EntityTable.GPT_MESSAGE,
    foreignKeys = [ForeignKey(
        entity = GptChannelEntity::class,
        parentColumns = arrayOf(GptChannelEntity.COLUMN_NAME_IDX),
        childColumns = arrayOf(GptMessageEntity.COLUMN_NAME_CHANNEL_IDX),
        onDelete = ForeignKey.CASCADE
    )]
)
data class GptMessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_IDX)
    val idx: Long = 0,
    @ColumnInfo(name = COLUMN_NAME_CHANNEL_IDX)
    val channelIdx: Long,
    @ColumnInfo(name = COLUMN_NAME_GPT_MESSAGE_TYPE)
    val gptMessageType: String,
    @ColumnInfo(name = COLUMN_NAME_GPT_MESSAGE)
    val message: String,
    @ColumnInfo(name = COLUMN_NAME_CREATED_AT_UNIX_TIME)
    val createdAtUnixTimeStamp: Long,
) {

    companion object {
        const val COLUMN_NAME_IDX = "idx"
        const val COLUMN_NAME_CHANNEL_IDX = "channelIdx"
        const val COLUMN_NAME_GPT_MESSAGE_TYPE = "gptMessageType"
        const val COLUMN_NAME_GPT_MESSAGE = "message"
        const val COLUMN_NAME_CREATED_AT_UNIX_TIME = "createdAt"
    }
}