# akka http simple client


## Introduction

The akka http client is a useful library, but it is a bit inconvenient in terms of interfaces.

So I wrapped up the akka http interface and created the akka http simple client.

## Quick Start


```
- sbt Dependency

It will be added later...

```

## Examples


<pre>
    <code>

Get(s"Input target uri").receiveAs[AnyYourResponseObject].await shouldBe AnyYourResponseObject("baz")

    </code>
</pre>