# Kafka Java Producer/Consumer Client Sample

## How to use 

```
jar xxx.jar -Dmode=<prod|cons> \
-Dservers="<BootstrapServers>"
-Dgid=<GroupId>
-Dtopic=<Specifying topics w/o space (e.g) topicA,topicB,topicC> \

```

## Limitations
* Currently only for K=Interger, V=String