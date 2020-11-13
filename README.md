# Kafka Unified Messenger
Java Producer/Consumer Client sample.

## Min requirements

```
➜  ~ java -version
java version "1.8.0_271"
Java(TM) SE Runtime Environment (build 1.8.0_271-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)
```

## How to use 

```
java -Dmode=<prod|cons> \
-Dservers="<BootstrapServers>"
-Dgid=<GroupId>
-Dtopics=<Specifying topics w/o space (e.g) topicA,topicB,topicC> \
-Dtopic=<A published topic by Producer> \
-jar kafka-unified-messenger.jar 
```

Example:

```
java -Dmode=cons \
-Dgid=consumer \
-Dservers="a,b,c" \
-Dtopics="testa,testb,testc" \
-Dtopic="testa" \
-jar kafka-unified-messenger.jar
```

## Limitations
* Currently supported only for K=Interger, V=String