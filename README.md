# Kafka Java Producer/Consumer Client Sample

## How to use 

```
jar xxx.jar -Dmode=<prod|cons> \
-Dservers="<BootstrapServers>"
-Dgid=<GroupId>
-Dtopic=<Specifying topics w/o space (e.g) topicA,topicB,topicC> \

```

Example:

```
jar kafka-messenger-sample-0.1.jar
-Dmode=cons \
-Dgid=consumer \
-Dservers="a,b,c" \
-Dtopics="testa,testb,testc"
```

## Limitations
* Currently only for K=Interger, V=String