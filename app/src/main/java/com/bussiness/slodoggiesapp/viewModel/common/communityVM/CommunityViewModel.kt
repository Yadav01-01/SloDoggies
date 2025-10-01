package com.bussiness.slodoggiesapp.viewModel.common.communityVM

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.Participants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CommunityProfileViewModel @Inject constructor() : ViewModel() {

    private val _communityName = MutableStateFlow("Event Community 1")
    val communityName: StateFlow<String> = _communityName

    private val _participants = MutableStateFlow<List<Participants>>(emptyList())
    val participants: StateFlow<List<Participants>> = _participants

    private val _menuDialog = MutableStateFlow(false)
    val menuDialog: StateFlow<Boolean> = _menuDialog

    private val _showEditNameDialog = MutableStateFlow(false)
    val showEditNameDialog: StateFlow<Boolean> = _showEditNameDialog

    private val _removeDialog = MutableStateFlow(false)
    val removeDialog: StateFlow<Boolean> = _removeDialog

    init {
        loadParticipants()
    }

    private fun loadParticipants() {
        _participants.value = listOf(
            Participants(R.drawable.lady_dm, "Lydia Vaccaro"),
            Participants(R.drawable.lady_dm, "Anika Torff"),
            Participants(R.drawable.lady_dm, "Zain Dorwart"),
            Participants(R.drawable.lady_ic, "Lydia Vaccaro"),
            Participants(R.drawable.lady_dm, "Kierra Westervelt"),
            Participants(R.drawable.lady_dm, "Ryan Dias"),
            Participants(R.drawable.lady_ic, "Lydia Vaccaro"),
            Participants(R.drawable.lady_dm, "Ryan Dias"),
            Participants(R.drawable.lady_dm, "Kierra Westervelt"),
        )
    }

    fun toggleMenuDialog(show: Boolean) {
        _menuDialog.value = show
    }

    fun toggleEditNameDialog(show: Boolean) {
        _showEditNameDialog.value = show
    }

    fun toggleRemoveDialog(show: Boolean) {
        _removeDialog.value = show
    }

    fun updateCommunityName(name: String) {
        _communityName.value = name.take(30)
    }
}
