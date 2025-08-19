package com.bussiness.slodoggiesapp.ui.screens.petowner.post.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.VisibilityOptionsSelector
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration.UploadPlaceholder
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@Composable
fun PetPostScreenContent( onClickLocation: () -> Unit,onClickPost: () -> Unit,viewModel: PostContentViewModel = hiltViewModel()) {

    val writePost by viewModel.writePost.collectAsState()
    val hashtags by viewModel.hashtags.collectAsState()
    val postalCode by viewModel.postalCode.collectAsState()
    val visibility by viewModel.visibility.collectAsState()
    // Add state for selected people
    val selectedPeople = viewModel.selectedPeople


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // Add the WhosThisPostAbout section at the top
        WhosThisPostAbout(
            selectedPeople = selectedPeople,
            onAddPersonClick = { /* Handle adding a person */ },
            onPersonClick = { person ->
                /* Handle person click, maybe remove them */
                viewModel.removePerson(person)
            }
        )
        FormHeadingText("Upload Media")

        Spacer(Modifier.height(10.dp))

        UploadPlaceholder()

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Write Post")

        Spacer(Modifier.height(10.dp))

        InputField(modifier = Modifier.height(106.dp), placeholder = "Enter Description", input = writePost, onValueChange ={ viewModel.updateWritePost(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Hashtags")

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Enter Hashtags", input = hashtags, onValueChange ={viewModel.updateHashtags(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Location")

        Spacer(Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onClickLocation() }) {
            Icon(
                painter = painterResource(id = R.drawable.precise_loc),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.wrapContentSize()
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "use my current location", fontFamily = FontFamily(Font(R.font.poppins)), fontSize = 12.sp, color = Color.Black)
        }

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Postal Code", input = postalCode, onValueChange ={ viewModel.updatePostalCode(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Privacy Settings")

        Spacer(Modifier.height(10.dp))

        VisibilityOptionsSelector(selected = visibility, onOptionSelected = { viewModel.updateVisibility(it) })

        Spacer(Modifier.height(15.dp))

        SubmitButton(modifier = Modifier, buttonText = "Post", onClickButton = { onClickPost() })
        Spacer(Modifier.height(30.dp))
    }
}

@Composable
fun WhosThisPostAbout(
    selectedPeople: List<Person> = emptyList(),
    onAddPersonClick: () -> Unit = {},
    onPersonClick: (Person) -> Unit = {}
) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Who's This Post About?",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


                // Selected people
                items(selectedPeople) { person ->
                    PersonItem(
                        person = person,
                        onClick = { onPersonClick(person) }
                    )
                }
                // Add button (always first)
                item {
                    AddPersonButton(onClick = onAddPersonClick)
                }
            }
        }

}

@Composable
fun AddPersonButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(64.dp).height(78.dp) // Adjusted size to match image
            ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize().padding(horizontal = 5.dp, vertical = 8.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp) // Circle size
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF258694), // Blue border
                        shape = CircleShape
                    )
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Person",
                    tint = Color(0xFF258694),
                    modifier = Modifier.size(17.dp) // Smaller icon to match image
                )
            }
        }
    }
}


@Composable
fun PersonItem(
    person: Person,
    onClick: () -> Unit
) {

        Card(
        modifier = Modifier
            .wrapContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
    Column(

        modifier = Modifier.padding(horizontal = (7.5).dp , vertical = 8.dp).clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = person.imageUrl,
            contentDescription = person.name,
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.dummy_baby_pic),
            error = painterResource(id = R.drawable.dummy_baby_pic)
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = person.name,
            fontSize = 12.sp,
            color = Color.Black,
            maxLines = 1,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            overflow = TextOverflow.Ellipsis
        )
    }
}
}

// Data class for Person
data class Person(
    val id: String,
    val name: String,
    val imageUrl: String
)

// Preview with empty state
@Preview
@Composable
fun WhosThisPostAboutEmptyPreview() {
    WhosThisPostAbout()
}

// Preview with selected people
@Preview
@Composable
fun WhosThisPostAboutWithPeoplePreview() {
    val samplePeople = listOf(
        Person("1", "Jimmy", "https://example.com/jimmy.jpg"),
        Person("2", "Barry", "https://example.com/barry.jpg"),
        Person("3", "Bill", "https://example.com/bill.jpg"),
        Person("4", "Julia", "https://example.com/julia.jpg")
    )

    WhosThisPostAbout(selectedPeople = samplePeople)
}
@Preview(showBackground = true)
@Composable
fun PetPostScreenContentPreview() {
    PetPostScreenContent(
        onClickLocation = { /* Handle location click */ },
        onClickPost = { /* Handle post click */ }
    )
}
