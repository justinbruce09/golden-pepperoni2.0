package com.games.pizzaquest.objects;

import java.util.HashMap;

public class NonPlayerCharacter implements PlayerInterface{
 private String name="";
 Boolean isQuestActive= false;

 private HashMap<String, String> dialogue = new HashMap<String,String>();

 public NonPlayerCharacter(String name, String dialog){
  setName(name);
  setDialogue(dialog);

 }


 public void setDialogue(String quest){
  dialogue.put("quest", quest);

 }

 @Override
 public void setName(String name) {
  this.name=name;
 }

 @Override
 public String getName() {
  return name;
 }

 @Override
 public String toString() {
  return "NonPlayerCharacter{" +
          "name='" + name + '\'' +
          ", isQuestActive=" + isQuestActive +
          ", permanentLocation=" +
          '}';
 }
}
