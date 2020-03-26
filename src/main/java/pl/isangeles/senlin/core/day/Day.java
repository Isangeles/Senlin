/*
 * Day.java
 *
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 *
 *
 */
package pl.isangeles.senlin.core.day;

import java.io.IOException;
import java.util.Random;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.core.WorldTime;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.util.Stopwatch;

/**
 * Class for manage game day values like weather, day phase, etc.
 *
 * @author Isangeles
 */
public class Day implements SaveElement {
  private Weather conditions;
  private DayPhase phase;
  private WorldTime time;
  private int second;
  private Random rng = new Random();
  /**
   * Day manager constructor
   *
   * @throws SlickException
   * @throws IOException
   */
  public Day() throws SlickException, IOException {
    conditions = new Weather();
    phase = new DayPhase();
    time = new WorldTime();
  }
  /** Draws current weather and day phase filter */
  public void draw() {
    conditions.draw(0, 0);
    phase.draw(0, 0);
  }
  /**
   * Updates day manager
   *
   * @param delta Time from last update in milliseconds
   */
  public void update(int delta) {
    second += delta;
    if (second >= 1000) {
      time.addMinutes(1);
      second = 0;
    }
    if (time.inMillis() < Stopwatch.min(6)) phase.changePhase(PhaseType.NIGHT);
    if (time.inMillis() > Stopwatch.min(6)) phase.changePhase(PhaseType.MORNING);
    if (time.inMillis() > Stopwatch.min(12)) phase.changePhase(PhaseType.MIDDAY);
    if (time.inMillis() > Stopwatch.min(18)) phase.changePhase(PhaseType.AFTERNOON);
    if (time.inMillis() > Stopwatch.min(22)) phase.changePhase(PhaseType.NIGHT);

    if (!conditions.isRaining() && rng.nextInt(100) == 1)
      conditions.startRaining(10000 + (rng.nextInt(50000)));

    conditions.update(delta);
  }
  /**
   * Sets specified time as current time
   *
   * @param time Time in milliseconds
   */
  public void setTime(WorldTime time) {
    this.time = new WorldTime(time.inMillis());
  }

  public void setWeather(WeatherType type, int timerState, int duration) {
    conditions.setWeatherEffect(type, timerState, duration);
  }
  /**
   * Returns current game time
   *
   * @return String with game time
   */
  public WorldTime getTime() {
    return time;
  }
  /**
   * Returns current day phase name
   *
   * @return String with day phase name
   */
  public DayPhase getPhase() {
    return phase;
  }
  /**
   * Returns current weather
   *
   * @return Day weather
   */
  public Weather getWeather() {
    return conditions;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element dayE = doc.createElement("day");
    dayE.setAttribute("time", time.inMillis() + "");
    dayE.appendChild(conditions.getSave(doc));
    return dayE;
  }
}
