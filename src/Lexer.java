//package src;

//A very generous thank you to the folks at:
//      https://stackoverflow.com/questions/13185727/reading-a-txt-file-using-scanner-class-in-java
//for teaching me how to read text from a file input, because I am not a perfect human being

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

//NOTE: Code is currently kind of incomplete, just messing around

public class Lexer {

    public static void main(String src) {

        ArrayList<Token> list = new ArrayList<>();
        File fileList = new File(src);

        int counter = 1; //Counts our token's position
        int programCounter = 1; //Counts the number of programs we've lexed through
        int lineCounter = 1;
        
     
        String stringolon = ""; //char to grab our current soon-to-be token (one at a time) from the file
        String string = ""; //string to grab the current whole string (not just one token), which will help us with our pointer

        boolean inAComment = false;
        boolean inAString = false;

        char symbolon = ' ';

        try {
        //Basic list stuff to handle our input and token dump
        Scanner tokenList = new Scanner(fileList);

            System.out.println();
            System.out.println("Lexing program " + programCounter + "...");
            while (tokenList.hasNextLine()) {
                //System.out.println("IOU one lexer");
                string = tokenList.nextLine();
                stringolon = ""; //dump this once you have the full thing set up
                lineCounter++;

                for (int i = 0; i < string.length(); i++) {
                    symbolon = ' ';
                    stringolon = stringolon + string.charAt(i);

                    //Works much better than combing our string for symbols
                    symbolon = string.charAt(i);
                    
                    //System.out.println(symbolon);
                    //System.out.println(stringolon);
                    //System.out.println(string);

                    //Switch statement to determine what we should do with our symbol tokens
                    //Vaguely follows the grammar order from the project 1 grammar.pdf
                    switch(symbolon) {
                        case '$':
                        System.out.println("DEBUG LEXER - EOP [ $ ] found at position (" + lineCounter + " : " + counter + ") - Program " + programCounter);
                            counter = 1;
                            programCounter++;
                            System.out.println("Lexing completed!");
                            if(tokenList.hasNextLine()) {
                                System.out.println();
                                System.out.println("Lexing program " + programCounter + "...");
                            }
                        break;
                        case '{':
                            /*list.add(new Token("OPEN_BLOCK", "{", lineCounter, counter, programCounter, counter));
                            System.out.println("DEBUG LEXER - OPEN_BLOCK [ { ] found at position (" + lineCounter + " : " + counter + ") - Program " +  programCounter );
                            counter++;*/
                            handleToken(list, "OPEN_BLOCK", "{", lineCounter, counter, programCounter, counter);
                        break;
                        case '}':
                            handleToken(list, "CLOSE_BLOCK", "}", lineCounter, counter, programCounter, counter);
                        break;
                        case '(':
                            handleToken(list, "OPEN_PAREN", "(", lineCounter, counter, programCounter, counter);
                        break;
                        case ')':
                            handleToken(list, "CLOSE_BLOCK", ")", lineCounter, counter, programCounter, counter);
                        break;
                        case '=':
                            handleToken(list, "ASSIGNOP", "=", lineCounter, counter, programCounter, counter);
                        break;
                        case '\'':
                            handleToken(list, "CHAR", "\'", lineCounter, counter, programCounter, counter);
                        break;
                        case '\"':
                            handleToken(list, "CHAR", "\"", lineCounter, counter, programCounter, counter);
                        break;
                        case '+':
                            handleToken(list, "INTOP", "+", lineCounter, counter, programCounter, counter);
                        break;
                        case ' ':
                            handleToken(list, "SPACE", " ", lineCounter, counter, programCounter, counter);
                        break;
                        case 'a': //test
                            System.out.println("DEBUG LEXER - CHAR [ a ] found at position (" + lineCounter + " : " + counter + ") - Program " +  programCounter );
                        break;
                        case 'b': //test
                            System.out.println("DEBUG LEXER - CHAR [ b ] found at position (" + lineCounter + " : " + counter + ") - Program " +  programCounter );
                        break;

                        /*Everything below this is purely for testing purposes right now
                        case "/*":
                            System.out.println("I'M GOIN' COMMENT MODE!!!!1!!");
                            inAComment = true;
                            
                            counter++;
                            //Pass to inAComment boolean at beginning of loop
                        break;
                        */
                    }
                    //New switch statement to handle our string inputs
                    switch(stringolon) {
                        case "string":
                            handleToken(list, "TYPE", "string", lineCounter, counter, programCounter, counter);
                        break;
                        case "int":
                            handleToken(list, "TYPE", "int", lineCounter, counter, programCounter, counter);
                        break;
                        case "print":
                            handleToken(list, "PRINTSTATEMENT", "print", lineCounter, counter, programCounter, counter);
                        break;
                        case "ID":
                            handleToken(list, "ID", "id", lineCounter, counter, programCounter, counter);
                        break;
                        case "while":
                            handleToken(list, "WHILESTATEMENT", "while", lineCounter, counter, programCounter, counter);
                        break;
                        case "if":
                            handleToken(list, "IFSTATEMENT", "if", lineCounter, counter, programCounter, counter);
                        break;
                        case "boolean":
                            handleToken(list, "TYPE", "bool", lineCounter, counter, programCounter, counter);
                        break;
                        case "true":
                            handleToken(list, "BOOLVAL", "true", lineCounter, counter, programCounter, counter);
                        break;
                        case "false":
                            handleToken(list, "BOOLVAL", "false", lineCounter, counter, programCounter, counter);
                        break;
                        case "==":
                            handleToken(list, "BOOLOP", "==", lineCounter, counter, programCounter, counter);
                        break;
                        case "!=":
                            handleToken(list, "BOOLOP", "!=", lineCounter, counter, programCounter, counter);
                        break;
                        default:
                        //Nothing here on purpose, we want to skip over white space
                    }
                }
            }
    tokenList.close(); 
    }
    
    //Not entirely sure I even need this but I"m too scared to take it out
    catch (Exception noFile) {
        noFile.printStackTrace();
    }
  }

    public static void handleToken(ArrayList<Token> list, String tokenType, String tokenName, int linePos, int countPos, int progPos, int counter) {
        //System.out.println("Use this to DEBUG");
        list.add(new Token(tokenType, tokenName, linePos, countPos, progPos));
        System.out.println("DEBUG LEXER - " + tokenType + " [ " + tokenName + " ]" + "found at position (" + linePos + " : " + countPos + ") - Program " + progPos);
        counter++;
    }
}