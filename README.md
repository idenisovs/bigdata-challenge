# Big Data Challenge

## 3rd party

* Scala - `2.12.8`
* SBT - `1.2.8`
* Spark - `spark-2.4.3-bin-without-hadoop-scala-2.12`
* Hadoop - `3.2.0`
* Kafka - `2.2.1`
* Docker - `18.09.5`

## Commands

```bash
# Build & Make fat jar: 
sbt assembly

# Build Producer (device-sim) docker image:
docker build --tag device-sim .

# Run 3 instances of Producer (device-sim):
docker-compose up --scale device-sim=3
```

## Structure

### Sources

```
root
|
+---> core (shared objects)
|
+---> device-simulator (Producer)
|
+---> process-job (Consumer)
```

### Project

```           
              messages
device sim 1 ----------> +-------+
                         |       |      
device sim 2 ----------> | Kafka |
                         |       |
device sim 3 ----------> +-------+
                            |
                            | messages
                            V
                    +---------------+
                    | Spark         |
                    | +-----------+ |
                    | |process-job| |
                    | +-----------+ |
                    +---------------+
```

