package model.interfaces;

import model.GrassField;

public interface MapChangeListener {
    void mapChanged(GrassField worldMap, String message);
}
