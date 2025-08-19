package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.model.common.Participant
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddParticipantViewModel @Inject constructor() : ViewModel() {

    // Search query
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // All participants (normally fetched from API)
    private val _allParticipants = MutableStateFlow<List<Participant>>(emptyList())

    // Selected participants
    private val _selectedParticipants = MutableStateFlow<List<Participant>>(emptyList())
    val selectedParticipants: StateFlow<List<Participant>> = _selectedParticipants.asStateFlow()

    // Filtered suggestions (excluding selected)
    val filteredSuggestions: StateFlow<List<Participant>> = combine(
        _allParticipants,
        _selectedParticipants,
        _query
    ) { all, selected, searchText ->
        all.filter { participant ->
            participant !in selected &&
                    participant.name.contains(searchText, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        // Simulated data load (replace with API call)
        viewModelScope.launch {
            _allParticipants.value = listOf(
                Participant("1", "Layla Vaccaro", "https://i.pravatar.cc/150?img=1"),
                Participant("2", "Aayeh Ormsal", "https://i.pravatar.cc/150?img=2"),
                Participant("3", "Jodie Yaroch", "https://i.pravatar.cc/150?img=3"),
                Participant("4", "Brandon Stoud", "https://i.pravatar.cc/150?img=4"),
                Participant("5", "Cristofer Siphron", "https://i.pravatar.cc/150?img=5"),
                Participant("6", "Gretchen Carder", "https://i.pravatar.cc/150?img=6"),
                Participant("7", "Madeleine Francis", "https://i.pravatar.cc/150?img=7"),
                Participant("8", "Marilyn Press", "https://i.pravatar.cc/150?img=8")
            )
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun selectParticipant(participant: Participant) {
        if (participant !in _selectedParticipants.value) {
            _selectedParticipants.value += participant
        }
    }

    fun removeParticipant(participant: Participant) {
        _selectedParticipants.value -= participant
    }
}
