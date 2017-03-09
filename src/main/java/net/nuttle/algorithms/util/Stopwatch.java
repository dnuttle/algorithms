package net.nuttle.algorithms.util;

public class Stopwatch {
  long start;
  long elapsed;
  boolean running;
  
  private Stopwatch() {
    
  }
  
  public static Stopwatch getStartedInstance() {
    Stopwatch watch = new Stopwatch();
    watch.start();
    return watch;
  }
  
  public static Stopwatch getStoppedInstance() {
    Stopwatch watch = new Stopwatch();
    return watch;
  }
  
  public void start() {
    running = true;
    start = System.currentTimeMillis();
  }
  
  public void stop() {
    long now = System.currentTimeMillis();
    elapsed += (now - start);
    running = false;
  }
  
  public long elapsed() {
    return elapsed;
  }
  
  public double elapsedSecs() {
    return elapsed / 1000.0;
  }
  
  public boolean isRunning() {
    return running;
  }
}
