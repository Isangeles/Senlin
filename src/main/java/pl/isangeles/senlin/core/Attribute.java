package pl.isangeles.senlin.core;
/**
 * Wrapped integer class, for mutability
 *
 * @author Isangeles
 */
public class Attribute {
  int value;

  public Attribute() {
    value = 0;
  }

  public Attribute(int value) {
    this.value = value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public void increment() {
    value++;
  }

  public void decrement() {
    value--;
  }

  @Override
  public String toString() {
    return value + "";
  }
}
