package com.example.questtugasuserinput_0111

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material3.SnackbarDuration
import java.util.Locale
import java.util.TimeZone


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
    val skillLabels = skillOptions.associateWith { stringResource(it) }
    val skillSeparator = stringResource(R.string.separator_comma)
    val dateFormat = stringResource(R.string.date_format)
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
    val skillText = skills.joinToString(skillSeparator) { skillLabels.getValue(it) }
    val message = stringResource(
        R.string.msg_saved,
        namaDepan, namaBelakang, email, tglLahir, experience, skillText
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Lapisan bawah: gambar background
        Image(
            painter = painterResource(R.drawable.background_bunga),
            contentDescription = stringResource(R.string.cd_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.bg_height))
        )

        // Box yang mengapung di atas Scaffold
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimensionResource(R.dimen.card_top_offset))
                .shadow(
                    elevation = dimensionResource(R.dimen.card_elevation),
                    shape = cardShape
                )
                .background(
                    color = colorResource(R.color.card_background),
                    shape = cardShape
                )
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_screen)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_field))
            ) {
                Text(
                    text = stringResource(R.string.form_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = colorResource(R.color.primary_title),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.form_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(R.color.text_subtitle)
                )

                // Nama Depan + Nama Belakang
                Row (horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_field))) {
                    OutlinedTextField(
                        value = namaDepan,
                        onValueChange = { namaDepan = it },
                        label = { Text(stringResource(R.string.lbl_first_name)) },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = namaBelakang,
                        onValueChange = { namaBelakang = it },
                        label = { Text(stringResource(R.string.lbl_last_name)) },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.lbl_email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = fieldShape,
                    modifier = Modifier.fillMaxWidth()
                )

                // Tgl Lahir + Experience
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_field))) {
                    val interaction = remember { MutableInteractionSource() }
                    val pressed by interaction.collectIsPressedAsState()
                    LaunchedEffect(pressed) { if (pressed) showDatePicker = true }

                    OutlinedTextField(
                        value = tglLahir,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.lbl_birth_date)) },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_calendar),
                                contentDescription = stringResource(R.string.cd_pick_date)
                            )
                        },
                        interactionSource = interaction,
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.weight(1f)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = experience,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(stringResource(R.string.lbl_experience)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            singleLine = true,
                            shape = fieldShape,
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            experienceOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        experience = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Skill
                Text(
                    text = stringResource(R.string.lbl_skills),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    skillOptions.forEach { skillRes ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = skills.contains(skillRes),
                                onCheckedChange = {
                                    if (it) skills.add(skillRes) else skills.remove(skillRes)
                                }
                            )
                            Text(
                                text = stringResource(skillRes),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                // Tombol Proceed
                Button (
                    onClick = {
                        scope.launch {
                            // Snackbar berisi data, tampil selama durasi dari resource (5 detik)
                            withTimeoutOrNull(snackbarDurationMs) {
                                snackbarHostState.showSnackbar(
                                    message = message,
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }
                    },
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = colorResource(R.color.button_disabled),
                        disabledContentColor = colorResource(R.color.button_disabled_text)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.button_height))
                ) {
                    Text(stringResource(R.string.btn_proceed))
                }
            }
        }
    }

    // DatePickerDialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat(dateFormat, Locale("id", "ID")).apply {
                            timeZone = TimeZone.getTimeZone("UTC")
                        }
                        tglLahir = formatter.format(Date(millis))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.btn_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.btn_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}