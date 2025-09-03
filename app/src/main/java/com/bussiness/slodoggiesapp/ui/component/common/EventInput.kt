package com.bussiness.slodoggiesapp.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.Event
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun EventCard(
    event: Event,
    selectedOption: String,
    onButtonClick: (Event) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Light gray background
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Network image
            AsyncImage(
                model = event.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.dog3),
                error = painterResource(R.drawable.dog3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            // Text Content
            Column(modifier = Modifier.padding(12.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.cal_ic),
                            contentDescription = "Calendar",
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = event.dateTime,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontSize = 14.sp,
                        color = TextGrey,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.time_ic),
                            contentDescription = "Duration",
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = event.duration, style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.location_ic),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.location,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onButtonClick(event) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(text = if (selectedOption == "My Events") "View Community Chats" else "Join Community Chats"
                        , color = Color.White,fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 14.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewEventCard() {
    val sampleEvent = Event(
        id = "1",
        imageUrl = "https://via.placeholder.com/600x400.png?text=Event+Image",
        title = "Event Title",
        description = "Lorem ipsum dolor sit a...",
        dateTime = "May 25, 4:00 PM",
        duration = "30 Mins.",
        location = "San Luis Obispo County",
    )
    EventCard(event = sampleEvent, selectedOption = "My Events", onButtonClick = {})
}
