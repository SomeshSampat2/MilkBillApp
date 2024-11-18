package com.example.milkbillcalculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.milkbillcalculator.ui.theme.*
import com.example.milkbillcalculator.data.MilkEntry
import com.example.milkbillcalculator.data.MilkType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MilkEntryCard(
    entry: MilkEntry,
    onDeleteClick: () -> Unit,
    onDeliveryStatusChange: (Boolean) -> Unit,
    onUpdateEntry: (MilkEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedLitres by remember { mutableStateOf(entry.litres.toString()) }
    var editedPrice by remember { mutableStateOf(entry.pricePerLitre.toString()) }
    var editedType by remember { mutableStateOf(entry.milkType) }

    val cardGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF8F9FF)
        )
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .background(cardGradient)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with date and actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = entry.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryBlue
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Edit/Save Button
                        IconButton(
                            onClick = {
                                if (isEditing) {
                                    val newLitres = editedLitres.toDoubleOrNull()
                                    val newPrice = editedPrice.toDoubleOrNull()
                                    if (newLitres != null && newPrice != null) {
                                        onUpdateEntry(
                                            entry.copy(
                                                milkType = editedType,
                                                litres = newLitres,
                                                pricePerLitre = newPrice
                                            )
                                        )
                                        isEditing = false
                                    }
                                } else {
                                    isEditing = true
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isEditing) AccentGreen else PrimaryTeal)
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                                contentDescription = if (isEditing) "Save changes" else "Edit entry",
                                tint = Color.White
                            )
                        }

                        if (isEditing) {
                            IconButton(
                                onClick = {
                                    isEditing = false
                                    editedLitres = entry.litres.toString()
                                    editedPrice = entry.pricePerLitre.toString()
                                    editedType = entry.milkType
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFFFEBEE))
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cancel editing",
                                    tint = Color(0xFFE53935)
                                )
                            }
                        }

                        IconButton(
                            onClick = onDeleteClick,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFFFEBEE))
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete entry",
                                tint = Color(0xFFE53935)
                            )
                        }
                    }
                }

                // Content
                if (isEditing) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MilkTypeSelector(
                            selectedType = editedType,
                            onTypeSelected = { editedType = it }
                        )
                        
                        NumberInput(
                            value = editedLitres,
                            onValueChange = { editedLitres = it },
                            label = "Litres"
                        )
                        
                        NumberInput(
                            value = editedPrice,
                            onValueChange = { editedPrice = it },
                            label = "Price per Litre"
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Type: ${entry.milkType.name}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = PrimaryTeal
                                )
                            )
                            Text(
                                text = "Litres: ${entry.litres}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = TextPrimary
                                )
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Price/L: ₹${entry.pricePerLitre}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = TextPrimary
                                )
                            )
                            Text(
                                text = "Total: ₹${entry.litres * entry.pricePerLitre}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SecondaryCoral
                                )
                            )
                        }
                    }
                }

                // Delivery Status
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (entry.isDelivered) 
                                Color(0xFFE8F5E9).copy(alpha = 0.7f)  // Lighter green background
                            else 
                                Color(0xFFFFEBEE).copy(alpha = 0.7f)  // Light red background
                        )
                        .padding(12.dp)  // Increased padding
                ) {
                    Checkbox(
                        checked = entry.isDelivered,
                        onCheckedChange = onDeliveryStatusChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = if (entry.isDelivered) Color(0xFF2E7D32) else Color(0xFFE53935),  // Darker green/red
                            uncheckedColor = Color(0xFF757575),  // Medium gray
                            checkmarkColor = Color.White  // White checkmark
                        )
                    )
                    Column(
                        modifier = Modifier.padding(start = 12.dp)  // Increased spacing
                    ) {
                        Text(
                            text = if (entry.isDelivered) "Delivered" else "Not Delivered",
                            style = MaterialTheme.typography.titleMedium.copy(  // Larger text
                                fontWeight = FontWeight.SemiBold,
                                color = if (entry.isDelivered) 
                                    Color(0xFF2E7D32)  // Darker green
                                else 
                                    Color(0xFFE53935)  // Darker red
                            )
                        )
                        Text(  // Added status timestamp
                            text = if (entry.isDelivered) 
                                "✓ Marked as delivered" 
                            else 
                                "Pending delivery",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (entry.isDelivered) 
                                    Color(0xFF43A047)  // Slightly lighter green
                                else 
                                    Color(0xFFEF5350)  // Slightly lighter red
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MilkTypeSelector(
    selectedType: MilkType,
    onTypeSelected: (MilkType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF3E5F5))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MilkType.values().forEach { type ->
            FilterChip(
                selected = type == selectedType,
                onClick = { onTypeSelected(type) },
                label = { 
                    Text(
                        text = type.name,
                        color = if (type == selectedType) Color.White else PrimaryBlue
                    )
                },
                modifier = Modifier.padding(horizontal = 4.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PrimaryBlue,
                    containerColor = Color.White
                )
            )
        }
    }
}

@Composable
fun NumberInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { 
            Text(
                text = label,
                color = PrimaryBlue
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryBlue,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = PrimaryBlue,
            unfocusedLabelColor = Color.Gray,
            cursorColor = PrimaryBlue,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
        ),
        singleLine = true
    )
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun MilkEntryForm(onSubmit: (MilkEntry) -> Unit) {
    var selectedMilkType by remember { mutableStateOf(MilkType.COW) }
    var litres by remember { mutableStateOf("") }
    var pricePerLitre by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NumberInput(
            value = litres,
            onValueChange = { litres = it },
            label = "Litres"
        )
        
        NumberInput(
            value = pricePerLitre,
            onValueChange = { pricePerLitre = it },
            label = "Price per Litre"
        )
        
        MilkTypeSelector(
            selectedType = selectedMilkType,
            onTypeSelected = { selectedMilkType = it }
        )
        
        ActionButton(
            onClick = {
                val litresNum = litres.toDoubleOrNull()
                val priceNum = pricePerLitre.toDoubleOrNull()
                
                if (litresNum != null && priceNum != null) {
                    onSubmit(
                        MilkEntry(
                            milkType = selectedMilkType,
                            litres = litresNum,
                            pricePerLitre = priceNum,
                            date = LocalDate.now(),
                            isDelivered = false
                        )
                    )
                }
            },
            text = "Add Entry",
            enabled = litres.toDoubleOrNull() != null && pricePerLitre.toDoubleOrNull() != null
        )
    }
}
