# Sensor class diagram

```mermaid
---
title: Sensor
---
classDiagram
    note "Sensors in Smarthome"
    Sensor ..> OnUpdateListener
    Sensor ..> SensorUpdateEvent
    Sensor ..|> ControllableSensor
    OnUpdateListener ..> SensorUpdateEvent
    ClimateSensor --|> Sensor

    class Sensor {
        <<abstract>>
        +description() String
        +addListener(OnUpdateListener listener)*
        +removeListener(OnUpdateListener listener)*
        +friendlyName() String
        +batteryStatus() float
    }
    class ClimateSensor {
        String friendlyName
        float currentTemperature
        float currentHumidity
        float batteryStatus
        +humidity() float
        +temperature() float
        +update(float temperature, float humidity)
    }
    class OnUpdateListener {
        <<interface>>
        +inform(SensorUpdateEvent event)
    }
    class ControllableSensor {
        <<interface>>
        +activate()
        +deactivate()
    }
    class SensorUpdateEvent {
        +sensor() Sensor
    }
```

## Sensor value update

```mermaid
---
title: Broadcast mapping
---
classDiagram
    ClimateSensorTopicMapper ..|> TopicObserver
    ClimateSensorTopicMapper ..> MessageParsingStrategy~T~
    ClimateSensorTopicMapper --> TemperatureAndHumidityParsing
    TemperatureAndHumidityParsing ..|> MessageParsingStrategy
    class ClimateSensorTopicMapper {
        ClimateSensor sensor
        ParsingStrategy~TemeratureHumidityData~ strategy
    }

    class TopicObserver {
        <<interface>>
        +interestedTopic() String
        +consume(String message)
    }

    class MessageParsingStrategy~T~ {
        <<interface>>
        +parse(String message) T
    }

    class TemperatureAndHumidityParsing {
        +parse(String message) TemperatureHumidityData
    }

```

## Sensor `.yml` config file
Sensors can be configured via an external `sensor.yml` file, with the following structure.

```yaml
sensors:
  - friendly_name: home/living_room/climate
    type: temperature_humidity
    id: 0x00128d0001d9e1d2
    
  - friendly_name: home/dormitory/climate
    type: temperature_humidity
    id: 0xXXXXXXXXXXXXXXX
```

__Supported values for `type`__:
 
- `temperature_humidity` (Temperature and humidity sensor) 
