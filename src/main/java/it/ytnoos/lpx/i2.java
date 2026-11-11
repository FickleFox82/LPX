package it.ytnoos.lpx;

import java.util.List;

final class i2 {
   static final int r = 0; // dead obfuscation field (never assigned/read in bytecode)
   static final List<f> f = List.of(
      new f(
         1,
         List.of(
            W7("checks.creative.c.options.max", "3000", new String[]{"64", "128", "256", "1024", "1500"}),
            W7("checks.creative.d.options.max", "16384", new String[]{"2048"}),
            W7("checks.flood.a.options.max", "1100", new String[]{"900", "1000"}),
            W7("checks.flood.b.options.limits.USE_ITEM.max", "80", new String[]{"60"}),
            W7("checks.flood.c.options.big-threshold", "6000", new String[]{"2048", "4096"}),
            W7("checks.flood.c.options.max", "20", new String[]{"70", "80", "50", "10", "15"}),
            W7("checks.flood.c.options.reset-interval", "100", new String[]{"500"}),
            W7("checks.flood.d.options.big-threshold", "4000", new String[]{"256", "2048"}),
            W7("checks.flood.d.options.max", "13", new String[]{"10"}),
            W7("checks.flood.d.options.max-big", "6", new String[]{"4"}),
            W7("checks.flood.g.options.interval", "250", new String[]{"500"}),
            W7("checks.netty.a.options.max", "98304", new String[]{"65536", "131328"}),
            W7("checks.netty.b.options.max-item-depth", "4", new String[]{"3"}),
            W7("checks.place.a.options.max", "100", new String[]{"20", "40"}),
            W7("checks.place.c.options.max", "50", new String[]{"40"}),
            W7("checks.position.b.options.delay", "2000", new String[]{"5000"}),
            W7("checks.position.c.options.max", "300000", new String[]{"2048", "15000", "30000"}),
            W7("checks.position.d.options.max", "1000000", new String[]{"100000"}),
            W7("checks.tab.b.options.max-length", "256", new String[]{"2048", "64"}),
            W7("options.mechanics.interact-container-delay", "100", new String[]{"80"}),
            W7("checks.window.e.options.max-hashed-slot-changes", "90", new String[]{"32"}),
            W7("checks.window.e.options.max-hashed-components-per-stack", "128", new String[]{"32"})
         )
      ),
      new f(2, List.of(W7("checks.netty.b.options.max-nbt-elements-per-packet", "2500", new String[]{"1800"})))
   );

   private i2() {
   }

   private static qu W7(String var0, String var1, String[] var2) {
      return new qu(var0, var1, List.of(var2));
   }
}
