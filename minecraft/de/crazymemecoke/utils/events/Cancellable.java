package de.crazymemecoke.utils.events;

public abstract interface Cancellable
{
  public abstract boolean isCancelled();
  
  public abstract void setCancelled(boolean paramBoolean);
}
