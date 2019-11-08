#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include "DHT.h"

const char* ssid = "TIM-24201256";
const char* password = "X0sdLQgaKWHSKPj9PHwXZ53Z";
const char* mqtt_server = "192.168.1.41";
String uuid;

#define DHTPIN 14    // related to the gpio number
#define DHTTYPE DHT21   // AM2301 
DHT dht(DHTPIN, DHTTYPE);

WiFiClient espClient;
PubSubClient client(espClient);

int timeSinceLastRead = 0;
float temp = 0.0;
float humd = 0.0;

void setup_wifi() {

  delay(10);

  // prepare GPIO13
  pinMode(13, OUTPUT);
  digitalWrite(13, 0);

  // prepare GPIO12
  pinMode(12, OUTPUT);
  digitalWrite(12, 0);

  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  uint8_t mac[6];
  WiFi.macAddress(mac);
  uuid += "esp8266-";
  uuid += macToStr(mac);

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
  Serial.println("UUID: " + uuid);
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");

  int slash_position = 0;
  String msg = "";
  for (int i=0;i<length;i++) {
      msg += (char)payload[i];
      if((char)payload[i] == '/'){
        slash_position = i;
      }
  }

  String id = msg.substring(0,slash_position);
  String pin = msg.substring(slash_position +1, slash_position +2);
  String val = msg.substring(slash_position +3);

  Serial.println("=== MSG ===> " + msg);
  Serial.println("=== UID ===>" + id);
  Serial.println("=== PIN ===>" + pin);
  Serial.println("=== VAL ===>" + val);

  // Check if the command is for this board
  if(id.equals(uuid)){
    // now check pins
    if(pin.equals("0")){
      if(val.equals("on")){
        digitalWrite(13,1);
        publishStatus();
      }
      if(val.equals("off")){
        digitalWrite(13,0);
        publishStatus();
      }
    }
    if(pin.equals("1")){
      if(val.equals("on")){
        digitalWrite(12,1);
        publishStatus();
      }
      if(val.equals("off")){
        digitalWrite(12,0);
        publishStatus();
      }
    }
  }
}

void publishStatus() {
  String msg = uuid;
  msg += "/";
  msg += digitalRead(12);
  msg += "/";
  msg += digitalRead(13);
  msg += "/";
  msg += temp;
  msg += "/";
  msg += humd;
  client.publish("domotics/values", (char*) msg.c_str() );
  Serial.println("===> " + msg);
}

String macToStr(const uint8_t* mac)
{
  String result;
  for (int i = 0; i < 6; ++i) {
    result += String(mac[i], 16);
    if (i < 5)
      result += ':';
  }
  return result;
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect((char*) uuid.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("domotics/controllers", (char*) uuid.c_str() );
      Serial.println("===> sent message on topic domotics/controllers");
      // ... and resubscribe
      client.subscribe("domotics/commands");
      Serial.println("===> subsribed to topic domotics/commands");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {
  pinMode(BUILTIN_LED, OUTPUT);     // Initialize the BUILTIN_LED pin as an output
  Serial.begin(115200);
  delay(10);

  // prepare GPIO13
  pinMode(13, OUTPUT);
  digitalWrite(13, 0);

  // prepare GPIO12
  pinMode(12, OUTPUT);  
  digitalWrite(12, 0);
  
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void loop() {

  // this block is used to read the temperature and humidity if available
  // every 3 seconds
  if(timeSinceLastRead > 3000) {
    int current = dht.readTemperature();
    if(current != temp){
      temp = current;
      humd = dht.readHumidity();
      publishStatus();
    }
    timeSinceLastRead = 0;
  }

  delay(50);
  timeSinceLastRead += 50;

  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  
}
