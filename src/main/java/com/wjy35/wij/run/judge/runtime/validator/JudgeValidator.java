package com.wjy35.wij.run.judge.runtime.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class JudgeValidator {
    public static boolean isAccepted(String actual,String expected) {
        if(actual.isEmpty()) return false;

        try {
            BufferedReader expectedBr = new BufferedReader(new StringReader(expected));
            BufferedReader actualBr = new BufferedReader(new StringReader(actual));
            String expectedLine = null;
            String actualLine = null;

            while(((expectedLine = expectedBr.readLine()) != null) & ((actualLine = actualBr.readLine()) != null)){
                if(!expectedLine.stripTrailing().equals(actualLine.stripTrailing())) return  false;
            }

            while(expectedLine!=null && expectedLine.isEmpty()) expectedLine = expectedBr.readLine();
            while(actualLine!=null && actualLine.isEmpty()) actualLine = actualBr.readLine();

            if(expectedLine!=null) return false;
            if(actualLine!=null) return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
