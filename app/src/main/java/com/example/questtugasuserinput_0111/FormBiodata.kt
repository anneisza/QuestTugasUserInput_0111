package com.example.questtugasuserinput_0111

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBiodata(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
){
    //State input
    var namaDepan by remember { mutableStateOf("") }
    var namaBelakang by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tglLahir by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    val skills = remember { mutableStateListOf<Int>() }

    // State UI
    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val scope = rememberCoroutineScope()

    // Data dari resources
    val experienceOptions = stringArrayResource(R.array.experience_option)
    val skillOptions = listOf(R.string.skill_android, R.string.skill_UI_UX, R.string.skill_web)
    val dateFormat = stringResource(R.string.date_format)
    val msgSaved = stringResource(R.string.msg_saved)
    val snackbarDurationMs = integerResource(R.integer.snackbar_duration_ms).toLong()

    // Shape dari dimens
    val fieldShape = RoundedCornerShape(dimensionResource(R.dimen.field_corner))
    val cardShape = RoundedCornerShape(
        topStart = dimensionResource(R.dimen.card_corner),
        topEnd = dimensionResource(R.dimen.card_corner)
    )

    val isFormValid = namaDepan.isNotBlank() && namaBelakang.isNotBlank() &&
            email.isNotBlank() && tglLahir.isNotBlank() &&
            experience.isNotBlank() && skills.isNotEmpty()
}