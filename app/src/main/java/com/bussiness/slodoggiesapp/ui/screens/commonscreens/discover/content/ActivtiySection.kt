package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor


@Composable
fun ActivitiesPostsList(
    posts: List<PostItem>,
    onReportClick: () -> Unit,
    onShareClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Spacer(Modifier.height(5.dp))
    if (posts.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.ic_pet_post_icon),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "OOps!,No Activity found",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryColor,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 18.sp
                )
            }
        }
    }else{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(posts) { post ->
                if (post is PostItem.NormalPost) {
                    NormalPostItem(
                        postItem = post,
                        modifier = Modifier.padding(0.dp),
                        onReportClick = onReportClick,
                        onShareClick = onShareClick,
                        normalPost = true,
                        onEditClick = { /* Handle edit click */ },
                        onDeleteClick = { /* Handle delete click */ },
                        onProfileClick = { onProfileClick() },
                        onSelfPostEdit = { /* Handle self post edit click */ },
                        onSelfPostDelete = { /* Handle self post delete click */ },
                        onSaveClick = {  },
                        onLikeClick = {  },
                        onCommentClick = {  },
                        onFollowingClick = {  }
                    )
                }
            }
        }
    }
}
