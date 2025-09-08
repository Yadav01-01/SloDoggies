package com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem


@Composable
fun ActivitiesPostsList(
    posts: List<PostItem>,
    onReportClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Spacer(Modifier.height(5.dp))
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
                    onShareClick = onShareClick
                )
            }
        }
    }
}
