# Minecraft HotbarRefill Plugin
A Minecraft plugin for automatically replacing a broken item or depleted item stack in the hotbar from the inventory if possible.

This plugin works for craftbukkit/spigot/paper from Minecraft version 1.17.

See: https://paper.readthedocs.io/en/latest/about/faq.html

## Build
This is a maven project. To build the plugins jar file you have to install maven (https://maven.apache.org/).

With maven installed you can simply build the plugin, by executing:
```shell
$ mvn package
```

The package will then be build in target/HotbarRefill-VERSION.jar. Put the plugin into the
plugins directory of your Minecraft Server, restart and enjoy.