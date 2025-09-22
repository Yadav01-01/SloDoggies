package com.bussiness.slodoggiesapp.ui.screens.petowner.post.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DescriptionBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.FillPetInfoDialog
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@Composable
fun PetPostScreenContent( onClickLocation: () -> Unit,addPetClick: () -> Unit,onClickPost: () -> Unit,viewModel: PostContentViewModel = hiltViewModel()) {

    val writePost by viewModel.writePost.collectAsState()
    val hashtags by viewModel.hashtags.collectAsState()
    val postalCode by viewModel.postalCode.collectAsState()
    var showPetInfoDialog by remember { mutableStateOf(false) }
    var petAddedSuccessDialog by remember { mutableStateOf(false) }
    // Add state for selected people
    val successDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 5.dp)
    ) {

        // Add the WhosThisPostAbout section at the top
        WhosThisPostAbout(
            selectedPet = viewModel.selectedPet,
            allPets = samplePeople,
            onAddPersonClick = { showPetInfoDialog = true },
            onPersonClick = { pet -> viewModel.selectPerson(pet) }
        )

        FormHeadingText(stringResource(R.string.Upload_Media))

        Spacer(Modifier.height(10.dp))

        MediaUploadSection(maxImages = 10) { uri ->
            // This lambda is called whenever a new image is selected
            // You can now send this URI to your API or store in ViewModel
//            viewModel.addPetImage(uri)
        }

        Spacer(Modifier.height(15.dp))

        FormHeadingText(stringResource(R.string.Write_Post))

        Spacer(Modifier.height(10.dp))

        DescriptionBox(placeholder = stringResource(R.string.Enter_Description), value = writePost, onValueChange ={ viewModel.updateWritePost(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText(stringResource(R.string.Hashtags))

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = stringResource(R.string.Enter_Hashtags), input = hashtags, onValueChange ={viewModel.updateHashtags(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText(stringResource(R.string.Zip_Code))

        Spacer(Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onClickLocation() }) {
            Icon(
                painter = painterResource(id = R.drawable.precise_loc),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.wrapContentSize()
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = stringResource(R.string.use_my_current_location), fontFamily = FontFamily(Font(R.font.poppins)), fontSize = 12.sp, color = Color.Black)
        }

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = stringResource(R.string.enter_your_zip_code), input = postalCode, onValueChange ={ viewModel.updatePostalCode(it)})

        Spacer(Modifier.height(35.dp))

        SubmitButton(modifier = Modifier, buttonText = stringResource(R.string.post), onClickButton = { onClickPost() })
        Spacer(Modifier.height(30.dp))
    }
    if (showPetInfoDialog) {
        FillPetInfoDialog(
            "Add Your Pet",
            onDismiss = { showPetInfoDialog = false },
            onAddPet = {
                // Handle pet info saving
                showPetInfoDialog = false
                petAddedSuccessDialog = true
            },
            onCancel = { showPetInfoDialog = false },
            onProfile = true
        )
    }
    if (petAddedSuccessDialog){
        UpdatedDialogWithExternalClose(onDismiss = { petAddedSuccessDialog = false }, iconResId = R.drawable.ic_sucess_p, text = "Pet Added Successfully!",
            description = "Thanks for keeping things up to date!")
    }
}

@Composable
fun WhosThisPostAbout(
    selectedPet: Person? = null,
    allPets: List<Person> = emptyList(),
    onAddPersonClick: () -> Unit = {},
    onPersonClick: (Person) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.whos_this_post_about),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Reorder pets: selected first, others after
        val reorderedPets = if (selectedPet != null) {
            listOf(selectedPet) + allPets.filter { it.id != selectedPet.id }
        } else {
            allPets
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                AddPersonButton(onClick = onAddPersonClick)
            }

            items(reorderedPets) { pet ->
                PersonItem(
                    person = pet,
                    selected = pet.id == selectedPet?.id,
                    onClick = { onPersonClick(pet) }
                )
            }
        }
    }
}




@Composable
fun AddPersonButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(65.dp)
            .height(78.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE5EFF2),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = PrimaryColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Person",
                tint = PrimaryColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}



@Composable
fun PersonItem(
    person: Person,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .height(78.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) PrimaryColor else Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE5EFF2),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = person.imageUrl,
            contentDescription = person.name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.dummy_baby_pic),
            error = painterResource(id = R.drawable.dummy_baby_pic)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = person.name,
            fontSize = 12.sp,
            color = if (selected) Color.White else Color.Black,
            maxLines = 1,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            overflow = TextOverflow.Ellipsis
        )
    }
}

val samplePeople = listOf(
    Person("1", "Jimmy", "https://example.com/jimmy.jpg"),
    Person("2", "Barry", "https://example.com/barry.jpg"),
    Person("3", "Bill", "https://example.com/bill.jpg"),
    Person("4", "Julia", "https://example.com/julia.jpg"),
    Person("5", "velit", "https://example.com/julia.jpg"),
    Person("6", "Julia", "https://example.com/julia.jpg"),
    Person("7", "velit", "https://example.com/julia.jpg"),
    Person("8", "Bill", "https://example.com/julia.jpg"),
    Person("9", "Julia", "https://example.com/julia.jpg"),
)

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


}
@Preview(showBackground = true)
@Composable
fun PetPostScreenContentPreview() {
    PetPostScreenContent(
        onClickLocation = { /* Handle location click */ },
        onClickPost = { /* Handle post click */ },
        addPetClick = { /* Handle add pet click */ }
    )
}
