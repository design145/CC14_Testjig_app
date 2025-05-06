//$$$$$$ CC14 key turn green with faulty keys
package com.example.basic_1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.Image
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun LEDCircle(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(color, CircleShape)
            .border(1.dp, Color.Black, CircleShape)
    )
}
//@Composable
//fun KeyLedTestScreen(bleManager: BleManager) {
//    val ledColors = remember { mutableStateListOf<Color>().apply { repeat(14) { add(Color.Gray) } } }
//    val message = remember { mutableStateOf("No Key Pressed") }
//    val bluetoothStatus = remember { mutableStateOf("Bluetooth Disconnected") }
//    val isConnected = remember { mutableStateOf(false) }
//    val context = LocalContext.current
//
//    val mediaPlayer = remember {
//        MediaPlayer.create(context, R.raw.beep)
//    }
//    val scope = rememberCoroutineScope()
//
//    val key1Pressed = remember { mutableStateOf(false) }
//    val key2Pressed = remember { mutableStateOf(false) }
//    val key3Pressed = remember { mutableStateOf(false) }
//    val key4Pressed = remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        bleManager.setOnMessageReceivedListener { receivedMessage ->
//            when {
//                receivedMessage.startsWith("Key 1") -> {
//                    mediaPlayer.start()
//                    ledColors.fill(Color.Red)
//                    message.value = "Key 1 Pressed"
//                    key1Pressed.value = true
//                    scope.launch { delay(10); key1Pressed.value = false }
//                }
//                receivedMessage.startsWith("Key 2") -> {
//                    mediaPlayer.start()
//                    ledColors.fill(Color.Green)
//                    message.value = "Key 2 Pressed"
//                    key2Pressed.value = true
//                    scope.launch { delay(10); key2Pressed.value = false }
//                }
//                receivedMessage.startsWith("Key 3") -> {
//                    mediaPlayer.start()
//                    for (i in 0 until 8) ledColors[i] = Color.Red
//                    for (i in 8 until 14) ledColors[i] = Color.Green
//                    message.value = "Key 3 Pressed"
//                    key3Pressed.value = true
//                    scope.launch { delay(10); key3Pressed.value = false }
//                }
//                receivedMessage.startsWith("Key 4") -> {
//                    mediaPlayer.start()
//                    for (i in 0 until 8) ledColors[i] = Color.Green
//                    for (i in 8 until 14) ledColors[i] = Color.Red
//                    message.value = "Key 4 Pressed"
//                    key4Pressed.value = true
//                    scope.launch { delay(10); key4Pressed.value = false }
//                }
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//            .border(15.dp, Color.Black)
//            .padding(18.dp),
//        verticalArrangement = Arrangement.SpaceBetween,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "GE Healthcare App",
//            fontSize = 40.sp,
//            fontWeight = FontWeight.ExtraBold,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // Connect, Disconnect, Bluetooth Icon
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Button(
//                onClick = {
//                    val device = BluetoothAdapter.getDefaultAdapter()
//                        ?.getRemoteDevice(BleManager.ESP32_MAC_ADDRESS)
//                    device?.let {
//                        bleManager.connect(it) {
//                            isConnected.value = true
//                            bluetoothStatus.value = "Bluetooth Connected"
//                        }
//                    }
//                },
//                modifier = Modifier
//                    .height(48.dp)
//                    .padding(end = 8.dp)
//            ) {
//                Text("Connect")
//            }
//
//            Image(
//                painter = painterResource(id = R.drawable.bt),
//                contentDescription = "Bluetooth Icon",
//                modifier = Modifier
//                    .size(48.dp)
//                    .padding(horizontal = 8.dp)
//            )
//
//            Button(
//                onClick = {
//                    bleManager.disconnect()
//                    isConnected.value = false
//                    bluetoothStatus.value = "Bluetooth Disconnected"
//                },
//                modifier = Modifier
//                    .height(48.dp)
//                    .padding(start = 8.dp)
//            ) {
//                Text("Disconnect")
//            }
//        }
//
//        // Bluetooth Status Text
//        Text(
//            text = bluetoothStatus.value,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = if (isConnected.value) Color.Green else Color.Red,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // 8 Top LEDs
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
//        ) {
//            for (i in 0 until 8) {
//                LEDCircle(color = ledColors[i])
//            }
//        }
//
//        // 6 Bottom LEDs
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
//        ) {
//            for (i in 8 until 14) {
//                LEDCircle(color = ledColors[i])
//            }
//        }
//
//        Text(
//            text = message.value,
//            fontSize = 20.sp,
//            modifier = Modifier.padding(top = 16.dp)
//        )
//
//        // Key Buttons Row
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = { bleManager.writeCommand("KEY1") },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (key1Pressed.value) Color.Green else Color.Gray
//                ),
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(120.dp)
//            ) {
//                Text("Key 1", fontSize = 18.sp)
//            }
//            Button(
//                onClick = { bleManager.writeCommand("KEY2") },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (key2Pressed.value) Color.Green else Color.Gray
//                ),
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(120.dp)
//            ) {
//                Text("Key 2", fontSize = 18.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = { bleManager.writeCommand("KEY3") },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (key3Pressed.value) Color.Green else Color.Gray
//                ),
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(120.dp)
//            ) {
//                Text("Key 3", fontSize = 18.sp)
//            }
//            Button(
//                onClick = { bleManager.writeCommand("KEY4") },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (key4Pressed.value) Color.Green else Color.Gray
//                ),
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(120.dp)
//            ) {
//                Text("Key 4", fontSize = 18.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Test Result and Reset Buttons
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = {
//                    // Handle Test Result here
//                    message.value = "Test Result: SUCCESS"
//                },
//                modifier = Modifier
//                    .height(60.dp)
//                    .width(140.dp)
//            ) {
//                Text("Test Result", fontSize = 18.sp)
//            }
//
//            Button(
//                onClick = {
//                    // Reset everything
//                    for (i in 0 until 14) ledColors[i] = Color.Gray
//                    message.value = "Reset Done"
//                },
//                modifier = Modifier
//                    .height(60.dp)
//                    .width(140.dp)
//            ) {
//                Text("Reset", fontSize = 18.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // GE Logo
//        Image(
//            painter = painterResource(id = R.drawable.ge_logo),
//            contentDescription = "App Logo",
//            modifier = Modifier
//                .size(100.dp)
//                .padding(top = 16.dp)
//        )
//    }
//}

// Helper extension function
//private fun SnapshotStateList<Color>.fill(color: Color) {
//    for (i in indices) this[i] = color
//}

//without test result,reset button
//@Composable
//fun KeyLedTestScreen(bleManager: BleManager) {
//    val ledColors = remember { mutableStateListOf<Color>().apply { repeat(14) { add(Color.Gray) } } }
//    val message = remember { mutableStateOf("No Key Pressed") }
//    val bluetoothStatus = remember { mutableStateOf("Bluetooth Disconnected") }
//    val isConnected = remember { mutableStateOf(false) }
//    val context = LocalContext.current
//
//    val mediaPlayer = remember {
//        MediaPlayer.create(context, R.raw.beep)
//    }
//    val scope = rememberCoroutineScope()
//    // Track which key button is pressed
//    val key1Pressed = remember { mutableStateOf(false) }
//    val key2Pressed = remember { mutableStateOf(false) }
//    val key3Pressed = remember { mutableStateOf(false) }
//    val key4Pressed = remember { mutableStateOf(false) }
//
//    // BLE callback to handle received data
//    LaunchedEffect(Unit) {
//        bleManager.setOnMessageReceivedListener { receivedMessage ->
//            when {
//                receivedMessage.startsWith("Key 1") -> {
//                    mediaPlayer.start()
//                    // Update the LED colors for Key 1
//                    for (i in 0 until 14) ledColors[i] = Color.Red
//                    message.value = "Key 1 Pressed"
//                    // Turn Key 1 button green
//                    key1Pressed.value = true
//                    key2Pressed.value = false
//                    key3Pressed.value = false
//                    key4Pressed.value = false
//                    scope.launch {
//                        delay(50)
//                        key1Pressed.value = false
//                    }
//                }
//                receivedMessage.startsWith("Key 2") -> {
//                    mediaPlayer.start()
//                    // Update the LED colors for Key 2
//                    for (i in 0 until 14) ledColors[i] = Color.Green
//                    message.value = "Key 2 Pressed"
//                    // Turn Key 2 button green
//                    key1Pressed.value = false
//                    key2Pressed.value = true
//                    key3Pressed.value = false
//                    key4Pressed.value = false
//                    scope.launch {
//                        delay(50)
//                        key2Pressed.value = false
//                    }
//                }
//                receivedMessage.startsWith("Key 3") -> {
//                    mediaPlayer.start()
//                    // Update the LED colors for Key 3
//                    for (i in 0 until 8) ledColors[i] = Color.Red
//                    for (i in 8 until 14) ledColors[i] = Color.Green
//                    message.value = "Key 3 Pressed"
//                    // Turn Key 3 button green
//                    key1Pressed.value = false
//                    key2Pressed.value = false
//                    key3Pressed.value = true
//                    key4Pressed.value = false
//                    scope.launch {
//                        delay(50)
//                        key3Pressed.value = false
//                    }
//                }
//                receivedMessage.startsWith("Key 4") -> {
//                    mediaPlayer.start()
//                    // Update the LED colors for Key 4
//                    for (i in 0 until 8) ledColors[i] = Color.Green
//                    for (i in 8 until 14) ledColors[i] = Color.Red
//                    message.value = "Key 4 Pressed"
//                    // Turn Key 4 button green
//                    key1Pressed.value = false
//                    key2Pressed.value = false
//                    key3Pressed.value = false
//                    key4Pressed.value = true
//                    scope.launch {
//                        delay(50)
//                        key4Pressed.value = false
//                    }
//                }
//            }
//        }
//    }
//
//    // Composables Layout
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//            .border(15.dp, Color.Black)
//            .padding(18.dp),
//        verticalArrangement = Arrangement.SpaceBetween,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "GE Healthcare App",
//            fontSize = 40.sp,
//            fontWeight = FontWeight.ExtraBold,
//            modifier = Modifier
//                .padding(bottom = 16.dp)
//                .align(Alignment.CenterHorizontally)
//        )
//
//        // Connect/Disconnect Buttons
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Button(
//                onClick = {
//                    val device = BluetoothAdapter.getDefaultAdapter()
//                        ?.getRemoteDevice(BleManager.ESP32_MAC_ADDRESS)
//                    if (device != null) {
//                        bleManager.connect(device) {
//                            isConnected.value = true
//                            bluetoothStatus.value = "Bluetooth Connected"
//                        }
//                    }
//                },
//                modifier = Modifier
//                    .height(48.dp)
//                    .padding(end = 8.dp)
//            ) {
//                Text("Connect")
//            }
//
//            Image(
//                painter = painterResource(id = R.drawable.bt),
//                contentDescription = "Bluetooth Icon",
//                modifier = Modifier.size(48.dp)
//            )
//
//            Button(
//                onClick = {
//                    bleManager.disconnect()
//                    isConnected.value = false
//                    bluetoothStatus.value = "Bluetooth Disconnected"
//                },
//                modifier = Modifier
//                    .height(48.dp)
//                    .padding(start = 8.dp)
//            ) {
//                Text("Disconnect")
//            }
//        }
//
//        // Bluetooth Status Message
//        Text(
//            text = bluetoothStatus.value,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = if (isConnected.value) Color.Green else Color.Red,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // LED Circles (Update when `ledColors` changes)
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
//        ) {
//            for (i in 0 until 8) {
//                LEDCircle(color = ledColors[i])
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
//        ) {
//            for (i in 0 until 6) {
//                LEDCircle(color = ledColors[8 + i])
//            }
//        }
//
//        // Display the current message (which key was pressed)
//        Text(text = message.value, fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp))
//
//        // Button to simulate key presses (send commands to ESP32 via BLE)
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = { bleManager.writeCommand("KEY1") },
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(150.dp)
//                    .background(if (key1Pressed.value) Color.Green else Color.Gray)
//            ) {
//                Text("Key 1", fontSize = 18.sp)
//            }
//
//            Button(
//                onClick = { bleManager.writeCommand("KEY2") },
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(150.dp)
//                    .background(if (key2Pressed.value) Color.Green else Color.Gray)
//            ) {
//                Text("Key 2", fontSize = 18.sp)
//            }
//
//            Button(
//                onClick = { bleManager.writeCommand("KEY3") },
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(150.dp)
//                    .background(if (key3Pressed.value) Color.Green else Color.Gray)
//            ) {
//                Text("Key 3", fontSize = 18.sp)
//            }
//
//            Button(
//                onClick = { bleManager.writeCommand("KEY4") },
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(150.dp)
//                    .background(if (key4Pressed.value) Color.Green else Color.Gray)
//            ) {
//                Text("Key 4", fontSize = 18.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // App Logo
//        Image(
//            painter = painterResource(id = R.drawable.ge_logo),
//            contentDescription = "App Logo",
//            modifier = Modifier
//                .size(100.dp)
//                .padding(top = 16.dp)
//        )
//    }
//}
//######key sequence,test result
//@Composable
//fun KeyLedTestScreen(bleManager: BleManager) {
//    val ledColors = remember { mutableStateListOf<Color>().apply { repeat(14) { add(Color.Gray) } } }
//    val message = remember { mutableStateOf("Press Key 1") }
//    val bluetoothStatus = remember { mutableStateOf("Bluetooth Disconnected") }
//    val isConnected = remember { mutableStateOf(false) }
//    val context = LocalContext.current
//    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.beep) }
//    val keyStates = remember { mutableStateListOf(false, false, false, false) }
//    val expectedKeyIndex = remember { mutableStateOf(0) }
//    val testResult = remember { mutableStateOf("") }
//
//    LaunchedEffect(Unit) {
//        bleManager.setOnMessageReceivedListener { receivedMessage ->
//            when {
//                receivedMessage.startsWith("Key 1") && expectedKeyIndex.value == 0 -> {
//                    mediaPlayer.start()
//                    ledColors.indices.forEach { ledColors[it] = Color.Red }
//                    message.value = "Key 1 Pressed"
//                    keyStates[0] = true
//                    expectedKeyIndex.value = 1
//                }
//                receivedMessage.startsWith("Key 2") && expectedKeyIndex.value == 1 -> {
//                    mediaPlayer.start()
//                    ledColors.indices.forEach { ledColors[it] = Color.Green }
//                    message.value = "Key 2 Pressed"
//                    keyStates[1] = true
//                    expectedKeyIndex.value = 2
//                }
//                receivedMessage.startsWith("Key 3") && expectedKeyIndex.value == 2 -> {
//                    mediaPlayer.start()
//                    for (i in 0 until 8) ledColors[i] = Color.Red
//                    for (i in 8 until 14) ledColors[i] = Color.Green
//                    message.value = "Key 3 Pressed"
//                    keyStates[2] = true
//                    expectedKeyIndex.value = 3
//                }
//                receivedMessage.startsWith("Key 4") && expectedKeyIndex.value == 3 -> {
//                    mediaPlayer.start()
//                    for (i in 0 until 8) ledColors[i] = Color.Green
//                    for (i in 8 until 14) ledColors[i] = Color.Red
//                    message.value = "Key 4 Pressed"
//                    keyStates[3] = true
//                    expectedKeyIndex.value = 4
//                    testResult.value = "Test Passed ✅"
//                }
//                receivedMessage.startsWith("Key") -> {
//                    message.value = "Please press Key ${expectedKeyIndex.value + 1}"
//                }
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//            .border(15.dp, Color.Black)
//            .padding(18.dp),
//        verticalArrangement = Arrangement.SpaceBetween,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("GE Healthcare App", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
//
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Button(onClick = {
//                val device = BluetoothAdapter.getDefaultAdapter()
//                    ?.getRemoteDevice(BleManager.ESP32_MAC_ADDRESS)
//                device?.let {
//                    bleManager.connect(it) {
//                        isConnected.value = true
//                        bluetoothStatus.value = "Bluetooth Connected"
//                    }
//                }
//            }) { Text("Connect") }
//
//            Image(
//                painter = painterResource(id = R.drawable.bt),
//                contentDescription = "Bluetooth Icon",
//                modifier = Modifier.size(48.dp).padding(horizontal = 8.dp)
//            )
//
//            Button(onClick = {
//                bleManager.disconnect()
//                isConnected.value = false
//                bluetoothStatus.value = "Bluetooth Disconnected"
//            }) { Text("Disconnect") }
//        }
//
//        Text(
//            text = bluetoothStatus.value,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = if (isConnected.value) Color.Green else Color.Red
//        )
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
//        ) {
//            for (i in 0 until 8) {
//                LEDCircle(color = ledColors[i])
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
//        ) {
//            for (i in 8 until 14) {
//                LEDCircle(color = ledColors[i])
//            }
//        }
//
//        Text(text = message.value, fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            (0..3).forEach { i ->
//                Button(
//                    onClick = {
//                        if (i == expectedKeyIndex.value) {
//                            bleManager.writeCommand("KEY${i + 1}")
//                        } else {
//                            message.value = "Please press Key ${expectedKeyIndex.value + 1}"
//                        }
//                    },
//                    modifier = Modifier
//                        .height(70.dp)
//                        .width(150.dp)
//                        .background(if (keyStates[i]) Color.Green else Color.Gray)
//                ) {
//                    Text("Key ${i + 1}", fontSize = 18.sp)
//                }
//            }
//        }
//
//        if (testResult.value.isNotEmpty()) {
//            Text(
//                text = testResult.value,
//                fontSize = 28.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Blue,
//                modifier = Modifier.padding(top = 16.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Image(
//            painter = painterResource(id = R.drawable.ge_logo),
//            contentDescription = "App Logo",
//            modifier = Modifier.size(100.dp)
//        )
//    }
//}
////##### working code delay less 5 milisec
@Composable
fun KeyLedTestScreen(bleManager: BleManager) {
    val scope = rememberCoroutineScope()
    val ledColors = remember { mutableStateListOf<Color>().apply { repeat(14) { add(Color.Gray) } } }
    val message = remember { mutableStateOf("Press any key to test") }
    val bluetoothStatus = remember { mutableStateOf("Bluetooth Disconnected") }
    val isConnected = remember { mutableStateOf(false) }
    val testResult = remember { mutableStateOf("") }

    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.beep) }

    val keyStates = remember { mutableStateListOf(false, false, false, false) }

    fun resetTest() {
        for (i in 0 until 14) ledColors[i] = Color.Gray
        for (i in 0 until 4) keyStates[i] = false
        message.value = "Press any key to test"
        testResult.value = ""
    }

    LaunchedEffect(Unit) {
        bleManager.setOnMessageReceivedListener { receivedMessage ->
            when {
                receivedMessage.startsWith("Key 1") -> {
                    mediaPlayer.start()
                    for (i in 0 until 14) ledColors[i] = Color.Red
                    keyStates[0] = true
                    message.value = "Key 1 Pressed"
                    scope.launch{
                        delay(5)
                    }
                }
                receivedMessage.startsWith("Key 2") -> {
                    mediaPlayer.start()
                    for (i in 0 until 14) ledColors[i] = Color.Green
                    keyStates[1] = true
                    message.value = "Key 2 Pressed"
                    scope.launch{
                        delay(5)
                    }
                }
                receivedMessage.startsWith("Key 3") -> {
                    mediaPlayer.start()
                    for (i in 0 until 8) ledColors[i] = Color.Red
                    for (i in 8 until 14) ledColors[i] = Color.Green
                    keyStates[2] = true
                    message.value = "Key 3 Pressed"
                    scope.launch{
                        delay(5)
                    }
                }
                receivedMessage.startsWith("Key 4") -> {
                    mediaPlayer.start()
                    for (i in 0 until 8) ledColors[i] = Color.Green
                    for (i in 8 until 14) ledColors[i] = Color.Red
                    keyStates[3] = true
                    message.value = "Key 4 Pressed"
                    scope.launch{
                        delay(5)
                    }
                }
            }
            if (keyStates.all { it }) {
                testResult.value = "TEST PASSED ✅"
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .border(15.dp, Color.Black)
            .padding(18.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("GE Healthcare App", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                val device = BluetoothAdapter.getDefaultAdapter()
                    ?.getRemoteDevice(BleManager.ESP32_MAC_ADDRESS)
                device?.let {
                    bleManager.connect(it) {
                        isConnected.value = true
                        bluetoothStatus.value = "Bluetooth Connected"
                    }
                }
            }) { Text("Connect") }

            Image(
                painter = painterResource(id = R.drawable.bt),
                contentDescription = "Bluetooth Icon",
                modifier = Modifier.size(48.dp).padding(horizontal = 8.dp)
            )

            Button(onClick = {
                bleManager.disconnect()
                isConnected.value = false
                bluetoothStatus.value = "Bluetooth Disconnected"
            }) { Text("Disconnect") }
        }

        Text(
            text = bluetoothStatus.value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isConnected.value) Color.Green else Color.Red
        )





        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            for (i in 0 until 8) {
                LEDCircle(color = ledColors[i])
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            for (i in 8 until 14) {
                LEDCircle(color = ledColors[i])
            }
        }

        Text(text = message.value, fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            (0..3).forEach { i ->
                Button(
                    onClick = {
                        bleManager.writeCommand("KEY${i + 1}")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor
                        = if (keyStates[i]) Color.Green else Color.Gray
                    ),
                    modifier = Modifier.height(70.dp).width(150.dp)
                ) {
                    Text("Key ${i + 1}", fontSize = 18.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (testResult.value.isNotEmpty()) {
            Text(
                text = testResult.value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
        Button(
            onClick = { resetTest() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Reset Test", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(id = R.drawable.ge_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )
    }
}
