package com.example.basic_1

import android.bluetooth.*
import android.content.Context
import java.util.*



import android.util.Log

class BleManager(private val context: Context) {

    companion object {
//        const val ESP32_MAC_ADDRESS = "08:B6:1F:28:B4:6E" // MAC address for jig1 CC14
        const val ESP32_MAC_ADDRESS = "08:B6:1F:29:33:2E"// MAC address for jig2 CC14
        val SERVICE_UUID: UUID = UUID.fromString("4fafc201-1fb5-459e-8fcc-c5c9c331914b")
        val CHARACTERISTIC_UUID: UUID = UUID.fromString("beb5483e-36e1-4688-b7f5-ea07361b26a8")
        val CCCD_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
    }

    private var bluetoothGatt: BluetoothGatt? = null
    private var bleCharacteristic: BluetoothGattCharacteristic? = null
    private var onMessageReceived: ((String) -> Unit)? = null

    fun setOnMessageReceivedListener(listener: (String) -> Unit) {
        onMessageReceived = listener
    }

    fun connect(device: BluetoothDevice, onConnected: () -> Unit) {
        bluetoothGatt = device.connectGatt(context, false, object : BluetoothGattCallback() {

            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d("BLE", "Connected to GATT server. Discovering services...")
                    gatt.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d("BLE", "Disconnected from GATT server.")
                    bluetoothGatt = null
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    val service = gatt.getService(SERVICE_UUID)
                    bleCharacteristic = service?.getCharacteristic(CHARACTERISTIC_UUID)

                    // Enable notifications
                    bleCharacteristic?.let { characteristic ->
                        gatt.setCharacteristicNotification(characteristic, true)
                        val descriptor = characteristic.getDescriptor(CCCD_UUID)
                        descriptor?.let {
                            it.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                            gatt.writeDescriptor(it)
                        }
                    }

                    Log.d("BLE", "Service and characteristic discovered.")
                    onConnected()
                } else {
                    Log.e("BLE", "Service discovery failed with status: $status")
                }
            }

            override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
                val message = characteristic.getStringValue(0)  // Get the received message
                Log.d("BLE", "Notification received: $message")  // Log the message
                onMessageReceived?.invoke(message)  // Send it to the listener
            }


            override fun onDescriptorWrite(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) {
                Log.d("BLE", "Notifications enabled.")
            }
        })
    }

    fun writeCommand(command: String) {
        bleCharacteristic?.apply {
            value = command.toByteArray()
            bluetoothGatt?.writeCharacteristic(this)
            Log.d("BLE", "Command sent: $command")
        } ?: Log.e("BLE", "BLE Characteristic not initialized")
    }



    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}
