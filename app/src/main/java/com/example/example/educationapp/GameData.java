package com.example.example.educationapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

//This is where the data needed to run the game will be created and stored
class GameData implements Serializable{
    String[] elementNames = new String[]{"Sodium", "Hydrogen", "Helium", "Gold", "Iron", "Nickle", "Cobalt"};
    String[] elementSymbols = new String[]{"Na", "H", "He", "Ag", "Fe", "Ni", "Co"};
    ArrayList<String> elementNamesList;
    ArrayList<String> elementSymbolsList;
    int playerScore;
    long playerTime;
    long clockTime;
    boolean settingsChanged;

    GameData(){
        this.elementNamesList = new ArrayList<String>();
        Collections.addAll(elementNamesList, elementNames);
        this.elementSymbolsList = new ArrayList<String>();
        Collections.addAll(elementSymbolsList, elementSymbols);
        this.playerScore = 0;
        this.playerTime = 0;
        this.clockTime = 20000;
        this.settingsChanged = false;
    }
}

