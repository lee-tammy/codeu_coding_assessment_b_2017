// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;
import java.util.ArrayList;

import com.google.codeu.mathlang.core.tokens.*;
import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.parsing.TokenReader;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {
  private String source;
  private ArrayList<Token> tokenArray;
  private int position;
  private int i;

  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
    this.source = source;
    this.position = 0;
    this.tokenArray = tokenArray(source);
  }

  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
    if(position < tokenArray.size()){
      Token token = tokenArray.get(position++);
      return token;
    }else{
      return null;
    }
  }

  private ArrayList<Token> tokenArray(String source){
    ArrayList<Token> tokenList = new ArrayList<Token>();
   
    while(i < source.length()){
      char c = source.charAt(i);
      if(isName(c)){ 
        String name = getName();
        tokenList.add(new NameToken(name));
      }else if(isString(c)){
        String string = getString();
        tokenList.add(new StringToken(string));
      }else if(isNumber(c)){
        double number = getNumber();
        tokenList.add(new NumberToken(number));
      }else if(isSymbol(c)){
        tokenList.add(new SymbolToken(c));
      }
      i++;
    }
    return tokenList;
  } 

   private String getName(){
    StringBuilder sb = new StringBuilder();
    sb.append(source.charAt(i));
    char next = source.charAt(i + 1);
    while(!Character.isWhitespace(next) && Character.isAlphabetic(next)){
      sb.append(next);
      next = source.charAt(++i + 1);
    }
    return sb.toString();
  }

  private String getString(){
    StringBuilder sb = new StringBuilder();
    char next = source.charAt(++i);
    while(next != '\"'){
      sb.append(next);
      next = source.charAt(++i);
    }
    return sb.toString();
  }

  private double getNumber(){
    StringBuilder sb = new StringBuilder(); 
    sb.append(source.charAt(i));
    char next = source.charAt(i + 1);
    while((Character.isDigit(next) || next == '.')){
      sb.append(next);
      next = source.charAt(++i + 1);
    }
    double number = Double.parseDouble(sb.toString());
    return number;
  } 

  private boolean isName(char c){
    return Character.isAlphabetic(c);
  }

  private boolean isNumber(char c){
    return Character.isDigit(c);
  }

  private boolean isString(char c){
    return c == '\"';
  }

  private boolean isSymbol(char c){
    if(c == '-' || c == '+' || c == '=' || c == ';'){
      return true;
    }
    return false;
  }
  
}
