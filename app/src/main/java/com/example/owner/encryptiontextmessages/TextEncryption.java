package com.example.owner.encryptiontextmessages;

/*
* Class that encrypts strings
* TESTING PURPOSES
* TEST INPUT = Test
* TEST OUTPUT = ¶▼òû
*
*/
public class TextEncryption
{
    //First Tier Encryption
    private String emulatorCompatable[][]=
            {
                    //Turns out emulators don't care much for ascii characters this version was added to support emulators and provide proof of concept
                    //without needing a phone on debugging mode
                    //UpperCase letters
                    {"A","B"},{"B","C"},{"C","D"},{"D","E"},{"E","F"},
                    {"F","G"},{"G","H"},{"H","I"},{"I","J"},{"J","K"},
                    {"K","L"},{"L","M"},{"M","N"},{"N","O"},{"O","P"},
                    {"P","Q"},{"Q","R"},{"R","S"},{"S","T"},{"T","U"},
                    {"U","V"},{"V","W"},{"W","X"},{"X","Y"},{"Y","Z"},
                    {"Z","A"},
                    //LowerCase Letters
                    {"a","z"},{"b","a"},{"c","b"},{"d","c"},{"e","d"},
                    {"f","e"},{"g","f"},{"h","g"},{"i","h"},{"j","i"},
                    {"k","j"},{"l","k"},{"m","l"},{"n","m"},{"o","n"},
                    {"p","o"},{"q","p"},{"r","q"},{"s","r"},{"t","s"},
                    {"u","t"},{"v","u"},{"w","v"},{"x","w"},{"y","x"},
                    {"z","y"},
                    //Numbers
                    {"1","2"},{"2","3"},{"3","4"},{"4","5"},{"5","6"},
                    {"6","7"},{"7","8"},{"8","9"},{"9","0"},{"0","1"},
                    //encrypted whitespace
                    {" ","_"}
            };
    private String levelOne[][] =
            {
                    //works on real phones only
                    //UpperCase letters
                    {"A","☺"},{"B","☻"},{"C","♥"},{"D","♦"},{"E","♣"},
                    {"F","♠"},{"G","•"},{"H","◘"},{"I","○"},{"J","◙"},
                    {"K","♂"},{"L","♀"},{"M","♪"},{"N","♫"},{"O","☼"},
                    {"P","►"},{"Q","◄"},{"R","↕"},{"S","‼"},{"T","¶"},
                    {"U","§"},{"V","▬"},{"W","↨"},{"X","↑"},{"Y","↓"},
                    {"Z","→"},
                    //LowerCase Letters
                    {"a","←"},{"b","∟"},{"c","↔"},{"d","▲"},{"e","▼"},
                    {"f","!"},{"g","#"},{"h","$"},{"i","%"},{"j","&"},
                    {"k","("},{"l",")"},{"m","*"},{"n","+"},{"o","æ"},
                    {"p","Æ"},{"q","ô"},{"r","ö"},{"s","ò"},{"t","û"},
                    {"u","ù"},{"v","ÿ"},{"w","¼"},{"x","├"},{"y","─"},
                    {"z","╟"},
                    //Numbers
                    {"1","╚"},{"2","╩"},{"3","Ü"},{"4","¢"},{"5","£"},
                    {"6","¥"},{"7","╝"},{"8","╛"},{"9","┐"},{"0","┴"},
                    //encrypted whitespace
                    {" ","τ"}
            };

    public String decrypt(String message){
        char test[] = message.toCharArray();//turns incoming message into char array
        String decryptedOutput = "";//initializes decrypted output
        //EMULATOR COMPATIBLE DECRYPTION***************************************************************************
        if(String.valueOf(test[0]).equals("Ü")){//checks for encryption key                                    //**
            for (int i = 1; i < message.length(); i++) {//used to individually parse each character            //**
                for (int x = 0; x < emulatorCompatable.length; x++) {//cross references character with keys    //**
                    if (String.valueOf(test[i]).equals(emulatorCompatable[x][1])) {//checks match              //**
                        decryptedOutput += emulatorCompatable[x][0];//modifies character to decrypted value    //**
                    }                                                                                          //**
                }                                                                                              //**
            }                                                                                                  //**
        return decryptedOutput;//returns decrypted message                                                     //**
        }                                                                                                      //**
        //*********************************************************************************************************
        //LEVEL ONE DECRYPTION*************************************************************************************
        else if(String.valueOf(test[0]).equals("Ñ")){//checks for encryption key                               //**
            for (int i = 1; i < message.length(); i++) {//used to individually parse each character            //**
                for (int x = 0; x < levelOne.length; x++) {//cross references character with keys              //**
                    if (String.valueOf(test[i]).equals(levelOne[x][1])) {//checks match                        //**
                        decryptedOutput += levelOne[x][0];//modifies character to decrypted value              //**
                    }                                                                                          //**
                }                                                                                              //**
            }                                                                                                  //**
            return decryptedOutput;//returns decrypted message                                                 //**
        //*********************************************************************************************************
        //UNENCRYPTED
        }else{
            return message;//if key is not present then message displays as normal
        }
    }
    public String encrypt(String message, int version) {

        char test[] = message.toCharArray();//turns incoming message into char array
        String finalMessage = "";//initializes encryption output
        //EMULATOR COMPATIBLE ENCRYPTION***************************************************************************
        if (version == 0){                                                                                     //**
            finalMessage = "Ü";//adds key                                                                      //**
            for (int i = 0; i < message.length(); i++) {//used to individually parse each character            //**
                for (int x = 0; x < emulatorCompatable.length; x++) {//cross references character with keys    //**
                    if (String.valueOf(test[i]).equals(emulatorCompatable[x][0])) {//checks match              //**
                        finalMessage += emulatorCompatable[x][1];//modifies character to encrypted value       //**
                    }                                                                                          //**
                }                                                                                              //**
            }                                                                                                  //**
        }                                                                                                      //**
        //*********************************************************************************************************
        //LEVEL ONE ENCRYPTION*************************************************************************************
        if (version == 1){                                                                                     //**
            finalMessage = "Ñ";//adds key                                                                      //**
            for (int i = 0; i < message.length(); i++) {//used to individually parse each character            //**
                for (int x = 0; x < levelOne.length; x++) {//cross references character with keys              //**
                    if (String.valueOf(test[i]).equals(levelOne[x][0])) {//checks match                        //**
                        finalMessage += levelOne[x][1];//modifies character to encrypted value                 //**
                    }                                                                                          //**
                }                                                                                              //**
            }                                                                                                  //**
        }                                                                                                      //**
        //*********************************************************************************************************
        return finalMessage;//returns encrypted string
    }
}

