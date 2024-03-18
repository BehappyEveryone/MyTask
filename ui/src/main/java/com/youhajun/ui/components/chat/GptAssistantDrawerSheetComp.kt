package com.youhajun.ui.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AddComment
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youhajun.domain.models.enums.GptType
import com.youhajun.domain.models.vo.gpt.GptAssistantVo
import com.youhajun.ui.R

@Composable
fun GptAssistantDrawerSheetComp(
    gptAssistantList: List<GptAssistantVo>,
    currentAssistantIdx: Long?,
    onClickAssistant: (idx: Long) -> Unit,
    onClickDeleteAssistant: (idx: Long) -> Unit,
    onClickCreateAssistant: () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalDrawerSheet(
            drawerShape = RectangleShape,
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight()
                .background(color = colorResource(id = R.color.color_e6f4fa))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {


                Text(
                    text = stringResource(id = R.string.gpt_drawer_title),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W800,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Blue)
                        .padding(vertical = 10.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color.White)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.gpt_drawer_list_title),
                        fontSize = 13.sp,
                        color = Color.Gray,
                    )

                    Icon(
                        Icons.Outlined.AddComment,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onClickCreateAssistant() }
                            .size(18.dp),
                        tint = Color.Gray
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 4.dp, vertical = 2.dp),

                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        items = gptAssistantList,
                        key = { it.assistantIdx },
                    ) { item ->
                        val isCurrentAssistant = item.assistantIdx == currentAssistantIdx
                        GptAssistantDrawerItemComp(
                            item,
                            isCurrentAssistant,
                            onClickAssistant,
                            onClickDeleteAssistant
                        )
                    }
                }
            }
        }
    }
}