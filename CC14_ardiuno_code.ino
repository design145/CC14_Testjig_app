#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>

// UUIDs for BLE service and characteristic
#define SERVICE_UUID        "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
#define CHARACTERISTIC_UUID "beb5483e-36e1-4688-b7f5-ea07361b26a8"

// Pin definitions
#define SDI_PIN 23
#define CLK_PIN 18
#define LE_PIN 19
#define OE_PIN 5

#define KEY1_PIN 25
#define KEY2_PIN 33
#define KEY3_PIN 27
#define KEY4_PIN 26

#define KEY1_ANALOG_PIN 34
#define KEY2_ANALOG_PIN 35
#define KEY3_ANALOG_PIN 39
#define KEY4_ANALOG_PIN 36

#define R_known 10000 // Pull-up resistor 10K

bool keyPressed = false;
BLECharacteristic *pCharacteristic;

void setup() {
  Serial.begin(115200);

  pinMode(SDI_PIN, OUTPUT);
  pinMode(CLK_PIN, OUTPUT);
  pinMode(LE_PIN, OUTPUT);
  pinMode(OE_PIN, OUTPUT);

  pinMode(KEY1_PIN, INPUT_PULLUP);
  pinMode(KEY2_PIN, INPUT_PULLUP);
  pinMode(KEY3_PIN, INPUT_PULLUP);
  pinMode(KEY4_PIN, INPUT_PULLUP);

  digitalWrite(OE_PIN, LOW);
  digitalWrite(LE_PIN, LOW);

  // Initialize BLE
  BLEDevice::init("ESP32_BLE_Keys");
  BLEServer *pServer = BLEDevice::createServer();
  BLEService *pService = pServer->createService(SERVICE_UUID);
  pCharacteristic = pService->createCharacteristic(
                      CHARACTERISTIC_UUID,
                      BLECharacteristic::PROPERTY_READ | BLECharacteristic::PROPERTY_NOTIFY);
  pService->start();
  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(SERVICE_UUID);
  pAdvertising->start();
}

void loop() {
  if (!keyPressed) {
    if (digitalRead(KEY1_PIN) == LOW) handleKeyPress(1, KEY1_ANALOG_PIN, 0xFFFF, 0x0000);
    else if (digitalRead(KEY2_PIN) == LOW) handleKeyPress(2, KEY2_ANALOG_PIN, 0x0000, 0xFFFF);
    else if (digitalRead(KEY3_PIN) == LOW) handleKeyPress(3, KEY3_ANALOG_PIN, 0x00FF, 0xFF00);
    else if (digitalRead(KEY4_PIN) == LOW) handleKeyPress(4, KEY4_ANALOG_PIN, 0xFF00, 0x00FF);
  }

  if (digitalRead(KEY1_PIN) == HIGH && digitalRead(KEY2_PIN) == HIGH &&
      digitalRead(KEY3_PIN) == HIGH && digitalRead(KEY4_PIN) == HIGH) {
    keyPressed = false;
  }
}

void handleKeyPress(int keyNumber, int keyAnalogPin, uint16_t redData, uint16_t greenData) {
  delay(50); // Debounce delay

  if (digitalRead(KEY1_PIN) == LOW || digitalRead(KEY2_PIN) == LOW ||
      digitalRead(KEY3_PIN) == LOW || digitalRead(KEY4_PIN) == LOW) {

    Serial.print("Key "); Serial.print(keyNumber); Serial.println(" Pressed");
    keyPressed = true;

    // Calculate electrical parameters
    float voltage = calculateVoltage(keyAnalogPin);
    float impedance = calculateImpedance(voltage);
    float current = calculateCurrent(voltage);

    // Display parameters
    Serial.print("Voltage: "); Serial.println(voltage);
    Serial.print("Impedance: "); Serial.println(impedance);
    Serial.print("Current: "); Serial.print(current * 1000); Serial.println(" mA");

    // LED testing
    if (keyNumber == 1) {
      setAllLEDsColor(0xFFFF, 0x0000);
      delay(1000);
    } else if (keyNumber == 2) {
      setAllLEDsColor(0x0000, 0xFFFF);
      delay(1000);
    } else if (keyNumber == 3) {
      setAllLEDsColor(0x00FF, 0xFF00);
      delay(1000);
    } else if (keyNumber == 4) {
      setAllLEDsColor(0xFF00, 0x00FF);
      delay(1000);
    }

    // Send BLE notification
    String message = "Key " + String(keyNumber) + " Pressed, Voltage: " + String(voltage) +
                     ", Impedance: " + String(impedance) + ", Current: " + String(current * 1000) + " mA";
    pCharacteristic->setValue(message.c_str());
    pCharacteristic->notify();

    delay(500);
  }
}

float calculateVoltage(int keyAnalogPin) {
  int sum = 0;
  for (int i = 0; i < 10; i++) {
    sum += analogRead(keyAnalogPin);
    delay(2);
  }
  return (sum / 10.0) * (3.3 / 4095.0);
}

float calculateImpedance(float voltage) {
  if (voltage == 0) return 0;
  return voltage / (voltage / R_known);
}

float calculateCurrent(float voltage) {
  if (voltage == 0) return 0;
  return voltage / R_known;
}

void setAllLEDsColor(uint16_t redData, uint16_t greenData) {
  uint32_t data = ((uint32_t)greenData << 16) | redData;
  shiftOutData(data, 32);
  digitalWrite(LE_PIN, HIGH);
  delayMicroseconds(500);
  digitalWrite(LE_PIN, LOW);
}

void shiftOutData(uint32_t data, uint8_t bits) {
  for (int i = bits - 1; i >= 0; i--) {
    digitalWrite(SDI_PIN, (data >> i) & 0x01);
    digitalWrite(CLK_PIN, HIGH);
    digitalWrite(CLK_PIN, LOW);
    delayMicroseconds(10);
  }
}